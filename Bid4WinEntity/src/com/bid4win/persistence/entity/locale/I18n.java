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
 * Cette classe d�fini une propri�t� d'internationalisation<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class I18n extends PropertyAbstract<I18n, I18nRoot>
{
  /** Marquage d'une nouvelle propri�t� d'internationalisation dont la valeur doit �tre valid�e */
  public static final String TODO = "TODO ";

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected I18n()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param key Cl� de la propri�t� d'internationalisation
   * @param value Valeur de la propri�t� d'internationalisation
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public I18n(String key, String value) throws UserException
  {
    super(key, value);
  }
  /**
   * Constructeur complet avec positionnement de la propri�t�  d'internationalisation
   * parent
   * @param key Cl� de la propri�t� d'internationalisation
   * @param value Valeur de la propri�t� d'internationalisation
   * @param property Propri�t� parent de la propri�t� d'internationalisation
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws UserException Si la propri�t� parent en argument est nulle ou r�f�rence
   * d�j� la propri�t� en construction ou si les param�tres entr�s sont invalides
   */
  public I18n(String key, String value, I18n property)
         throws ProtectionException, UserException
  {
    super(key, value, property);
  }
  /**
   * Constructeur complet avec positionnement de la propri�t�  d'internationalisation
   * racine
   * @param key Cl� de la propri�t� d'internationalisation
   * @param value Valeur de la propri�t� d'internationalisation
   * @param root Propri�t� racine de la propri�t� d'internationalisation
   * @throws ProtectionException Si la propri�t� racine en argument est prot�g�e
   * @throws UserException Si la propri�t� racine en argument est nulle ou r�f�rence
   * d�j� la propri�t� en construction ou si les param�tres entr�s sont invalides
   */
  public I18n(String key, String value, I18nRoot root)
         throws ProtectionException, UserException
  {
    super(key, value, root);
  }
  /**
   * Constructeur par copie. Un marquage de la valeur de la propri�t� sera effectu�e
   * afin de bien montrer que celle-ci doit �tre valid�e
   * @param toBeCopied Propri�t�  d'internationalisation dont on va reprendre tous
   * les �l�ments sauf son potentiel lien vers une propri�t� parent (ou racine)
   * @param property Propri�t� parent de la propri�t� d'internationalisation
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws ModelArgumentException Si la propri�t� d'internationalisation � copier
   * ou la propri�t� parent en argument est nulle
   * @throws UserException Si les param�tres entr�s sont invalides ou si la propri�t�
   * parent r�f�rence d�j� la propri�t� en construction
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
   * Cette m�thode permet de cr�er une propri�t� d'internationalisation
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
   * Cette m�thode permet de cr�er par copie une propri�t� d'internationalisation
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
