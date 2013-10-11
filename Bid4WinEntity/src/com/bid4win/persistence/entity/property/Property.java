package com.bid4win.persistence.entity.property;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;

/**
 * Cette entité représente une propriété<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
// Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Property extends PropertyAbstract<Property, PropertyRoot>
{
  /**
   * Constructeur pour création par introspection
   */
  protected Property()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param key Clé de la propriété
   * @param value Valeur de la propriété
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public Property(String key, String value) throws UserException
  {
    super(key, value);
  }
  /**
   * Constructeur complet avec positionnement de la propriété parent
   * @param key Clé de la propriété
   * @param value Valeur de la propriété
   * @param property Propriété parent de la propriété
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws UserException Si la propriété parent en argument est nulle ou référence
   * déjà la propriété en construction ou si les paramètres entrés sont invalides
   */
  public Property(String key, String value, Property property)
         throws ProtectionException, UserException
  {
    super(key, value, property);
  }
  /**
   * Constructeur complet avec positionnement de la propriété racine
   * @param key Clé de la propriété
   * @param value Valeur de la propriété
   * @param root Propriété racine de la propriété
   * @throws ProtectionException Si la propriété racine en argument est protégée
   * @throws UserException Si la propriété racine en argument est nulle ou référence
   * déjà la propriété en construction ou si les paramètres entrés sont invalides
   */
  public Property(String key, String value, PropertyRoot root)
         throws ProtectionException, UserException
  {
    super(key, value, root);
  }
  /**
   * Constructeur par copie
   * @param toBeCopied Propriété dont on va reprendre tous les éléments sauf son
   * potentiel lien vers une propriété parent (ou racine)
   * @param property Propriété parent de la propriété
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws ModelArgumentException Si la propriété à copier ou la propriété parent
   * en argument est nulle
   * @throws UserException Si les paramètres entrés sont invalides ou si la propriété
   * parent référence déjà la propriété en construction
   */
  public Property(Property toBeCopied, Property property)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(toBeCopied, property);
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
  /**
   * Cette méthode permet de créer par copie une propriété
   * @param toBeCopied {@inheritDoc}
   * @param Property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstract#createProperty(com.bid4win.commons.persistence.entity.property.PropertyAbstract, com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  protected Property createProperty(Property toBeCopied, Property Property)
            throws ProtectionException, ModelArgumentException, UserException
  {
    return new Property(toBeCopied, Property);
  }
}
