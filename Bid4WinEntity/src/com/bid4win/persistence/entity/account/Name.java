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
 * Cette classe défini un nom complet d'utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Name extends Bid4WinEmbeddable<Name>
{
  /** Genre de l'individu */
  @Transient private Gender gender = null;
  /** Prénom de l'utilisateur */
  @Transient private String firstName = null;
  /** Deuxième prénom de l'utilisateur */
  @Transient private String middleName = null;
  /** Nom de famille de l'utilisateur */
  @Transient private String lastName = null;

  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private Name()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param gender Genre de l'individu
   * @param firstName Prénom de l'utilisateur
   * @param middleName Deuxième prénom de l'utilisateur
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
   * Redéfini l'égalité interne de deux nom d'utilisateurs par l'égalité de leur
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
   * Redéfini la transformation en chaîne de caractères d'un nom d'utilisateur
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments de l'utilisateur
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
   * Cette méthode permet de fusionner le nom complet courant avec celui en paramètre
   * @param name Nom complet à fusionner avec celui courant
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
   * Cette méthode permet de définir le genre de l'individu
   * @param gender Définition du genre de l'individu
   * @throws ProtectionException Si le nom complet courant est protégé
   */
  public void defineGender(Gender gender) throws ProtectionException
  {
    // Vérifie la protection de l'utilisateur courant
    this.checkProtection();
    if(gender != null)
    {
      this.setGender(gender);
    }
  }
  /**
   * Cette méthode permet de définir le prénom de l'utilisateur
   * @param firstName Définition du prénom de l'utilisateur
   * @throws ProtectionException Si le nom complet courant est protégé
   */
  public void defineFirstName(String firstName) throws ProtectionException
  {
    // Vérifie la protection du nom courant
    this.checkProtection();
    firstName = UtilString.trimNotNull(firstName);
    if(!firstName.equals(""))
    {
      this.setFirstName(firstName);
    }
  }
  /**
   * Cette méthode permet de définir le deuxième prénom de l'utilisateur
   * @param middleName Définition du deuxième prénom de l'utilisateur
   * @throws ProtectionException Si le nom complet courant est protégé
   */
  public void defineMiddleName(String middleName) throws ProtectionException
  {
    // Vérifie la protection du nom courant
    this.checkProtection();
    this.setMiddleName(UtilString.trimNotNull(middleName));
  }
  /**
   * Cette méthode permet de définir le nom de famille de l'utilisateur
   * @param lastName Définition du nom de famille de l'utilisateur
   * @throws ProtectionException Si le nom complet courant est protégé
   */
  public void defineLastName(String lastName) throws ProtectionException
  {
    // Vérifie la protection du nom courant
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
   * @param gender Genre de l'utilisateur à positionner
   */
  private void setGender(Gender gender)
  {
    this.gender = gender;
  }

  /**
   * Getter du prénom de l'utilisateur
   * @return Le prénom de l'utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "FIRST_NAME", length = 40, nullable = true, unique = false)
  public String getFirstName()
  {
    return this.firstName;
  }
  /**
   * Setter du prénom de l'utilisateur
   * @param firstName Prénom de l'utilisateur à positionner
   */
  private void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * Getter du deuxième prénom de l'utilisateur
   * @return Le deuxième prénom de l'utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "MIDDLE_NAME", length = 40, nullable = true, unique = false)
  public String getMiddleName()
  {
    return this.middleName;
  }
  /**
   * Setter du deuxième prénom de l'utilisateur
   * @param middleName Deuxième prénom de l'utilisateur à positionner
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
   * @param lastName Nom de famille de l'utilisateur à positionner
   */
  private void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
}
