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
// * Manager de gestion des propri�t�s<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class PropertyManager
//{
//  /** Pattern de l'identifiant du manager */
//  private final static String ID_PATTERN = "BBBBBBBBBB";
//  /** C'est l'instance unique utilis�e comme singleton */
//  private final static PropertyManager instance = new PropertyManager();
//
//  /**
//   * Le manager est un singleton. On passe donc par cette m�thode pour r�cup�rer
//   * l'unique instance en m�moire
//   * @return L'instance du manager
//   */
//  public static PropertyManager getInstance()
//  {
//    return PropertyManager.instance;
//  }
//
//  /** Marqueur d'initialisation */
//  private boolean initialized = false;
//  /** Identifiant de protection des propri�t�s */
//  private String protectionId = "";
//  /** Map des propri�t�s class�es selon leur cl� */
//  private PropertyRoot propertyRoot = null;
//
//  /**
//   * Constructeur prot�g� car accessible seulement comme un singleton
//   */
//  protected PropertyManager()
//  {
//    super();
//  }
//
//  /**
//   * Cette m�thode permet d'initialiser le manager avec les propri�t�s d�j�
//   * d�finies
//   * @throws PersistenceException Si un probl�me lors de la manipulation des
//   *           donn�es persist�es intervient
//   * @throws ModelArgumentException Si un probl�me lors de la manipulation des
//   *           donn�es intervient
//   * @throws ProtectionException Si la protection n'est pas v�rifi�e
//   */
//  public void init() throws PersistenceException, ModelArgumentException, ProtectionException
//  {
//    // Manager d�j� initialis�
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
//   * Cette m�thode permet de r�cup�rer une propri�t� en fonction de sa cl�
//   * @param key Cl� de la propri�t� � r�cup�rer
//   * @return La propri�t� r�cup�r�e en fonction de sa cl�
//   * @throws ModelArgumentException Si la r�cup�ration de la propri�t� �choue
//   */
//  public Property getProperty(String key) throws ModelArgumentException
//  {
//    Property property = this.propertyRoot.getProperty(key);
//    UtilObject.checkNotNull("property", property);
//    return property;
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer toutes les propri�t� du manager
//   * @return La liste de toutes les propri�t�s du manager
//   */
//  public PropertyList getPropertyList()
//  {
//    return this.propertyRoot.getPropertyList();
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer toutes propri�t�s qui passent, ou dont
//   * l'une des filles passent le filtre, � savoir la pr�sence le la cha�ne �
//   * rechercher dans la cl� ou la valeur.
//   * @param searchString La cha�ne � rechercher
//   * @return La liste des propri�t�s
//   * @throws ModelArgumentException Si la r�cup�ration des propri�t�s �chouent
//   * @throws ProtectionException Si la r�cup�ration des propri�t�s �chouent
//   */
//  public PropertyList getFilteredPropertyList(String searchString) throws ProtectionException, ModelArgumentException
//  {
//    // La liste des propri�t�s qui passent le filtre
//    PropertyList resPropertyList = new PropertyList();
//    // On filtre toutes les propri�t�s
//    for(int i = 0 ; i < this.getPropertyList().size() ; i++)
//    {
//      Property resSubProperty = this.getFilteredProperty(this.getPropertyList().get(i), searchString);
//      if(resSubProperty != null)
//      {
//        // On ajoute les propri�t�s qui passent le filtre
//        resPropertyList.add(resSubProperty);
//      }
//    }
//    return resPropertyList;
//  }
//
//  /**
//   * Cette m�thode renvoi une copie de la propri�t� en param�tre si elle, ou
//   * l'une de ses propri�t�s filles, passent le filtre, � savoir la pr�sence le
//   * la cha�ne � rechercher dans la cl� ou la valeur.
//   * @param property La propri�t� � filtrer
//   * @param searchString La cha�ne � rechercher
//   * @return La propri�t� filtr�e
//   * @throws ModelArgumentException Si la r�cup�ration de la propri�t� �choue
//   * @throws ProtectionException Si la r�cup�ration de la propri�t� �choue
//   */
//  public Property getFilteredProperty(Property property, String searchString) throws ProtectionException, ModelArgumentException
//  {
//    // La propri�t� � retourner si elle, ou une de ses filles, passe le filtre
//    Property resProperty = new Property(property.getKey(), property.getValue());
//    // On filtre toutes les propri�t�s filles
//    for(int i = 0 ; i < property.getPropertyList().size() ; i++)
//    {
//      Property resSubProperty = this.getFilteredProperty(property.getPropertyList().get(i), searchString);
//      if(resSubProperty != null)
//      {
//        // On ajoute les propri�t�s filles qui passent le filtre
//        resProperty.addProperty(resSubProperty);
//      }
//    }
//    // Si la propri�t� n'a pas de fille ou qu'aucune de ses filles n'a pass� le
//    // filtre
//    if(resProperty.getPropertyList() == null || resProperty.getPropertyList().size() == 0)
//    {
//      // On v�rifie la pr�sence de la cha�ne � rechercher dans la cl� ou la
//      // valeur de la propri�t�
//      Matcher matcherKey = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(property.getFullKey());
//      Matcher matcherValue = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(property.getValue());
//      if(!matcherKey.find() && !matcherValue.find())
//      {
//        // Si la cha�ne � rechercher n'est pr�sente ni dans la cl�, ni dans la
//        // valeur de la propri�t� on retourne null
//        return null;
//      }
//    }
//    return resProperty;
//  }
//
//  /**
//   * Cette m�thode permet d'ajouter une nouvelle propri�t�
//   * @param key Cl� de la propri�t� � ajouter
//   * @param value Valeur de la propri�t� � ajouter
//   * @return La premi�re propri�t� ajout�e (s'il manquait des propri�t�s
//   *         interm�diaires)
//   * @throws PersistenceException Si un probl�me lors de la manipulation des
//   *           donn�es persist�es intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propri�t� � ajouter d�j� existante
//   * @throws ProtectionException Si la protection n'est pas v�rifi�e
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
//   * Cette m�thode permet d'ajouter une nouvelle propri�t�
//   * @param key Cl� de la propri�t� � ajouter
//   * @param property Propri�t� � ajouter
//   * @return La premi�re propri�t� ajout�e (s'il manquait des propri�t�s
//   *         interm�diaires)
//   * @throws PersistenceException Si un probl�me lors de la manipulation des
//   *           donn�es persist�es intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propri�t� � ajouter d�j� existante
//   * @throws ProtectionException Si la protection n'est pas v�rifi�e
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
//   * Cette m�thode permet de supprimer une propri�t�
//   * @param key Cl� de la propri�t� � supprimer
//   * @return La propri�t� supprim�e
//   * @throws PersistenceException Si un probl�me lors de la manipulation des
//   *           donn�es persist�es intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propri�t� � supprimer inexistante
//   * @throws ProtectionException Si la protection n'est pas v�rifi�e
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
//   * Cette m�thode permet de modifier la valeur d'une propri�t�
//   * @param key Cl� de la propri�t� � modifier
//   * @param value La nouvelle valeur de la propri�t�
//   * @return La propri�t� une fois modifi�e
//   * @throws PersistenceException Si un probl�me lors de la manipulation des
//   *           donn�es persist�es intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propri�t� � modifier inexistante
//   * @throws ProtectionException Si la protection n'est pas v�rifi�e
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
//   * Cette m�thode permet de modifier la cl� d'une propri�t�
//   * @param oldKey Ancienne cl� de la propri�t� � modifier
//   * @param newKey Nouvelle cl� de la propri�t� � modifier
//   * @return La propri�t� une fois sa cl� modifi�e
//   * @throws PersistenceException Si un probl�me lors de la manipulation des
//   *           donn�es persist�es intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propri�t� � modifier inexistante ou la nouvelle cl� d�j�
//   *           r�f�renc�e
//   * @throws ProtectionException Si la protection n'est pas v�rifi�e
//   */
//  public synchronized Property updateKey(String oldKey, String newKey) throws PersistenceException, ModelArgumentException, ProtectionException
//  {
//    this.startProtection();
//    Property property = null;
//    try
//    {
//      // Retire l'ancienne propri�t�
//      Property old = this.propertyRoot.removeProperty(oldKey);
//      // Ajoute la nouvelle propri�t�
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
//   * Cette m�thode permet de recopier une propri�t� � un endroit donn�
//   * @param oldKey Cl� de la propri�t� originale � copier
//   * @param newKey Cl� de la future propri�t� copie
//   * @return La propri�t� copi�e
//   * @throws PersistenceException Si un probl�me lors de la manipulation des
//   *           donn�es persist�es intervient
//   * @throws ModelArgumentException Si les arguments sont invalides ou la
//   *           propri�t� � modifier inexistante ou la nouvelle cl� d�j�
//   *           r�f�renc�e
//   * @throws ProtectionException Si la protection n'est pas v�rifi�e
//   */
//  public synchronized Property copyProperty(String oldKey, String newKey) throws ModelArgumentException, ProtectionException, PersistenceException
//  {
//    this.startProtection();
//    Property copyProperty = null;
//    try
//    {
//      Property oldProperty = this.getProperty(oldKey);
//      // Ajoute la nouvelle propri�t�
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
//   * Getter de l'identifiant de protection des propri�t�s
//   * @return L'identifiant de protection des propri�t�s
//   */
//  private String getProtectionId()
//  {
//    return this.protectionId;
//  }
//
//  /**
//   * Setter de l'identifiant de protection des propri�t�s
//   * @param protectionId Identifiant de protection des propri�t�s � positionner
//   */
//  private void setProtectionId(String protectionId)
//  {
//    this.protectionId = protectionId;
//  }
//
//  /**
//   * M�thode permettant de d�marrer la protection des propri�t�s
//   */
//  private void startProtection()
//  {
//    ObjectProtector.getInstance().start(this.getProtectionId());
//  }
//
//  /**
//   * M�thode permettant d'arr�ter la protection des propri�t�s
//   * @throws ProtectionException Si la protection n'est pas v�rifi�e
//   */
//  private void stopProtection() throws ProtectionException
//  {
//    ObjectProtector.getInstance().stop(this.getProtectionId());
//  }
//}
