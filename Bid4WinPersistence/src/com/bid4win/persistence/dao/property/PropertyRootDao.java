package com.bid4win.persistence.dao.property;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 * DAO pour les entit�s de la classe PropertyRoot<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * D�fini la cl� unique de la racine des propri�t�s
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_#getUniqueId()
   */
  @Override
  public int getUniqueId()
  {
    return PropertyRoot.UNIQUE_ID;
  }

  /**
   * Cette m�thode permet de cr�er la racine des propri�t�s
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_#createPropertyRoot()
   */
  @Override
  protected PropertyRoot createPropertyRoot()
  {
    return new PropertyRoot();
  }
}
