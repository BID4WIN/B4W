package com.bid4win.commons.service.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntitySetComparator;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.service.Bid4WinServiceTester;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <SESSION> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <CONNECTION> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountAbstractServiceTester<SESSION extends SessionDataAbstract<ACCOUNT, CONNECTION>,
                                                   ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                   CONNECTION extends ConnectionAbstract<CONNECTION, ?, ACCOUNT>,
                                                   GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinServiceTester<ACCOUNT, String, SESSION, ACCOUNT, CONNECTION, GENERATOR>
{
  /**
   * Test of getAccountList(), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetAccountList_0args() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("getAccountList");

    Bid4WinSet<ACCOUNT> accountSet = new Bid4WinSet<ACCOUNT>(this.getDao().findAll());
    try
    {
      Bid4WinSet<ACCOUNT> result = new Bid4WinSet<ACCOUNT>(this.getService().getAccountList());
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     accountSet, result, new Bid4WinList<Bid4WinRelationNode>()));
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("should not fail :" + ex.getMessage());
    }
  }
  /**
   * Test of createAccount(Credential, Email), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testCreateAccount_Credential_Email() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("createAccount", Credential.class, Email.class);

    Credential credential = this.getGenerator().createCredential(10);
    credential.defineRole(this.getService().getAdminRole());
    Email email = this.getGenerator().createEmail(10);
    ACCOUNT account = this.getService().createAccount(credential, email);
    assertEquals("Wrong credential", credential, account.getCredential());
    assertEquals("Wrong email", email, account.getEmail());
    // Test avec un login déjà référencé
    try
    {
      this.getService().createAccount(account.getCredential(),
                                      this.getGenerator().createEmail(11));
    }
    catch(AuthorizationException ex)
    {
      ex.printStackTrace();
      fail("Wrong error :" + ex.getMessage());
    }
    catch(AuthenticationException ex)
    {
      ex.printStackTrace();
      fail("Wrong error :" + ex.getMessage());
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    // Test avec un email déjà référencé
    try
    {
      credential = this.getGenerator().createCredential(11);
      credential.defineRole(this.getService().getAdminRole());
      this.getService().createAccount(credential, account.getEmail());
    }
    catch(AuthorizationException ex)
    {
      ex.printStackTrace();
      fail("Wrong error :" + ex.getMessage());
    }
    catch(AuthenticationException ex)
    {
      ex.printStackTrace();
      fail("Wrong error :" + ex.getMessage());
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of updateRole(String, Role), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdateRole_String_Role() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    //this.checkAdminRestriction("updateRole", String.class, Role.class);
    // TODO probleme pour tester la vérification des droits administrateur
    this.connectAccount(0);
    this.updateRole(0, this.getService().getAdminRole());
    this.disconnectSessionAccount();
    this.updateRole(1, this.getService().getAdminRole());

    this.getService().updateRole(this.getAccountId(0), this.getService().getAdminRole());

    this.getService().updateRole(this.getAccountId(1), this.getService().getAdminRole());
    assertEquals("Wrong new role", this.getService().getRole(),
                 this.getAccount(1).getCredential().getRole());

  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#getSetupAccountNb()
   */
  @Override
  public int getSetupAccountNb()
  {
    return 2;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#getService()
   */
  @Override
  public abstract AccountAbstractService_<SESSION, ACCOUNT, ?> getService();
  /**
  *
  * TODO A COMMENTER
  * @throws Exception {@inheritDoc}
  * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#tearDown()
  */
 @Override
 public void tearDown() throws Exception
 {
   // Supprime les comptes utilisateurs utilisés pour les tests
   this.getAccountInitializer_().tearDown();
   super.tearDown();
 }
}
