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
 * Cette classe d�finie les �l�ments de planification d'une vente aux ench�res<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Schedule<CLASS extends Schedule<CLASS>> extends ScheduleAbstract<CLASS>
{
  /** Compte � rebours initial de fermeture d'une vente aux ench�res en secondes */
  @Transient
  private int initialCountdown = 0;
  /** Compte � rebours additionnel de fermeture d'une vente aux ench�res en secondes */
  @Transient
  private int additionalCountdown = 0;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Schedule()
  {
    super();
  }
  /**
   * Constructeur
   * @param startDate Date de d�marrage pr�vue d'une vente aux ench�res
   * @param initialCountdown Compte � rebours initial de fermeture d'une vente aux
   * ench�res en secondes
   * @param additionalCountdown Compte � rebours additionnel de fermeture d'une
   * vente aux ench�res en secondes
   * @throws UserException Si la date d'ouverture pr�vue est nulle ou si le compte
   * � rebours initial ou additionnel est inf�rieur � un
   */
  public Schedule(Bid4WinDate startDate, int initialCountdown, int additionalCountdown)
         throws UserException
  {
    super(startDate);
    this.defineInitialCountdown(initialCountdown);
    this.defineAdditionalCountdown(additionalCountdown);
  }

  /**
   * Red�fini l'�galit� interne de deux planifications par l'�galit� de leur contenu
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
   * Red�fini la transformation en cha�ne de caract�res d'une planification lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.ScheduleAbstract#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'une planification de vente aux ench�res
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments de planification d'une vente aux ench�res
    buffer.append(" INITIAL_COUNTDOWN=").append(this.getInitialCountdown());
    buffer.append(" ADDITIONAL_COUNTDOWN=").append(this.getAdditionalCountdown());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de d�finir la nouvelle date de cl�ture pr�vue de la vente
   * aux ench�res courante en fonction de la date d'ench�re en param�tre. Si la
   * date d'ench�re se trouve dans le compte � rebours final correspondant � la
   * valeur du compte � rebours additionnel, la date de cl�ture se retrouve donc
   * repouss�e de cette m�me valeur. Sinon, elle n'est pas modifi�e
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
   * Cette m�thode permet de d�finir le compte � rebours initial de fermeture d'
   * une vente aux ench�res en secondes
   * @param initialCountdown D�finition du compte � rebours initial de fermeture
   * d'une vente aux ench�res en secondes
   * @throws ProtectionException Si la planification courante est prot�g�e
   * @throws UserException Si on d�fini un compte � rebours inf�rieur � une seconde
   */
  private void defineInitialCountdown(int initialCountdown) throws UserException
  {
    // V�rifie la protection de la planification courante
    this.checkProtection();
    UtilNumber.checkMinValue("initialCountdown", initialCountdown, 1, true,
                             AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    this.setInitialCountdown(initialCountdown);
    this.defineNewEndDate(this.getStartDate());
  }
  /**
   * Cette m�thode permet de d�finir le compte � rebours additionnel de fermeture
   * d' une vente aux ench�res en secondes
   * @param aditionalCountdown D�finition du compte � rebours additionnel de fermeture
   * d'une vente aux ench�res en secondes
   * @throws ProtectionException Si la planification courante est prot�g�e
   * @throws UserException Si on d�fini un compte � rebours inf�rieur � une seconde
   */
  private void defineAdditionalCountdown(int aditionalCountdown) throws UserException
  {
    // V�rifie la protection de la planification courante
    this.checkProtection();
    UtilNumber.checkMinValue("initialCountdown", aditionalCountdown, 1, true,
                             AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    this.setAdditionalCountdown(aditionalCountdown);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du compte � rebours initial de fermeture d'une vente aux ench�res en
   * secondes
   * @return Le compte � rebours initial de fermeture d'une vente aux ench�res en
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
   * Setter du compte � rebours initial de fermeture d'une vente aux ench�res en
   * secondes
   * @param initialCountdown Compte � rebours initial de fermeture d'une vente
   * aux ench�res � positionner en secondes
   *
   */
  private void setInitialCountdown(int initialCountdown)
  {
    this.initialCountdown = initialCountdown;
  }

  /**
   * Getter du compte � rebours additionnel de fermeture d'une vente aux ench�res
   * en secondes
   * @return Le compte � rebours additionnel de fermeture d'une vente aux ench�res
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
   * Setter du compte � rebours additionnel de fermeture d'une vente aux ench�res
   * en secondes
   * @param additionalCountdown Compte � rebours additionnel de fermeture d'une
   * vente aux ench�res � positionner en secondes
   *
   */
  private void setAdditionalCountdown(int additionalCountdown)
  {
    this.additionalCountdown = additionalCountdown;
  }
}
