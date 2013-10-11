package com.bid4win.commons.persistence.usertype.core;

import java.util.Properties;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
public abstract class Bid4WinDateUserTypeTester
       extends Bid4WinStringUserTypeTester<Bid4WinDate, Bid4WinDateUserType>
{
  /** TODO A COMMENTER */
  private final Bid4WinDate date = new Bid4WinDate();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getUserType()
   */
  @Override
  protected Bid4WinDateUserType getUserType()
  {
    Bid4WinDateUserType userType = new Bid4WinDateUserType();
    Properties properties = new Properties();
    properties.put(Bid4WinDateUserType.TIME_PARAMETER, "" + this.isTimeNeeded());
    userType.setParameterValues(properties);
    return userType;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract boolean isTimeNeeded();

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getType()
   */
  @Override
  protected Bid4WinDate getType()
  {
    return this.date;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getExpected()
   */
  @Override
  protected Bid4WinDate getExpected() throws Bid4WinException
  {
    Bid4WinDate expected = super.getExpected();
    if(!this.isTimeNeeded())
    {
      expected = expected.removeTimeInfo();
    }
    return expected;
  }
}
