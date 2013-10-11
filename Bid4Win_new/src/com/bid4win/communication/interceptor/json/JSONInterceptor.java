package com.bid4win.communication.interceptor.json;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.interceptor.Interceptor;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * <BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONInterceptor extends Interceptor
{

  /** Serial */
  private static final long serialVersionUID = -7203945618618655832L;

  /**
   * {@inheritDoc}
   * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
   */
  @Override
  public String intercept(ActionInvocation actionInvocation) throws Exception
  {
    String result = Action.SUCCESS;
    try
    {
      result = actionInvocation.invoke();
    }
    catch(UserException ex)
    {
      this.getAction(actionInvocation).putWarnMessage(ex);
      ex.printStackTrace();
    }
    catch(Throwable th)
    {
      this.getAction(actionInvocation).putErrorMessage(MessageRef.SUFFIX_UNKNOWN_ERROR);
      th.printStackTrace();
    }
    return result;
  }

  /**
   * @param actionInvocation The action invocation
   * @return The invocated action
   */
  @Override
  public JSONAction getAction(ActionInvocation actionInvocation)
  {
    return (JSONAction)actionInvocation.getAction();
  }

}
