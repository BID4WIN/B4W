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
// * Cette classe définie les éléments de planification d'une vente aux enchères<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class Schedule<CLASS extends Schedule<CLASS>> extends ScheduleAbstract<CLASS>
//{
//  /** Compte à rebours initial de fermeture d'une vente aux enchères en secondes */
//  @Transient
//  private int initialCountdown = 0;
//  /** Compte à rebours additionnel de fermeture d'une vente aux enchères en secondes */
//  @Transient
//  private int aditionalCountdown = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected Schedule()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param openingDate Date d'ouverture prévue d'une vente aux enchères
//   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
//   * enchères en secondes
//   * @param aditionalCountdown Compte à rebours additionnel de fermeture d'une
//   * vente aux enchères en secondes
//   * @throws ModelArgumentException Si la date d'ouverture prévue est nulle ou si
//   * le compte à rebours initial ou additionnel est inférieur à un
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
//   * Redéfini l'égalité interne de deux planifications par l'égalité de leur contenu
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
//   * Redéfini la transformation en chaîne de caractères d'une planification lisiblement
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.ScheduleAbstract#render()
//   */
//  @Override
//  public StringBuffer render()
//  {
//    // Effectue le rendu de base d'une planification de vente aux enchères
//    StringBuffer buffer = super.render();
//    // Ajoute les éléments de planification d'une vente aux enchères
//    buffer.append(" INITIAL_COUNTDOWN=").append(this.getInitialCountdown());
//    buffer.append(" ADITIONAL_COUNTDOWN=").append(this.getAditionalCountdown());
//    // Retourne le rendu
//    return buffer;
//  }
//
//  /**
//   * Cette méthode permet de définir le compte à rebours initial de fermeture d'
//   * une vente aux enchères en secondes
//   * @param initialCountdown Définition du compte à rebours initial de fermeture
//   * d'une vente aux enchères en secondes
//   * @throws ProtectionException Si la planification courante est protégée
//   * @throws ModelArgumentException Si on défini un compte à rebours inférieur à
//   * une seconde
//   */
//  private void defineInitialCountdown(int initialCountdown) throws ModelArgumentException
//  {
//    // Vérifie la protection de la planification courante
//    this.checkProtection();
//    this.setInitialCountdown(UtilNumber.checkMinValue(
//        "initialCountdown", initialCountdown, 1, true));
//  }
//  /**
//   * Cette méthode permet de définir le compte à rebours additionnel de fermeture
//   * d' une vente aux enchères en secondes
//   * @param aditionalCountdown Définition du compte à rebours additionnel de fermeture
//   * d'une vente aux enchères en secondes
//   * @throws ProtectionException Si la planification courante est protégée
//   * @throws ModelArgumentException Si on défini un compte à rebours inférieur à
//   * une seconde
//   */
//  private void defineAditionalCountdown(int aditionalCountdown) throws ModelArgumentException
//  {
//    // Vérifie la protection de la planification courante
//    this.checkProtection();
//    this.setAditionalCountdown(UtilNumber.checkMinValue(
//        "aditionalCountdown", aditionalCountdown, 1, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du compte à rebours initial de fermeture d'une vente aux enchères en
//   * secondes
//   * @return Le compte à rebours initial de fermeture d'une vente aux enchères en
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
//   * Setter du compte à rebours initial de fermeture d'une vente aux enchères en
//   * secondes
//   * @param initialCountdown Compte à rebours initial de fermeture d'une vente
//   * aux enchères à positionner en secondes
//   *
//   */
//  private void setInitialCountdown(int initialCountdown)
//  {
//    this.initialCountdown = initialCountdown;
//  }
//
//  /**
//   * Getter du compte à rebours additionnel de fermeture d'une vente aux enchères
//   * en secondes
//   * @return Le compte à rebours additionnel de fermeture d'une vente aux enchères
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
//   * Setter du compte à rebours additionnel de fermeture d'une vente aux enchères
//   * en secondes
//   * @param aditionalCountdown Compte à rebours additionnel de fermeture d'une
//   * vente aux enchères à positionner en secondes
//   *
//   */
//  private void setAditionalCountdown(int aditionalCountdown)
//  {
//    this.aditionalCountdown = aditionalCountdown;
//  }
//}
