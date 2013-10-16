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
 * Cette classe d�fini la base de tous les services du projet. Ces services devront
 * directement g�rer les transactions ainsi que la validation des habilitations
 * li�es � leurs actions. Ils se reposeront sur des managers pour assurer les gestions
 * m�tier attenantes � ces m�mes actions. Ils peuvent impl�menter un cache applicatif
 * qui ne rechargera r�ellement les entit�s demand�es que si leur version a chang�.
 * Attention � n'utiliser ce m�canisme que pour des actions de remont�e de donn�es
 * (non li�es � des comptes utilisateurs si la validation d'habilitations est n�cessaire)
 * et qui ne font rien d'autres afin d'�tre s�r que les entit�s charg�es sont prot�g�es
 * contre de potentielles modifications ult�rieures<BR>
 * <BR>
 * @param <SESSION> D�finition du type de conteneur de donn�es d'une session utilis�
 * par le projet<BR>
 * @param <ACCOUNT> D�finition de type de compte utilisateur utilis� par le projet<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinService_<SESSION extends SessionDataAbstract<ACCOUNT, ?>,
                                      ACCOUNT extends AccountAbstract<ACCOUNT>,
                                      SERVICE extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinSelfReferencedBean<SERVICE>
{
  /** Cache applicatif utilis� par le service pour stocker les entit�s charg�es */
  private Bid4WinEntityCache cache = new Bid4WinEntityCache();
  /** Service de gestion des connexions */
  private ConnectionAbstractService_<?, ?, ?, SESSION, ACCOUNT, ?> connectionService;

  /////////////////////////////////////////////////////////////////////////////
  ////////////////// GESTION DU CACHE APPLICATIF DU SERVICE ///////////////////
  /////////////////////////////////////////////////////////////////////////////
  // Le cache applicatif est un cache interne d�connect� des transactions. Les
  // entit�s y sont stock�es telles quelles et sont prot�g�es contre toute modification
  // ult�rieure. Il faut faire attention � n'appeler cette fonctionnalit� que pour
  // la r�cup�ration d'entit�s non encore charg�es, pour de simples requ�tes en
  // lecture !!!
  /**
   * Cette m�thode permet de r�cup�rer et de charger l'entit� choisie dans le cache
   * en s'assurant de sa validit�. Pour cela, un contr�le de version est effectu�
   * gr�ce au manager et le cas �ch�ant, l'entit� cach�e est retir�e du cache et
   * recherch�e directement via le manager avant d'�tre � son tour ajout�e au cache.
   * L'entit�s retourn�e sera prot�g�e contre les modifications et toute tentative
   * se soldera pas une exception de protection. Les relations d�finies par l'entit�
   * seront charg�es automatiquement
   * @param <ENTITY> Definition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant de l'entit� � rechercher
   * @param entityClass Classe de l'entit� � rechercher
   * @param id Identifiant de l'entit� � rechercher
   * @param roles Roles potentiels � v�rifier pour l'acc�s � l'entit� sp�cifi�e
   * @return L'entit� trouv�e gr�ce � son identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la tentative
   * de validation de la version de l'entit� cach�e ou lors de sa recherche � travers
   * le manager
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws SessionException TODO A COMMENTER
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * alors que des r�les doivent �tre v�rifi�s
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            ENTITY load(Class<ENTITY> entityClass, ID id, Role ... roles)
            throws PersistenceException, NotFoundEntityException, SessionException,
                   AuthenticationException, AuthorizationException
  {
    return this.loadAll(entityClass, new Bid4WinSet<ID>(id), roles).get(id);
  }
  /**
   * Cette m�thode permet de r�cup�rer et de charger toutes les entit�s choisies
   * dans le cache en s'assurant de leur validit�. Pour cela, un contr�le de version
   * est effectu� gr�ce au manager et le cas �ch�ant, les entit�s cach�es sont retir�es
   * du cache et recherch�es directement via le manager avant d'�tre � leur tour
   * ajout�es au cache. Les entit�s retourn�es sont prot�g�es contre les modifications
   * et toute tentative se soldera pas une exception de protection. Les relations
   * d�finies par l'entit� seront charg�es automatiquement
   * @param <ENTITY> Definition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Set d'identifiants des entit�s � rechercher
   * @param roles Roles potentiels � v�rifier pour l'acc�s aux entit�s sp�cifi�es
   * @return Toutes les entit�s trouv�es gr�ce � leur identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la tentative
   * de validation de la version des entit�s cach�es ou lors de leur recherche �
   * travers le manager
   * @throws NotFoundEntityException Si une des entit�s n'a pu �tre r�cup�r�e avec
   * l'un des identifiants en argument
   * @throws SessionException TODO A COMMENTER
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * alors que des r�les doivent �tre v�rifi�s
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
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
   * Cette m�thode permet de r�cup�rer et de charger les entit�s choisies dans le
   * cache en s'assurant de leur validit�. Pour cela, un contr�le de version est
   * effectu� gr�ce au manager et le cas �ch�ant, les entit�s cach�es sont retir�es
   * du cache et recherch�es directement via le manager avant d'�tre � leur tour
   * ajout�es au cache. Les entit�s retourn�es sont prot�g�es contre les modifications
   * et toute tentative se soldera pas une exception de protection. Les relations
   * d�finies par l'entit� seront charg�es automatiquement
   * @param <ENTITY> Definition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Set d'identifiants des entit�s � rechercher
   * @param roles Roles potentiels � v�rifier pour l'acc�s aux entit�s sp�cifi�es
   * @return Les entit�s trouv�es gr�ce � leur identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la tentative
   * de validation de la version des entit�s cach�es ou lors de leur recherche �
   * travers le manager
   * @throws SessionException TODO A COMMENTER
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * alors que des r�les doivent �tre v�rifi�s
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            Bid4WinMap<ID, ENTITY> load(Class<ENTITY> entityClass,
                                        Bid4WinSet<ID> idSet,
                                        Role ... roles)
            throws PersistenceException, SessionException,
                   AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect� si n�cessaire
    if(roles.length != 0)
    {
      this.checkRole(roles);
    }
    // D�marre la protection des objets avant toute potentielle remont�e d'entit�
    // de la base de donn�es
    String protectionId = ObjectProtector.startProtection();
    try
    {
      // R�cup�re les entit�s potentiellement cach�es en v�rifiant leur version
      Bid4WinMap<ID, ENTITY> result = this.getFromCache(entityClass, idSet);
      // Recherche les entit�s non pr�sentes dans le cache
      if(result.size() != idSet.size())
      {
        // Charge les entit�s manquantes gr�ce au manager
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
        // Arr�te la protection des objets
        ObjectProtector.stopProtection(protectionId);
      }
      catch(ProtectionException ex)
      {
        throw new PersistenceException(ex);
      }
    }
  }

  /**
   * Cette m�thode permet de r�cup�rer les entit�s choisies dans le cache en s'
   * assurant de leur validit�. Pour cela, un contr�le de version est effectu�
   * gr�ce au manager et le cas �ch�ant, l'entit� cach�e est retir�e du cache.
   * Les entit�s cach�es sont prot�g�es contre les modifications et toute tentative
   * se soldera pas une exception de protection
   * @param <ENTITY> Definition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Set d'identifiants des entit�s � rechercher
   * @return Les entit�s trouv�es dans le cache gr�ce � leur identifiant apr�s
   * validation de leur version
   * @throws PersistenceException Si un probl�me intervient lors de la tentative
   * de validation de la version des entit�s cach�es
   */
  private <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
          Bid4WinMap<ID, ENTITY> getFromCache(Class<ENTITY> entityClass,
                                              Bid4WinSet<ID> idSet)
          throws PersistenceException
  {
    // R�cup�re les entit�s en cache en fonction de leur identifiant
    Bid4WinMap<ID, ENTITY> cachedMap = this.getCache().get(entityClass, idSet);
    // Compare leur version avec les versions actuellement disponibles
    if(cachedMap.size() != 0)
    {
      Bid4WinMap<ID, Integer> versionMap = this.getVersion(entityClass,
                                                           cachedMap.keySet());
      for(Entry<ID, Integer> version : versionMap.entrySet())
      {
        ENTITY cached = cachedMap.get(version.getKey());
        // Une version plus r�cente existe, il faut annuler la version cach�e
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
   * Cette m�thode permet de r�cup�rer les entit�s choisies en base de donn�es (ou
   * dans le cache de second-niveau) gr�ce au manager
   * @param <ENTITY> Definition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Set d'identifiants des entit�s � rechercher
   * @return Les entit�s trouv�es dans par le manager gr�ce � leur identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la recherche
   * des entit�s � travers le manager
   */
  private <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
          Bid4WinMap<ID, ENTITY> getFromManager(Class<ENTITY> entityClass,
                                                Bid4WinSet<ID> idSet)
            throws PersistenceException
  {
    return this.getManager().load(entityClass, idSet);
  }
  /**
   * Cette m�thode permet de r�cup�rer la version des entit�s choisies en base de
   * donn�es (ou dans le cache de second-niveau) gr�ce au manager
   * @param <ENTITY> D�finition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Identifiants des entit�s � rechercher
   * @return La map de version des entit�s recherch�es (si une entit� n'est pas
   * trouv�e, sa version sera n�gative)
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode permet d'ajouter une entit� au cache. Celle-ci ne sera r�ellement
   * ajout�e � la place de toute entit� potentiellement d�j� pr�sente avec le m�me
   * identifiant que si sa version lui est sup�rieure
   * @param <ENTITY> Definition du type d'entit� � cacher
   * @param <ID> D�finition du type d'identifiant de l'entit� � cacher
   * @param entityClass Classe de l'entit�s � cacher
   * @param entity Entit� � cacher
   * @return L'entit� cach�e, c'est � dire l'entit� en argument s'il n'existait
   * pas une r�f�rence au moins aussi r�cente dans le cache, ou l'entit� d�j� pr�sente
   * dans le cache le cas �ch�ant
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas �
   * celle de l'entit�
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            ENTITY cache(Class<ENTITY> entityClass, ENTITY entity)
            throws IllegalArgumentException
  {
    return this.getCache().cache(entityClass, entity);
  }
  /**
   * Cette m�thode permet d'ajouter des entit�s au cache. Celles-ci ne seront r�ellement
   * ajout�es � la place de toute entit� potentiellement d�j� pr�sente avec le m�me
   * identifiant que si leur version leur est sup�rieure
   * @param <ENTITY> Definition du type d'entit� � cacher
   * @param <ID> D�finition du type d'identifiant des entit�s � cacher
   * @param entityClass Classe des entit�s � cacher
   * @param entityMap Entit�s � cacher
   * @return Les entit� cach�es, c'est � dire les entit�s en argument s'il n'existait
   * pas une r�f�rence au moins aussi r�cente dans le cache, ou les entit�s d�j�
   * pr�sentes dans le cache le cas �ch�ant
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas �
   * celles des entit�s
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            Bid4WinMap<ID, ENTITY> cache(Class<ENTITY> entityClass, Bid4WinMap<ID, ENTITY> entityMap)
            throws IllegalArgumentException
  {
    return this.getCache().cache(entityClass, entityMap);
  }
  /**
   * Cette m�thode permet de retirer une entit� du cache. Celle-ci ne sera r�ellement
   * retir�e que si l'entit� potentiellement d�j� pr�sente a le m�me identifiant
   * et la m�me version
   * @param <ENTITY> Definition du type d'entit� � retirer du cache
   * @param <ID> D�finition du type d'identifiant de l'entit� � retirer du cache
   * @param entityClass Classe de l'entit� � retirer du cache
   * @param entity Entit� � retirer du cache
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas �
   * celle de l'entit�
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            void uncache(Class<ENTITY> entityClass, ENTITY entity)
  {
    this.getCache().uncache(entityClass, entity);
  }
  /**
   * Cette m�thode permet de vider le cache de toutes les entit�s contenues
   */
  protected void clearCache()
  {
    this.getCache().clear();
  }

  /**
   * Cette m�thode permet de rechercher parmi les identifiants donn�s ceux qui ne
   * sont pas r�f�renc�s dans la map d'entit�s en argument
   * @param idSet Identifiants � rechercher dans la map d'entit�s en argument
   * @param entityMap Map d'entit�s parmi lesquelles rechercher les identifiants
   * manquants
   * @return Les identifiants non r�f�renc�s dans la map d'entit�s en argument
   */
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            Bid4WinSet<ID> getMissingId(Bid4WinSet<ID> idSet, Bid4WinMap<ID, ENTITY> entityMap)
  {
    // R�cup�re les identifiants des entit�s non pr�sentes dans le cache
    Bid4WinSet<ID> missingIdSet = new Bid4WinSet<ID>(entityMap.size() - idSet.size());
    for(ID id : idSet)
    {
      // L'entit� n'a pas pu �tre trouv�e dans le cache
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
  // La gestion des habilitations de faire au travers de la validation des r�les
  // du compte utilisateur connect� sur la session courante. Pour se faire, la
  // connexion correspondante est d'abord recherch�e et si elle est trouv�e, les
  // habilitations du compte li� sont valid�es
  /**
   * Cette m�thode permet de r�cup�rer l'identifiant du compte utilisateur connect�
   * sur la session courante
   * @return L'identifiant du compte utilisateur connect� sur la session courante
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est consid�r�
   * connect�
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour �tre consid�r� connect�
   */
  public String getConnectedAccountId()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.getConnectedAccount().getId();
  }
  /**
   * Cette m�thode permet de r�cup�rer le compte utilisateur connect� sur la session
   * courante
   * @return Le compte utilisateur connect� sur la session courante
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est consid�r�
   * connect�
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour �tre consid�r� connect�
   */
  public ACCOUNT getConnectedAccount()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.getConnectionService().getConnectedAccount();
  }
  /**
   * Cette m�thode permet de r�cup�rer le r�le du compte utilisateur connect�
   * @return Le r�le du compte utilisateur connect�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour �tre consid�r� connect�
   */
  public Role getRole() throws PersistenceException, SessionException,
                               AuthenticationException, AuthorizationException
  {
    return this.getConnectedAccount().getCredential().getRole();
  }
  /**
   * Cette m�thode permet de v�rifier les r�les du compte utilisateur connect�
   * @param roles R�les que doit avoir le compte utilisateur connect�
   * @return Le r�le de l'utilisateur connect�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le compte utilisateur connect� n'a pas les
   * r�les en argument
   */
  public Role checkRole(Role ... roles)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return UtilSecurity.checkRole(this.getRole(), roles);
  }
  /**
   * Cette m�thode permet de v�rifier les r�les du compte utilisateur connect�
   * @param roles R�les que ne doit pas avoir le compte utilisateur connect�
   * @return Le r�le de l'utilisateur connect�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le compte utilisateur connect� a l'un des
   * r�les en argument
   */
  public Role checkNotRole(Role ... roles)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return UtilSecurity.checkNotRole(this.getRole(), roles);
  }
  /**
   * Cette m�thode permet de v�rifier que le compte utilisateur connect� correspond
   * au compte utilisateur dont l'identifiant est pass� en argument ou qu'il a l'
   * un des r�les pr�cis�s
   * @param accountId Identifiant attendu pour le compte utilisateur connect�
   * @param alternateRoles R�les possible pour le compte utilisateur connect� s'il
   * ne correspond pas � l'identifiant en argument
   * @return Le r�le de l'utilisateur connect�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le compte utilisateur connect� n'a pas l'
   * identifiant attendu ni l'un des r�les en argument
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
   * Cette m�thode permet de v�rifier le r�le d'administrateur du service du compte
   * utilisateur connect�
   * @return Le r�le de l'utilisateur connect�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le compte utilisateur connect� n'a pas les
   * habilitations suffisantes pour administrer le service
   */
  public Role checkAdminRole()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.checkRole(this.getAdminRole());
  }
  /**
   * Cette m�thode permet de v�rifier le r�le d'utilisateur du le service du compte
   * utilisateur connect�
   * @return Le r�le de l'utilisateur connect�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le compte utilisateur connect� n'a pas les
   * habilitations suffisantes pour utiliser le service
   */
  public Role checkUserRole()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.checkRole(this.getUserRole());
  }
  /**
   * Cette m�thode doit d�finir le r�le d'administration du service
   * @return Le r�le d'administration du service
   */
  public abstract Role getAdminRole();
  /**
   * Cette m�thode d�fini le r�le d'utilisation du service
   * @return Le r�le d'utilisation du service
   */
  public Role getUserRole()
  {
    return Role.USER;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Getter du cache utilis� par le service pour stocker les entit�s charg�es
   * @return Le cache utilis� par le service pour stocker les entit�s charg�es
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
   * Setter du service de gestion des connexions. L'injection se fait gr�ce � un
   * setter pour pouvoir la d�brayer dans le cas de d�pendance circulaire
   * @param connectionService R�f�rence du service de gestion des connexions �
   * positionner
   * @throws ModelArgumentException Si un service est d�j� positionn�
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
   * Getter de la r�f�rence du manager des entit�s g�r�es par le service
   * @return La r�f�rence du manager des entit�s g�r�es par le service
   */
  protected abstract Bid4WinManager_<ACCOUNT, ?> getManager();
}
