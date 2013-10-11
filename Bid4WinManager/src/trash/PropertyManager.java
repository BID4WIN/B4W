//package trash;
//
//import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Properties;
//import java.util.PropertyResourceBundle;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.security.IdGenerator;
//import com.bid4win.core.Property;
//import com.bid4win.core.UtilProperty;
//import com.bid4win.core.collection.PropertyList;
//import com.bid4win.core.security.ObjectProtector;
//import com.bid4win.core.security.exception.ProtectionException;
//
///**
// * Manager de gestion des propriétés<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class PropertyManager
//{
//  /** Pattern de l'identifiant du manager */
//  private final static String ID_PATTERN = "BBBBBBBBBB";
//  /** C'est l'instance unique utilisée comme singleton */
//  private final static PropertyManager instance = new PropertyManager();
//
//  /**
//   * Le manager est un singleton. On passe donc par cette méthode pour récupérer
//   * l'unique instance en mémoire
//   * @return L'instance du manager
//   */
//  public static PropertyManager getInstance()
//  {
//    return PropertyManager.instance;
//  }
//
//  /** Marqueur d'initialisation */
//  private boolean initialized = false;
//  /** Identifiant de protection des propriétés */
//  private String protectionId = "";
//  /** Map des propriétés classées selon leur clé */
//  private PropertyRoot propertyRoot = null;
//
//  /**
//   * Constructeur protégé car accessible seulement comme un singleton
//   */
//  protected PropertyManager()
//  {
//    super();
//  }
//
//  /**
//   * Cette méthode permet d'initialiser le manager avec les propriétés déjà
//   * définies
//   * @throws PersistenceException Si un problème lors de la manipulation des
//   *           données persistées intervient
//   * @throws ModelArgumentException Si un problème lors de la manipulation des
//   *           données intervient
//   * @throws ProtectionException Si la protection n'est pas vérifiée
//   */
//  public void init() throws PersistenceException, ModelArgumentException, ProtectionException
//  {
//    // Manager déjà initialisé
//    if(this.initialized)
//    {
//      return;
//    }
//    this.setProtectionId(IdGenerator.getInstance().generateId(ID_PATTERN));
//    this.startProtection();
//    try
//    {
//      this.propertyRoot = new PropertyRoot();
//      // ************************************************
//      // TODO PERSISTENCE
//      PropertyResourceBundle properties;
//      try
//      {
//        properties = new PropertyResourceBundle(new FileInputStream("d:/bid4win.properties"));
//      }
//      catch(Exception ex)
//      {
//        throw new PersistenceException(ex);
//      }
//      ArrayList keys = new ArrayList(properties.keySet());
//      Collections.sort(keys);
//      for(int i = 0 ; i < keys.size() ; i++)
//      {
//        String key = (String)keys.get(i);
//        this.propertyRoot.addProperty(key, properties.getString(key));
//      }
//      // TODO PERSISTENCE
//      // ************************************************
//      this.initialized = true;
//    }
//    finally
//    {
//      this.stopProtection();
//    }
//  }
//
//  /**
//   * Cette méthode permet de récupérer une propriété en fonction de sa clé
//   * @param key Clé de la propriété à récupérer
//   * @return La propriété récupérée en fonction de sa clé
//   * @throws ModelArgumentException Si la récupération de la propriété échoue
//   */
//  public Property getProperty(String key) throws ModelArgumentException
//  {
//    Property property = this.propertyRoot.getProperty(key);
//    UtilObject.checkNotNull("property", property);
//    return property;
//  }
//
//  /**
//   * Cette méthode permet de récupérer toutes les propriété du manager
//   * @return La liste de toutes les propriétés du manager
//   */
//  public PropertyList getPropertyList()
//  {
//    return this.propertyRoot.getPropertyList();
//  }
//
//  /**
//   * Cette méthode permet de récupérer toutes propriétés qui passent, ou dont
//   * l'une des filles passent le filtre, à savoir la présence le la chaîne à
//   * rechercher dans la clé ou la valeur.
//   * @param searchString La chaîne à rechercher
//   * @return La liste des propriétés
//   * @throws ModelArgumentException Si la récupération des propriétés échouent
//   * @throws ProtectionException Si la récupération des propriétés échouent
//   */
//  public PropertyList getFilteredPropertyList(String searchString) throws ProtectionException, ModelArgumentException
//  {
//    // La liste des propriétés qui passent le filtre
//    PropertyList resPropertyList = new PropertyList();
//    // On filtre toutes les propriétés
//    for(int i = 0 ; i < this.getPropertyList().size() ; i++)
//    {
//      Property resSubProperty = this.getFilteredProperty(this.getPropertyList().get(i), searchString);
//      if(resSubProperty != null)
//      {
//        // On ajoute les propriétés qui passent le filtre
//        resPropertyList.add(resSubProperty);
//      }
//    }
//    return resPropertyList;
//  }
//
//  /**
//   * Cette méthode renvoi une copie de la propriété en paramètre si elle, ou
//   * l'une de ses propriétés filles, passent le filtre, à savoir la présence le
//   * la chaîne à rechercher dans la clé ou la valeur.
//   * @param property La propriété à filtrer
//   * @param searchString La chaîne à rechercher
//   * @return La propriété filtrée
//   * @throws ModelArgumentException Si la récupération de la propriété échoue
//   * @throws ProtectionException Si la récupération de la propriété échoue
//   */
//  public Property getFilteredProperty(Property property, String searchString) throws ProtectionException, ModelArgumentException
//  {
//    // La propriété à retourner si elle, ou une de ses filles, passe le filtre
//    Property resProperty = new Property(property.getKey(), property.getValue());
//    // On filtre toutes les propriétés filles
//    for(int i = 0 ; i < property.getPropertyList().size() ; i++)
//    {
//      Property resSubProperty = this.getFilteredProperty(property.getPropertyList().get(i), searchString);
//      if(resSubProperty != null)
//      {
//        // On ajoute les propriétés filles qui passent le filtre
//        resProperty.addProperty(resSubProperty);
//      }
//    }
//    // Si la propriété n'a pas de fille ou qu'aucune de ses filles n'a passé le
//    // filtre
//    if(resProperty.getPropertyList() == null || resProperty.getPropertyList().size() == 0)
//    {
//      // On vérifie la présence de la chaîne à rechercher dans la clé ou la
//      // valeur de la propriété
//      Matcher matcherKey = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(property.getFullKey());
//      Matcher matcherValue = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(property.getValue());
//      if(!matcherKey.find() && !matcherValue.find())
//      {
//        // Si la chaîne à rechercher n'est présente ni dans la clé, ni dans la
//        // valeur de la propriété on retourne null
//        return null;
//      }
//    }
//    return resProperty;
//  }
//
//  /**
//   * Cette méthode permet d'ajouter une nouvelle propriété
//   * @param key Clé de la propriété à ajouter
//   * @param value Valeur de la propriété à ajouter
//   * @return La première propriété ajoutée (s'il manquait des propriétés
//   *         intermédiaires)
//   * @throws PersistenceException Si un problème lors de la manipulation des
//   *           données persistées intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propriété à ajouter déjà existante
//   * @throws ProtectionException Si la protection n'est pas vérifiée
//   */
//  public synchronized Property addProperty(String key, String value) throws PersistenceException, ModelArgumentException, ProtectionException
//  {
//    this.startProtection();
//    Property addedProperty = null;
//    try
//    {
//      if(this.propertyRoot.getProperty(key) != null)
//      {
//        addedProperty = this.updateProperty(key, value);
//      }
//      else
//      {
//        addedProperty = this.addProperty(key, new Property(UtilProperty.getLastKey(key), value));
//      }
//    }
//    finally
//    {
//      this.stopProtection();
//    }
//    return addedProperty;
//  }
//
//  /**
//   * Cette méthode permet d'ajouter une nouvelle propriété
//   * @param key Clé de la propriété à ajouter
//   * @param property Propriété à ajouter
//   * @return La première propriété ajoutée (s'il manquait des propriétés
//   *         intermédiaires)
//   * @throws PersistenceException Si un problème lors de la manipulation des
//   *           données persistées intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propriété à ajouter déjà existante
//   * @throws ProtectionException Si la protection n'est pas vérifiée
//   */
//  public synchronized Property addProperty(String key, Property property) throws PersistenceException, ModelArgumentException, ProtectionException
//  {
//    this.startProtection();
//    Property addedProperty = null;
//    try
//    {
//      addedProperty = this.propertyRoot.addProperty(key, property);
//      // ************************************************
//      // TODO PERSISTENCE
//      this.persist();
//      // TODO PERSISTENCE
//      // ************************************************
//    }
//    finally
//    {
//      this.stopProtection();
//    }
//    return addedProperty;
//  }
//
//  /**
//   * Cette méthode permet de supprimer une propriété
//   * @param key Clé de la propriété à supprimer
//   * @return La propriété supprimée
//   * @throws PersistenceException Si un problème lors de la manipulation des
//   *           données persistées intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propriété à supprimer inexistante
//   * @throws ProtectionException Si la protection n'est pas vérifiée
//   */
//  public synchronized Property removeProperty(String key) throws PersistenceException, ModelArgumentException, ProtectionException
//  {
//    this.startProtection();
//    Property property = null;
//    try
//    {
//      property = this.propertyRoot.removeProperty(key);
//      // ************************************************
//      // TODO PERSISTENCE
//      this.persist();
//      // TODO PERSISTENCE
//      // ************************************************
//    }
//    finally
//    {
//      this.stopProtection();
//    }
//    return property;
//  }
//
//  /**
//   * Cette méthode permet de modifier la valeur d'une propriété
//   * @param key Clé de la propriété à modifier
//   * @param value La nouvelle valeur de la propriété
//   * @return La propriété une fois modifiée
//   * @throws PersistenceException Si un problème lors de la manipulation des
//   *           données persistées intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propriété à modifier inexistante
//   * @throws ProtectionException Si la protection n'est pas vérifiée
//   */
//  public synchronized Property updateProperty(String key, String value) throws PersistenceException, ModelArgumentException, ProtectionException
//  {
//    this.startProtection();
//    Property property = null;
//    try
//    {
//      property = this.getProperty(key);
//      property.updateValue(value);
//      // ************************************************
//      // TODO PERSISTENCE
//      this.persist();
//      // TODO PERSISTENCE
//      // ************************************************
//    }
//    finally
//    {
//      this.stopProtection();
//    }
//    return property;
//  }
//
//  /**
//   * Cette méthode permet de modifier la clé d'une propriété
//   * @param oldKey Ancienne clé de la propriété à modifier
//   * @param newKey Nouvelle clé de la propriété à modifier
//   * @return La propriété une fois sa clé modifiée
//   * @throws PersistenceException Si un problème lors de la manipulation des
//   *           données persistées intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propriété à modifier inexistante ou la nouvelle clé déjà
//   *           référencée
//   * @throws ProtectionException Si la protection n'est pas vérifiée
//   */
//  public synchronized Property updateKey(String oldKey, String newKey) throws PersistenceException, ModelArgumentException, ProtectionException
//  {
//    this.startProtection();
//    Property property = null;
//    try
//    {
//      // Retire l'ancienne propriété
//      Property old = this.propertyRoot.removeProperty(oldKey);
//      // Ajoute la nouvelle propriété
//      property = this.addProperty(newKey, new Property(UtilProperty.getLastKey(newKey), old));
//    }
//    finally
//    {
//      this.stopProtection();
//    }
//    return property;
//  }
//
//  /**
//   * Cette méthode permet de recopier une propriété à un endroit donné
//   * @param oldKey Clé de la propriété originale à copier
//   * @param newKey Clé de la future propriété copie
//   * @return La propriété copiée
//   * @throws PersistenceException Si un problème lors de la manipulation des
//   *           données persistées intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propriété à modifier inexistante ou la nouvelle clé déjà
//   *           référencée
//   * @throws ProtectionException Si la protection n'est pas vérifiée
//   */
//  public synchronized Property copyProperty(String oldKey, String newKey) throws ModelArgumentException, ProtectionException, PersistenceException
//  {
//    this.startProtection();
//    Property copyProperty = null;
//    try
//    {
//      Property oldProperty = this.getProperty(oldKey);
//      // Ajoute la nouvelle propriété
//      copyProperty = this.addProperty(newKey, new Property(UtilProperty.getLastKey(newKey), oldProperty));
//    }
//    finally
//    {
//      this.stopProtection();
//    }
//    return copyProperty;
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @throws PersistenceException TODO
//   */
//  private void persist() throws PersistenceException
//  {
//    try
//    {
//      BufferedWriter writer = new BufferedWriter(new FileWriter("d:/bid4win.properties"));
//      Properties properties = this.propertyRoot.toProperties();
//      ArrayList keys = new ArrayList(properties.keySet());
//      Collections.sort(keys);
//      for(int i = 0 ; i < keys.size() ; i++)
//      {
//        String key = (String)keys.get(i);
//        writer.write(key + "=" + properties.getProperty(key));
//        writer.newLine();
//      }
//      writer.close();
//    }
//    catch(Exception ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Getter de l'identifiant de protection des propriétés
//   * @return L'identifiant de protection des propriétés
//   */
//  private String getProtectionId()
//  {
//    return this.protectionId;
//  }
//
//  /**
//   * Setter de l'identifiant de protection des propriétés
//   * @param protectionId Identifiant de protection des propriétés à positionner
//   */
//  private void setProtectionId(String protectionId)
//  {
//    this.protectionId = protectionId;
//  }
//
//  /**
//   * Méthode permettant de démarrer la protection des propriétés
//   */
//  private void startProtection()
//  {
//    ObjectProtector.getInstance().start(this.getProtectionId());
//  }
//
//  /**
//   * Méthode permettant d'arrêter la protection des propriétés
//   * @throws ProtectionException Si la protection n'est pas vérifiée
//   */
//  private void stopProtection() throws ProtectionException
//  {
//    ObjectProtector.getInstance().stop(this.getProtectionId());
//  }
//}
