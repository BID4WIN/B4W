package trash.property;
//package com.bid4win.manager.property.abstraction;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.exception.UserException.MessageRef;
//import com.bid4win.commons.entity.property.abstraction.PropertyAbstract;
//import com.bid4win.commons.entity.property.abstraction.PropertyRootAbstract;
//import com.bid4win.commons.persistence.dao.property.abstraction.PropertyAbstractDao;
//import com.bid4win.commons.persistence.dao.property.abstraction.PropertyRootAbstractDao;
//import com.bid4win.manager.Bid4WinManagerInternal;
//
///**
// * 
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <PROPERTY> TODO A COMMENTER
// * @param <PROPERTY_ROOT> TODO A COMMENTER
// * @param <DAO> TODO A COMMENTER
// * @param <ROOT_DAO> TODO A COMMENTER<BR>
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
//  @Transactional(readOnly = false)
//  public PROPERTY getProperty(String key) throws PersistenceException, UserException
//  {
//    // Récupère la propriété racine
//    PROPERTY_ROOT root = this.getRoot();
//    // Recherche la propriété demandée
//    PROPERTY property = root.getProperty(key);
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
//  @Transactional(readOnly = false)
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
//  private ROOT_DAO getRootDao()
//  {
//    return this.getDao().getRootDao();
//  }
//}
