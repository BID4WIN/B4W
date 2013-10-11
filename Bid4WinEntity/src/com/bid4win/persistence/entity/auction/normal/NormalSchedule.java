package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.Schedule;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class NormalSchedule extends Schedule<NormalSchedule>
{
  /**
   * Constructeur pour création par introspection
   */
  protected NormalSchedule()
  {
    super();
  }
  /**
   * Constructeur
   * @param startDate Date de démarrage prévue d'une vente aux enchères normale
   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
   * enchères normale en secondes
   * @param additionalCountdown Compte à rebours additionnel de fermeture d'une
   * vente aux enchères normale en secondes
   * @throws UserException Si la date d'ouverture prévue est nulle ou si le compte
   * à rebours initial ou additionnel est inférieur à un
   */
  public NormalSchedule(Bid4WinDate startDate, int initialCountdown, int additionalCountdown)
         throws UserException
  {
    super(startDate, initialCountdown, additionalCountdown);
  }
}
