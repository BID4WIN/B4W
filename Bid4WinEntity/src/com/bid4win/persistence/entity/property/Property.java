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
 * Cette entit� repr�sente une propri�t�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
// Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Property extends PropertyAbstract<Property, PropertyRoot>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Property()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param key Cl� de la propri�t�
   * @param value Valeur de la propri�t�
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Property(String key, String value) throws UserException
  {
    super(key, value);
  }
  /**
   * Constructeur complet avec positionnement de la propri�t� parent
   * @param key Cl� de la propri�t�
   * @param value Valeur de la propri�t�
   * @param property Propri�t� parent de la propri�t�
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws UserException Si la propri�t� parent en argument est nulle ou r�f�rence
   * d�j� la propri�t� en construction ou si les param�tres entr�s sont invalides
   */
  public Property(String key, String value, Property property)
         throws ProtectionException, UserException
  {
    super(key, value, property);
  }
  /**
   * Constructeur complet avec positionnement de la propri�t� racine
   * @param key Cl� de la propri�t�
   * @param value Valeur de la propri�t�
   * @param root Propri�t� racine de la propri�t�
   * @throws ProtectionException Si la propri�t� racine en argument est prot�g�e
   * @throws UserException Si la propri�t� racine en argument est nulle ou r�f�rence
   * d�j� la propri�t� en construction ou si les param�tres entr�s sont invalides
   */
  public Property(String key, String value, PropertyRoot root)
         throws ProtectionException, UserException
  {
    super(key, value, root);
  }
  /**
   * Constructeur par copie
   * @param toBeCopied Propri�t� dont on va reprendre tous les �l�ments sauf son
   * potentiel lien vers une propri�t� parent (ou racine)
   * @param property Propri�t� parent de la propri�t�
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws ModelArgumentException Si la propri�t� � copier ou la propri�t� parent
   * en argument est nulle
   * @throws UserException Si les param�tres entr�s sont invalides ou si la propri�t�
   * parent r�f�rence d�j� la propri�t� en construction
   */
  public Property(Property toBeCopied, Property property)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(toBeCopied, property);
  }

  /**
   * Cette m�thode permet de cr�er une propri�t�
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
   * Cette m�thode permet de cr�er par copie une propri�t�
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
