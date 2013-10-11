package com.bid4win.persistence.dao.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 * DAO pour les entités de la classe Property<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyDao")
@Scope("singleton")
public class PropertyDao extends PropertyAbstractDao_<Property, PropertyRoot>
{
  /** Référence du DAO des propriétés racines */
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
   * Getter du DAO des propriétés racines
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#getRootDao()
   */
  @Override
  public PropertyRootDao getRootDao()
  {
    return this.rootDao;
  }
}
