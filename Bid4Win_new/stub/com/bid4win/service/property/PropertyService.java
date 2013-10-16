package com.bid4win.service.property;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.PropertyRef;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.property.UtilProperty;
import com.bid4win.commons.service.property.PropertyAbstractService_;
import com.bid4win.manager.property.PropertyManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion des propriétés incluant la gestion des transactions ainsi
 * que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyService")
@Scope("singleton")
public class PropertyService
       extends PropertyAbstractService_<Property, PropertyRoot, SessionData, Account, PropertyService>
{
  /** Référence du service interne de gestion des propriétés */
  @Autowired
  @Qualifier("PropertyInternalService")
  private PropertyInternalService internal = null;

  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * et qu'on souhaite accéder à une propriété serveur
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant pour accéder à une propriété serveur
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getProperty(java.lang.String)
   */
  @Override
  public Property getProperty(String key)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    if(UtilProperty.getFirstKey(key).equals(PropertyRef.SERVER))
    {
      return this.getInternal().getProperty(key, this.getAdminRole());
    }
    return super.getProperty(key);
  }

  /**
   * Cette méthode permet d'ajouter une nouvelle propriété
   * @param key Clé de la propriété à ajouter
   * @param value Valeur de la propriété à ajouter
   * @return La première propriété ajoutée (s'il manquait des propriétés intermédiaires)
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la clé est invalide ou déjà référencée
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Property addProperty(String key, String value)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    return this.getInternal().getRoot().addProperty(key, value);
  }

  /**
   * Cette méthode permet de recopier une propriété à un endroit donné
   * @param oldKey Clé de la propriété originale à copier
   * @param newKey Clé de la future propriété copie
   * @return La propriété copiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si on positionne une propriété déjà référencée ou si
   * la propriété à copier est inexistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Property copyProperty(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Copie et retourne la propriété définie en argument
    return this.getManager().copyProperty(oldKey, newKey);
  }

  /**
   * Cette méthode permet de modifier la clé d'une propriété
   * @param oldKey Ancienne clé de la propriété à modifier
   * @param newKey Nouvelle clé de la propriété à modifier
   * @return La propriété une fois sa clé modifiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la propriété à modifier est inexistante ou la nouvelle
   * clé déjà référencée ou invalide
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Property updateKey(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Modifie la clé de la propriété définie en argument et la retourne
    return this.getManager().updateKey(oldKey, newKey).loadRelation();
  }

  /**
   * Cette méthode permet de supprimer une propriété
   * @param key Clé de la propriété à supprimer
   * @return La propriété supprimée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété à supprimer est inexistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Property removeProperty(String key)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    Property property = this.getProperty(key);
    if(property.hasProperty())
    {
      property.unlinkFromProperty();
    }
    else
    {
      property.unlinkFromRoot();
    }
    return property;
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
  @Override
  public Property updateProperty(String key, String value)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    Property property = this.getProperty(key);
    property.defineValue(value);
    return property;
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
  @Override
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
  @Override
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
   * Getter de la référence du manager de gestion des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getManager()
   */
  @Override
  protected PropertyManager getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Getter de la référence du service interne de gestion des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getInternal()
   */
  @Override
  protected PropertyInternalService getInternal()
  {
    return this.internal;
  }
}
