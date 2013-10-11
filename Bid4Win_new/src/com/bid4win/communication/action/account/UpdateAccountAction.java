package com.bid4win.communication.action.account;

import org.apache.struts2.convention.annotation.Action;

/**
 * Action class. Handles a request for an account update
 * @author Maxime Ollagnier
 */
public class UpdateAccountAction extends AccountAction
{
  /**
   * Executes the account update
   * @return {@inheritDoc}
   * @see com.opensymphony.xwork2.ActionSupport#execute()
   */
  @Action("UpdateAccountAction")
  @Override
  public String execute()
  {
    return SUCCESS;
  }
}