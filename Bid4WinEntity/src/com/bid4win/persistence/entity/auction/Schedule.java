package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe définie les éléments de planification d'une vente aux enchères<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Schedule<CLASS extends Schedule<CLASS>> extends ScheduleAbstract<CLASS>
{
  /** Compte à rebours initial de fermeture d'une vente aux enchères en secondes */
  @Transient
  private int initialCountdown = 0;
  /** Compte à rebours additionnel de fermeture d'une vente aux enchères en secondes */
  @Transient
  private int additionalCountdown = 0;

  /**
   * Constructeur pour création par introspection
   */
  protected Schedule()
  {
    super();
  }
  /**
   * Constructeur
   * @param startDate Date de démarrage prévue d'une vente aux enchères
   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
   * enchères en secondes
   * @param additionalCountdown Compte à rebours additionnel de fermeture d'une
   * vente aux enchères en secondes
   * @throws UserException Si la date d'ouverture prévue est nulle ou si le compte
   * à rebours initial ou additionnel est inférieur à un
   */
  public Schedule(Bid4WinDate startDate, int initialCountdown, int additionalCountdown)
         throws UserException
  {
    super(startDate);
    this.defineInitialCountdown(initialCountdown);
    this.defineAdditionalCountdown(additionalCountdown);
  }

  /**
   * Redéfini l'égalité interne de deux planifications par l'égalité de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.ScheduleAbstract#equalsInternal(com.bid4win.persistence.entity.auction.ScheduleAbstract)
   */
  @Override
  protected boolean equalsInternal(CLASS toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           this.getInitialCountdown() == toBeCompared.getInitialCountdown() &&
           this.getAdditionalCountdown() == toBeCompared.getAdditionalCountdown();
  }
  /**
   * Redéfini la transformation en chaîne de caractères d'une planification lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.ScheduleAbstract#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'une planification de vente aux enchères
    StringBuffer buffer = super.render();
    // Ajoute les éléments de planification d'une vente aux enchères
    buffer.append(" INITIAL_COUNTDOWN=").append(this.getInitialCountdown());
    buffer.append(" ADDITIONAL_COUNTDOWN=").append(this.getAdditionalCountdown());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette méthode permet de définir la nouvelle date de clôture prévue de la vente
   * aux enchères courante en fonction de la date d'enchère en paramètre. Si la
   * date d'enchère se trouve dans le compte à rebours final correspondant à la
   * valeur du compte à rebours additionnel, la date de clôture se retrouve donc
   * repoussée de cette même valeur. Sinon, elle n'est pas modifiée
   * @param bidDate TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected Bid4WinDate defineNewEndDate(Bid4WinDate bidDate) throws UserException
  {
    UtilObject.checkNotNull("bidDate", bidDate, AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    Bid4WinDate endDate = this.getEndDate();
    if(endDate == null || endDate.equals(Bid4WinDate.FINAL_DATE))
    {
      endDate = this.getStartDate().addTime(this.getInitialCountdown() * 1000);
    }
    else if(bidDate.addTime(this.getAdditionalCountdown() * 1000).after(endDate))
    {
      endDate = endDate.addTime(this.getAdditionalCountdown() * 1000);
    }
    this.defineEndDate(endDate);
    return this.getEndDate();
  }
  /**
   * Cette méthode permet de définir le compte à rebours initial de fermeture d'
   * une vente aux enchères en secondes
   * @param initialCountdown Définition du compte à rebours initial de fermeture
   * d'une vente aux enchères en secondes
   * @throws ProtectionException Si la planification courante est protégée
   * @throws UserException Si on défini un compte à rebours inférieur à une seconde
   */
  private void defineInitialCountdown(int initialCountdown) throws UserException
  {
    // Vérifie la protection de la planification courante
    this.checkProtection();
    UtilNumber.checkMinValue("initialCountdown", initialCountdown, 1, true,
                             AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    this.setInitialCountdown(initialCountdown);
    this.defineNewEndDate(this.getStartDate());
  }
  /**
   * Cette méthode permet de définir le compte à rebours additionnel de fermeture
   * d' une vente aux enchères en secondes
   * @param aditionalCountdown Définition du compte à rebours additionnel de fermeture
   * d'une vente aux enchères en secondes
   * @throws ProtectionException Si la planification courante est protégée
   * @throws UserException Si on défini un compte à rebours inférieur à une seconde
   */
  private void defineAdditionalCountdown(int aditionalCountdown) throws UserException
  {
    // Vérifie la protection de la planification courante
    this.checkProtection();
    UtilNumber.checkMinValue("initialCountdown", aditionalCountdown, 1, true,
                             AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    this.setAdditionalCountdown(aditionalCountdown);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du compte à rebours initial de fermeture d'une vente aux enchères en
   * secondes
   * @return Le compte à rebours initial de fermeture d'une vente aux enchères en
   * secondes
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "COUNTDOWN_INIT", length = 6, nullable = false, unique = false)
  public int getInitialCountdown()
  {
    return this.initialCountdown;
  }
  /**
   * Setter du compte à rebours initial de fermeture d'une vente aux enchères en
   * secondes
   * @param initialCountdown Compte à rebours initial de fermeture d'une vente
   * aux enchères à positionner en secondes
   *
   */
  private void setInitialCountdown(int initialCountdown)
  {
    this.initialCountdown = initialCountdown;
  }

  /**
   * Getter du compte à rebours additionnel de fermeture d'une vente aux enchères
   * en secondes
   * @return Le compte à rebours additionnel de fermeture d'une vente aux enchères
   * en secondes
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "COUNTDOWN_ADD", length = 6, nullable = false, unique = false)
  public int getAdditionalCountdown()
  {
    return this.additionalCountdown;
  }
  /**
   * Setter du compte à rebours additionnel de fermeture d'une vente aux enchères
   * en secondes
   * @param additionalCountdown Compte à rebours additionnel de fermeture d'une
   * vente aux enchères à positionner en secondes
   *
   */
  private void setAdditionalCountdown(int additionalCountdown)
  {
    this.additionalCountdown = additionalCountdown;
  }
}
