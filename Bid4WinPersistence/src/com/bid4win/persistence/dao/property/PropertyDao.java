package com.bid4win.persistence.dao.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 * DAO pour les entit�s de la classe Property<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("PropertyDao")
@Scope("singleton")
public class PropertyDao extends PropertyAbstractDao_<Property, PropertyRoot>
{
  /** R�f�rence du DAO des propri�t�s racines */
  @Autowired
  @Qualifier("PropertyRootDao")
  private PropertyRootDao rootDao;

  /**
   * Constructeur
   */
  protected PropertyDao()
  {
    super(Property.class);
  }
  
  /**
   * Getter du DAO des propri�t�s racines
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#getRootDao()
   */
  @Override
  public PropertyRootDao getRootDao()
  {
    return this.rootDao;
  }
}
