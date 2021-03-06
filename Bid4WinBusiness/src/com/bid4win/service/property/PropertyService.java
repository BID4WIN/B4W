package com.bid4win.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
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
 * Service de gestion des propri�t�s incluant la gestion des transactions ainsi
 * que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("PropertyService")
@Scope("singleton")
public class PropertyService
       extends PropertyAbstractService_<Property, PropertyRoot, SessionData, Account, PropertyService>
{
  /** R�f�rence du service interne de gestion des propri�t�s */
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
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * et qu'on souhaite acc�der � une propri�t� serveur
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant pour acc�der � une propri�t� serveur
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
   * Cette m�thode permet d'ajouter une nouvelle propri�t�
   * @param key Cl� de la propri�t� � ajouter
   * @param value Valeur de la propri�t� � ajouter
   * @return La premi�re propri�t� ajout�e (s'il manquait des propri�t�s interm�diaires)
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
   * des propri�t�s
   * @throws UserException Si la cl� est invalide ou d�j� r�f�renc�e
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Property addProperty(String key, String value)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Ajoute la propri�t� d�finie en argument et r�cup�re la base de l'arbre ajout�
    return this.getManager().addProperty(key, value);
  }

  /**
   * Cette m�thode permet de recopier une propri�t� � un endroit donn�
   * @param oldKey Cl� de la propri�t� originale � copier
   * @param newKey Cl� de la future propri�t� copie
   * @return La propri�t� copi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
   * des propri�t�s
   * @throws UserException Si on positionne une propri�t� d�j� r�f�renc�e ou si
   * la propri�t� � copier est inexistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Property copyProperty(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Copie et retourne la propri�t� d�finie en argument
    return this.getManager().copyProperty(oldKey, newKey);
  }

  /**
   * Cette m�thode permet de modifier la cl� d'une propri�t�
   * @param oldKey Ancienne cl� de la propri�t� � modifier
   * @param newKey Nouvelle cl� de la propri�t� � modifier
   * @return La propri�t� une fois sa cl� modifi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
   * des propri�t�s
   * @throws UserException Si la propri�t� � modifier est inexistante ou la nouvelle
   * cl� d�j� r�f�renc�e ou invalide
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Property updateKey(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Modifie la cl� de la propri�t� d�finie en argument et la retourne
    return this.getManager().updateKey(oldKey, newKey).loadRelation();
  }

  /**
   * Cette m�thode permet de supprimer une propri�t�
   * @param key Cl� de la propri�t� � supprimer
   * @return La propri�t� supprim�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propri�t� � supprimer est inexistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Property removeProperty(String key)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Supprime et retourne la propri�t� d�finie en argument
    return this.getManager().removeProperty(key);
  }

  /**
   * Getter de la r�f�rence du manager de gestion des propri�t�s
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getManager()
   */
  @Override
  protected PropertyManager getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Getter de la r�f�rence du service interne de gestion des propri�t�s
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getInternal()
   */
  @Override
  protected PropertyInternalService getInternal()
  {
    return this.internal;
  }
}
