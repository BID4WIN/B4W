package com.bid4win.persistence.entity.locale;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.LanguageRef;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap;

/**
 * Cette classe défini un groupe d'éléments d'internationalisation dans différentes
 * langues dont celle par défaut<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "embeddedMap", column = @Column(name = "I18N_MAP"))
public class I18nGroup
       extends Bid4WinEmbeddableWithTypeMap<I18nGroup, I18nElement, Language>
{
  /**
   * Constructeur pour création par introspection
   */
  protected I18nGroup()
  {
    super();
  }
  /**
   * Constructeur pour la langue par défaut
   * @param value Valeur de l'élément d'internationalisation pour la langue par
   * défaut
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public I18nGroup(String value) throws UserException
  {
    super(new I18nElement(value));
  }
  /**
   * Constructeur
   * @param elements Elements d'internationalisation du groupe
   * @throws UserException Si plus d'un des éléments d'internationalisation en
   * argument est lié à la même langue ou si aucun n'est lié à la langue par défaut
   */
  public I18nGroup(I18nElement ... elements) throws UserException
  {
    super(elements);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#getMessageRefBase()
   */
  @Override
  protected MessageRef getMessageRefBase()
  {
    return LanguageRef.LANGUAGE;
  }

  /**
   * Getter de la classe du type lié aux objets de la map
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap#getTypeClass()
   */
  @Override
  public Class<Language> getTypeClass()
  {
    return Language.class;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap#getDefaultType()
   */
  @Override
  public Language getDefaultType()
  {
    return Language.DEFAULT;
  }

  /**
   * Getter de la valeur de l'élément d'internationalisation associé à la langue
   * par défaut
   * @return La valeur de l'élément d'internationalisation associé à la langue
   * par défaut
   */
  public String getValue()
  {
    return this.getElement().getValue();
  }
  /**
   * Getter de la valeur de l'élément d'internationalisation associé à langue en
   * argument. Si la langue n'est pas référencée, la valeur de l'élément d'
   * internationalisation associé à la langue par défaut sera retournée
   * @param language Langue associée à l'élément d'internationalisation dont la
   * valeur est retournée
   * @return La valeur de l'élément d'internationalisation associé à la langue en
   * argument
   */
  public String getValue(Language language)
  {
    return this.getElement(language).getValue();
  }
  /**
   * Getter de l'élément d'internationalisation associé à la langue par défaut
   * @return L'élément d'internationalisation associé à la langue par défaut
   */
  public I18nElement getElement()
  {
    return this.getEmbedded();
  }
  /**
   * Getter de l'élément d'internationalisation associé à langue en argument. Si
   * la langue n'est pas référencée, l'élément d'internationalisation associé à
   * la langue par défaut sera retourné
   * @param language Langue associée à l'élément d'internationalisation à retourner
   * @return L'élément d'internationalisation associé à la langue en argument
   */
  public I18nElement getElement(Language language)
  {
    return this.getEmbedded(language);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map d'élément d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap#getEmbeddedMap()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "ELEMENT_MAP", length = 250, nullable = false, unique = false)
  @Type(type = "I18N_ELEMENT_MAP")
  protected Bid4WinMap<Language, I18nElement> getEmbeddedMap()
  {
    return super.getEmbeddedMap();
  }
}
