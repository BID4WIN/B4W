package com.bid4win.communication.action.account;

import org.apache.struts2.convention.annotation.Action;

import com.bid4win.communication.action.json.JSONAction;

/**
 * Action class. Handles a request for an account deletion.
 * 
 * @author Maxime Ollagnier
 */
public class RemoveAccountAction extends JSONAction
{
  /**
   * Asks the AccountManager to remove the account whom id is passed within the
   * request. The JSON result is filled with the removed JSON account.
   * @return {@inheritDoc}
   * @see com.opensymphony.xwork2.ActionSupport#execute()
   */
  @Action("RemoveAccountAction")
  @Override
  public String execute()
  {
    // try
    // {
//      String accountId = this.getParameter("id");
//      Account removedAccount = null;
//
//      removedAccount = AccountManager.getInstance().removeAccount(accountId);
//      AccountManager.getInstance().persist();
//
//      this.jsonResult = JSONAccountFactory.getInstance().getJSONAccount(removedAccount);
//    }
//    catch(Exception e)
//    {
//      this.jsonResult = JSONErrorFactory.getInstance().getJSONError(e);
//      e.printStackTrace();
//    }
    return SUCCESS;
  }
}