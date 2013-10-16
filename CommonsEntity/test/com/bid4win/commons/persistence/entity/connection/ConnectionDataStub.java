package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionDataStub extends ConnectionData
{
  /** TODO A COMMENTER */
  private Bid4WinDate startDate = null;

  /**
   *
   * TODO A COMMENTER
   * @param data TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public ConnectionDataStub(ConnectionData data, Bid4WinDate startDate) throws UserException
  {
    super(data.getSessionId(), data.getIpAddress(), data.isRemanent());
    this.defineStartDate(startDate);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionData#getStartDate()
   */
  @Override
  public Bid4WinDate getStartDate()
  {
    return this.startDate;
  }
  /**
   *
   * TODO A COMMENTER
   * @param startDate TODO A COMMENTER
   */
  protected void defineStartDate(Bid4WinDate startDate)
  {
    this.startDate = startDate;
  }
}
