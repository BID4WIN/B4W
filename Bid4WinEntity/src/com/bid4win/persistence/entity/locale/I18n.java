package com.bid4win.persistence.entity.locale;

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
 * Cette classe défini une propriété d'internationalisation<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class I18n extends PropertyAbstract<I18n, I18nRoot>
{
  /** Marquage d'une nouvelle propriété d'internationalisation dont la valeur doit être validée */
  public static final String TODO = "TODO ";

  /**
   * Constructeur pour création par introspection
   */
  protected I18n()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param key Clé de la propriété d'internationalisation
   * @param value Valeur de la propriété d'internationalisation
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public I18n(String key, String value) throws UserException
  {
    super(key, value);
  }
  /**
   * Constructeur complet avec positionnement de la propriété  d'internationalisation
   * parent
   * @param key Clé de la propriété d'internationalisation
   * @param value Valeur de la propriété d'internationalisation
   * @param property Propriété parent de la propriété d'internationalisation
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws UserException Si la propriété parent en argument est nulle ou référence
   * déjà la propriété en construction ou si les paramètres entrés sont invalides
   */
  public I18n(String key, String value, I18n property)
         throws ProtectionException, UserException
  {
    super(key, value, property);
  }
  /**
   * Constructeur complet avec positionnement de la propriété  d'internationalisation
   * racine
   * @param key Clé de la propriété d'internationalisation
   * @param value Valeur de la propriété d'internationalisation
   * @param root Propriété racine de la propriété d'internationalisation
   * @throws ProtectionException Si la propriété racine en argument est protégée
   * @throws UserException Si la propriété racine en argument est nulle ou référence
   * déjà la propriété en construction ou si les paramètres entrés sont invalides
   */
  public I18n(String key, String value, I18nRoot root)
         throws ProtectionException, UserException
  {
    super(key, value, root);
  }
  /**
   * Constructeur par copie. Un marquage de la valeur de la propriété sera effectuée
   * afin de bien montrer que celle-ci doit être validée
   * @param toBeCopied Propriété  d'internationalisation dont on va reprendre tous
   * les éléments sauf son potentiel lien vers une propriété parent (ou racine)
   * @param property Propriété parent de la propriété d'internationalisation
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws ModelArgumentException Si la propriété d'internationalisation à copier
   * ou la propriété parent en argument est nulle
   * @throws UserException Si les paramètres entrés sont invalides ou si la propriété
   * parent référence déjà la propriété en construction
   */
  public I18n(I18n toBeCopied, I18n property)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(toBeCopied, property);
    this.defineValue(TODO + this.getValue());
  }

  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public I18n addProperty(String key) throws ProtectionException, ModelArgumentException, UserException
  {
    return super.addProperty(key, TODO);
  }

  /**
   * Cette méthode permet de créer une propriété d'internationalisation
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#createProperty(java.lang.String, java.lang.String)
   */
  @Override
  protected I18n createProperty(String key, String value) throws UserException
  {
    return new I18n(key, value);
  }
  /**
   * Cette méthode permet de créer par copie une propriété d'internationalisation
   * @param toBeCopied {@inheritDoc}
   * @param Property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstract#createProperty(com.bid4win.commons.persistence.entity.property.PropertyAbstract, com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  protected I18n createProperty(I18n toBeCopied, I18n Property)
            throws ProtectionException, ModelArgumentException, UserException
  {
    return new I18n(toBeCopied, Property);
  }
}
