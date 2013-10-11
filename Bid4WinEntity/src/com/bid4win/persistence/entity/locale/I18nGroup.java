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
 * Cette classe d�fini un groupe d'�l�ments d'internationalisation dans diff�rentes
 * langues dont celle par d�faut<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "embeddedMap", column = @Column(name = "I18N_MAP"))
public class I18nGroup
       extends Bid4WinEmbeddableWithTypeMap<I18nGroup, I18nElement, Language>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected I18nGroup()
  {
    super();
  }
  /**
   * Constructeur pour la langue par d�faut
   * @param value Valeur de l'�l�ment d'internationalisation pour la langue par
   * d�faut
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public I18nGroup(String value) throws UserException
  {
    super(new I18nElement(value));
  }
  /**
   * Constructeur
   * @param elements Elements d'internationalisation du groupe
   * @throws UserException Si plus d'un des �l�ments d'internationalisation en
   * argument est li� � la m�me langue ou si aucun n'est li� � la langue par d�faut
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
   * Getter de la classe du type li� aux objets de la map
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
   * Getter de la valeur de l'�l�ment d'internationalisation associ� � la langue
   * par d�faut
   * @return La valeur de l'�l�ment d'internationalisation associ� � la langue
   * par d�faut
   */
  public String getValue()
  {
    return this.getElement().getValue();
  }
  /**
   * Getter de la valeur de l'�l�ment d'internationalisation associ� � langue en
   * argument. Si la langue n'est pas r�f�renc�e, la valeur de l'�l�ment d'
   * internationalisation associ� � la langue par d�faut sera retourn�e
   * @param language Langue associ�e � l'�l�ment d'internationalisation dont la
   * valeur est retourn�e
   * @return La valeur de l'�l�ment d'internationalisation associ� � la langue en
   * argument
   */
  public String getValue(Language language)
  {
    return this.getElement(language).getValue();
  }
  /**
   * Getter de l'�l�ment d'internationalisation associ� � la langue par d�faut
   * @return L'�l�ment d'internationalisation associ� � la langue par d�faut
   */
  public I18nElement getElement()
  {
    return this.getEmbedded();
  }
  /**
   * Getter de l'�l�ment d'internationalisation associ� � langue en argument. Si
   * la langue n'est pas r�f�renc�e, l'�l�ment d'internationalisation associ� �
   * la langue par d�faut sera retourn�
   * @param language Langue associ�e � l'�l�ment d'internationalisation � retourner
   * @return L'�l�ment d'internationalisation associ� � la langue en argument
   */
  public I18nElement getElement(Language language)
  {
    return this.getEmbedded(language);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map d'�l�ment d'internationalisation
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
