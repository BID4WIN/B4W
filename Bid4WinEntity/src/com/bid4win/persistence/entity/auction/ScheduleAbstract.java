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
 * Cette classe définie les éléments de planification de base d'une vente aux enchères<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class ScheduleAbstract<CLASS extends ScheduleAbstract<CLASS>>
       extends Bid4WinEmbeddable<CLASS>
{
  /** Date de démarrage prévue d'une vente aux enchères */
  @Transient
  private Bid4WinDate startDate = null;
  /** Date de clôture prévue d'une vente aux enchères */
  @Transient
  private Bid4WinDate endDate = null;

  /**
   * Constructeur pour création par introspection
   */
  protected ScheduleAbstract()
  {
    super();
  }
  /**
   * Constructeur sans positionnement de date de fin
   * @param startDate Date de démarrage prévue d'une vente aux enchères
   * @throws UserException Si la date d'ouverture prévue est nulle
   */
  protected ScheduleAbstract(Bid4WinDate startDate) throws UserException
  {
    this(startDate, Bid4WinDate.FINAL_DATE);
  }
  /**
   * Constructeur
   * @param startDate Date de démarrage prévue d'une vente aux enchères
   * @param endDate Date de clôture prévue d'une vente aux enchères
   * @throws UserException Si la date d'ouverture prévue est nulle
   */
  protected ScheduleAbstract(Bid4WinDate startDate, Bid4WinDate endDate) throws UserException
  {
    super();
    this.defineStartDate(startDate);
    this.defineEndDate(endDate);
  }

  /**
   * Redéfini l'égalité interne de deux planifications par l'égalité de leur contenu
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
   * Redéfini la transformation en chaîne de caractères d'une planification lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments de planification de base d'une vente aux enchères
    buffer.append("START_DATE=").append(this.getStartDate().formatDDIMMIYYYY_HHIMMISSISSS());
    buffer.append("END_DATE=").append(this.getEndDate().formatDDIMMIYYYY_HHIMMISSISSS());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette méthode permet de connaître le temps en millisecondes séparant la date
   * en argument de la date de démarrage prévue
   * @param date Date à partir de laquelle calculer le temps la séparant de la date
   * de démarrage prévue
   * @return Le temps en millisecondes séparant la date en argument de la date de
   * démarrage prévue ou zéro si celle-ci est dépassée
   */
  public long getStartCountdown(Bid4WinDate date)
  {
    return Math.max(0, date.getTimeBetween(this.getStartDate()));
  }
  /**
   * Cette méthode permet de connaître le temps en millisecondes séparant la date
   * en argument de la date de clôture prévue
   * @param date Date à partir de laquelle calculer le temps la séparant de la date
   * de clôture prévue
   * @return Le temps en millisecondes séparant la date en argument de la date de
   * clôture prévue ou zéro si celle-ci est dépassée
   */
  public long getEndCountdown(Bid4WinDate date)
  {
    return Math.max(0, date.getTimeBetween(this.getEndDate()));
  }

  /**
   * Cette méthode permet de définir la date de démarrage prévue d'une vente aux
   * enchères
   * @param startDate Définition de la date de démarrage prévue
   * @throws ProtectionException Si la planification courante est protégée
   * @throws UserException Si on défini une date de démarrage nulle
   */
  private void defineStartDate(Bid4WinDate startDate) throws UserException
  {
    // Vérifie la protection de la planification de base courante
    this.checkProtection();
    UtilObject.checkNotNull("startDate", startDate,
                            AuctionRef.AUCTION_SCHEDULE_INVALID_ERROR);
    this.setStartDate(startDate.removeSecondInfo());
  }
  /**
   * Cette méthode permet de définir la date de clôture prévue d'une vente aux
   * enchères
   * @param endDate Définition de la date de clôture prévue
   * @throws ProtectionException Si la planification courante est protégée
   * @throws UserException Si on défini une date de clôture nulle ou antérieure
   * à la date de démarrage
   */
  protected void defineEndDate(Bid4WinDate endDate) throws UserException
  {
    // Vérifie la protection de la planification de base courante
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
   * Getter de la date de démarrage prévue d'une vente aux enchères
   * @return La date de démarrage prévue d'une vente aux enchères
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "START_DATE", length = 23, nullable = false, unique = false)
  public Bid4WinDate getStartDate()
  {
    return this.startDate;
  }
  /**
   * Setter de la date de démarrage prévue d'une vente aux enchères
   * @param startDate Date de démarrage prévue d'une vente aux enchères à positionner
   */
  private void setStartDate(Bid4WinDate startDate)
  {
    this.startDate = startDate;
  }

  /**
   * Getter de la date de clôture prévue d'une vente aux enchères
   * @return La date de clôture prévue d'une vente aux enchères
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "END_DATE", length = 23, nullable = false, unique = false)
  public Bid4WinDate getEndDate()
  {
    return this.endDate;
  }
  /**
   * Setter de la date de clôture prévue d'une vente aux enchères
   * @param endDate Date de clôture prévue d'une vente aux enchères à positionner
   */
  private void setEndDate(Bid4WinDate endDate)
  {
    this.endDate = endDate;
  }
}
