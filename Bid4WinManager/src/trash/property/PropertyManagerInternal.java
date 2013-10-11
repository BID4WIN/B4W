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
// * Manager interne de gestion des propriétés incluant la gestion des transactions<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class PropertyManagerInternal
//{
//  /** Référence du DAO des propriétés racines */
//  @Autowired
//  @Qualifier("PropertyRootDao")
//  private PropertyRootDao rootDao = null;
//  /** Référence du DAO des propriétés */
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
//   * Cette méthode permet de récupérer l'unique propriété racine
//   * @return L'unique propriété racine
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   */
//  @Transactional(readOnly = false)
//  public PropertyRoot getRoot() throws PersistenceException
//  {
//    return this.getRootDao().getRoot();
//  }
//  
//  /**
//   * Cette méthode permet de récupérer une propriété en fonction de sa clé
//   * @param key Clé de la propriété à récupérer
//   * @return La propriété récupérée en fonction de sa clé
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété demandée est inexistante
//   */
//  @Transactional(readOnly = false)
//  public Property getProperty(String key) throws PersistenceException, UserException
//  {
//    // Récupère la propriété racine
//    PropertyRoot root = this.getRoot();
//    // Recherche la propriété demandée
//    Property property = root.getProperty(key);
//    // Retourne la propriété demandée en vérifiant son existence
//    return UtilObject.checkNotNull("property", property, MessageRef.ERROR_PROPERTY_UNKNOWN);
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
//   */
//  @Transactional(readOnly = false)
//  public Property addProperty(String key, String value)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // Récupère la propriété racine
//    PropertyRoot root = this.getRoot();
//    // Ajoute la propriété définie en argument et récupère la base de l'arbre ajouté
//    Property property = root.addProperty(key, value);
//    // Persiste l'arbre et retourne sa base
//    return this.getPropertyDao().add(property);
//  }
//
//  /**
//   * Cette méthode permet de recopier une propriété à un endroit donné
//   * @param oldKey Clé de la propriété originale à copier
//   * @param newKey Clé de la future propriété après copie
//   * @return La propriété copiée
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si on positionne une propriété déjà référencée ou si
//   * la propriété à copier est inexistante
//   */
//  @Transactional(readOnly = false)
//  public synchronized Property copyProperty(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // Récupère la propriété à copier
//    Property oldProperty = this.getProperty(oldKey);
//    // Ajoute la nouvelle propriété copiée à partir de celle récupérée
//    this.addProperty(newKey, oldProperty.getValue());
//    // Récupère la propriété ajoutée
//    Property result = this.getProperty(newKey);
//    // Copie les sous-propriétés de la propriété copiée
//    for(Iterator<Property> iterator = oldProperty.getPropertySet().iterator() ; iterator.hasNext() ; )
//    {
//      // Récupère la sous-propriété et la copie
//      Property newSubProperty = new Property(iterator.next(), result);
//      // Ajoute la copie de la sous-propriété
//      result = this.getPropertyDao().add(newSubProperty).getProperty();
//    }
//    // Retourne le résultat
//    return result;
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
//   */
//  @Transactional(readOnly = false)
//  public Property removeProperty(String key) throws PersistenceException, UserException
//  {
//    // Récupère la propriété à supprimer
//    Property property = this.getProperty(key);
//    // Supprime la propriété et la retourne
//    return this.getPropertyDao().remove(property);
//  }
//
//  /**
//   * Cette méthode permet de modifier la clé d'une propriété
//   * @param oldKey Ancienne clé de la propriété à modifier
//   * @param newKey Nouvelle clé de la propriété à modifier
//   * @return La propriété une fois sa clé modifiée
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la propriété à modifier est inexistante ou la nouvelle
//   * clé déjà référencée ou invalide
//   */
//  @Transactional(readOnly = false)
//  public Property updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // Valide la nouvelle clé
//    UtilProperty.checkFullKey(newKey);
//    // Récupère la propriété à modifier
//    Property property = this.getProperty(oldKey);
//    // Récupère la base de la nouvelle clé
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
//    // Modifie la clé de la propriété
//    property.defineKey(UtilProperty.getLastKey(newKey));
//    // On crée le lien avec la nouvelle base
//    PropertyRoot root = this.getRootDao().getRoot();
//    if(newKeyBase.equals(""))
//    {
//      // La propriété est maintenant liée à la racine
//      property.linkTo(root);
//      this.getRootDao().update(root);
//    }
//    else
//    {
//      Property base = root.getProperty(newKeyBase);
//      // La nouvelle base n'existe pas encore, il faut donc la créer
//      if(base == null)
//      {
//        this.getPropertyDao().add(root.addProperty(newKeyBase, ""));
//        base = root.getProperty(newKeyBase);
//      }
//      // La propriété est maintenant liée à sa nouvelle base
//      property.linkTo(base);
//      this.getPropertyDao().update(base);
//    }
//    // Modifie la propriété et la retourne
//    return this.getPropertyDao().update(property);
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
//   */
//  @Transactional(readOnly = false)
//  public Property updateProperty(String key, String value)
//         throws PersistenceException, UserException
//  {
//    // Récupère la propriété à modifier
//    Property property = this.getProperty(key);
//    // Modifie la propriété et la retourne
//    property.defineValue(value);
//    return this.getPropertyDao().update(property);
//  }
//
//  /**
//   * Cette méthode permet d'importer toutes les propriétés du flux en paramètre.
//   * Les propriétés déjà existantes verront leur valeur mise à jour
//   * @param inputStream Flux à partir duquel importer les propriétés
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante ou du flux d'import
//   * @throws UserException Si une clé est invalide
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   */
//  @Transactional(readOnly = false)
//  public void importProperties(InputStream inputStream)
//         throws PersistenceException, UserException, ModelArgumentException
//  {
//    // Importe toutes les propriétés du flux en paramètre
//    Properties properties = new Properties();
//    try
//    {
//      properties.load(inputStream);
//    }
//    catch(IOException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//    // Ajoute ou met à jour chaque propriété
//    for(Enumeration<Object> enumeration = properties.keys() ; enumeration.hasMoreElements() ; )
//    {
//      String key = (String)enumeration.nextElement();
//      String value = properties.getProperty(key);
//      // Récupère la racine des propriétés
//      PropertyRoot root = this.getRoot();
//      Property property = root.getProperty(key);
//      // Il faut ajouter la propriété
//      if(property == null)
//      {
//        this.addProperty(key, value);
//      }
//      // Il faut mettre à jour la propriété
//      else
//      {
//        this.updateProperty(key, value);
//      }
//    }
//  }
//  
//  /**
//   * Getter de la référence du DAO des propriétés racines
//   * @return La référence du DAO des propriétés racines
//   */
//  private PropertyRootDao getRootDao()
//  {
//    return this.rootDao;
//  }
//  /**
//   * Getter de la référence du DAO des propriétés
//   * @return La référence du DAO des propriétés
//   */
//  private PropertyDao getPropertyDao()
//  {
//    return this.propertyDao;
//  }
//}
