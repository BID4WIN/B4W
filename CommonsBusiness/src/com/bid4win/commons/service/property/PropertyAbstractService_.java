package com.bid4win.commons.service.property;

import java.util.Properties;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.property.PropertyAbstractManager_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.persistence.entity.property.UtilProperty;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Service de base du projet pour la gestion des propriétés incluant la gestion
 * des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @param <PROPERTY> Définition du type de propriétés gérées par le service<BR>
 * @param <PROPERTY_ROOT> Définition du type des propriétés racines gérées par le
 * service<BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractService_<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                               PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                               SESSION extends SessionDataAbstract<ACCOUNT>,
                                               ACCOUNT extends AccountAbstract<ACCOUNT>,
                                               SERVICE extends PropertyAbstractService_<PROPERTY, PROPERTY_ROOT, SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
  /**
   * Cette méthode permet de vérifier l'existence d'une propriété en fonction de
   * sa clé
   * @param key Clé de la propriété dont il faut vérifier l'existence
   * @return True si la propriété existe, false sinon
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public boolean hasProperty(String key) throws PersistenceException
  {
    try
    {
      // Recherche si la propriété demandée existe
      return this.getInternal().getRoot().getProperty(key) != null;
    }
    catch(UserException ex)
    {
      // Ne devrais pas arrive car on ne demande aucune validation d'habilitation
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette méthode permet de récupérer une propriété en fonction de sa clé
   * @param key Clé de la propriété à récupérer
   * @return La propriété récupérée en fonction de sa clé liée récursivement à
   * ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété demandée est inexistante
   */
  public PROPERTY getProperty(String key)
         throws PersistenceException, UserException
  {
    return this.getInternal().getProperty(key);
  }
  /**
   * Cette méthode permet de récupérer toutes les propriétés gérées par le service
   * @return Le set de toutes les propriétés de premier niveau récupérées liées
   * récursivement à toutes leurs relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Bid4WinSet<PROPERTY> getPropertySet()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Retourne le set de toutes les propriétés. On ne recherche pas directement
    // les propriétés en accédant au manager afin de bénéficier du cache interne
    // au service
    return this.getInternal().getRoot(this.getAdminRole()).getPropertySet();
  }
  /**
   * Cette méthode permet de récupérer toutes les propriétés qui passent, ou dont
   * l'une des filles passe le filtre, à savoir la présence de la chaîne à rechercher
   * dans la clé ou la valeur.
   * @param searchString La chaîne à rechercher
   * @return Le set des propriétés filtrées liées récursivement à toutes leurs relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la manipulation d'une propriété échoue
   * @throws SessionException
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Bid4WinSet<PROPERTY> getFilteredPropertySet(String searchString)
        throws PersistenceException, ModelArgumentException, UserException,
               SessionException, AuthenticationException, AuthorizationException
  {
    return UtilProperty.getFilteredPropertySet(this.self().getPropertySet(), searchString);
  }

  /**
   * Cette méthode permet de modifier la valeur d'une propriété
   * @param key Clé de la propriété à modifier
   * @param value La nouvelle valeur de la propriété
   * @return La propriété une fois modifiée récursivement liée à toutes ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété à modifier est inexistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PROPERTY updateProperty(String key, String value)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Modifie la propriété et charge entièrement ses relations
    return this.getManager().updateProperty(key, value).loadRelation();
  }
  /**
   * Cette méthode permet d'importer toutes les propriétés en paramètre. Celles
   * déjà existantes verront leur valeur mise à jour
   * @param properties Propriétés à importer
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si une clé est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void importProperties(Properties properties)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Importe les propriétés en paramètre
    this.getManager().importProperties(properties);
  }
  /**
   * Cette méthode permet d'exporter toutes les propriétés gérées par le service
   * @return L'export de toutes les propriétés gérées par le service
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Properties exportProperties()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // La transformation en propriétés chargera entièrement la propriété racine
    return this.getManager().getRoot().toProperties();
  }

  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#clearCache()
   */
  @Override
  protected void clearCache()
  {
    super.clearCache();
    this.getInternal().clearCache();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public final Role getAdminRole()
  {
    return this.getInternal().getAdminRole();
  }
  /**
   * Permet de préciser la référence du manager des propriétés gérées par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected PropertyAbstractManager_<PROPERTY,PROPERTY_ROOT, ACCOUNT> getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Permet de préciser la référence du service interne de gestion des propriétés
   * @return La référence du service interne de gestions des propriétés
   */
  protected abstract PropertyAbstractInternalService_<PROPERTY,PROPERTY_ROOT, SESSION, ACCOUNT, ?> getInternal();
}
