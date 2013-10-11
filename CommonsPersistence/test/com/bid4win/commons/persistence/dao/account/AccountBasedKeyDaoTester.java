package com.bid4win.commons.persistence.dao.account;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <KEY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedKeyDaoTester<KEY extends AccountBasedKey<KEY, ACCOUNT>,
                                               ACCOUNT extends AccountAbstract<ACCOUNT>,
                                               GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedEntitySingleDaoTester<KEY, String, ACCOUNT, GENERATOR>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IAccountBasedKeyDaoStub<KEY, ACCOUNT> getDao();

  /**
   * Test of getById(String), of class AccountBasedKeyDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetById_String() throws Bid4WinException
  {
    ACCOUNT account = this.getAccount(0);
    try
    {
      KEY stub = this.create(account);
      this.add(stub);
      KEY result = this.getById("  " + stub.getId().toLowerCase() + "  ");
      assertTrue("Wrong result", stub.identical(result));
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
}
