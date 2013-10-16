package com.bid4win.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Cette classe d�fini un nom complet d'utilisateur<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Name extends Bid4WinEmbeddable<Name>
{
  /** Genre de l'individu */
  @Transient private Gender gender = null;
  /** Pr�nom de l'utilisateur */
  @Transient private String firstName = null;
  /** Deuxi�me pr�nom de l'utilisateur */
  @Transient private String middleName = null;
  /** Nom de famille de l'utilisateur */
  @Transient private String lastName = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private Name()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param gender Genre de l'individu
   * @param firstName Pr�nom de l'utilisateur
   * @param middleName Deuxi�me pr�nom de l'utilisateur
   * @param lastName Nom de famille de l'utilisateur
   */
  public Name(Gender gender, String firstName, String middleName, String lastName)
  {
    super();
    this.defineGender(gender);
    this.defineFirstName(firstName);
    this.defineMiddleName(middleName);
    this.defineLastName(lastName);
  }

  /**
   * Red�fini l'�galit� interne de deux nom d'utilisateurs par l'�galit� de leur
   * contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(Name toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getGender(), toBeCompared.getGender()) &&
           Bid4WinComparator.getInstance().equals(this.getFirstName(), toBeCompared.getFirstName()) &&
           Bid4WinComparator.getInstance().equals(this.getMiddleName(), toBeCompared.getMiddleName()) &&
           Bid4WinComparator.getInstance().equals(this.getLastName(), toBeCompared.getLastName());
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'un nom d'utilisateur
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments de l'utilisateur
    if(this.getGender() != null)
    {
      buffer.append(this.getGender().getCode()).append(" ");
    }
    buffer.append("FIRST_NAME=").append(this.getFirstName());
    buffer.append(" MIDDLE_NAME=").append(this.getMiddleName());
    buffer.append(" LAST_NAME=").append(this.getLastName());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de fusionner le nom complet courant avec celui en param�tre
   * @param name Nom complet � fusionner avec celui courant
   */
  public void merge(Name name)
  {
    if(name != null)
    {
        this.defineGender(name.getGender());
        this.defineFirstName(name.getFirstName());
        this.defineMiddleName(name.getMiddleName());
        this.defineLastName(name.getLastName());
    }
  }

  /**
   * Cette m�thode permet de d�finir le genre de l'individu
   * @param gender D�finition du genre de l'individu
   * @throws ProtectionException Si le nom complet courant est prot�g�
   */
  public void defineGender(Gender gender) throws ProtectionException
  {
    // V�rifie la protection de l'utilisateur courant
    this.checkProtection();
    if(gender != null)
    {
      this.setGender(gender);
    }
  }
  /**
   * Cette m�thode permet de d�finir le pr�nom de l'utilisateur
   * @param firstName D�finition du pr�nom de l'utilisateur
   * @throws ProtectionException Si le nom complet courant est prot�g�
   */
  public void defineFirstName(String firstName) throws ProtectionException
  {
    // V�rifie la protection du nom courant
    this.checkProtection();
    firstName = UtilString.trimNotNull(firstName);
    if(!firstName.equals(""))
    {
      this.setFirstName(firstName);
    }
  }
  /**
   * Cette m�thode permet de d�finir le deuxi�me pr�nom de l'utilisateur
   * @param middleName D�finition du deuxi�me pr�nom de l'utilisateur
   * @throws ProtectionException Si le nom complet courant est prot�g�
   */
  public void defineMiddleName(String middleName) throws ProtectionException
  {
    // V�rifie la protection du nom courant
    this.checkProtection();
    this.setMiddleName(UtilString.trimNotNull(middleName));
  }
  /**
   * Cette m�thode permet de d�finir le nom de famille de l'utilisateur
   * @param lastName D�finition du nom de famille de l'utilisateur
   * @throws ProtectionException Si le nom complet courant est prot�g�
   */
  public void defineLastName(String lastName) throws ProtectionException
  {
    // V�rifie la protection du nom courant
    this.checkProtection();
    lastName = UtilString.trimNotNull(lastName);
    if(!lastName.equals(""))
    {
      this.setLastName(lastName.toUpperCase());
    }
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du genre de l'utilisateur
   * @return Le genre de l'utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "GENDER", length = 5, nullable = true, unique = false)
  public Gender getGender()
  {
    return this.gender;
  }
  /**
   * Setter du genre de l'utilisateur
   * @param gender Genre de l'utilisateur � positionner
   */
  private void setGender(Gender gender)
  {
    this.gender = gender;
  }

  /**
   * Getter du pr�nom de l'utilisateur
   * @return Le pr�nom de l'utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "FIRST_NAME", length = 40, nullable = true, unique = false)
  public String getFirstName()
  {
    return this.firstName;
  }
  /**
   * Setter du pr�nom de l'utilisateur
   * @param firstName Pr�nom de l'utilisateur � positionner
   */
  private void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * Getter du deuxi�me pr�nom de l'utilisateur
   * @return Le deuxi�me pr�nom de l'utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "MIDDLE_NAME", length = 40, nullable = true, unique = false)
  public String getMiddleName()
  {
    return this.middleName;
  }
  /**
   * Setter du deuxi�me pr�nom de l'utilisateur
   * @param middleName Deuxi�me pr�nom de l'utilisateur � positionner
   */
  private void setMiddleName(String middleName)
  {
    this.middleName = middleName;
  }

  /**
   * Getter du nom de famille de l'utilisateur
   * @return Le nom de famille de l'utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "LAST_NAME", length = 40, nullable = true, unique = false)
  public String getLastName()
  {
    return this.lastName;
  }
  /**
   * Setter du nom de famille de l'utilisateur
   * @param lastName Nom de famille de l'utilisateur � positionner
   */
  private void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
}
