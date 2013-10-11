package com.bid4win.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.j2ee.Bid4WinSelfReferer;

/***
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountServiceReferer")
@Scope("singleton")
public class AccountServiceReferer extends Bid4WinSelfReferer<AccountService>
{
  /**
   *
   * TODO A COMMENTER
   * @param bean {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferer#setBean(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  @Autowired
  @Qualifier("AccountService")
  public void setBean(AccountService bean)
  {
    super.setBean(bean);
  }
}
