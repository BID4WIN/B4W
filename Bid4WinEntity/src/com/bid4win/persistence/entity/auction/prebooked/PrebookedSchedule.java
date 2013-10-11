package com.bid4win.persistence.entity.auction.prebooked;

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
public class PrebookedSchedule extends Schedule<PrebookedSchedule>
{
  /**
   * Constructeur pour création par introspection
   */
  protected PrebookedSchedule()
  {
    super();
  }
  /**
   * Constructeur
   * @param startDate Date de démarrage prévue d'une vente aux enchères avec pré-
   * inscription
   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
   * enchères avec pré-inscription en secondes
   * @param additionalCountdown Compte à rebours additionnel de fermeture d'une
   * vente aux enchères avec pré-inscription en secondes
   * @throws UserException Si la date d'ouverture prévue est nulle ou si le compte
   * à rebours initial ou additionnel est inférieur à un
   */
  public PrebookedSchedule(Bid4WinDate startDate, int initialCountdown, int additionalCountdown)
         throws UserException
  {
    super(startDate, initialCountdown, additionalCountdown);
  }
}
