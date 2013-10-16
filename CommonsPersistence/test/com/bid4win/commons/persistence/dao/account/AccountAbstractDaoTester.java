package com.bid4win.commons.persistence.dao.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.Bid4WinDaoTester;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * Classe de test du DAO de base de gestion des comptes utilisateurs<BR>
 * <BR>
 * @param <ENTITY> Entité sujet du test<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountAbstractDaoTester<ENTITY extends AccountAbstract<ENTITY>,
                                               GENERATOR extends EntityGenerator<ENTITY>>
       extends Bid4WinDaoTester<ENTITY, String, ENTITY, GENERATOR>
{
  /**
   * Précise le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IAccountAbstractDaoStub<ENTITY> getDao();

  /**
   * Test of getOneByLogin(Login), of class AccountAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetOneByLogin_Login() throws Bid4WinException
  {
    ENTITY account = this.createAccount();
    try
    {
      this.getDao().getOneByLogin(account.getCredential().getLogin());
      fail("Shoud fail with unexisting login");
    }
    catch(NotFoundEntityException ex)
    {
      System.out.println(ex.getMessage());
    }
    account = this.add(this.createAccount());
    ENTITY result = this.getDao().getOneByLogin(account.getCredential().getLogin());
    this.checkIdentical(account, result);
  }
  /**
   * Test of findOneByLogin(Login), of class AccountAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindOneByLogin_Login() throws Bid4WinException
  {
    ENTITY account = this.createAccount();
    ENTITY result = this.getDao().findOneByLogin(account.getCredential().getLogin());
    assertNull("Should be null", result);

    account = this.add(this.createAccount());
    result = this.getDao().findOneByLogin(account.getCredential().getLogin());
    this.checkIdentical(account, result);
  }

  /**
   * Test of getOneByEmail(Email), of class AccountAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetOneByEmail_Email() throws Bid4WinException
  {
    ENTITY account = this.createAccount();
    try
    {
      this.getDao().getOneByEmail(account.getEmail());
      fail("Shoud fail with unexisting login");
    }
    catch(NotFoundEntityException ex)
    {
      System.out.println(ex.getMessage());
    }
    account = this.add(this.createAccount());
    ENTITY result = this.getDao().getOneByEmail(account.getEmail());
    this.checkIdentical(account, result);
  }
  /**
   * Test of findOneByEmail(Email), of class AccountAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindOneByEmail_Email() throws Bid4WinException
  {
    ENTITY account = this.createAccount();
    ENTITY result = this.getDao().findOneByEmail(account.getEmail());
    assertNull("Should be null", result);

    account = this.add(this.createAccount());
    result = this.getDao().findOneByEmail(account.getEmail());
    this.checkIdentical(account, result);
  }

  /**
   * Test of findOneByLoginOrEmail(String), of class AccountDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindOneByLoginOrEmail_String() throws Bid4WinException
  {
    ENTITY result = this.getDao().findOneByLoginOrEmail(this.getGenerator().createLogin().getValue());
    assertNull("Should be null", result);
    result = this.getDao().findOneByLoginOrEmail(this.getGenerator().createEmail().getAddress());
    assertNull("Should be null", result);

    ENTITY account = this.getDao().add(this.createAccount(this.getGenerator().createCredential()));

    result = this.getDao().findOneByLoginOrEmail(this.getGenerator().createLogin().getValue());
    this.checkIdentical(account, account);
    result = this.getDao().findOneByLoginOrEmail(this.getGenerator().createEmail().getAddress());
    this.checkIdentical(account, account);

    result = this.getDao().findOneByLoginOrEmail(this.getGenerator().createLogin().getValue() + "1");
    assertNull("Should be null", result);
    result = this.getDao().findOneByLoginOrEmail("1" + this.getGenerator().createEmail().getAddress());
    assertNull("Should be null", result);
    try
    {
      this.getDao().findOneByLoginOrEmail(UtilString.EMPTY);
      fail("Should fail with invalid parameter");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of add(ENTITY), of class AccountAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAdd_ENTITY() throws Bid4WinException
  {
    ENTITY account = this.createAccount();
    try
    {
      this.add(account);
      ENTITY result = this.getDao().findOneByLogin(account.getCredential().getLogin());
      assertNotNull("Should not be null", result);
      assertEquals("Wrong version", 0, result.getVersion());
      this.checkIdentical(account, result);
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      this.add(this.createAccount(account.getCredential(), this.getGenerator().createEmail(1)));
      fail("Should fail with same credential");
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.add(this.createAccount(this.getGenerator().createCredential(1), account.getEmail()));
      fail("Should fail with same email");
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.add(this.createAccount(this.getGenerator().createCredential(1),
                                  this.getGenerator().createEmail(1)));
      ENTITY result = this.getDao().findOneByLogin(account.getCredential().getLogin());
      assertNotNull("Should not be null", result);
      assertEquals("Wrong version", 0, result.getVersion());
      this.checkIdentical(account, result);
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }

  /**
   * Test of update(ENTITY), of class AccountDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdate_ENTITY() throws Bid4WinException
  {
    ENTITY account = this.add(this.createAccount());
    assertEquals("Wrong version", 0, account.getVersion());

    account.getCredential().defineRole(Role.ADMIN);
    try
    {
      this.update(account);
      ENTITY result = this.getDao().findOneByLogin(account.getCredential().getLogin());
      assertNotNull("Should not be null", result);
      assertEquals("Wrong ID", account.getId(), result.getId());
      assertEquals("Wrong version", account.getVersion() + 1, result.getVersion());
      assertTrue("Wrong result", result.same(account));
      this.checkNotIdentical(account, result);
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  private ENTITY createAccount() throws Bid4WinException
  {
    return this.getGenerator().createAccount();
  }
  /**
   *
   * TODO A COMMENTER
   * @param credential TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected final ENTITY createAccount(Credential credential) throws Bid4WinException
  {
    return this.createAccount(credential, this.getGenerator().createEmail());
  }
  /**
   *
   * TODO A COMMENTER
   * @param credential TODO A COMMENTER
   * @param email TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract ENTITY createAccount(Credential credential, Email email) throws Bid4WinException;
}
