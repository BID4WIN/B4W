package trash.property;
//package com.bid4win.manager.property;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import com.bid4win.commons.core.Bid4WinSortedProperties;
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.entity.property.Property;
//import com.bid4win.commons.entity.property.PropertyRoot;
//import com.bid4win.commons.entity.property.UtilProperty;
//
///**
// * Manager de gestion des propriétés<BR>
// * Le manager peut être accédé simplement par sa méthode statique getInstance()<BR>
// * qui renvoi l'unique instance en mémoire positionné lors de la création du manager.<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class PropertyManager
//{
//  /** Nom de la propriété contenant l'url racine de la web app */
//  public static final String ROOT_LOCATION = "server.root_location";
//  /** Nom de la propriété de timeout de session */
//  public static final String SESSION_TIMEOUT = "server.session.timeout";
//
//  /** Référence du manager interne de gestion des propriétés */
//  @Autowired
//  @Qualifier("PropertyManagerInternal")
//  private PropertyManagerInternal manager = null;
//
//  /** Référence vers l'unique instance en mémoire du manager */
//  private static PropertyManager instance = null;
//
//  /**
//   * Renvoi l'unique instance en mémoire
//   * @return L'unique instance en mémoire
//   */
//  public static PropertyManager getInstance()
//  {
//    return PropertyManager.instance;
//  }
//
//  /**
//   * Constructeur
//   */
//  protected PropertyManager()
//  {
//    super();
//    PropertyManager.instance = this;
//  }
//
//  /**
//   * Cette méthode permet de vérifier l'existence d'une propriété en fonction de
//   * sa clé
//   * @param key Clé de la propriété dont il faut vérifier l'existence
//   * @return True si la propriété existe, false sinon
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public boolean hasProperty(String key) throws PersistenceException
//  {
//    try
//    {
//      // Récupère la propriété racine
//      PropertyRoot root = this.getManager().getRoot();
//      // Recherche si la propriété demandée existe
//      return root.getProperty(key) != null;
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette méthode permet de récupérer une propriété en fonction de sa clé
//   * @param key Clé de la propriété à récupérer
//   * @return La propriété récupérée en fonction de sa clé
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété demandée est inexistante
//   */
//  public Property getProperty(String key) throws PersistenceException, UserException
//  {
//    try
//    {
//      return this.getManager().getProperty(key);
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette méthode permet de récupérer la valeur "string" d'une propriété en
//   * fonction de sa clé
//   * @param key Clé de la propriété concernée
//   * @return La valeur "string" de la propriété concernée
//   * @throws PersistenceException Si un problème intervient lors de la
//   * manipulation de la couche persistante
//   * @throws UserException Si la propriété concernée est inexistante
//   */
//  public String getString(String key) throws PersistenceException, UserException
//  {
//      return this.getProperty(key).getValue();
//  }
//
//  /**
//   * Cette méthode permet de récupérer la valeur "int" d'une propriété en
//   * fonction de sa clé
//   * @param key Clé de la propriété concernée
//   * @return La valeur "int" de la propriété concernée
//   * @throws NumberFormatException Si la valeur de la propriété ne peut être
//   * parsée en "int"
//   * @throws PersistenceException Si un problème intervient lors de la
//   * manipulation de la couche persistante
//   * @throws UserException Si la propriété concernée est inexistante
//   */
//  public int getInt(String key) throws NumberFormatException, PersistenceException, UserException
//  {
//      return Integer.valueOf(this.getString(key)).intValue();
//  }
//
//  /**
//   * Cette méthode permet de récupérer la valeur "double" d'une propriété en
//   * fonction de sa clé
//   * @param key Clé de la propriété concernée
//   * @return La valeur "double" de la propriété concernée
//   * @throws NumberFormatException Si la valeur de la propriété ne peut être
//   * parsée en "double"
//   * @throws PersistenceException Si un problème intervient lors de la
//   * manipulation de la couche persistante
//   * @throws UserException Si la propriété concernée est inexistante
//   */
//  public double getDouble(String key) throws NumberFormatException, PersistenceException, UserException
//  {
//      return Double.valueOf(this.getString(key)).doubleValue();
//  }
//
//  /**
//   * Cette méthode permet de récupérer la valeur "boolean" d'une propriété en
//   * fonction de sa clé
//   * @param key Clé de la propriété concernée
//   * @return La valeur "boolean" de la propriété concernée
//   * @throws PersistenceException Si un problème intervient lors de la
//   * manipulation de la couche persistante
//   * @throws UserException Si la propriété concernée est inexistante
//   */
//  public boolean getBoolean(String key) throws PersistenceException, UserException
//  {
//      return Boolean.valueOf(this.getString(key)).booleanValue();
//  }
//
//  /**
//   * Cette méthode permet de récupérer toutes les propriétés du manager
//   * @return La liste de toutes les propriétés du manager
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public Bid4WinSet<Property> getPropertySet() throws PersistenceException
//  {
//    try
//    {
//      // Récupère la propriété racine
//      PropertyRoot root = this.getManager().getRoot();
//      // Retourne le set de ses propriétés
//      return root.getPropertySet();
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette méthode permet d'ajouter une nouvelle propriété
//   * @param key Clé de la propriété à ajouter
//   * @param value Valeur de la propriété à ajouter
//   * @return La première propriété ajoutée (s'il manquait des propriétés intermédiaires)
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la clé est invalide ou déjà référencée
//   */
//  public Property addProperty(String key, String value)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    try
//    {
//      return this.getManager().addProperty(key, value);
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette méthode permet de recopier une propriété à un endroit donné
//   * @param oldKey Clé de la propriété originale à copier
//   * @param newKey Clé de la future propriété copie
//   * @return La propriété copiée
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si on positionne une propriété déjà référencée ou si
//   * la propriété à copier est inexistante
//   */
//  public Property copyProperty(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    try
//    {
//      return this.getManager().copyProperty(oldKey, newKey);
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette méthode permet de récupérer toutes les propriétés qui passent, ou dont
//   * l'une des filles passe le filtre, à savoir la présence de la chaîne à rechercher
//   * dans la clé ou la valeur.
//   * @param searchString La chaîne à rechercher
//   * @return Le set des propriétés filtrées
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la manipulation d'une propriété échoue
//   */
//  public Bid4WinSet<Property> getFilteredPropertySet(String searchString)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    return UtilProperty.getFilteredPropertySet(this.getPropertySet(), searchString);
//  }
//
//  /**
//   * Cette méthode permet de supprimer une propriété
//   * @param key Clé de la propriété à supprimer
//   * @return La propriété supprimée
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété à supprimer est inexistante
//   */
//  public Property removeProperty(String key) throws PersistenceException, UserException
//  {
//    try
//    {
//      return this.getManager().removeProperty(key);
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
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
//   */
//  public Property updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    try
//    {
//      return this.getManager().updateKey(oldKey, newKey);
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette méthode permet de modifier la valeur d'une propriété
//   * @param key Clé de la propriété à modifier
//   * @param value La nouvelle valeur de la propriété
//   * @return La propriété une fois modifiée
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété à modifier est inexistante
//   */
//  public Property updateProperty(String key, String value)
//         throws PersistenceException, UserException
//  {
//    try
//    {
//      return this.getManager().updateProperty(key, value);
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette méthode permet d'exporter toutes les propriétés dans le flux en paramètre
//   * @param outputStream Flux dans lequel exporter les propriétés
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante ou du flux d'export
//   */
//  public void exportProperties(OutputStream outputStream) throws PersistenceException
//  {
//    try
//    {
//      // Récupère la racine des propriétés
//      PropertyRoot root = this.getManager().getRoot();
//      // Tri les propriétés
//      Properties properties = new Bid4WinSortedProperties(root.toProperties());
//      try
//      {
//        // Exporte toutes les propriétés dans le flux en paramètre
//        properties.store(outputStream, "");
//      }
//      catch(IOException ex)
//      {
//        throw new PersistenceException(ex);
//      }
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//  /**
//   * Cette méthode permet d'importer toutes les propriétés du flux en paramètre.
//   * Les propriétés déjà existantes verront leur valeur mise à jour
//   * @param inputStream Flux à partir duquel importer les propriétés
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante ou du flux d'import
//   * @throws UserException Si une clé ou une langue est invalide
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   */
//  public void importProperties(InputStream inputStream)
//         throws PersistenceException, UserException, ModelArgumentException
//  {
//    try
//    {
//      this.getManager().importProperties(inputStream);
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Getter de la référence du manager interne de gestion des propriétés
//   * @return La référence du manager interne de gestion des propriétés
//   */
//  private PropertyManagerInternal getManager()
//  {
//    return this.manager;
//  }
//}
