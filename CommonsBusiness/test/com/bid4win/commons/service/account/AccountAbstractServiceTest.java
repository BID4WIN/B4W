package com.bid4win.commons.service.account;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.persistence.dao.account.AccountAbstractDaoStub;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstractStub;
import com.bid4win.commons.service.connection.SessionDataAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class AccountAbstractServiceTest
       extends AccountAbstractServiceTester<SessionDataAbstractStub, AccountAbstractStub,
                                            ConnectionAbstractStub, EntityGeneratorStub>
{
  /** Référence du DAO de gestion des comptes utilisateur */
  @Autowired
  @Qualifier("AccountAbstractDaoStub")
  private AccountAbstractDaoStub accountDao;
  /** Référence du service à tester */
  @Autowired
  @Qualifier("AccountService")
  private AccountAbstractServiceStub service;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.account.AccountAbstractServiceTester#getService()
   */
  @Override
  public AccountAbstractServiceStub getService()
  {
    // TODO Auto-generated method stub
    return this.service;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected AccountAbstractDaoStub getDao()
  {
    return this.accountDao;
  }

}
