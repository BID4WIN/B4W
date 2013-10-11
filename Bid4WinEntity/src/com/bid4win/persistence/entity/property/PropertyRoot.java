package com.bid4win.persistence.entity.property;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;

/**
 * Cette entité représente la racine des propriétés<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PropertyRoot extends PropertyRootAbstract<PropertyRoot, Property>
{
  /** Définition de l'identifiant unique de la racine des propriétés */
  public final static int UNIQUE_ID = 0;

  /**
   * Constructeur de la racine des propriétés. L'identifiant y est directement
   * précisé car celui-ci se doit d'être unique
   */
  public PropertyRoot()
  {
    super(PropertyRoot.UNIQUE_ID);
  }

  /**
   * Cette méthode permet de créer une propriété
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#createProperty(java.lang.String, java.lang.String)
   */
  @Override
  protected Property createProperty(String key, String value) throws UserException
  {
    return new Property(key, value);
  }
}
