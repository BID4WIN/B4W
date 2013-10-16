package com.bid4win.commons.service;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.caching.Bid4WinEntityCache;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.security.ObjectProtector;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean;
import com.bid4win.commons.manager.Bid4WinManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.UtilSecurity;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.service.connection.ConnectionAbstractService_;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Cette classe défini la base de tous les services du projet. Ces services devront
 * directement gérer les transactions ainsi que la validation des habilitations
 * liées à leurs actions. Ils se reposeront sur des managers pour assurer les gestions
 * métier attenantes à ces mêmes actions. Ils peuvent implémenter un cache applicatif
 * qui ne rechargera réellement les entités demandées que si leur version a changé.
 * Attention à n'utiliser ce mécanisme que pour des actions de remontée de données
 * (non liées à des comptes utilisateurs si la validation d'habilitations est nécessaire)
 * et qui ne font rien d'autres afin d'être sûr que les entités chargées sont protégées
 * contre de potentielles modifications ultérieures<BR>
 * <BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Définition de type de compte utilisateur utilisé par le projet<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinService_<SESSION extends SessionDataAbstract<ACCOUNT, ?>,
                                      ACCOUNT extends AccountAbstract<ACCOUNT>,
                                      SERVICE extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinSelfReferencedBean<SERVICE>
{
  /** Cache applicatif utilisé par le service pour stocker les entités chargées */
  private Bid4WinEntityCache cache = new Bid4WinEntityCache();
  /** Service de gestion des connexions */
  private ConnectionAbstractService_<?, ?, ?, SESSION, ACCOUNT, ?> connectionService;

  /////////////////////////////////////////////////////////////////////////////
  ////////////////// GESTION DU CACHE APPLICATIF DU SERVICE ///////////////////
  /////////////////////////////////////////////////////////////////////////////
  // Le cache applicatif est un cache interne déconnecté des transactions. Les
  // entités y sont stockées telles quelles et sont protégées contre toute modification
  // ultérieure. Il faut faire attention à n'appeler cette fonctionnalité que pour
  // la récupération d'entités non encore chargées, pour de simples requêtes en
  // lecture !!!
  /**
   * Cette méthode permet de récupérer et de charger l'entité choisie dans le cache
   * en s'assurant de sa validité. Pour cela, un contrôle de version est effectué
   * grâce au manager et le cas échéant, l'entité cachée est retirée du cache et
   * recherchée directement via le manager avant d'être à son tour ajoutée au cache.
   * L'entités retournée sera protégée contre les modifications et toute tentative
   * se soldera pas une exception de protection. Les relations définies par l'entité
   * seront chargées automatiquement
   * @param <ENTITY> Definition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant de l'entité à rechercher
   * @param entityClass Classe de l'entité à rechercher
   * @param id Identifiant de l'entité à rechercher
   * @param roles Roles potentiels à vérifier pour l'accès à l'entité spécifiée
   * @return L'entité trouvée grâce à son identifiant
   * @throws PersistenceException Si un problème intervient lors de la tentative
   * de validation de la version de l'entité cachée ou lors de sa recherche à travers
   * le manager
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
   * l'identifiant en argument
   * @throws SessionException TODO A COMMENTER
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * alors que des rôles doivent être vérifiés
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            ENTITY load(Class<ENTITY> entityClass, ID id, Role ... roles)
            throws PersistenceException, NotFoundEntityException, SessionException,
                   AuthenticationException, AuthorizationException
  {
    return this.loadAll(entityClass, new Bid4WinSet<ID>(id), roles).get(id);
  }
  /**
   * Cette méthode permet de récupérer et de charger toutes les entités choisies
   * dans le cache en s'assurant de leur validité. Pour cela, un contrôle de version
   * est effectué grâce au manager et le cas échéant, les entités cachées sont retirées
   * du cache et recherchées directement via le manager avant d'être à leur tour
   * ajoutées au cache. Les entités retournées sont protégées contre les modifications
   * et toute tentative se soldera pas une exception de protection. Les relations
   * définies par l'entité seront chargées automatiquement
   * @param <ENTITY> Definition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Set d'identifiants des entités à rechercher
   * @param roles Roles potentiels à vérifier pour l'accès aux entités spécifiées
   * @return Toutes les entités trouvées grâce à leur identifiant
   * @throws PersistenceException Si un problème intervient lors de la tentative
   * de validation de la version des entités cachées ou lors de leur recherche à
   * travers le manager
   * @throws NotFoundEntityException Si une des entités n'a pu être récupérée avec
   * l'un des identifiants en argument
   * @throws SessionException TODO A COMMENTER
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * alors que des rôles doivent être vérifiés
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            Bid4WinMap<ID, ENTITY> loadAll(Class<ENTITY> entityClass,
                                           Bid4WinSet<ID> idSet,
                                           Role ... roles)
            throws PersistenceException, NotFoundEntityException, SessionException,
                   AuthenticationException, AuthorizationException
  {
    Bid4WinMap<ID, ENTITY> result = this.load(entityClass, idSet, roles);
    if(result.size() != idSet.size())
    {
      throw new NotFoundEntityException(entityClass, this.getMissingId(idSet, result));
    }
    return result;
  }
  /**
   * Cette méthode permet de récupérer et de charger les entités choisies dans le
   * cache en s'assurant de leur validité. Pour cela, un contrôle de version est
   * effectué grâce au manager et le cas échéant, les entités cachées sont retirées
   * du cache et recherchées directement via le manager avant d'être à leur tour
   * ajoutées au cache. Les entités retournées sont protégées contre les modifications
   * et toute tentative se soldera pas une exception de protection. Les relations
   * définies par l'entité seront chargées automatiquement
   * @param <ENTITY> Definition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Set d'identifiants des entités à rechercher
   * @param roles Roles potentiels à vérifier pour l'accès aux entités spécifiées
   * @return Les entités trouvées grâce à leur identifiant
   * @throws PersistenceException Si un problème intervient lors de la tentative
   * de validation de la version des entités cachées ou lors de leur recherche à
   * travers le manager
   * @throws SessionException TODO A COMMENTER
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * alors que des rôles doivent être vérifiés
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            Bid4WinMap<ID, ENTITY> load(Class<ENTITY> entityClass,
                                        Bid4WinSet<ID> idSet,
                                        Role ... roles)
            throws PersistenceException, SessionException,
                   AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté si nécessaire
    if(roles.length != 0)
    {
      this.checkRole(roles);
    }
    // Démarre la protection des objets avant toute potentielle remontée d'entité
    // de la base de données
    String protectionId = ObjectProtector.startProtection();
    try
    {
      // Récupère les entités potentiellement cachées en vérifiant leur version
      Bid4WinMap<ID, ENTITY> result = this.getFromCache(entityClass, idSet);
      // Recherche les entités non présentes dans le cache
      if(result.size() != idSet.size())
      {
        // Charge les entités manquantes grâce au manager
        Bid4WinSet<ID> missingIdSet = this.getMissingId(idSet, result);
        Bid4WinMap<ID, ENTITY> map = this.getFromManager(entityClass, missingIdSet);
        result.putAll(this.cache(entityClass, map));
      }
      return result;
    }
    finally
    {
      try
      {
        // Arrête la protection des objets
        ObjectProtector.stopProtection(protectionId);
      }
      catch(ProtectionException ex)
      {
        throw new PersistenceException(ex);
      }
    }
  }

  /**
   * Cette méthode permet de récupérer les entités choisies dans le cache en s'
   * assurant de leur validité. Pour cela, un contrôle de version est effectué
   * grâce au manager et le cas échéant, l'entité cachée est retirée du cache.
   * Les entités cachées sont protégées contre les modifications et toute tentative
   * se soldera pas une exception de protection
   * @param <ENTITY> Definition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Set d'identifiants des entités à rechercher
   * @return Les entités trouvées dans le cache grâce à leur identifiant après
   * validation de leur version
   * @throws PersistenceException Si un problème intervient lors de la tentative
   * de validation de la version des entités cachées
   */
  private <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
          Bid4WinMap<ID, ENTITY> getFromCache(Class<ENTITY> entityClass,
                                              Bid4WinSet<ID> idSet)
          throws PersistenceException
  {
    // Récupère les entités en cache en fonction de leur identifiant
    Bid4WinMap<ID, ENTITY> cachedMap = this.getCache().get(entityClass, idSet);
    // Compare leur version avec les versions actuellement disponibles
    if(cachedMap.size() != 0)
    {
      Bid4WinMap<ID, Integer> versionMap = this.getVersion(entityClass,
                                                           cachedMap.keySet());
      for(Entry<ID, Integer> version : versionMap.entrySet())
      {
        ENTITY cached = cachedMap.get(version.getKey());
        // Une version plus récente existe, il faut annuler la version cachée
        if(cached.getVersion() != version.getValue())
        {
          this.uncache(entityClass, cached);
          cachedMap.remove(version.getKey());
        }
      }
    }
    return cachedMap;
  }
  /**
   * Cette méthode permet de récupérer les entités choisies en base de données (ou
   * dans le cache de second-niveau) grâce au manager
   * @param <ENTITY> Definition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Set d'identifiants des entités à rechercher
   * @return Les entités trouvées dans par le manager grâce à leur identifiant
   * @throws PersistenceException Si un problème intervient lors de la recherche
   * des entités à travers le manager
   */
  private <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
          Bid4WinMap<ID, ENTITY> getFromManager(Class<ENTITY> entityClass,
                                                Bid4WinSet<ID> idSet)
            throws PersistenceException
  {
    return this.getManager().load(entityClass, idSet);
  }
  /**
   * Cette méthode permet de récupérer la version des entités choisies en base de
   * données (ou dans le cache de second-niveau) grâce au manager
   * @param <ENTITY> Définition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Identifiants des entités à rechercher
   * @return La map de version des entités recherchées (si une entité n'est pas
   * trouvée, sa version sera négative)
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  private <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
          Bid4WinMap<ID, Integer> getVersion(Class<ENTITY> entityClass,
                                             Bid4WinSet<ID> idSet)
          throws PersistenceException
  {
    return this.getManager().getVersion(entityClass, idSet);
  }

  /**
   * Cette méthode permet d'ajouter une entité au cache. Celle-ci ne sera réellement
   * ajoutée à la place de toute entité potentiellement déjà présente avec le même
   * identifiant que si sa version lui est supérieure
   * @param <ENTITY> Definition du type d'entité à cacher
   * @param <ID> Définition du type d'identifiant de l'entité à cacher
   * @param entityClass Classe de l'entités à cacher
   * @param entity Entité à cacher
   * @return L'entité cachée, c'est à dire l'entité en argument s'il n'existait
   * pas une référence au moins aussi récente dans le cache, ou l'entité déjà présente
   * dans le cache le cas échéant
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas à
   * celle de l'entité
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            ENTITY cache(Class<ENTITY> entityClass, ENTITY entity)
            throws IllegalArgumentException
  {
    return this.getCache().cache(entityClass, entity);
  }
  /**
   * Cette méthode permet d'ajouter des entités au cache. Celles-ci ne seront réellement
   * ajoutées à la place de toute entité potentiellement déjà présente avec le même
   * identifiant que si leur version leur est supérieure
   * @param <ENTITY> Definition du type d'entité à cacher
   * @param <ID> Définition du type d'identifiant des entités à cacher
   * @param entityClass Classe des entités à cacher
   * @param entityMap Entités à cacher
   * @return Les entité cachées, c'est à dire les entités en argument s'il n'existait
   * pas une référence au moins aussi récente dans le cache, ou les entités déjà
   * présentes dans le cache le cas échéant
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas à
   * celles des entités
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            Bid4WinMap<ID, ENTITY> cache(Class<ENTITY> entityClass, Bid4WinMap<ID, ENTITY> entityMap)
            throws IllegalArgumentException
  {
    return this.getCache().cache(entityClass, entityMap);
  }
  /**
   * Cette méthode permet de retirer une entité du cache. Celle-ci ne sera réellement
   * retirée que si l'entité potentiellement déjà présente a le même identifiant
   * et la même version
   * @param <ENTITY> Definition du type d'entité à retirer du cache
   * @param <ID> Définition du type d'identifiant de l'entité à retirer du cache
   * @param entityClass Classe de l'entité à retirer du cache
   * @param entity Entité à retirer du cache
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas à
   * celle de l'entité
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            void uncache(Class<ENTITY> entityClass, ENTITY entity)
  {
    this.getCache().uncache(entityClass, entity);
  }
  /**
   * Cette méthode permet de vider le cache de toutes les entités contenues
   */
  protected void clearCache()
  {
    this.getCache().clear();
  }

  /**
   * Cette méthode permet de rechercher parmi les identifiants donnés ceux qui ne
   * sont pas référencés dans la map d'entités en argument
   * @param idSet Identifiants à rechercher dans la map d'entités en argument
   * @param entityMap Map d'entités parmi lesquelles rechercher les identifiants
   * manquants
   * @return Les identifiants non référencés dans la map d'entités en argument
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            Bid4WinSet<ID> getMissingId(Bid4WinSet<ID> idSet, Bid4WinMap<ID, ENTITY> entityMap)
  {
    // Récupère les identifiants des entités non présentes dans le cache
    Bid4WinSet<ID> missingIdSet = new Bid4WinSet<ID>(entityMap.size() - idSet.size());
    for(ID id : idSet)
    {
      // L'entité n'a pas pu être trouvée dans le cache
      if(!entityMap.containsKey(id))
      {
        missingIdSet.add(id);
      }
    }
    return missingIdSet;
  }

  /////////////////////////////////////////////////////////////////////////////
  ///////////////// GESTION DES HABILITATIONS LIEES AU SERVICE ////////////////
  /////////////////////////////////////////////////////////////////////////////
  // La gestion des habilitations de faire au travers de la validation des rôles
  // du compte utilisateur connecté sur la session courante. Pour se faire, la
  // connexion correspondante est d'abord recherchée et si elle est trouvée, les
  // habilitations du compte lié sont validées
  /**
   * Cette méthode permet de récupérer l'identifiant du compte utilisateur connecté
   * sur la session courante
   * @return L'identifiant du compte utilisateur connecté sur la session courante
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est considéré
   * connecté
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour être considéré connecté
   */
  public String getConnectedAccountId()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.getConnectedAccount().getId();
  }
  /**
   * Cette méthode permet de récupérer le compte utilisateur connecté sur la session
   * courante
   * @return Le compte utilisateur connecté sur la session courante
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est considéré
   * connecté
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour être considéré connecté
   */
  public ACCOUNT getConnectedAccount()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.getConnectionService().getConnectedAccount();
  }
  /**
   * Cette méthode permet de récupérer le rôle du compte utilisateur connecté
   * @return Le rôle du compte utilisateur connecté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour être considéré connecté
   */
  public Role getRole() throws PersistenceException, SessionException,
                               AuthenticationException, AuthorizationException
  {
    return this.getConnectedAccount().getCredential().getRole();
  }
  /**
   * Cette méthode permet de vérifier les rôles du compte utilisateur connecté
   * @param roles Rôles que doit avoir le compte utilisateur connecté
   * @return Le rôle de l'utilisateur connecté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le compte utilisateur connecté n'a pas les
   * rôles en argument
   */
  public Role checkRole(Role ... roles)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return UtilSecurity.checkRole(this.getRole(), roles);
  }
  /**
   * Cette méthode permet de vérifier les rôles du compte utilisateur connecté
   * @param roles Rôles que ne doit pas avoir le compte utilisateur connecté
   * @return Le rôle de l'utilisateur connecté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le compte utilisateur connecté a l'un des
   * rôles en argument
   */
  public Role checkNotRole(Role ... roles)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return UtilSecurity.checkNotRole(this.getRole(), roles);
  }
  /**
   * Cette méthode permet de vérifier que le compte utilisateur connecté correspond
   * au compte utilisateur dont l'identifiant est passé en argument ou qu'il a l'
   * un des rôles précisés
   * @param accountId Identifiant attendu pour le compte utilisateur connecté
   * @param alternateRoles Rôles possible pour le compte utilisateur connecté s'il
   * ne correspond pas à l'identifiant en argument
   * @return Le rôle de l'utilisateur connecté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le compte utilisateur connecté n'a pas l'
   * identifiant attendu ni l'un des rôles en argument
   */
  public Role checkAccount(String accountId, Role ... alternateRoles)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    ACCOUNT account = this.getConnectedAccount();
    if(!account.getId().equals(accountId))
    {
      UtilSecurity.checkOneRole(account, alternateRoles);
    }
    return account.getCredential().getRole();
  }
  /**
   * Cette méthode permet de vérifier le rôle d'administrateur du service du compte
   * utilisateur connecté
   * @return Le rôle de l'utilisateur connecté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le compte utilisateur connecté n'a pas les
   * habilitations suffisantes pour administrer le service
   */
  public Role checkAdminRole()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.checkRole(this.getAdminRole());
  }
  /**
   * Cette méthode permet de vérifier le rôle d'utilisateur du le service du compte
   * utilisateur connecté
   * @return Le rôle de l'utilisateur connecté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le compte utilisateur connecté n'a pas les
   * habilitations suffisantes pour utiliser le service
   */
  public Role checkUserRole()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.checkRole(this.getUserRole());
  }
  /**
   * Cette méthode doit définir le rôle d'administration du service
   * @return Le rôle d'administration du service
   */
  public abstract Role getAdminRole();
  /**
   * Cette méthode défini le rôle d'utilisation du service
   * @return Le rôle d'utilisation du service
   */
  public Role getUserRole()
  {
    return Role.USER;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Getter du cache utilisé par le service pour stocker les entités chargées
   * @return Le cache utilisé par le service pour stocker les entités chargées
   */
  private Bid4WinEntityCache getCache()
  {
    return this.cache;
  }
  /**
   * Getter du service de gestion des connexions
   * @return Le service de gestion des connexions
   */
  protected ConnectionAbstractService_<?, ?, ?, SESSION, ACCOUNT, ?> getConnectionService()
  {
    return this.connectionService;
  }
  /**
   * Setter du service de gestion des connexions. L'injection se fait grâce à un
   * setter pour pouvoir la débrayer dans le cas de dépendance circulaire
   * @param connectionService Référence du service de gestion des connexions à
   * positionner
   * @throws ModelArgumentException Si un service est déjà positionné
   */
  @Autowired
  @Qualifier("ConnectionService")
  protected void setConnectionService(ConnectionAbstractService_<?, ?, ?, SESSION, ACCOUNT, ?> connectionService)
            throws ModelArgumentException
  {
    UtilObject.checkNull("connectionService", this.getConnectionService());
    this.connectionService = connectionService;
  }

  /**
   * Getter de la référence du manager des entités gérées par le service
   * @return La référence du manager des entités gérées par le service
   */
  protected abstract Bid4WinManager_<ACCOUNT, ?> getManager();
}
