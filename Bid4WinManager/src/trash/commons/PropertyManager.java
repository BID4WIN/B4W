package trash.commons;
//package com.bid4win.commons.manager.property;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.entity.property.Property;
//import com.bid4win.commons.entity.property.PropertyRoot;
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
//@Component("PropertyManager")
//@Scope("singleton")
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class PropertyManager extends PropertyAbstractManager<Property, PropertyRoot, PropertyManagerInternal>
//{
//  /** Nom de la propri�t� contenant l'url racine de la web app */
//  public static final String ROOT_LOCATION = "server.root_location";
//  /** Nom de la propri�t� de timeout de session */
//  public static final String SESSION_TIMEOUT = "server.session.timeout";
//
//  /** R�f�rence du manager interne de gestion des propri�t�s */
//  @Autowired
//  @Qualifier("PropertyManagerInternal")
//  private PropertyManagerInternal internal = null;
//
//  /**
//   * Cette m�thode permet de v�rifier l'existence d'une propri�t� en fonction de
//   * sa cl�
//   * @param key Cl� de la propri�t� dont il faut v�rifier l'existence
//   * @return True si la propri�t� existe, false sinon
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public boolean hasProperty(String key) throws PersistenceException
//  {
//    // R�cup�re la propri�t� racine
//    PropertyRoot root = this.getInternal().getRoot();
//    // Recherche si la propri�t� demand�e existe
//    return root.getProperty(key) != null;
//  }
//
//  /**
//   * Cette m�thode permet d'ajouter une nouvelle propri�t�
//   * @param key Cl� de la propri�t� � ajouter
//   * @param value Valeur de la propri�t� � ajouter
//   * @return La premi�re propri�t� ajout�e (s'il manquait des propri�t�s interm�diaires)
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
//  public Property addProperty(String key, String value)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // Ajoute la propri�t� d�finie en argument et r�cup�re la base de l'arbre ajout�
//    return this.getInternal().addProperty(key, value);
//  }
//
//  /**
//   * Cette m�thode permet de recopier une propri�t� � un endroit donn�
//   * @param oldKey Cl� de la propri�t� originale � copier
//   * @param newKey Cl� de la future propri�t� copie
//   * @return La propri�t� copi�e
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si on positionne une propri�t� d�j� r�f�renc�e ou si
//   * la propri�t� � copier est inexistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Property copyProperty(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // Copie compl�tement la propri�t� et retourne le r�sultat
//    return this.getInternal().copyProperty(oldKey, newKey);
//  }
//
//  /**
//   * Cette m�thode permet de modifier la cl� d'une propri�t�
//   * @param oldKey Ancienne cl� de la propri�t� � modifier
//   * @param newKey Nouvelle cl� de la propri�t� � modifier
//   * @return La propri�t� une fois sa cl� modifi�e
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la propri�t� � modifier est inexistante ou la nouvelle
//   * cl� d�j� r�f�renc�e ou invalide
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Property updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // D�place la propri�t� et retourne le r�sultat compl�tement charg�
//    return this.getInternal().updateKey(oldKey, newKey).loadRelation();
//  }
//
//  /**
//   * Cette m�thode permet de supprimer une propri�t�
//   * @param key Cl� de la propri�t� � supprimer
//   * @return La propri�t� supprim�e
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� � supprimer est inexistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Property removeProperty(String key)
//         throws PersistenceException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // Supprime compl�tement la propri�t� et la retourne
//    return this.getInternal().removeProperty(key);
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManager#getInternal()
//   */
//  @Override
//  protected PropertyManagerInternal getInternal()
//  {
//    return this.internal;
//  }
//}
