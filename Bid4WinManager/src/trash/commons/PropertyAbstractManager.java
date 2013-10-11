package trash.commons;
//package com.bid4win.commons.manager.property.abstraction;
//
//import java.util.Properties;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.entity.property.abstraction.PropertyAbstract;
//import com.bid4win.commons.entity.property.abstraction.PropertyRootAbstract;
//import com.bid4win.commons.manager.Bid4WinManager;
//import com.bid4win.commons.manager.exception.AuthenticationException;
//import com.bid4win.commons.persistence.entity.account.security.Role;
//
///**
// * Manager de gestion des propriétés incluant la gestion des transactions<BR>
// * <BR>
// * @param <PROPERTY> Type des propriétés gérées par le manager
// * @param <PROPERTY_ROOT> Type des propriétés racines des propriétés gérées par
// * le manager
// * @param <INTERNAL> Manager interne utilisé pour la gestion métier des propriétés<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public abstract class PropertyAbstractManager<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
//                                              PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
//                                              INTERNAL extends PropertyAbstractManagerInternal<PROPERTY, PROPERTY_ROOT, ?, ?>>
//       extends Bid4WinManager<PROPERTY, INTERNAL>
//{
//  /**
//   * Cette méthode permet de récupérer une propriété en fonction de sa clé. Attention
//   * à ne pas accéder aux sous-propriétés car celles-ci ne seront pas chargées
//   * @param key Clé de la propriété à récupérer
//   * @return La propriété récupérée en fonction de sa clé
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété demandée est inexistante
//   */
//  public PROPERTY getProperty(String key) throws PersistenceException, UserException
//  {
//    // Récupère et retourne la propriété
//    return this.getInternal().getProperty(key);
//  }
//  
//  /**
//   * Cette méthode permet de récupérer une propriété complètement chargée en fonction
//   * de sa clé
//   * @param key Clé de la propriété à récupérer
//   * @return La propriété complètement chargées récupérée en fonction de sa clé
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété demandée est inexistante
//   */
//  public PROPERTY getProperties(String key) throws PersistenceException, UserException
//  {
//    // Récupère, charge complètement et retourne la propriété
//    return this.getInternal().getProperty(key).loadRelation();
//  }
//
//  /**
//   * Cette méthode permet de récupérer toutes les propriétés du manager
//   * @return La liste de toutes les propriétés du manager
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  public Bid4WinSet<PROPERTY> getPropertySet()
//         throws PersistenceException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Récupère la propriété racine et retourne le set de ses propriétés complètement chargé
//    return this.getInternal().getRoot().loadRelation().getPropertySet();
//  }
//  
//  /**
//   * Cette méthode permet de récupérer toutes les propriétés du manager
//   * @return Les propriétés du manager
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  public Properties getProperties() throws PersistenceException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // La transformation en propriétés chargera entièrement la propriété racine
//    return this.getInternal().getRoot().toProperties();
//  }
//
//  /**
//   * Cette méthode permet de modifier la valeur d'une propriété
//   * @param key Clé de la propriété à modifier
//   * @param value La nouvelle valeur de la propriété
//   * @return La propriété une fois modifiée
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété à modifier est inexistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public PROPERTY updateProperty(String key, String value)
//         throws PersistenceException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Modifie la propriété et charge entièrement ses relations
//    return this.getInternal().updateProperty(key, value).loadRelation();
//  }
//
//  /**
//   * Cette méthode permet d'importer toutes les propriétés en paramètre. Celles
//   * déjà existantes verront leur valeur mise à jour
//   * @param properties Propriétés à importer
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si une clé est invalide
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connecté n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public void importProperties(Properties properties)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // Vérifie le niveau d'autorisation de l'utilisateur connecté
//    this.checkRole(Role.ADMIN);
//    // Importe les propriétés en paramètre
//    this.getInternal().importProperties(properties);
//  }
//}
