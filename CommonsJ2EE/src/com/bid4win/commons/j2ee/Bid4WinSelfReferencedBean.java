package com.bid4win.commons.j2ee;

import javax.annotation.PreDestroy;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BEAN> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinSelfReferencedBean<BEAN extends Bid4WinSelfReferencedBean<BEAN>>
{
  /** TODO A COMMENTER */
  @SuppressWarnings("unchecked")
  private BEAN self = (BEAN)this;
  /** TODO A COMMENTER */
  private boolean registered = false;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public BEAN self()
  {
    return this.self;
  }
  /**
   *
   * TODO A COMMENTER
   * @param self TODO A COMMENTER
   */
  protected synchronized void setSelf(BEAN self)
  {
    if(this.registered)
    {
      throw new RuntimeException("self already defined");
    }
    this.registered = true;
    this.self = self;
  }

  /**
   *
   * TODO A COMMENTER
   */
  @SuppressWarnings({"unused", "unchecked"})
  @PreDestroy
  private void unregister()
  {
    this.self = (BEAN)this;
  }
}
