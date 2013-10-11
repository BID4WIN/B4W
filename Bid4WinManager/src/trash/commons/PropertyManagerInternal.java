package trash.commons;
//package com.bid4win.commons.manager.property;
//
//import java.util.Enumeration;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.exception.UserException.MessageRef;
//import com.bid4win.commons.entity.property.Property;
//import com.bid4win.commons.entity.property.PropertyRoot;
//import com.bid4win.commons.entity.property.UtilProperty;
//import com.bid4win.commons.manager.property.abstraction.PropertyAbstractManagerInternal;
//import com.bid4win.commons.persistence.dao.property.PropertyDao;
//import com.bid4win.commons.persistence.dao.property.PropertyRootDao;
//
///**
// * 
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Component("PropertyManagerInternal")
//@Scope("singleton")
//public class PropertyManagerInternal
//       extends PropertyAbstractManagerInternal<Property, PropertyRoot, PropertyDao, PropertyRootDao>
//{
//  /** R�f�rence du DAO des propri�t�s */
//  @Autowired
//  @Qualifier("PropertyDao")
//  private PropertyDao dao = null;
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
//    // Ajoute la propri�t� d�finie en argument et r�cup�re la base de l'arbre ajout�
//    Property property = this.getRoot().addProperty(key, value);
//    // Persiste l'arbre et retourne sa base
//    return this.getDao().add(property);
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
//    for(Property oldSubProperty : oldProperty.getPropertySet())
//    {
//      // R�cup�re la sous-propri�t� et la copie
//      Property newSubProperty = new Property(oldSubProperty, result);
//      // Ajoute la copie de la sous-propri�t�
//      result = this.getDao().add(newSubProperty).getProperty();
//    }
//    // Retourne le r�sultat
//    return result;
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
//    // Valide la nouvelle cl�
//    UtilProperty.checkFullKey(newKey);
//    // R�cup�re la propri�t� � modifier
//    Property property = this.getProperty(oldKey);
//    // R�cup�re la base de la nouvelle cl�
//    String newKeyBase = UtilProperty.removeLastKey(newKey);
//    // On retire le lien avec l'ancienne base
//    if(property.hasProperty())
//    {
//      this.getDao().update(property.unlinkFromProperty());
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
//        this.getDao().add(root.addProperty(newKeyBase, ""));
//        base = root.getProperty(newKeyBase);
//      }
//      // La propri�t� est maintenant li�e � sa nouvelle base
//      property.linkTo(base);
//      this.getDao().update(base);
//    }
//    // Modifie la propri�t� et la retourne
//    return this.getDao().update(property);
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
//    // Supprime la propri�t� et la retourne
//    return this.getDao().remove(this.getProperty(key));
//  }
//  
//  /**
//   * Cette m�thode permet d'importer toutes les propri�t�s en param�tre, celles
//   * d�j� existantes voyant leur valeur mise � jour
//   * @param properties {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see com.bid4win.commons.manager.property.abstraction.PropertyAbstractManagerInternal#importProperties(java.util.Properties)
//   */
//  @Override
//  public void importProperties(Properties properties)
//         throws PersistenceException, UserException, ModelArgumentException
//  {
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
//   * 
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.property.abstraction.PropertyAbstractManagerInternal#getUnknownPropertyMessage()
//   */
//  @Override
//  protected MessageRef getUnknownPropertyMessage()
//  {
//    return MessageRef.ERROR_PROPERTY_UNKNOWN;
//  }
//
//  /**
//   * Getter de la r�f�rence du DAO des propri�t�s
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManagerInternal#getDao()
//   */
//  @Override
//  protected PropertyDao getDao()
//  {
//    return this.dao;
//  }
//}
