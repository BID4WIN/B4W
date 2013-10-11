package com.bid4win.communication.action.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.service.account.AccountService;

/**
 * Common Account action class
 * @author Maxime Ollagnier
 */
public class AccountAction extends JSONAction
{
  /** Account manager reference */
  @Autowired
  @Qualifier("AccountService")
  private AccountService accountService = null;

  /**
   * Returns the AccountService
   * @return the AccountService
   */
  protected AccountService getAccountService()
  {
    return this.accountService;
  }
}
