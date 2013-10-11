package com.bid4win.service.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.LanguageRef;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.service.property.PropertyAbstractService_;
import com.bid4win.manager.locale.I18nManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion des propri�t�s d'internationalisation incluant la gestion
 * des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("I18nService")
@Scope("singleton")
public class I18nService
       extends PropertyAbstractService_<I18n, I18nRoot, SessionData, Account, I18nService>
{
  /** R�f�rence du service interne de gestion des propri�t�s d'internationalisation */
  @Autowired
  @Qualifier("I18nInternalService")
  private I18nInternalService internal = null;

  /**
   * Cette m�thode permet de r�cup�rer une propri�t� d'internationalisation en
   * fonction de sa cl� et de sa langue
   * @param language Langue de la propri�t� d'internationalisation � r�cup�rer
   * @param key Cl� de la propri�t� d'internationalisation � r�cup�rer
   * @return La propri�t� d'internationalisation r�cup�r�e en fonction de sa cl�
   * et de sa langue li�e r�cursivement � ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propri�t� d'internationalisation demand�e est
   * inexistante ou la langue nulle
   */
  public I18n getI18n(Language language, String key)
         throws PersistenceException, UserException
  {
    // Retourne la propri�t� d'internationalisation demand�e en v�rifiant son existence
    // On ne recherche pas directement la propri�t� en acc�dant au manager afin
    // de b�n�ficier du cache interne au service
    I18n i18nLanguage = this.self().getLanguage(language);
    I18n i18n = i18nLanguage.getProperty(key);
    return UtilObject.checkNotNull(
        "i18n", i18n, i18nLanguage.getMessageRef(MessageRef.SUFFIX_UNKNOWN_ERROR));
  }
  /**
   * Cette m�thode permet de r�cup�rer la valeur d'une propri�t� d'internationalisation
   * en fonction de sa cl� et de sa langue
   * @param language Langue de la propri�t� d'internationalisation � r�cup�rer
   * @param key Cl� de la propri�t� d'internationalisation � r�cup�rer
   * @return La valeur de la propri�t� d'internationalisation r�cup�r�e en fonction
   * de sa cl� et de sa langue ou la cl� si la propri�t� d'internationalisation
   * n'a pas pu �tre trouv�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la langue demand�e est nulle ou si ni celle-ci, ni
   * la langue par d�faut n'existent
   */
  public String getString(Language language, String key)
         throws PersistenceException, UserException
  {
    // Retourne la propri�t� d'internationalisation demand�e en v�rifiant son existence
    I18n i18n = this.self().getLanguage(language).getProperty(key);
    if(i18n != null)
    {
      return i18n.getValue();
    }
    return key;
  }
  /**
   * Cette m�thode permet de r�cup�rer les propri�t�s d'internationalisation d'une
   * langue. Si la langue n'est pas trouv�e, les propri�t�s d'internationalisation
   * de la langue par d�faut seront retourn�es
   * @param language Langue des propri�t�s d'internationalisation � r�cup�rer
   * @return Les propri�t�s d'internationalisation de premier niveau r�cup�r�es
   * en fonction d'une langue li�es r�cursivement � toutes leurs relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la langue demand�e est nulle ou si ni celle-ci, ni
   * la langue par d�faut n' existent
   */
  public I18n getLanguage(Language language) throws PersistenceException, UserException
  {
    // V�rifie la langue
    UtilObject.checkNotNull("language", language, LanguageRef.LANGUAGE_MISSING_ERROR);
    // Recherche la langue demand�e. On ne recherche pas directement la propri�t�
    // en acc�dant au manager afin de b�n�ficier du cache interne au service
    I18nRoot root = this.getInternal().getRoot();
    I18n i18n = root.getProperty(language.getCode());
    if(i18n == null)
    {
      i18n = root.getProperty(Language.DEFAULT.getCode());
    }
    return UtilObject.checkNotNull("language", i18n,
                                   LanguageRef.LANGUAGE_UNKNOWN_ERROR);
  }

  /**
   * Cette m�thode permet d'ajouter une nouvelle langue � la racine des propri�t�s
   * d'internationalisation qui sera alors compl�t�e d'une copie des propri�t�s
   * de la langue par d�faut
   * @param code Code de la langue � ajouter � la racine des propri�t�s d'internationalisation
   * @return La propri�t� d'internationalisation repr�sentant la langue ajout�e
   * et compl�t�e d'une copie des propri�t�s de la langue par d�faut
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
   * des propri�t�s
   * @throws UserException Si la langue est manquante, inconnue ou d�j� r�f�renc�e
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public I18n addLanguage(String code)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Ajoute, compl�te et retourne la langue choisie
    return this.getManager().addLanguage(code);
  }
  /**
   * Cette m�thode permet d'ajouter une nouvelle entr�e d'internationalisation
   * qui sera alors copi�e dans toutes les langues
   * @param key Cl� de la propri�t� d'internationalisation � ajouter � toutes les
   * langues
   * @return La liste des premi�res propri�t�s ajout�es (s'il manquait des propri�t�s
   * interm�diaires) sur chaque langue
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
  public Bid4WinSet<I18n> addI18n(String key)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Ajoute et retourne la propri�t� dans toutes les langues
    return this.getManager().addI18n(key);
  }
  /**
   * Cette m�thode permet de modifier la cl� d'une propri�t� d'internationalisation
   * dans toutes les langues
   * @param oldKey Ancienne cl� de la propri�t� d'internationalisation � modifier
   * @param newKey Nouvelle cl� de la propri�t� d'internationalisation � modifier
   * @return La liste des propri�t�s de chaque langue une fois leur cl� modifi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
   * des propri�t�s
   * @throws UserException Si la propri�t� � modifier n'est pas r�f�renc�e ou la
   * nouvelle cl� d�j� r�f�renc�e ou invalide
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<I18n> updateKey(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Modifie la cl� de la propri�t� dans toutes les langues et la retourne
    return Bid4WinEntityLoader.getInstance().loadRelation(
        this.getManager().updateKey(oldKey, newKey));
  }
  /**
   * Cette m�thode permet de retirer une entr�e d'internationalisation vide de
   * toutes les langues
   * @param key Cl� de la propri�t� d'internationalisation � retirer de toutes les
   * langues
   * @return La liste des propri�t�s retir�es de chaque langue
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propri�t� n'est pas r�f�renc�e ou vide
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<I18n> removeI18n(String key)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Supprime et retourne la propri�t� dans toutes les langues
    return this.getManager().removeI18n(key);
  }

  /**
   * Getter de la r�f�rence du manager de gestion des propri�t�s d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getManager()
   */
  @Override
  protected I18nManager getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Getter de la r�f�rence du service interne de gestion des propri�t�s d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getInternal()
   */
  @Override
  protected I18nInternalService getInternal()
  {
    return this.internal;
  }
}
