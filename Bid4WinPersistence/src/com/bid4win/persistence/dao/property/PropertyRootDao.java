package com.bid4win.persistence.dao.property;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 * DAO pour les entités de la classe PropertyRoot<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyRootDao")
@Scope("singleton")
public class PropertyRootDao extends PropertyRootAbstractDao_<PropertyRoot, Property>
{
  /**
   * Constructeur
   */
  protected PropertyRootDao()
  {
    super(PropertyRoot.class);
  }

  /**
   * Défini la clé unique de la racine des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_#getUniqueId()
   */
  @Override
  public int getUniqueId()
  {
    return PropertyRoot.UNIQUE_ID;
  }

  /**
   * Cette méthode permet de créer la racine des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_#createPropertyRoot()
   */
  @Override
  protected PropertyRoot createPropertyRoot()
  {
    return new PropertyRoot();
  }
}
