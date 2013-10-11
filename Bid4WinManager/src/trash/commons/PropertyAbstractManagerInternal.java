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
// * Manager interne de gestion g�n�rique des propri�t�s<BR>
// * <BR>
// * @param <PROPERTY> Type des propri�t�s g�r�es par le manager interne
// * @param <PROPERTY_ROOT> Type des propri�t�s racines g�r�es par le manager interne
// * @param <DAO> DAO des propri�t�s g�r�es par le manager interne
// * @param <ROOT_DAO> DAO des propri�t�s racines g�r�es par le manager interne<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public abstract class PropertyAbstractManagerInternal<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
//                                                      PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
//                                                      DAO extends PropertyAbstractDao<PROPERTY, PROPERTY_ROOT, ROOT_DAO>,
//                                                      ROOT_DAO extends PropertyRootAbstractDao<PROPERTY_ROOT, PROPERTY>>
//       extends Bid4WinManagerInternal<PROPERTY, DAO>
//{
//  /**
//   * Cette m�thode permet de r�cup�rer l'unique propri�t� racine. Attention, aucune
//   * sous-propri�t� ne sera charg�e automatiquement.
//   * @return L'unique propri�t� racine
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public PROPERTY_ROOT getRoot() throws PersistenceException
//  {
//    return this.getRootDao().getRoot();
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer une propri�t� en fonction de sa cl�. Attention,
//   * aucune sous-propri�t� ne sera charg�e automatiquement.
//   * @param key Cl� de la propri�t� � r�cup�rer
//   * @return La propri�t� r�cup�r�e en fonction de sa cl�
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� demand�e est inexistante
//   */
//  public PROPERTY getProperty(String key) throws PersistenceException, UserException
//  {
//    // Recherche la propri�t� demand�e
//    PROPERTY property = this.getRoot().getProperty(key);
//    // Retourne la propri�t� demand�e en v�rifiant son existence
//    return UtilObject.checkNotNull("property", property, this.getUnknownPropertyMessage());
//  }
//  
//  /**
//   * Cette m�thode permet de modifier la valeur d'une propri�t�. Attention, aucune
//   * sous-propri�t� ne sera charg�e automatiquement.
//   * @param key Cl� de la propri�t� � modifier
//   * @param value La nouvelle valeur de la propri�t�
//   * @return La propri�t� une fois modifi�e
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� � modifier est inexistante
//   */
//  public PROPERTY updateProperty(String key, String value)
//         throws PersistenceException, UserException
//  {
//    // R�cup�re la propri�t� � modifier
//    PROPERTY property = this.getProperty(key);
//    // Modifie la propri�t� et la retourne
//    property.defineValue(value);
//    return this.getDao().update(property);
//  }
//
//  /**
//   * Cette m�thode doit permettre d'importer toutes les propri�t�s en param�tre,
//   * celles d�j� existantes voyant leur valeur mise � jour
//   * @param properties Propri�t�s � importer
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si une cl� est invalide
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
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
//   * Getter de la r�f�rence du DAO des propri�t�s racines
//   * @return La r�f�rence du DAO des propri�t�s racines
//   */
//  protected ROOT_DAO getRootDao()
//  {
//    return this.getDao().getRootDao();
//  }
//}
