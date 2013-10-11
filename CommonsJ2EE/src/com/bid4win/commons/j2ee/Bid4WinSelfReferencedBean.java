package com.bid4win.commons.j2ee;

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
  protected void setSelf(BEAN self)
  {
    if(this.self != this)
    {
      System.out.println("self already defined");
    }
    else
    {
      this.self = self;
    }
  }
}
