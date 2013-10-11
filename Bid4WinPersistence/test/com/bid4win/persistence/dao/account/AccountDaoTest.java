package com.bid4win.persistence.dao.account;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.account.AccountAbstractDaoTester;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class AccountDaoTest extends AccountAbstractDaoTester<Account, EntityGenerator>
{
  /** Référence du DAO des comptes utilisateur */
  @Autowired
  @Qualifier("AccountDaoStub")
  private AccountDaoStub dao;

  /**
   *
   * TODO A COMMENTER
   * @param credential TODO A COMMENTER
   * @param email TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDaoTester#createAccount(com.bid4win.commons.persistence.entity.account.security.Credential, com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  protected Account createAccount(Credential credential, Email email) throws Bid4WinException
  {
    return new Account(credential, email);
  }

  /**
   * Getter du DAO des comptes utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected AccountDaoStub getDao()
  {
    return this.dao;
  }
}
