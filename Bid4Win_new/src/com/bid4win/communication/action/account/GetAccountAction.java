package com.bid4win.communication.action.account;

import org.apache.struts2.convention.annotation.Action;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.communication.json.factory.JSONAccountFactory;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.UtilAccount;

/**
 * Action class. Handles requests for property fetching.
 *
 * @author Maxime Ollagnier
 */
public class GetAccountAction extends AccountAction
{
  /** Serial */
  private static final long serialVersionUID = -3660537861844374390L;

  /** Tableau des filtres à appliquer sur les comptes utilisateur */
  protected static UtilAccount.Filter[] FILTERS = {UtilAccount.Filter.LOGIN, UtilAccount.Filter.EMAIL};

  /**
   * Executes the account fetching in database.
   *
   * @return JSON_RESULT
   * @throws Throwable if TODO
   */
  @Action("GetAccountAction")
  public String toto() throws Throwable
  {
    String searchString = this.findParameter("searchString");
    Bid4WinList<Account> accountList = null;
    if(searchString != null)
    {
      accountList = this.getAccountService().getFilteredAccountList(searchString, new Bid4WinList<UtilAccount.Filter>(GetAccountAction.FILTERS));
    }
    else
    {
      accountList = this.getAccountService().getAccountList();
    }
    this.putJSONObject(JSONAccountFactory.getInstance().getJSONAccountMap(accountList));
    this.setSuccess(true);
    return JSON_RESULT;
  }
}