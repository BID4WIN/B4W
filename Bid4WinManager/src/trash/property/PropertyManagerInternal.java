package trash.property;
//package com.bid4win.manager.property;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Enumeration;
//import java.util.Iterator;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.exception.UserException.MessageRef;
//import com.bid4win.commons.entity.property.Property;
//import com.bid4win.commons.entity.property.PropertyRoot;
//import com.bid4win.commons.entity.property.UtilProperty;
//import com.bid4win.commons.persistence.dao.property.PropertyDao;
//import com.bid4win.commons.persistence.dao.property.PropertyRootDao;
//
///**
// * Manager interne de gestion des propri�t�s incluant la gestion des transactions<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class PropertyManagerInternal
//{
//  /** R�f�rence du DAO des propri�t�s racines */
//  @Autowired
//  @Qualifier("PropertyRootDao")
//  private PropertyRootDao rootDao = null;
//  /** R�f�rence du DAO des propri�t�s */
//  @Autowired
//  @Qualifier("PropertyDao")
//  private PropertyDao propertyDao = null;
//  
//  /**
//   * Constructeur
//   */
//  protected PropertyManagerInternal()
//  {
//    super();
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer l'unique propri�t� racine
//   * @return L'unique propri�t� racine
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   */
//  @Transactional(readOnly = false)
//  public PropertyRoot getRoot() throws PersistenceException
//  {
//    return this.getRootDao().getRoot();
//  }
//  
//  /**
//   * Cette m�thode permet de r�cup�rer une propri�t� en fonction de sa cl�
//   * @param key Cl� de la propri�t� � r�cup�rer
//   * @return La propri�t� r�cup�r�e en fonction de sa cl�
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� demand�e est inexistante
//   */
//  @Transactional(readOnly = false)
//  public Property getProperty(String key) throws PersistenceException, UserException
//  {
//    // R�cup�re la propri�t� racine
//    PropertyRoot root = this.getRoot();
//    // Recherche la propri�t� demand�e
//    Property property = root.getProperty(key);
//    // Retourne la propri�t� demand�e en v�rifiant son existence
//    return UtilObject.checkNotNull("property", property, MessageRef.ERROR_PROPERTY_UNKNOWN);
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
//   */
//  @Transactional(readOnly = false)
//  public Property addProperty(String key, String value)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // R�cup�re la propri�t� racine
//    PropertyRoot root = this.getRoot();
//    // Ajoute la propri�t� d�finie en argument et r�cup�re la base de l'arbre ajout�
//    Property property = root.addProperty(key, value);
//    // Persiste l'arbre et retourne sa base
//    return this.getPropertyDao().add(property);
//  }
//
//  /**
//   * Cette m�thode permet de recopier une propri�t� � un endroit donn�
//   * @param oldKey Cl� de la propri�t� originale � copier
//   * @param newKey Cl� de la future propri�t� apr�s copie
//   * @return La propri�t� copi�e
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si on positionne une propri�t� d�j� r�f�renc�e ou si
//   * la propri�t� � copier est inexistante
//   */
//  @Transactional(readOnly = false)
//  public synchronized Property copyProperty(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // R�cup�re la propri�t� � copier
//    Property oldProperty = this.getProperty(oldKey);
//    // Ajoute la nouvelle propri�t� copi�e � partir de celle r�cup�r�e
//    this.addProperty(newKey, oldProperty.getValue());
//    // R�cup�re la propri�t� ajout�e
//    Property result = this.getProperty(newKey);
//    // Copie les sous-propri�t�s de la propri�t� copi�e
//    for(Iterator<Property> iterator = oldProperty.getPropertySet().iterator() ; iterator.hasNext() ; )
//    {
//      // R�cup�re la sous-propri�t� et la copie
//      Property newSubProperty = new Property(iterator.next(), result);
//      // Ajoute la copie de la sous-propri�t�
//      result = this.getPropertyDao().add(newSubProperty).getProperty();
//    }
//    // Retourne le r�sultat
//    return result;
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
//   */
//  @Transactional(readOnly = false)
//  public Property removeProperty(String key) throws PersistenceException, UserException
//  {
//    // R�cup�re la propri�t� � supprimer
//    Property property = this.getProperty(key);
//    // Supprime la propri�t� et la retourne
//    return this.getPropertyDao().remove(property);
//  }
//
//  /**
//   * Cette m�thode permet de modifier la cl� d'une propri�t�
//   * @param oldKey Ancienne cl� de la propri�t� � modifier
//   * @param newKey Nouvelle cl� de la propri�t� � modifier
//   * @return La propri�t� une fois sa cl� modifi�e
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la propri�t� � modifier est inexistante ou la nouvelle
//   * cl� d�j� r�f�renc�e ou invalide
//   */
//  @Transactional(readOnly = false)
//  public Property updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // Valide la nouvelle cl�
//    UtilProperty.checkFullKey(newKey);
//    // R�cup�re la propri�t� � modifier
//    Property property = this.getProperty(oldKey);
//    // R�cup�re la base de la nouvelle cl�
//    String newKeyBase = UtilProperty.removeLastKey(newKey);
//    // On retire le lien avec l'ancienne base
//    if(property.hasProperty())
//    {
//      this.getPropertyDao().update(property.unlinkFromProperty());
//    }
//    else if(property.hasRoot())
//    {
//      this.getRootDao().update(property.unlinkFromRoot());
//    }
//    // Modifie la cl� de la propri�t�
//    property.defineKey(UtilProperty.getLastKey(newKey));
//    // On cr�e le lien avec la nouvelle base
//    PropertyRoot root = this.getRootDao().getRoot();
//    if(newKeyBase.equals(""))
//    {
//      // La propri�t� est maintenant li�e � la racine
//      property.linkTo(root);
//      this.getRootDao().update(root);
//    }
//    else
//    {
//      Property base = root.getProperty(newKeyBase);
//      // La nouvelle base n'existe pas encore, il faut donc la cr�er
//      if(base == null)
//      {
//        this.getPropertyDao().add(root.addProperty(newKeyBase, ""));
//        base = root.getProperty(newKeyBase);
//      }
//      // La propri�t� est maintenant li�e � sa nouvelle base
//      property.linkTo(base);
//      this.getPropertyDao().update(base);
//    }
//    // Modifie la propri�t� et la retourne
//    return this.getPropertyDao().update(property);
//  }
//  
//  /**
//   * Cette m�thode permet de modifier la valeur d'une propri�t�
//   * @param key Cl� de la propri�t� � modifier
//   * @param value La nouvelle valeur de la propri�t�
//   * @return La propri�t� une fois modifi�e
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� � modifier est inexistante
//   */
//  @Transactional(readOnly = false)
//  public Property updateProperty(String key, String value)
//         throws PersistenceException, UserException
//  {
//    // R�cup�re la propri�t� � modifier
//    Property property = this.getProperty(key);
//    // Modifie la propri�t� et la retourne
//    property.defineValue(value);
//    return this.getPropertyDao().update(property);
//  }
//
//  /**
//   * Cette m�thode permet d'importer toutes les propri�t�s du flux en param�tre.
//   * Les propri�t�s d�j� existantes verront leur valeur mise � jour
//   * @param inputStream Flux � partir duquel importer les propri�t�s
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante ou du flux d'import
//   * @throws UserException Si une cl� est invalide
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   */
//  @Transactional(readOnly = false)
//  public void importProperties(InputStream inputStream)
//         throws PersistenceException, UserException, ModelArgumentException
//  {
//    // Importe toutes les propri�t�s du flux en param�tre
//    Properties properties = new Properties();
//    try
//    {
//      properties.load(inputStream);
//    }
//    catch(IOException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//    // Ajoute ou met � jour chaque propri�t�
//    for(Enumeration<Object> enumeration = properties.keys() ; enumeration.hasMoreElements() ; )
//    {
//      String key = (String)enumeration.nextElement();
//      String value = properties.getProperty(key);
//      // R�cup�re la racine des propri�t�s
//      PropertyRoot root = this.getRoot();
//      Property property = root.getProperty(key);
//      // Il faut ajouter la propri�t�
//      if(property == null)
//      {
//        this.addProperty(key, value);
//      }
//      // Il faut mettre � jour la propri�t�
//      else
//      {
//        this.updateProperty(key, value);
//      }
//    }
//  }
//  
//  /**
//   * Getter de la r�f�rence du DAO des propri�t�s racines
//   * @return La r�f�rence du DAO des propri�t�s racines
//   */
//  private PropertyRootDao getRootDao()
//  {
//    return this.rootDao;
//  }
//  /**
//   * Getter de la r�f�rence du DAO des propri�t�s
//   * @return La r�f�rence du DAO des propri�t�s
//   */
//  private PropertyDao getPropertyDao()
//  {
//    return this.propertyDao;
//  }
//}
