package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Cette classe d�finie les �l�ments de planification de base d'une vente aux ench�res<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class ScheduleAbstract<CLASS extends ScheduleAbstract<CLASS>>
       extends Bid4WinEmbeddable<CLASS>
{
  /** Date de d�marrage pr�vue d'une vente aux ench�res */
  @Transient
  private Bid4WinDate startDate = null;
  /** Date de cl�ture pr�vue d'une vente aux ench�res */
  @Transient
  private Bid4WinDate endDate = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected ScheduleAbstract()
  {
    super();
  }
  /**
   * Constructeur sans positionnement de date de fin
   * @param startDate Date de d�marrage pr�vue d'une vente aux ench�res
   * @throws UserException Si la date d'ouverture pr�vue est nulle
   */
  protected ScheduleAbstract(Bid4WinDate startDate) throws UserException
  {
    this(startDate, Bid4WinDate.FINAL_DATE);
  }
  /**
   * Constructeur
   * @param startDate Date de d�marrage pr�vue d'une vente aux ench�res
   * @param endDate Date de cl�ture pr�vue d'une vente aux ench�res
   * @throws UserException Si la date d'ouverture pr�vue est nulle
   */
  protected ScheduleAbstract(Bid4WinDate startDate, Bid4WinDate endDate) throws UserException
  {
    super();
    this.defineStartDate(startDate);
    this.defineEndDate(endDate);
  }

  /**
   * Red�fini l'�galit� interne de deux planifications par l'�galit� de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(CLASS toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getStartDate(), toBeCompared.getStartDate()) &&
           Bid4WinComparator.getInstance().equals(this.getEndDate(), toBeCompared.getEndDate());
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'une planification lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments de planification de base d'une vente aux ench�res
    buffer.append("START_DATE=").append(this.getStartDate().formatDDIMMIYYYY_HHIMMISSISSS());
    buffer.append("END_DATE=").append(this.getEndDate().formatDDIMMIYYYY_HHIMMISSISSS());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de conna�tre le temps en millisecondes s�parant la date
   * en argument de la date de d�marrage pr�vue
   * @param date Date � partir de laquelle calculer le temps la s�parant de la date
   * de d�marrage pr�vue
   * @return Le temps en millisecondes s�parant la date en argument de la date de
   * d�marrage pr�vue ou z�ro si celle-ci est d�pass�e
   */
  public long getStartCountdown(Bid4WinDate date)
  {
    return Math.max(0, date.getTimeBetween(this.getStartDate()));
  }
  /**
   * Cette m�thode permet de conna�tre le temps en millisecondes s�parant la date
   * en argument de la date de cl�ture pr�vue
   * @param date Date � partir de laquelle calculer le temps la s�parant de la date
   * de cl�ture pr�vue
   * @return Le temps en millisecondes s�parant la date en argument de la date de
   * cl�ture pr�vue ou z�ro si celle-ci est d�pass�e
   */
  public long getEndCountdown(Bid4WinDate date)
  {
    return Math.max(0, date.getTimeBetween(this.getEndDate()));
  }

  /**
   * Cette m�thode permet de d�finir la date de d�marrage pr�vue d'une vente aux
   * ench�res
   * @param startDate D�finition de la date de d�marrage pr�vue
   * @throws ProtectionException Si la planification courante est prot�g�e
   * @throws UserException Si on d�fini une date de d�marrage nulle
   */
  private void defineStartDate(Bid4WinDate startDate) throws UserException
  {
    // V�rifie la protection de la planification de base courante
    this.checkProtection();
    UtilObject.checkNotNull("startDate", startDate,
                            AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    this.setStartDate(startDate.removeSecondInfo());
  }
  /**
   * Cette m�thode permet de d�finir la date de cl�ture pr�vue d'une vente aux
   * ench�res
   * @param endDate D�finition de la date de cl�ture pr�vue
   * @throws ProtectionException Si la planification courante est prot�g�e
   * @throws UserException Si on d�fini une date de cl�ture nulle ou ant�rieure
   * � la date de d�marrage
   */
  protected void defineEndDate(Bid4WinDate endDate) throws UserException
  {
    // V�rifie la protection de la planification de base courante
    this.checkProtection();
    UtilObject.checkNotNull("endDate", endDate, AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    endDate = endDate.removeMilliSecondInfo();
    UtilBoolean.checkTrue("endeDate", endDate.after(this.getStartDate()),
                          AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    this.setEndDate(endDate);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la date de d�marrage pr�vue d'une vente aux ench�res
   * @return La date de d�marrage pr�vue d'une vente aux ench�res
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "START_DATE", length = 23, nullable = false, unique = false)
  public Bid4WinDate getStartDate()
  {
    return this.startDate;
  }
  /**
   * Setter de la date de d�marrage pr�vue d'une vente aux ench�res
   * @param startDate Date de d�marrage pr�vue d'une vente aux ench�res � positionner
   */
  private void setStartDate(Bid4WinDate startDate)
  {
    this.startDate = startDate;
  }

  /**
   * Getter de la date de cl�ture pr�vue d'une vente aux ench�res
   * @return La date de cl�ture pr�vue d'une vente aux ench�res
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "END_DATE", length = 23, nullable = false, unique = false)
  public Bid4WinDate getEndDate()
  {
    return this.endDate;
  }
  /**
   * Setter de la date de cl�ture pr�vue d'une vente aux ench�res
   * @param endDate Date de cl�ture pr�vue d'une vente aux ench�res � positionner
   */
  private void setEndDate(Bid4WinDate endDate)
  {
    this.endDate = endDate;
  }
}
