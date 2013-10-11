package com.bid4win.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilDate;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Cette classe d�fini un utilisateur<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class User extends Bid4WinEmbeddable<User>
{
  /** Nom complet de l'utilisateur */
  @Transient
  private Name name = null;
  /** Date de naissance de l'utilisateur */
  @Transient
  private Bid4WinDate birthDate = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private User()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param name Nom complet de l'utilisateur
   * @param birthDate Date de naissance de l'utilisateur
   */
  public User(Name name, Bid4WinDate birthDate)
  {
    super();
    this.defineName(name);
    this.defineBirthDate(birthDate);
  }

  /**
   * Red�fini l'�galit� interne de deux utilisateurs par l'�galit� de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(User toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getName(),
                                                              toBeCompared.getName()) &&
           Bid4WinComparator.getInstance().equals(this.getBirthDate(),
                                                   toBeCompared.getBirthDate());
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'un utilisateur lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments de l'utilisateur
    if(this.getName() != null)
    {
      buffer.append(this.getName().render());
    }
    if(this.getBirthDate() != null)
    {
      buffer.append(" BIRTH_DATE=").append(UtilDate.formatDDIMMIYYYY(this.getBirthDate()));
    }
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de fusionner les informations utilisateur courantes avec
   * celle en param�tre
   * @param user Informations utilisateur � fusionner avec celles courantes
   */
  public void merge(User user)
  {
    if(user != null)
    {
      this.defineName(user.getName());
      this.defineBirthDate(user.getBirthDate());
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isNameDefined()
  {
    return this.getName() != null;
  }
  /**
   * Cette m�thode permet de d�finir le nom de l'utilisateur
   * @param name D�finition du nom de l'utilisateur
   * @throws ProtectionException Si les informations utilisateur sont prot�g�es
   */
  public void defineName(Name name) throws ProtectionException
  {
    // V�rifie la protection de l'utilisateur courant
    this.checkProtection();
    if(this.isNameDefined())
    {
      this.getName().merge(name);
    }
    else
    {
      this.setName(name);
    }
  }
  /**
   * Cette m�thode permet de d�finir la date de naissance de l'utilisateur
   * @param birthDate D�finition de la date de naissance de l'utilisateur
   * @throws ProtectionException Si les informations utilisateur sont prot�g�es
   */
  public void defineBirthDate(Bid4WinDate birthDate) throws ProtectionException
  {
    // V�rifie la protection de l'utilisateur courant
    this.checkProtection();
    if(birthDate != null)
    {
      this.setBirthDate(birthDate = birthDate.removeTimeInfo());
    }
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du nom de l'utilisateur
   * @return Le nom de l'utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public Name getName()
  {
    return this.name;
  }
  /**
   * Setter du nom de l'utilisateur
   * @param name Nom de l'utilisateur � positionner
   */
  private void setName(Name name)
  {
    this.name = name;
  }

  /**
   * Getter de la date de naissance de l'utilisateur
   * @return La date de naissance de l'utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "BIRTH_DATE", length = 10, nullable = true, unique = false)
  @Type(type = "DATE")
  public Bid4WinDate getBirthDate()
  {
    return this.birthDate;
  }
  /**
   * Setter de la date de naissance de l'utilisateur
   * @param birthDate Date de naissance de l'utilisateur � positionner
   */
  private void setBirthDate(Bid4WinDate birthDate)
  {
    this.birthDate = birthDate;
  }
}
