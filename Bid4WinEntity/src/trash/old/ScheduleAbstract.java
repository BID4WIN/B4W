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
// * Cette classe définie les éléments de planification de base d'une vente aux enchères<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class ScheduleAbstract<CLASS extends ScheduleAbstract<CLASS>>
//       extends Bid4WinEmbeddable<CLASS>
//{
//  /** Date d'ouverture prévue d'une vente aux enchères */
//  @Transient
//  private Bid4WinDate openingDate = null;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected ScheduleAbstract()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param openingDate Date d'ouverture prévue d'une vente aux enchères
//   * @throws ModelArgumentException Si la date d'ouverture prévue est nulle
//   */
//  public ScheduleAbstract(Bid4WinDate openingDate) throws ModelArgumentException
//  {
//    super();
//    this.defineOpeningDate(openingDate);
//  }
//
//  /**
//   * Redéfini l'égalité interne de deux planifications par l'égalité de leur contenu
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
//   * Redéfini la transformation en chaîne de caractères d'une planification lisiblement
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
//   */
//  @Override
//  public StringBuffer render()
//  {
//    // Effectue le rendu de base d'un objet
//    StringBuffer buffer = super.render();
//    // Ajoute les éléments de planification de base d'une vente aux enchères
//    buffer.append("OPENING_DATE=").append(this.getOpeningDate().formatDDIMMIYYYY_HHIMMISSISSS());
//    // Retourne le rendu
//    return buffer;
//  }
//
//  /**
//   * Cette méthode permet de définir la date d'ouverture prévue d'une vente aux
//   * enchères
//   * @param openingDate Définition de la date d'ouverture prévue
//   * @throws ProtectionException Si la planification courante est protégée
//   * @throws ModelArgumentException Si on défini une date d'ouverture prévue nulle
//   */
//  private void defineOpeningDate(Bid4WinDate openingDate) throws ModelArgumentException
//  {
//    // Vérifie la protection de la planification de base courante
//    this.checkProtection();
//    openingDate = UtilObject.checkNotNull("openingDate", openingDate);
//    this.setOpeningDate(openingDate.removeSecondInfo());
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de la date d'ouverture prévue d'une vente aux enchères
//   * @return La date d'ouverture prévue d'une vente aux enchères
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
//   * Setter de la date d'ouverture prévue d'une vente aux enchères
//   * @param openingDate Date d'ouverture prévue d'une vente aux enchères à positionner
//   */
//  private void setOpeningDate(Bid4WinDate openingDate)
//  {
//    this.openingDate = openingDate;
//  }
//}
