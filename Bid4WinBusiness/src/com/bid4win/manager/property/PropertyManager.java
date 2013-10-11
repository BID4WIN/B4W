package com.bid4win.manager.property;

import java.util.Enumeration;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.property.PropertyAbstractManager_;
import com.bid4win.commons.persistence.entity.property.UtilProperty;
import com.bid4win.persistence.dao.property.PropertyDao;
import com.bid4win.persistence.dao.property.PropertyRootDao;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 * Manager de gestion des propriétés incluant leur gestion métier<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyManager")
@Scope("singleton")
public class PropertyManager
       extends PropertyAbstractManager_<Property, PropertyRoot, Account>
{
  /** Référence du DAO des propriétés racines */
  @Autowired
  @Qualifier("PropertyRootDao")
  private PropertyRootDao rootDao = null;
  /** Référence du DAO des propriétés */
  @Autowired
  @Qualifier("PropertyDao")
  private PropertyDao propertyDao = null;

  /**
   * Cette méthode permet d'ajouter une nouvelle propriété
   * @param key Clé de la propriété à ajouter
   * @param value Valeur de la propriété à ajouter
   * @return La première propriété ajoutée (s'il manquait des propriétés intermédiaires)
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la clé est invalide ou déjà référencée
   */
  public Property addProperty(String key, String value)
         throws PersistenceException, ModelArgumentException, UserException
  {
    // Ajoute la propriété définie en argument et récupère la base de l'arbre ajouté
    Property property = this.getRoot().addProperty(key, value);
    // Persiste l'arbre et retourne sa base
    return this.getPropertyDao().add(property);
  }

  /**
   * Cette méthode permet de recopier une propriété à un endroit donné
   * @param oldKey Clé de la propriété originale à copier
   * @param newKey Clé de la future propriété après copie
   * @return La propriété copiée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si on positionne une propriété déjà référencée ou si
   * la propriété à copier est inexistante
   */
  public synchronized Property copyProperty(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException
  {
    // Récupère la propriété à copier
    Property oldProperty = this.getProperty(oldKey);
    // Ajoute la nouvelle propriété copiée à partir de celle récupérée
    this.addProperty(newKey, oldProperty.getValue());
    // Récupère la propriété ajoutée
    Property result = this.getProperty(newKey);
    // Copie les sous-propriétés de la propriété copiée
    for(Property oldSubProperty : oldProperty.getPropertySet())
    {
      // Récupère la sous-propriété et la copie
      Property newSubProperty = new Property(oldSubProperty, result);
      // Ajoute la copie de la sous-propriété
      result = this.getPropertyDao().add(newSubProperty).getProperty();
    }
    // Retourne le résultat
    return result;
  }

  /**
   * Cette méthode permet de modifier la clé d'une propriété
   * @param oldKey Ancienne clé de la propriété à modifier
   * @param newKey Nouvelle clé de la propriété à modifier
   * @return La propriété une fois sa clé modifiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la propriété à modifier est inexistante ou la nouvelle
   * clé déjà référencée ou invalide
   */
  public Property updateKey(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException
  {
    // Récupère la propriété à modifier
    Property property = this.getProperty(oldKey);
    // Valide la nouvelle clé
    UtilProperty.checkFullKey(newKey, property.getMessageRefBase());
    // Récupère la base de la nouvelle clé
    String newKeyBase = UtilProperty.removeLastKey(newKey);
    // On retire le lien avec l'ancienne base
    if(property.hasProperty())
    {
      this.getPropertyDao().update(property.unlinkFromProperty());
    }
    else if(property.hasRoot())
    {
      this.getRootDao().update(property.unlinkFromRoot());
    }
    // Modifie la clé de la propriété
    property.defineKey(UtilProperty.getLastKey(newKey));
    // On crée le lien avec la nouvelle base
    PropertyRoot root = this.getRootDao().getRoot();
    if(newKeyBase.equals(""))
    {
      // La propriété est maintenant liée à la racine
      property.linkTo(root);
      this.getRootDao().update(root);
    }
    else
    {
      Property base = root.getProperty(newKeyBase);
      // La nouvelle base n'existe pas encore, il faut donc la créer
      if(base == null)
      {
        this.getPropertyDao().add(root.addProperty(newKeyBase, ""));
        base = root.getProperty(newKeyBase);
      }
      // La propriété est maintenant liée à sa nouvelle base
      property.linkTo(base);
      this.getPropertyDao().update(base);
    }
    // Modifie la propriété et la retourne
    return this.getPropertyDao().update(property);
  }

  /**
   * Cette méthode permet de supprimer une propriété
   * @param key Clé de la propriété à supprimer
   * @return La propriété supprimée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété à supprimer est inexistante
   */
  public Property removeProperty(String key) throws PersistenceException, UserException
  {
    // Supprime la propriété et la retourne
    return this.getPropertyDao().remove(this.getProperty(key));
  }

  /**
   * Cette méthode permet d'importer toutes les propriétés paramètre. Les propriétés
   * déjà existantes verront leur valeur mise à jour
   * @param properties {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#importProperties(java.util.Properties)
   */
  @Override
  public void importProperties(Properties properties)
         throws PersistenceException, UserException, ModelArgumentException
  {
    // Ajoute ou met à jour chaque propriété
    for(Enumeration<Object> enumeration = properties.keys() ; enumeration.hasMoreElements() ; )
    {
      String key = (String)enumeration.nextElement();
      String value = properties.getProperty(key);
      // Récupère la racine des propriétés
      PropertyRoot root = this.getRoot();
      Property property = root.getProperty(key);
      // Il faut ajouter la propriété
      if(property == null)
      {
        this.addProperty(key, value);
      }
      // Il faut mettre à jour la propriété
      else
      {
        this.updateProperty(key, value);
      }
    }
  }

  /**
   * Getter de la référence du DAO des propriétés racines
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#getRootDao()
   */
  @Override
  protected PropertyRootDao getRootDao()
  {
    return this.rootDao;
  }
  /**
   * Getter de la référence du DAO des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#getPropertyDao()
   */
  @Override
  protected PropertyDao getPropertyDao()
  {
    return this.propertyDao;
  }
}
