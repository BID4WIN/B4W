package trash.old;
//package com.bid4win.persistence.entity.auction.history;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.persistence.entity.auction.ScheduleAbstract;
//import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
//
///**
// * Cette classe d�fini les �l�ments de planification d'une vente aux ench�res historis�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class ScheduleHistory extends ScheduleAbstract<ScheduleHistory>
//{
//  /** Compte � rebours initial de fermeture d'une vente aux ench�res en secondes */
//  @Transient
//  private int initialCountdown = 0;
//  /** Compte � rebours additionnel de fermeture d'une vente aux ench�res en secondes */
//  @Transient
//  private int aditionalCountdown = 0;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected ScheduleHistory()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param schedule TODO A COMMENTER
//   * @throws UserException TODO A COMMENTER
//   */
//  public ScheduleHistory(NormalSchedule schedule) throws UserException
//  {
//    super(UtilObject.checkNotNull("schedule", schedule,
//                                  AuctionRef.AUCTION_SCHEDULE_MISSING_ERROR).getOpeningDate());
//    this.defineInitialCountdown(schedule.getInitialCountdown());
//    this.defineAditionalCountdown(schedule.getAditionalCountdown());
//  }
//  /**
//   * Constructeur
//   * @param schedule TODO A COMMENTER
//   * @throws UserException TODO A COMMENTER
//   */
//  public ScheduleHistory(PrebookedSchedule schedule) throws UserException
//  {
//    super(UtilObject.checkNotNull("schedule", schedule,
//                                  AuctionRef.AUCTION_SCHEDULE_MISSING_ERROR).getOpeningDate());
//    this.defineInitialCountdown(schedule.getInitialCountdown());
//    this.defineAditionalCountdown(schedule.getAditionalCountdown());
//  }
//
//  /**
//   * Red�fini l'�galit� interne de deux planifications par l'�galit� de leur contenu
//   * @param toBeCompared {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.ScheduleAbstract#equalsInternal(com.bid4win.persistence.entity.auction.ScheduleAbstract)
//   */
//  @Override
//  protected boolean equalsInternal(ScheduleHistory toBeCompared)
//  {
//    return super.equalsInternal(toBeCompared) &&
//           this.getInitialCountdown() == toBeCompared.getInitialCountdown() &&
//           this.getAditionalCountdown() == toBeCompared.getAditionalCountdown();
//  }
//  /**
//   * Red�fini la transformation en cha�ne de caract�res d'une planification lisiblement
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.ScheduleAbstract#render()
//   */
//  @Override
//  public StringBuffer render()
//  {
//    // Effectue le rendu de base d'une planification de vente aux ench�res
//    StringBuffer buffer = super.render();
//    // Ajoute les �l�ments de planification d'une vente aux ench�res
//    buffer.append(" INITIAL_COUNTDOWN=").append(this.getInitialCountdown());
//    buffer.append(" ADITIONAL_COUNTDOWN=").append(this.getAditionalCountdown());
//    // Retourne le rendu
//    return buffer;
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.ScheduleAbstract#toHistory()
//   */
//  @Override
//  public ScheduleHistory toHistory()
//  {
//    return this;
//  }
//
//  /**
//   * Cette m�thode permet de d�finir le compte � rebours initial de fermeture d'
//   * une vente aux ench�res en secondes
//   * @param initialCountdown D�finition du compte � rebours initial de fermeture
//   * d'une vente aux ench�res en secondes
//   * @throws ProtectionException Si la planification courante est prot�g�e
//   * @throws UserException Si on d�fini un compte � rebours inf�rieur � une seconde
//   */
//  private void defineInitialCountdown(int initialCountdown) throws UserException
//  {
//    // V�rifie la protection de la planification courante
//    this.checkProtection();
//    UtilNumber.checkMinValue("initialCountdown", initialCountdown, 1, true,
//                             AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
//    this.setInitialCountdown(initialCountdown);
//  }
//  /**
//   * Cette m�thode permet de d�finir le compte � rebours additionnel de fermeture
//   * d' une vente aux ench�res en secondes
//   * @param aditionalCountdown D�finition du compte � rebours additionnel de fermeture
//   * d'une vente aux ench�res en secondes
//   * @throws ProtectionException Si la planification courante est prot�g�e
//   * @throws UserException Si on d�fini un compte � rebours inf�rieur � une seconde
//   */
//  private void defineAditionalCountdown(int aditionalCountdown) throws UserException
//  {
//    // V�rifie la protection de la planification courante
//    this.checkProtection();
//    UtilNumber.checkMinValue("initialCountdown", aditionalCountdown, 1, true,
//                             AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
//    this.setAditionalCountdown(aditionalCountdown);
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du compte � rebours initial de fermeture d'une vente aux ench�res en
//   * secondes
//   * @return Le compte � rebours initial de fermeture d'une vente aux ench�res en
//   * secondes
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "COUNTDOWN_INIT", length = 6, nullable = true, unique = false)
//  public int getInitialCountdown()
//  {
//    return this.initialCountdown;
//  }
//  /**
//   * Setter du compte � rebours initial de fermeture d'une vente aux ench�res en
//   * secondes
//   * @param initialCountdown Compte � rebours initial de fermeture d'une vente
//   * aux ench�res � positionner en secondes
//   *
//   */
//  private void setInitialCountdown(int initialCountdown)
//  {
//    this.initialCountdown = initialCountdown;
//  }
//
//  /**
//   * Getter du compte � rebours additionnel de fermeture d'une vente aux ench�res
//   * en secondes
//   * @return Le compte � rebours additionnel de fermeture d'une vente aux ench�res
//   * en secondes
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "COUNTDOWN_ADD", length = 6, nullable = true, unique = false)
//  public int getAditionalCountdown()
//  {
//    return this.aditionalCountdown;
//  }
//  /**
//   * Setter du compte � rebours additionnel de fermeture d'une vente aux ench�res
//   * en secondes
//   * @param aditionalCountdown Compte � rebours additionnel de fermeture d'une
//   * vente aux ench�res � positionner en secondes
//   *
//   */
//  private void setAditionalCountdown(int aditionalCountdown)
//  {
//    this.aditionalCountdown = aditionalCountdown;
//  }
//}
