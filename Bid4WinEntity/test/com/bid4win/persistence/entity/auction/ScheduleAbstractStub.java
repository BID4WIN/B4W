package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ScheduleAbstractStub extends ScheduleAbstract<ScheduleAbstractStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected ScheduleAbstractStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param startDate Date de d�marrage pr�vue d'une vente aux ench�res
   * @throws UserException Si la date d'ouverture pr�vue est nulle
   */
  public ScheduleAbstractStub(Bid4WinDate startDate) throws UserException
  {
    super(startDate);
  }
  /**
   * Constructeur
   * @param startDate Date de d�marrage pr�vue d'une vente aux ench�res
   * @param endDate Date de cl�ture pr�vue d'une vente aux ench�res
   * @throws UserException Si la date d'ouverture pr�vue est nulle
   */
  public ScheduleAbstractStub(Bid4WinDate startDate, Bid4WinDate endDate) throws UserException
  {
    super(startDate, endDate);
  }
}
