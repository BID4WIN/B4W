package com.bid4win.commons.persistence.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("EntityGenerator")
@Scope("singleton")
public class EntityGeneratorStub extends EntityGenerator<AccountAbstractStub>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.EntityGenerator#createAccount()
   */
  @Override
  public AccountAbstractStub createAccount() throws Bid4WinException
  {
    return new AccountAbstractStub(this.createCredential(), this.createEmail());
  }
  /**
   *
   * TODO A COMMENTER
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.EntityGenerator#createAccount(int)
   */
  @Override
  public AccountAbstractStub createAccount(int index) throws Bid4WinException
  {
    return new AccountAbstractStub(this.createCredential(index), this.createEmail(index));
  }
  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.EntityGenerator#createAccount(java.lang.String)
   */
  @Override
  public AccountAbstractStub createAccount(String id) throws Bid4WinException
  {
    return new AccountAbstractStub(id, this.createCredential(), this.createEmail());
  }
}
