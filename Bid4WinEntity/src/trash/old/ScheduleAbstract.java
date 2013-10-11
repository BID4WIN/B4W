package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.Bid4WinDate;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;
//
///**
// * Cette classe d�finie les �l�ments de planification de base d'une vente aux ench�res<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class ScheduleAbstract<CLASS extends ScheduleAbstract<CLASS>>
//       extends Bid4WinEmbeddable<CLASS>
//{
//  /** Date d'ouverture pr�vue d'une vente aux ench�res */
//  @Transient
//  private Bid4WinDate openingDate = null;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected ScheduleAbstract()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param openingDate Date d'ouverture pr�vue d'une vente aux ench�res
//   * @throws ModelArgumentException Si la date d'ouverture pr�vue est nulle
//   */
//  public ScheduleAbstract(Bid4WinDate openingDate) throws ModelArgumentException
//  {
//    super();
//    this.defineOpeningDate(openingDate);
//  }
//
//  /**
//   * Red�fini l'�galit� interne de deux planifications par l'�galit� de leur contenu
//   * @param toBeCompared {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
//   */
//  @Override
//  protected boolean equalsInternal(CLASS toBeCompared)
//  {
//    return super.equalsInternal(toBeCompared) &&
//           this.getOpeningDate().equals(toBeCompared.getOpeningDate());
//  }
//  /**
//   * Red�fini la transformation en cha�ne de caract�res d'une planification lisiblement
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
//   */
//  @Override
//  public StringBuffer render()
//  {
//    // Effectue le rendu de base d'un objet
//    StringBuffer buffer = super.render();
//    // Ajoute les �l�ments de planification de base d'une vente aux ench�res
//    buffer.append("OPENING_DATE=").append(this.getOpeningDate().formatDDIMMIYYYY_HHIMMISSISSS());
//    // Retourne le rendu
//    return buffer;
//  }
//
//  /**
//   * Cette m�thode permet de d�finir la date d'ouverture pr�vue d'une vente aux
//   * ench�res
//   * @param openingDate D�finition de la date d'ouverture pr�vue
//   * @throws ProtectionException Si la planification courante est prot�g�e
//   * @throws ModelArgumentException Si on d�fini une date d'ouverture pr�vue nulle
//   */
//  private void defineOpeningDate(Bid4WinDate openingDate) throws ModelArgumentException
//  {
//    // V�rifie la protection de la planification de base courante
//    this.checkProtection();
//    openingDate = UtilObject.checkNotNull("openingDate", openingDate);
//    this.setOpeningDate(openingDate.removeSecondInfo());
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de la date d'ouverture pr�vue d'une vente aux ench�res
//   * @return La date d'ouverture pr�vue d'une vente aux ench�res
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "OPENING_DATE", length = 23, nullable = false, unique = false)
//  @org.hibernate.annotations.Type(type = "DATE_TIME")
//  public Bid4WinDate getOpeningDate()
//  {
//    return this.openingDate;
//  }
//  /**
//   * Setter de la date d'ouverture pr�vue d'une vente aux ench�res
//   * @param openingDate Date d'ouverture pr�vue d'une vente aux ench�res � positionner
//   */
//  private void setOpeningDate(Bid4WinDate openingDate)
//  {
//    this.openingDate = openingDate;
//  }
//}
