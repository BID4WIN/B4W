package com.bid4win.commons.persistence.entity.property;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PropertyRootAbstractStub extends PropertyRootAbstract<PropertyRootAbstractStub, PropertyAbstractStub>
{
  /** Définition de l'identifiant unique de la racine des propriétés */
  public final static int UNIQUE_ID = 9;

  /**
   * Constructeur de la racine des propriétés. L'identifiant y est directement
   * précisé car celui-ci se doit d'être unique
   */
  public PropertyRootAbstractStub()
  {
    super(PropertyRootAbstractStub.UNIQUE_ID);
  }
  /**
   * Constructeur avec précision de l'identifiant
   * @param id Identifiant de la propriété racine de base
   */
  public PropertyRootAbstractStub(int id)
  {
    super(id);
  }

  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#createProperty(java.lang.String, java.lang.String)
   */
  @Override
  protected PropertyAbstractStub createProperty(String key, String value) throws UserException
  {
    return new PropertyAbstractStub(key, value);
  }
}
