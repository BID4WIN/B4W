package com.bid4win.commons.persistence.dao.connection;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.persistence.dao.account.AccountInitializerStub;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractStub;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class ConnectionHistoryAbstractDaoTest
       extends ConnectionHistoryAbstractDaoTester<ConnectionHistoryAbstractStub, AccountAbstractStub, EntityGeneratorStub>
{
  /** R�f�rence du DAO � tester */
  @Autowired
  @Qualifier("ConnectionHistoryDao")
  private ConnectionHistoryAbstractDaoStub dao;
  /** R�f�rence TODO */
  @Autowired
  @Qualifier("AccountInitializer")
  private AccountInitializerStub accountInitializer;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected ConnectionHistoryAbstractDaoStub getDao()
  {
    return this.dao;
  }
  /**
   * Pr�cise TODO
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#getAccountInitializer()
   */
  @Override
  protected AccountInitializerStub getAccountInitializer()
  {
    return this.accountInitializer;
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#create(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected ConnectionHistoryAbstractStub create(AccountAbstractStub account)
            throws Bid4WinException
  {
    return new ConnectionHistoryAbstractStub(account, IdGenerator.generateId(32), true,
                                             this.getGenerator().createIpAddress(true),
                                             new Bid4WinDate(), DisconnectionReason.AUTO);
  }
}
