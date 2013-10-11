package trash.commons;
//package com.bid4win.commons.manager.locale;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.entity.Bid4WinEntityLoader;
//import com.bid4win.commons.entity.locale.I18n;
//import com.bid4win.commons.entity.locale.I18nRoot;
//import com.bid4win.commons.manager.exception.AuthenticationException;
//import com.bid4win.commons.manager.property.abstraction.PropertyAbstractManager;
//import com.bid4win.commons.persistence.entity.account.security.Role;
//
///**
// * 
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("I18nManager")
//@Scope("singleton")
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class I18nManager extends PropertyAbstractManager<I18n, I18nRoot, I18nManagerInternal>
//{
//  /** Référence du manager interne de gestion des propriétés d'internationalisation */
//  @Autowired
//  @Qualifier("I18nManagerInternal")
//  private I18nManagerInternal internal = null;
//
//  /**
//   * Cette méthode permet d'ajouter une nouvelle langue à la racine des propriétés
//   * d'internationalisation qui sera alors complétée d'une copie des propriétés
//   * de la langue par défaut
//   * @param code Code de la langue à ajouter à la racine des propriétés d'internationalisation
//   * @return La propriété d'internationalisation représentant la langue ajoutée
//   * et complétée d'une copie des propriétés de la langue par défaut
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la langue est manquante, inconnue ou déjà référencée
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public I18n addLanguage(String code)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Ajoute la langue définie en argument et la complète des propriétés de la
//    // langue par défaut
//    return this.getInternal().addLanguage(code);
//  }
//
//  /**
//   * Cette méthode permet d'ajouter une nouvelle entrée d'internationalisation
//   * qui sera alors copiée dans toutes les langues
//   * @param key Clé de la propriété d'internationalisation à ajouter à toutes les
//   * langues
//   * @return La liste des premières propriétés ajoutées (s'il manquait des propriétés
//   * intermédiaires) sur chaque langue
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la clé est invalide ou déjà référencée
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Bid4WinSet<I18n> addI18n(String key)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Ajoute la propriété dont la clé est définie en argument dans chaque langue
//    return this.getInternal().addI18n(key);
//  }
//
//  /**
//   * Cette méthode permet de modifier la clé d'une propriété d'internationalisation
//   * dans toutes les langues
//   * @param oldKey Ancienne clé de la propriété d'internationalisation à modifier
//   * @param newKey Nouvelle clé de la propriété d'internationalisation à modifier
//   * @return La liste des propriétés de chaque langue une fois leur clé modifiée
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la propriété à modifier n'est pas référencée ou la
//   * nouvelle clé déjà référencée ou invalide
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Bid4WinSet<I18n> updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Déplace la propriété dans chaque langue
//    Bid4WinSet<I18n> result = this.getInternal().updateKey(oldKey, newKey);
//    // Retourne le résultat complètement chargé
//    return Bid4WinEntityLoader.getInstance().loadRelation(result);
//  }
//
//  /**
//   * Cette méthode permet de retirer une entrée d'internationalisation vide de
//   * toutes les langues
//   * @param key Clé de la propriété d'internationalisation à retirer de toutes les
//   * langues
//   * @return La liste des propriétés retirées de chaque langue
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété n'est pas référencée ou non vide
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Bid4WinSet<I18n> removeI18n(String key)
//         throws PersistenceException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Supprime la propriété de chaque langue et les retourne
//    return this.getInternal().removeI18n(key);
//  }
//
//  /**
//   * 
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManager#getInternal()
//   */
//  @Override
//  protected I18nManagerInternal getInternal()
//  {
//    return this.internal;
//  }
//}
