package com.bid4win.commons.persistence.dao.account;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class AccountAbstractDaoTest extends AccountAbstractDaoTester<AccountAbstractStub, EntityGeneratorStub>
{
  /** Référence du DAO des comptes utilisateur */
  @Autowired
  @Qualifier("AccountAbstractDaoStub")
  private AccountAbstractDaoStub dao;

  /**
   *
   * TODO A COMMENTER
   * @param credential {@inheritDoc}
   * @param email {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDaoTester#createAccount(com.bid4win.commons.persistence.entity.account.security.Credential, com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  protected AccountAbstractStub createAccount(Credential credential, Email email) throws Bid4WinException
  {
    return new AccountAbstractStub(credential, email);
  }

  /**
   * Getter du DAO des comptes utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected AccountAbstractDaoStub getDao()
  {
    return this.dao;
  }
}
