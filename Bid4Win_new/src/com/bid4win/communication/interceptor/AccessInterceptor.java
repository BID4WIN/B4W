package com.bid4win.communication.interceptor;

import com.bid4win.security.ConnectionHandler;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * TODO A COMMENTER
 *
 * @author Maxime Ollagnier
 */
public class AccessInterceptor extends Interceptor
{
  /** Serial */
  private static final long serialVersionUID = -5089660090669925284L;

  /**
   * {@inheritDoc}
   * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
   */
  @Override
  public String intercept(ActionInvocation actionInvocation) throws Exception
  {
    String accountId = this.getAction(actionInvocation).getSession().getAccountId();
    if(accountId != null)
    {
      ConnectionHandler.getInstance().connectId(accountId);
    }
    String result = null;
    try
    {
      result = actionInvocation.invoke();
    }
    finally
    {
      if(accountId != null)
      {
        ConnectionHandler.getInstance().disconnectId(accountId);
      }
    }
    return result;
  }
}
