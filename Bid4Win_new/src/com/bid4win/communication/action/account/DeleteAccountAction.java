package com.bid4win.communication.action.account;

import org.apache.struts2.convention.annotation.Action;

import com.bid4win.communication.action.json.JSONAction;

/**
 * Action class. Handles a request for an account soft deletion.
 * (Sets the account status to "deleted")
 *
 * @author Maxime Ollagnier
 */
public class DeleteAccountAction extends JSONAction
{
  /**
   * Asks the AccountManager to set the account status to "deleted". The JSON
   * result is filled with the deleted JSON account.
   * @return {@inheritDoc}
   * @see com.opensymphony.xwork2.ActionSupport#execute()
   */
  @Action("DeleteAccountAction_old")
  @Override
  public String execute()
  {
    // try
    // {
    // String accountId = this.getParameter("id");
    // Account deletedAccount = null;
    //
    // deletedAccount = AccountManager.getInstance().getAccount(accountId);
    // deletedAccount.setStatus(Account.Status.DELETED);
    // AccountManager.getInstance().persist();
    //
    // this.jsonResult =
    // JSONAccountFactory.getInstance().getJSONAccount(deletedAccount);
    // }
    // catch(Exception e)
    // {
    // this.jsonResult = JSONErrorFactory.getInstance().getJSONError(e);
    // e.printStackTrace();
    // }
    return SUCCESS;
  }
}