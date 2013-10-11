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
 * Service de gestion des propriétés d'internationalisation incluant la gestion
 * des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("I18nService")
@Scope("singleton")
public class I18nService
       extends PropertyAbstractService_<I18n, I18nRoot, SessionData, Account, I18nService>
{
  /** Référence du service interne de gestion des propriétés d'internationalisation */
  @Autowired
  @Qualifier("I18nInternalService")
  private I18nInternalService internal = null;

  /**
   * Cette méthode permet de récupérer une propriété d'internationalisation en
   * fonction de sa clé et de sa langue
   * @param language Langue de la propriété d'internationalisation à récupérer
   * @param key Clé de la propriété d'internationalisation à récupérer
   * @return La propriété d'internationalisation récupérée en fonction de sa clé
   * et de sa langue liée récursivement à ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété d'internationalisation demandée est
   * inexistante ou la langue nulle
   */
  public I18n getI18n(Language language, String key)
         throws PersistenceException, UserException
  {
    // Retourne la propriété d'internationalisation demandée en vérifiant son existence
    // On ne recherche pas directement la propriété en accédant au manager afin
    // de bénéficier du cache interne au service
    I18n i18nLanguage = this.self().getLanguage(language);
    I18n i18n = i18nLanguage.getProperty(key);
    return UtilObject.checkNotNull(
        "i18n", i18n, i18nLanguage.getMessageRef(MessageRef.SUFFIX_UNKNOWN_ERROR));
  }
  /**
   * Cette méthode permet de récupérer la valeur d'une propriété d'internationalisation
   * en fonction de sa clé et de sa langue
   * @param language Langue de la propriété d'internationalisation à récupérer
   * @param key Clé de la propriété d'internationalisation à récupérer
   * @return La valeur de la propriété d'internationalisation récupérée en fonction
   * de sa clé et de sa langue ou la clé si la propriété d'internationalisation
   * n'a pas pu être trouvée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la langue demandée est nulle ou si ni celle-ci, ni
   * la langue par défaut n'existent
   */
  public String getString(Language language, String key)
         throws PersistenceException, UserException
  {
    // Retourne la propriété d'internationalisation demandée en vérifiant son existence
    I18n i18n = this.self().getLanguage(language).getProperty(key);
    if(i18n != null)
    {
      return i18n.getValue();
    }
    return key;
  }
  /**
   * Cette méthode permet de récupérer les propriétés d'internationalisation d'une
   * langue. Si la langue n'est pas trouvée, les propriétés d'internationalisation
   * de la langue par défaut seront retournées
   * @param language Langue des propriétés d'internationalisation à récupérer
   * @return Les propriétés d'internationalisation de premier niveau récupérées
   * en fonction d'une langue liées récursivement à toutes leurs relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la langue demandée est nulle ou si ni celle-ci, ni
   * la langue par défaut n' existent
   */
  public I18n getLanguage(Language language) throws PersistenceException, UserException
  {
    // Vérifie la langue
    UtilObject.checkNotNull("language", language, LanguageRef.LANGUAGE_MISSING_ERROR);
    // Recherche la langue demandée. On ne recherche pas directement la propriété
    // en accédant au manager afin de bénéficier du cache interne au service
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
   * Cette méthode permet d'ajouter une nouvelle langue à la racine des propriétés
   * d'internationalisation qui sera alors complétée d'une copie des propriétés
   * de la langue par défaut
   * @param code Code de la langue à ajouter à la racine des propriétés d'internationalisation
   * @return La propriété d'internationalisation représentant la langue ajoutée
   * et complétée d'une copie des propriétés de la langue par défaut
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la langue est manquante, inconnue ou déjà référencée
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public I18n addLanguage(String code)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Ajoute, complète et retourne la langue choisie
    return this.getManager().addLanguage(code);
  }
  /**
   * Cette méthode permet d'ajouter une nouvelle entrée d'internationalisation
   * qui sera alors copiée dans toutes les langues
   * @param key Clé de la propriété d'internationalisation à ajouter à toutes les
   * langues
   * @return La liste des premières propriétés ajoutées (s'il manquait des propriétés
   * intermédiaires) sur chaque langue
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<I18n> addI18n(String key)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Ajoute et retourne la propriété dans toutes les langues
    return this.getManager().addI18n(key);
  }
  /**
   * Cette méthode permet de modifier la clé d'une propriété d'internationalisation
   * dans toutes les langues
   * @param oldKey Ancienne clé de la propriété d'internationalisation à modifier
   * @param newKey Nouvelle clé de la propriété d'internationalisation à modifier
   * @return La liste des propriétés de chaque langue une fois leur clé modifiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la propriété à modifier n'est pas référencée ou la
   * nouvelle clé déjà référencée ou invalide
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<I18n> updateKey(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Modifie la clé de la propriété dans toutes les langues et la retourne
    return Bid4WinEntityLoader.getInstance().loadRelation(
        this.getManager().updateKey(oldKey, newKey));
  }
  /**
   * Cette méthode permet de retirer une entrée d'internationalisation vide de
   * toutes les langues
   * @param key Clé de la propriété d'internationalisation à retirer de toutes les
   * langues
   * @return La liste des propriétés retirées de chaque langue
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété n'est pas référencée ou vide
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<I18n> removeI18n(String key)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Supprime et retourne la propriété dans toutes les langues
    return this.getManager().removeI18n(key);
  }

  /**
   * Getter de la référence du manager de gestion des propriétés d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getManager()
   */
  @Override
  protected I18nManager getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Getter de la référence du service interne de gestion des propriétés d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getInternal()
   */
  @Override
  protected I18nInternalService getInternal()
  {
    return this.internal;
  }
}
