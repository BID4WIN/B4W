package com.bid4win.commons.persistence.entity.core;

import java.io.Serializable;

import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.comparator.Bid4WinComparator;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDateForRequest extends Bid4WinObject<Bid4WinDateForRequest>
       implements Serializable, Comparable<Bid4WinDateForRequest>
{
  /** TODO A COMMENTER */
  private static final long serialVersionUID = 5006979490116603886L;

  /**
   *
   * TODO A COMMENTER
   * @param date TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static Bid4WinDateForRequest getDateForRequest(Bid4WinDate date)
  {
    if(date == null)
    {
      return null;
    }
    return new Bid4WinDateForRequest(date);
  }
  /**
   *
   * TODO A COMMENTER
   * @param dates TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static Bid4WinDateForRequest[] getDateForRequests(Bid4WinDate ... dates)
  {
    Bid4WinDateForRequest[] result = new Bid4WinDateForRequest[dates.length];
    for(int i = 0 ; i < dates.length ; i++)
    {
      result[i] = Bid4WinDateForRequest.getDateForRequest(dates[i]);
    }
    return result;
  }

  /** TODO A COMMENTER */
  @Transient private Bid4WinDate date = null;

  /**
   *
   * TODO A COMMENTER
   * @param date TODO A COMMENTER
   */
  private Bid4WinDateForRequest(Bid4WinDate date)
  {
    this.setDate(date);
  }

  /**
   *
   * TODO A COMMENTER
   * @param date {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Bid4WinDateForRequest date)
  {
    if(date == null)
    {
      return this.getDate().compareTo(null);
    }
    return this.getDate().compareTo(date.getDate());
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  {
    if(this.getDate() == null)
    {
      return this.hashCodeDefault();
    }
    return this.getDate().hashCode();
  }
  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(Bid4WinDateForRequest toBeCompared)
  {
    return Bid4WinComparator.getInstance().equals(this.getDate(), toBeCompared.getDate());
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    if(this.getDate() == null)
    {
      return new StringBuffer("null");
    }
    return new StringBuffer(this.getDate().formatDDIMMIYYYY_HHIMMISSISSS());
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinDate getDate()
  {
    return this.date;
  }
  /**
   *
   * TODO A COMMENTER
   * @param date TODO A COMMENTER
   */
  private void setDate(Bid4WinDate date)
  {
    this.date = date;
  }

}
