package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ScheduleAbstractStub extends ScheduleAbstract<ScheduleAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected ScheduleAbstractStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param startDate Date de démarrage prévue d'une vente aux enchères
   * @throws UserException Si la date d'ouverture prévue est nulle
   */
  public ScheduleAbstractStub(Bid4WinDate startDate) throws UserException
  {
    super(startDate);
  }
  /**
   * Constructeur
   * @param startDate Date de démarrage prévue d'une vente aux enchères
   * @param endDate Date de clôture prévue d'une vente aux enchères
   * @throws UserException Si la date d'ouverture prévue est nulle
   */
  public ScheduleAbstractStub(Bid4WinDate startDate, Bid4WinDate endDate) throws UserException
  {
    super(startDate, endDate);
  }
}
