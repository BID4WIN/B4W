package com.bid4win.commons.persistence.entity.property;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;

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
public class PropertyAbstractStub extends PropertyAbstract<PropertyAbstractStub, PropertyRootAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected PropertyAbstractStub()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param key Clé de la propriété
   * @param value Valeur de la propriété
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public PropertyAbstractStub(String key, String value) throws UserException
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
  public PropertyAbstractStub(String key, String value, PropertyAbstractStub property)
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
  public PropertyAbstractStub(String key, String value, PropertyRootAbstractStub root)
         throws ProtectionException, UserException
  {
    super(key, value, root);
  }
  /**
   * Constructeur par copie
   * @param toBeCopied Propriété dont on va reprendre tous les éléments sauf son
   * potentiel lien vers une propriété parent (ou racine)
   * @param property Propriété parent de la propriété
   * @throws ModelArgumentException Si la propriété à copier ou la propriété parent
   * en argument est nulle
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws UserException Si les paramètres entrés sont invalides ou si la propriété
   * parent référence déjà la propriété en construction
   */
  public PropertyAbstractStub(PropertyAbstractStub toBeCopied, PropertyAbstractStub property)
         throws ModelArgumentException, ProtectionException, UserException
  {
    super(toBeCopied, property);
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
   * @param toBeCopied {@inheritDoc}
   * @param Property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstract#createProperty(com.bid4win.commons.persistence.entity.property.PropertyAbstract, com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  protected PropertyAbstractStub createProperty(PropertyAbstractStub toBeCopied, PropertyAbstractStub Property)
            throws ModelArgumentException, ProtectionException, UserException
  {
    return new PropertyAbstractStub(toBeCopied, Property);
  }
}
