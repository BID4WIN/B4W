package com.bid4win.commons.manager.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountManager")
@Scope("singleton")
public class AccountAbstractManagerStub
       extends AccountAbstractManager_<AccountAbstractStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param credential {@inheritDoc}
   * @param email {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.manager.account.AccountAbstractManager_#createAccountEntity(com.bid4win.commons.persistence.entity.account.security.Credential, com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  protected AccountAbstractStub createAccountEntity(Credential credential, Email email)
            throws ModelArgumentException
  {
    return new AccountAbstractStub(credential, email);
  }
}
