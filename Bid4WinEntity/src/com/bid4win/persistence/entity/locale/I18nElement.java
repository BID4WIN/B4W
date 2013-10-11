package com.bid4win.persistence.entity.locale;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.LanguageRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType;

/**
 * Cette classe d�fini un �l�ment d'internationalisation dans une certaine langue<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "type", column = @Column(name = "I18N_TYPE"))
public class I18nElement extends Bid4WinEmbeddableWithType<I18nElement, Language>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 6810506204585676299L;

  /** Valeur de l'�l�ment d'internationalisation */
  private String value = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected I18nElement()
  {
    super();
  }
  /**
   * Constructeur pour la langue par d�faut
   * @param value Valeur de l'�l�ment d'internationalisation
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public I18nElement(String value) throws UserException
  {
    this(Language.DEFAULT, value);
  }
  /**
   * Constructeur
   * @param language Langue de l'�l�ment d'internationalisation
   * @param value Valeur de l'�l�ment d'internationalisation
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public I18nElement(Language language, String value) throws UserException
  {
    super(language);
    this.defineValue(value);
  }

  /**
   * Red�fini l'�galit� interne de deux �l�ments d'internationalisation par l'
   * �galit� de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType)
   */
  @Override
  protected boolean equalsInternal(I18nElement toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           this.getValue().equals(toBeCompared.getValue());
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'un �l�ment d'internationalisation
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = super.render();
    buffer.append("|" + this.getValue());
    return buffer;
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
   * Getter de la langue de l'�l�ment d'internationalisation
   * @return La langue de l'�l�ment d'internationalisation
   */
  public Language getLanguage()
  {
    return this.getType();
  }
  /**
   * Cette m�thode permet de d�finir la valeur de l'�l�ment d'internationalisation
   * @param value D�finition de la valeur de l'�l�ment d'internationalisation
   * @throws ProtectionException Si l'�l�ment d'internationalisation courant est
   * prot�g�
   */
  protected void defineValue(String value) throws ProtectionException
  {
    this.checkProtection();
    value = UtilString.trimNotNull(value);
    if(value.equals(""))
    {
      value = "TODO";
    }
    this.setValue(value);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la valeur de l'�l�ment d'internationalisation
   * @return La valeur de l'�l�ment d'internationalisation
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "I18N_VALUE", length = 50, nullable = false, unique = false)
  public String getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur de l'�l�ment d'internationalisation
   * @param value Valeur de l'�l�ment d'internationalisation � positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }
}
