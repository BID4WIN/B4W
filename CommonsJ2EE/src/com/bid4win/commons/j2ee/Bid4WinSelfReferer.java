package com.bid4win.commons.j2ee;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BEAN> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinSelfReferer<BEAN extends Bid4WinSelfReferencedBean<BEAN>>
{
  /**
   *
   * TODO A COMMENTER
   * @param bean TODO A COMMENTER
   */
  public void setBean(BEAN bean)
  {
    bean.setSelf(bean);
  }
}
