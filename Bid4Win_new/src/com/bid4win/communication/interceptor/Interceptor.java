package com.bid4win.communication.interceptor;

import com.bid4win.communication.action.BaseAction;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * Custom parent abstract Interceptor class.
 *
 * @author Maxime Ollagnier
 */
public abstract class Interceptor implements com.opensymphony.xwork2.interceptor.Interceptor
{
  /** Serial */
  private static final long serialVersionUID = -533572738315537513L;

  /**
   * @param actionInvocation The action invocation
   * @return The invocated action
   */
  public BaseAction getAction(ActionInvocation actionInvocation)
  {
    return (BaseAction)actionInvocation.getAction();
  }

  /**
   * {@inheritDoc}
   * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
   */
  @Override
  public void init()
  {
    // null
  }

  /**
   * {@inheritDoc}
   * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
   */
  @Override
  public void destroy()
  {
    // null
  }
}
