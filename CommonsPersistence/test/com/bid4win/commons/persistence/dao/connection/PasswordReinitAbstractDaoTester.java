package com.bid4win.commons.persistence.dao.connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.account.AccountBasedKeyDaoTester;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <REINIT> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PasswordReinitAbstractDaoTester<REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                                      ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                      GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedKeyDaoTester<REINIT, ACCOUNT, GENERATOR>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedKeyDaoTester#getDao()
   */
  @Override
  protected abstract IPasswordReinitAbstractDaoStub<REINIT, ACCOUNT> getDao();

  /**
   * Test of remove(REINIT), of class PasswordReinitAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemove_REINIT() throws Bid4WinException
  {
    ACCOUNT account1 = this.getAccount(0);
    REINIT reinit = this.create(account1);
    this.getDao().add(reinit);
    REINIT result = this.getDao().findById(reinit.getId());
    assertNotNull("Wrong result", result);
    this.getDao().removeById(reinit.getId());
    try
    {
      this.getDao().getById(reinit.getId());
      fail("Should have been removed");
    }
    catch(NotFoundEntityException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      this.getDao().removeById(reinit.getId());
      fail("Should fail");
    }
    catch(Throwable th)
    {
      th.printStackTrace();
    }
  }
}
