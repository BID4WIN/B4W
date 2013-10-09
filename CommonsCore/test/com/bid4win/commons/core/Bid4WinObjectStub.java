package com.bid4win.commons.core;

import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.renderer.Bid4WinRenderer;


/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectStub extends Bid4WinObject<Bid4WinObjectStub>
{
  /** TODO A COMMENTER */
  private String key = null;
  /** TODO A COMMENTER */
  private String value = null;

  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   */
  public Bid4WinObjectStub(String key, String value)
  {
    this.key = key;
    this.value = value;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getKey()
  {
    return this.key;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getValue()
  {
    return this.value;
  }
  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(Bid4WinObjectStub toBeCompared)
  {
    return Bid4WinComparator.getInstance().equals(this.getKey(), toBeCompared.getKey()) &&
           Bid4WinComparator.getInstance().equals(this.getValue(), toBeCompared.getValue());
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
    return this.toString().hashCode();
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
    StringBuffer buffer = new StringBuffer("KEY=");
    buffer.append(Bid4WinRenderer.getInstance().toString(this.getKey()));
    buffer.append(" VALUE=");
    buffer.append(Bid4WinRenderer.getInstance().toString(this.getValue()));
    return buffer;
  }
}
