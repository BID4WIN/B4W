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
// * @author Emeric Fillâtre
// */
//@Component("PropertyManager")
//@Scope("singleton")
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class PropertyManager extends PropertyAbstractManager<Property, PropertyRoot, PropertyManagerInternal>
//{
//  /** Nom de la propriété contenant l'url racine de la web app */
//  public static final String ROOT_LOCATION = "server.root_location";
//  /** Nom de la propriété de timeout de session */
//  public static final String SESSION_TIMEOUT = "server.session.timeout";
//
//  /** Référence du manager interne de gestion des propriétés */
//  @Autowired
//  @Qualifier("PropertyManagerInternal")
//  private PropertyManagerInternal internal = null;
//
//  /**
//   * Cette méthode permet de vérifier l'existence d'une propriété en fonction de
//   * sa clé
//   * @param key Clé de la propriété dont il faut vérifier l'existence
//   * @return True si la propriété existe, false sinon
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public boolean hasProperty(String key) throws PersistenceException
//  {
//    // Récupère la propriété racine
//    PropertyRoot root = this.getInternal().getRoot();
//    // Recherche si la propriété demandée existe
//    return root.getProperty(key) != null;
//  }
//
//  /**
//   * Cette méthode permet d'ajouter une nouvelle propriété
//   * @param key Clé de la propriété à ajouter
//   * @param value Valeur de la propriété à ajouter
//   * @return La première propriété ajoutée (s'il manquait des propriétés intermédiaires)
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
//  public Property addProperty(String key, String value)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Ajoute la propriété définie en argument et récupère la base de l'arbre ajouté
//    return this.getInternal().addProperty(key, value);
//  }
//
//  /**
//   * Cette méthode permet de recopier une propriété à un endroit donné
//   * @param oldKey Clé de la propriété originale à copier
//   * @param newKey Clé de la future propriété copie
//   * @return La propriété copiée
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si on positionne une propriété déjà référencée ou si
//   * la propriété à copier est inexistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Property copyProperty(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Copie complètement la propriété et retourne le résultat
//    return this.getInternal().copyProperty(oldKey, newKey);
//  }
//
//  /**
//   * Cette méthode permet de modifier la clé d'une propriété
//   * @param oldKey Ancienne clé de la propriété à modifier
//   * @param newKey Nouvelle clé de la propriété à modifier
//   * @return La propriété une fois sa clé modifiée
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la propriété à modifier est inexistante ou la nouvelle
//   * clé déjà référencée ou invalide
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Property updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Déplace la propriété et retourne le résultat complètement chargé
//    return this.getInternal().updateKey(oldKey, newKey).loadRelation();
//  }
//
//  /**
//   * Cette méthode permet de supprimer une propriété
//   * @param key Clé de la propriété à supprimer
//   * @return La propriété supprimée
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété à supprimer est inexistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public Property removeProperty(String key)
//         throws PersistenceException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Supprime complètement la propriété et la retourne
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
