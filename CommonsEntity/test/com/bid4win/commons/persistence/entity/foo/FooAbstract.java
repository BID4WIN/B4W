package com.bid4win.commons.persistence.entity.foo;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.core.Bid4WinDateForRequest;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class FooAbstract<CLASS extends FooAbstract<CLASS>>
       extends Bid4WinEntityAutoID<CLASS>
{
  /** Valeur de l'objet */
  @Transient
  private String value = null;
  /** Date de l'objet */
  @Transient
  private Bid4WinDate date = null;
  /** R�le de l'objet */
  @Transient
  private Role role = null;

  /**
   * Constructeur
   */
  protected FooAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur de l'objet
   * @param date Date de l'objet
   */
  public FooAbstract(String value, Bid4WinDate date)
  {
    this();
    this.defineValue(value);
    this.defineDate(date);
  }
  /**
   * Constructeur
   * @param value Valeur de l'objet
   * @param date Date de l'objet
   * @param role R�le de l'objet
   */
  public FooAbstract(String value, Bid4WinDate date, Role role)
  {
    this(value, date);
    this.defineRole(role);
  }

  /**
   * D�finition de l'�quivalence de la classe de test simple
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getValue(), toBeCompared.getValue()) &&
           Bid4WinComparator.getInstance().equals(this.getDate(), toBeCompared.getDate()) &&
           Bid4WinComparator.getInstance().equals(this.getRole(), toBeCompared.getRole());
  }
  /**
   * Permet de transformer l'objet en une cha�ne de caract�res lisible
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    StringBuffer buffer = super.renderRelationNone();
    buffer.append(" Value=").append(this.getValue());
    buffer.append(" Date=").append(this.getDate().formatDDIMMIYYYY_HHIMMISSISSS());
    buffer.append(" Role=").append(this.getRole());
    return buffer;
  }
  /**
  *
  * TODO A COMMENTER
  * @param value TODO A COMMENTER
  * @throws ProtectionException TODO A COMMENTER
  */
 public void defineValue(String value) throws ProtectionException
 {
   // V�rifie la protection de l'objet courant
   this.checkProtection();
   this.setValue(value);
 }
  /**
   *
   * TODO A COMMENTER
   * @param date TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   */
  public void defineDate(Bid4WinDate date) throws ProtectionException
  {
    // V�rifie la protection de l'objet courant
    this.checkProtection();
    this.setDate(date);
  }
  /**
   *
   * TODO A COMMENTER
   * @param role TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   */
  public void defineRole(Role role) throws ProtectionException
  {
    // V�rifie la protection de l'objet courant
    this.checkProtection();
    this.setRole(role);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la valeur de l'objet
   * @return La valeur de l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "VALUE", length = 20, nullable = false, unique = false)
  public String getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur de l'objet
   * @param value Valeur de l'objet � positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }

  /**
   * Getter de la date de l'objet
   * @return La date de l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "DATE_", nullable = true, unique = false)
  public Bid4WinDate getDate()
  {
    return this.date;
  }
  /**
   * Setter de la date de l'objet
   * @param date Date de l'objet � positionner
   */
  private void setDate(Bid4WinDate date)
  {
    this.date= date;
  }

  /**
   * Getter de la date de l'objet pour les requ�tes
   * @return La date de l'objet pour les requ�tes
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "DATE_", nullable = true, unique = false,
          // Utilise la m�me colonne que la date r�elle
          insertable = false, updatable = false)
  public Bid4WinDateForRequest getDateForRequest()
  {
    return Bid4WinDateForRequest.getDateForRequest(this.getDate());
  }
  /**
   * Setter inutilis� car la date est positionn�e par ailleurs
   * @param date Date de l'objet  pour les requ�tes � positionner
   */
  @SuppressWarnings("unused")
  private void setDateForRequest(Bid4WinDateForRequest date)
  {
    // Ne fait rien
  }

  /**
   * Getter du r�le de l'objet
   * @return Le r�le de l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "ROLE", nullable = true, unique = false)
  public Role getRole()
  {
    return this.role;
  }
  /**
   * Setter du r�le de l'objet
   * @param role R�le de l'objet � positionner
   */
  private void setRole(Role role)
  {
    this.role = role;
  }
}
