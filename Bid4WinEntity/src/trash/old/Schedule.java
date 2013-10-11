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
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//
///**
// * Cette classe d�finie les �l�ments de planification d'une vente aux ench�res<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class Schedule<CLASS extends Schedule<CLASS>> extends ScheduleAbstract<CLASS>
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
//  protected Schedule()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param openingDate Date d'ouverture pr�vue d'une vente aux ench�res
//   * @param initialCountdown Compte � rebours initial de fermeture d'une vente aux
//   * ench�res en secondes
//   * @param aditionalCountdown Compte � rebours additionnel de fermeture d'une
//   * vente aux ench�res en secondes
//   * @throws ModelArgumentException Si la date d'ouverture pr�vue est nulle ou si
//   * le compte � rebours initial ou additionnel est inf�rieur � un
//   */
//  public Schedule(Bid4WinDate openingDate, int initialCountdown, int aditionalCountdown)
//         throws ModelArgumentException
//  {
//    super(openingDate);
//    this.defineInitialCountdown(initialCountdown);
//    this.defineAditionalCountdown(aditionalCountdown);
//  }
//
//  /**
//   * Red�fini l'�galit� interne de deux planifications par l'�galit� de leur contenu
//   * @param toBeCompared {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.old.ScheduleAbstract#equalsInternal(com.bid4win.persistence.entity.auction.old.ScheduleAbstract)
//   */
//  @Override
//  protected boolean equalsInternal(CLASS toBeCompared)
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
//   * Cette m�thode permet de d�finir le compte � rebours initial de fermeture d'
//   * une vente aux ench�res en secondes
//   * @param initialCountdown D�finition du compte � rebours initial de fermeture
//   * d'une vente aux ench�res en secondes
//   * @throws ProtectionException Si la planification courante est prot�g�e
//   * @throws ModelArgumentException Si on d�fini un compte � rebours inf�rieur �
//   * une seconde
//   */
//  private void defineInitialCountdown(int initialCountdown) throws ModelArgumentException
//  {
//    // V�rifie la protection de la planification courante
//    this.checkProtection();
//    this.setInitialCountdown(UtilNumber.checkMinValue(
//        "initialCountdown", initialCountdown, 1, true));
//  }
//  /**
//   * Cette m�thode permet de d�finir le compte � rebours additionnel de fermeture
//   * d' une vente aux ench�res en secondes
//   * @param aditionalCountdown D�finition du compte � rebours additionnel de fermeture
//   * d'une vente aux ench�res en secondes
//   * @throws ProtectionException Si la planification courante est prot�g�e
//   * @throws ModelArgumentException Si on d�fini un compte � rebours inf�rieur �
//   * une seconde
//   */
//  private void defineAditionalCountdown(int aditionalCountdown) throws ModelArgumentException
//  {
//    // V�rifie la protection de la planification courante
//    this.checkProtection();
//    this.setAditionalCountdown(UtilNumber.checkMinValue(
//        "aditionalCountdown", aditionalCountdown, 1, true));
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
//  @Column(name = "COUNTDOWN_INIT", length = 6, nullable = false, unique = false)
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
//  @Column(name = "COUNTDOWN_ADD", length = 6, nullable = false, unique = false)
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
