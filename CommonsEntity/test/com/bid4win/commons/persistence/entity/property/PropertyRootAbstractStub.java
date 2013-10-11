package com.bid4win.commons.persistence.entity.property;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.collection.Bid4WinMatchReferenceMap;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PropertyRootAbstractStub extends PropertyRootAbstract<PropertyRootAbstractStub, PropertyAbstractStub>
{
  /** D�finition de l'identifiant unique de la racine des propri�t�s */
  public final static int UNIQUE_ID = 9;

  /**
   * Constructeur de la racine des propri�t�s. L'identifiant y est directement
   * pr�cis� car celui-ci se doit d'�tre unique
   */
  public PropertyRootAbstractStub()
  {
    super(PropertyRootAbstractStub.UNIQUE_ID);
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de la propri�t� racine de base
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

  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared TODO A COMMENTER
   * @param identical TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected boolean same(PropertyRootAbstractStub toBeCompared, boolean identical)
  {
    return this.sameInternal(toBeCompared, this.getFullRelationNodeList(), new Bid4WinMatchReferenceMap(), identical);
  }
}
