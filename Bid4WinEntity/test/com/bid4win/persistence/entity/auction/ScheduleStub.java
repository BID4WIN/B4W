package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ScheduleStub extends Schedule<ScheduleStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected ScheduleStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param openingDate Date d'ouverture pr�vue d'une vente aux ench�res
   * @param initialCountdown Compte � rebours initial de fermeture d'une vente aux
   * ench�res en secondes
   * @param aditionalCountdown Compte � rebours additionnel de fermeture d'une
   * vente aux ench�res en secondes
   * @throws UserException Si la date d'ouverture pr�vue est nulle ou si le compte
   * � rebours initial ou additionnel est inf�rieur � un
   */
  public ScheduleStub(Bid4WinDate openingDate, int initialCountdown, int aditionalCountdown)
         throws UserException
  {
    super(openingDate, initialCountdown, aditionalCountdown);
  }
}
