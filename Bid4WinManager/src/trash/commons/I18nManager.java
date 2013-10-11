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
// * @author Emeric Fill�tre
// */
//@Component("I18nManager")
//@Scope("singleton")
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class I18nManager extends PropertyAbstractManager<I18n, I18nRoot, I18nManagerInternal>
//{
//  /** R�f�rence du manager interne de gestion des propri�t�s d'internationalisation */
//  @Autowired
//  @Qualifier("I18nManagerInternal")
//  private I18nManagerInternal internal = null;
//
//  /**
//   * Cette m�thode permet d'ajouter une nouvelle langue � la racine des propri�t�s
//   * d'internationalisation qui sera alors compl�t�e d'une copie des propri�t�s
//   * de la langue par d�faut
//   * @param code Code de la langue � ajouter � la racine des propri�t�s d'internationalisation
//   * @return La propri�t� d'internationalisation repr�sentant la langue ajout�e
//   * et compl�t�e d'une copie des propri�t�s de la langue par d�faut
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la langue est manquante, inconnue ou d�j� r�f�renc�e
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public I18n addLanguage(String code)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // Ajoute la langue d�finie en argument et la compl�te des propri�t�s de la
//    // langue par d�faut
//    return this.getInternal().addLanguage(code);
//  }
//
//  /**
//   * Cette m�thode permet d'ajouter une nouvelle entr�e d'internationalisation
//   * qui sera alors copi�e dans toutes les langues
//   * @param key Cl� de la propri�t� d'internationalisation � ajouter � toutes les
//   * langues
//   * @return La liste des premi�res propri�t�s ajout�es (s'il manquait des propri�t�s
//   * interm�diaires) sur chaque langue
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la cl� est invalide ou d�j� r�f�renc�e
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Bid4WinSet<I18n> addI18n(String key)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // Ajoute la propri�t� dont la cl� est d�finie en argument dans chaque langue
//    return this.getInternal().addI18n(key);
//  }
//
//  /**
//   * Cette m�thode permet de modifier la cl� d'une propri�t� d'internationalisation
//   * dans toutes les langues
//   * @param oldKey Ancienne cl� de la propri�t� d'internationalisation � modifier
//   * @param newKey Nouvelle cl� de la propri�t� d'internationalisation � modifier
//   * @return La liste des propri�t�s de chaque langue une fois leur cl� modifi�e
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la propri�t� � modifier n'est pas r�f�renc�e ou la
//   * nouvelle cl� d�j� r�f�renc�e ou invalide
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Bid4WinSet<I18n> updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // D�place la propri�t� dans chaque langue
//    Bid4WinSet<I18n> result = this.getInternal().updateKey(oldKey, newKey);
//    // Retourne le r�sultat compl�tement charg�
//    return Bid4WinEntityLoader.getInstance().loadRelation(result);
//  }
//
//  /**
//   * Cette m�thode permet de retirer une entr�e d'internationalisation vide de
//   * toutes les langues
//   * @param key Cl� de la propri�t� d'internationalisation � retirer de toutes les
//   * langues
//   * @return La liste des propri�t�s retir�es de chaque langue
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� n'est pas r�f�renc�e ou non vide
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Bid4WinSet<I18n> removeI18n(String key)
//         throws PersistenceException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // Supprime la propri�t� de chaque langue et les retourne
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
