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
// * Manager de gestion des propri�t�s<BR>
// * Le manager peut �tre acc�d� simplement par sa m�thode statique getInstance()<BR>
// * qui renvoi l'unique instance en m�moire positionn� lors de la cr�ation du manager.<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class PropertyManager
//{
//  /** Nom de la propri�t� contenant l'url racine de la web app */
//  public static final String ROOT_LOCATION = "server.root_location";
//  /** Nom de la propri�t� de timeout de session */
//  public static final String SESSION_TIMEOUT = "server.session.timeout";
//
//  /** R�f�rence du manager interne de gestion des propri�t�s */
//  @Autowired
//  @Qualifier("PropertyManagerInternal")
//  private PropertyManagerInternal manager = null;
//
//  /** R�f�rence vers l'unique instance en m�moire du manager */
//  private static PropertyManager instance = null;
//
//  /**
//   * Renvoi l'unique instance en m�moire
//   * @return L'unique instance en m�moire
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
//   * Cette m�thode permet de v�rifier l'existence d'une propri�t� en fonction de
//   * sa cl�
//   * @param key Cl� de la propri�t� dont il faut v�rifier l'existence
//   * @return True si la propri�t� existe, false sinon
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public boolean hasProperty(String key) throws PersistenceException
//  {
//    try
//    {
//      // R�cup�re la propri�t� racine
//      PropertyRoot root = this.getManager().getRoot();
//      // Recherche si la propri�t� demand�e existe
//      return root.getProperty(key) != null;
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer une propri�t� en fonction de sa cl�
//   * @param key Cl� de la propri�t� � r�cup�rer
//   * @return La propri�t� r�cup�r�e en fonction de sa cl�
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� demand�e est inexistante
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
//   * Cette m�thode permet de r�cup�rer la valeur "string" d'une propri�t� en
//   * fonction de sa cl�
//   * @param key Cl� de la propri�t� concern�e
//   * @return La valeur "string" de la propri�t� concern�e
//   * @throws PersistenceException Si un probl�me intervient lors de la
//   * manipulation de la couche persistante
//   * @throws UserException Si la propri�t� concern�e est inexistante
//   */
//  public String getString(String key) throws PersistenceException, UserException
//  {
//      return this.getProperty(key).getValue();
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer la valeur "int" d'une propri�t� en
//   * fonction de sa cl�
//   * @param key Cl� de la propri�t� concern�e
//   * @return La valeur "int" de la propri�t� concern�e
//   * @throws NumberFormatException Si la valeur de la propri�t� ne peut �tre
//   * pars�e en "int"
//   * @throws PersistenceException Si un probl�me intervient lors de la
//   * manipulation de la couche persistante
//   * @throws UserException Si la propri�t� concern�e est inexistante
//   */
//  public int getInt(String key) throws NumberFormatException, PersistenceException, UserException
//  {
//      return Integer.valueOf(this.getString(key)).intValue();
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer la valeur "double" d'une propri�t� en
//   * fonction de sa cl�
//   * @param key Cl� de la propri�t� concern�e
//   * @return La valeur "double" de la propri�t� concern�e
//   * @throws NumberFormatException Si la valeur de la propri�t� ne peut �tre
//   * pars�e en "double"
//   * @throws PersistenceException Si un probl�me intervient lors de la
//   * manipulation de la couche persistante
//   * @throws UserException Si la propri�t� concern�e est inexistante
//   */
//  public double getDouble(String key) throws NumberFormatException, PersistenceException, UserException
//  {
//      return Double.valueOf(this.getString(key)).doubleValue();
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer la valeur "boolean" d'une propri�t� en
//   * fonction de sa cl�
//   * @param key Cl� de la propri�t� concern�e
//   * @return La valeur "boolean" de la propri�t� concern�e
//   * @throws PersistenceException Si un probl�me intervient lors de la
//   * manipulation de la couche persistante
//   * @throws UserException Si la propri�t� concern�e est inexistante
//   */
//  public boolean getBoolean(String key) throws PersistenceException, UserException
//  {
//      return Boolean.valueOf(this.getString(key)).booleanValue();
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer toutes les propri�t�s du manager
//   * @return La liste de toutes les propri�t�s du manager
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public Bid4WinSet<Property> getPropertySet() throws PersistenceException
//  {
//    try
//    {
//      // R�cup�re la propri�t� racine
//      PropertyRoot root = this.getManager().getRoot();
//      // Retourne le set de ses propri�t�s
//      return root.getPropertySet();
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Cette m�thode permet d'ajouter une nouvelle propri�t�
//   * @param key Cl� de la propri�t� � ajouter
//   * @param value Valeur de la propri�t� � ajouter
//   * @return La premi�re propri�t� ajout�e (s'il manquait des propri�t�s interm�diaires)
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la cl� est invalide ou d�j� r�f�renc�e
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
//   * Cette m�thode permet de recopier une propri�t� � un endroit donn�
//   * @param oldKey Cl� de la propri�t� originale � copier
//   * @param newKey Cl� de la future propri�t� copie
//   * @return La propri�t� copi�e
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si on positionne une propri�t� d�j� r�f�renc�e ou si
//   * la propri�t� � copier est inexistante
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
//   * Cette m�thode permet de r�cup�rer toutes les propri�t�s qui passent, ou dont
//   * l'une des filles passe le filtre, � savoir la pr�sence de la cha�ne � rechercher
//   * dans la cl� ou la valeur.
//   * @param searchString La cha�ne � rechercher
//   * @return Le set des propri�t�s filtr�es
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la manipulation d'une propri�t� �choue
//   */
//  public Bid4WinSet<Property> getFilteredPropertySet(String searchString)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    return UtilProperty.getFilteredPropertySet(this.getPropertySet(), searchString);
//  }
//
//  /**
//   * Cette m�thode permet de supprimer une propri�t�
//   * @param key Cl� de la propri�t� � supprimer
//   * @return La propri�t� supprim�e
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� � supprimer est inexistante
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
//   * Cette m�thode permet de modifier la cl� d'une propri�t�
//   * @param oldKey Ancienne cl� de la propri�t� � modifier
//   * @param newKey Nouvelle cl� de la propri�t� � modifier
//   * @return La propri�t� une fois sa cl� modifi�e
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la propri�t� � modifier est inexistante ou la nouvelle
//   * cl� d�j� r�f�renc�e ou invalide
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
//   * Cette m�thode permet de modifier la valeur d'une propri�t�
//   * @param key Cl� de la propri�t� � modifier
//   * @param value La nouvelle valeur de la propri�t�
//   * @return La propri�t� une fois modifi�e
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� � modifier est inexistante
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
//   * Cette m�thode permet d'exporter toutes les propri�t�s dans le flux en param�tre
//   * @param outputStream Flux dans lequel exporter les propri�t�s
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante ou du flux d'export
//   */
//  public void exportProperties(OutputStream outputStream) throws PersistenceException
//  {
//    try
//    {
//      // R�cup�re la racine des propri�t�s
//      PropertyRoot root = this.getManager().getRoot();
//      // Tri les propri�t�s
//      Properties properties = new Bid4WinSortedProperties(root.toProperties());
//      try
//      {
//        // Exporte toutes les propri�t�s dans le flux en param�tre
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
//   * Cette m�thode permet d'importer toutes les propri�t�s du flux en param�tre.
//   * Les propri�t�s d�j� existantes verront leur valeur mise � jour
//   * @param inputStream Flux � partir duquel importer les propri�t�s
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante ou du flux d'import
//   * @throws UserException Si une cl� ou une langue est invalide
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
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
//   * Getter de la r�f�rence du manager interne de gestion des propri�t�s
//   * @return La r�f�rence du manager interne de gestion des propri�t�s
//   */
//  private PropertyManagerInternal getManager()
//  {
//    return this.manager;
//  }
//}
