package com.bid4win.persistence.dao.account;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ID> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedEntityMultipleDaoTester<ENTITY extends AccountBasedEntityMultiple<ENTITY, ID, Account>, ID>
       extends com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester<ENTITY, ID, Account, EntityGenerator>
{
  /** Référence TODO */
  @Autowired
  @Qualifier("AccountInitializer")
  private AccountInitializer accountInitializer;

  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    // Supprime les comptes utilisateurs utilisés pour les tests
    this.getAccountInitializer().tearDown();
    super.tearDown();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#getAccountInitializer()
   */
  @Override
  protected AccountInitializer getAccountInitializer()
  {
    return this.accountInitializer;
  }
}
