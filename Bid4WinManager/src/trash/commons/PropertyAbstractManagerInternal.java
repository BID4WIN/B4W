package trash.commons;
//package com.bid4win.commons.manager.property.abstraction;
//
//import java.util.Properties;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.exception.UserException.MessageRef;
//import com.bid4win.commons.entity.property.abstraction.PropertyAbstract;
//import com.bid4win.commons.entity.property.abstraction.PropertyRootAbstract;
//import com.bid4win.commons.manager.Bid4WinManagerInternal;
//import com.bid4win.commons.persistence.dao.property.abstraction.PropertyAbstractDao;
//import com.bid4win.commons.persistence.dao.property.abstraction.PropertyRootAbstractDao;
//
///**
// * Manager interne de gestion générique des propriétés<BR>
// * <BR>
// * @param <PROPERTY> Type des propriétés gérées par le manager interne
// * @param <PROPERTY_ROOT> Type des propriétés racines gérées par le manager interne
// * @param <DAO> DAO des propriétés gérées par le manager interne
// * @param <ROOT_DAO> DAO des propriétés racines gérées par le manager interne<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public abstract class PropertyAbstractManagerInternal<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
//                                                      PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
//                                                      DAO extends PropertyAbstractDao<PROPERTY, PROPERTY_ROOT, ROOT_DAO>,
//                                                      ROOT_DAO extends PropertyRootAbstractDao<PROPERTY_ROOT, PROPERTY>>
//       extends Bid4WinManagerInternal<PROPERTY, DAO>
//{
//  /**
//   * Cette méthode permet de récupérer l'unique propriété racine. Attention, aucune
//   * sous-propriété ne sera chargée automatiquement.
//   * @return L'unique propriété racine
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public PROPERTY_ROOT getRoot() throws PersistenceException
//  {
//    return this.getRootDao().getRoot();
//  }
//  /**
//   * Cette méthode permet de récupérer une propriété en fonction de sa clé. Attention,
//   * aucune sous-propriété ne sera chargée automatiquement.
//   * @param key Clé de la propriété à récupérer
//   * @return La propriété récupérée en fonction de sa clé
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété demandée est inexistante
//   */
//  public PROPERTY getProperty(String key) throws PersistenceException, UserException
//  {
//    // Recherche la propriété demandée
//    PROPERTY property = this.getRoot().getProperty(key);
//    // Retourne la propriété demandée en vérifiant son existence
//    return UtilObject.checkNotNull("property", property, this.getUnknownPropertyMessage());
//  }
//  
//  /**
//   * Cette méthode permet de modifier la valeur d'une propriété. Attention, aucune
//   * sous-propriété ne sera chargée automatiquement.
//   * @param key Clé de la propriété à modifier
//   * @param value La nouvelle valeur de la propriété
//   * @return La propriété une fois modifiée
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété à modifier est inexistante
//   */
//  public PROPERTY updateProperty(String key, String value)
//         throws PersistenceException, UserException
//  {
//    // Récupère la propriété à modifier
//    PROPERTY property = this.getProperty(key);
//    // Modifie la propriété et la retourne
//    property.defineValue(value);
//    return this.getDao().update(property);
//  }
//
//  /**
//   * Cette méthode doit permettre d'importer toutes les propriétés en paramètre,
//   * celles déjà existantes voyant leur valeur mise à jour
//   * @param properties Propriétés à importer
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si une clé est invalide
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   */
//  public abstract void importProperties(Properties properties)
//         throws PersistenceException, UserException, ModelArgumentException;
//  
//  /**
//   * 
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  protected abstract MessageRef getUnknownPropertyMessage();
//
//  /**
//   * Getter de la référence du DAO des propriétés racines
//   * @return La référence du DAO des propriétés racines
//   */
//  protected ROOT_DAO getRootDao()
//  {
//    return this.getDao().getRootDao();
//  }
//}
