package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ScheduleStub extends Schedule<ScheduleStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected ScheduleStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param openingDate Date d'ouverture prévue d'une vente aux enchères
   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
   * enchères en secondes
   * @param aditionalCountdown Compte à rebours additionnel de fermeture d'une
   * vente aux enchères en secondes
   * @throws UserException Si la date d'ouverture prévue est nulle ou si le compte
   * à rebours initial ou additionnel est inférieur à un
   */
  public ScheduleStub(Bid4WinDate openingDate, int initialCountdown, int aditionalCountdown)
         throws UserException
  {
    super(openingDate, initialCountdown, aditionalCountdown);
  }
}
