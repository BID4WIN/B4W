package com.bid4win.service.account;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.service.account.AccountAbstractServiceTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.account.AccountDaoStub;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.service.connection.SessionData;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class AccountServiceTest
       extends AccountAbstractServiceTester<SessionData, Account, EntityGenerator>
{
  /** Référence du manager à tester */
  @Autowired
  @Qualifier("AccountService")
  private AccountService service;
  /** Référence du DAO de test des comptes utilisateur */
  @Autowired
  @Qualifier("AccountDaoStub")
  private AccountDaoStub accountDao;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.account.AccountAbstractServiceTester#getService()
   */
  @Override
  public AccountService getService()
  {
    return this.service;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected AccountDaoStub getDao()
  {
    return this.accountDao;
  }
}
