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
   *
   * TODO A COMMENTER
   * @param invocation TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getActionName(ActionInvocation invocation)
  {
    return (String)invocation.getInvocationContext().getContextMap().get("com.opensymphony.xwork2.ActionContext.name");
  }

/*  public HttpServletRequest getRequest(ActionInvocation actionInvocation)
  {
    return (HttpServletRequest)actionInvocation.getInvocationContext().get(
        StrutsStatics.HTTP_REQUEST);
  }
  public HttpServletResponse getResponse(ActionInvocation actionInvocation)
  {
    return (HttpServletResponse)actionInvocation.getInvocationContext().get(
        StrutsStatics.HTTP_RESPONSE);
  }*/

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
