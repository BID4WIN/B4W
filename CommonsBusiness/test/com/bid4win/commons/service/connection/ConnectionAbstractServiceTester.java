package com.bid4win.commons.service.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.persistence.dao.connection.IConnectionAbstractDaoStub;
import com.bid4win.commons.persistence.dao.connection.IConnectionHistoryAbstractDaoStub;
import com.bid4win.commons.persistence.dao.connection.IPasswordReinitAbstractDaoStub;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract;
import com.bid4win.commons.service.Bid4WinServiceTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CONNECTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <REINIT> TODO A COMMENTER<BR>
 * @param <SESSION> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ConnectionAbstractServiceTester<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                                      HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                                      REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                                      SESSION extends SessionDataAbstract<ACCOUNT>,
                                                      ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                      GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinServiceTester<CONNECTION, String, SESSION, ACCOUNT, GENERATOR>
{
  /**
   * Test of connect(String, Password, IpAddress, String, boolean), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testConnect_String_Password_IpAddress_String_boolean() throws Bid4WinException
  {
    ACCOUNT account1 = this.getAccount(0);
    // Test avec un utilisateur aux habilitations insuffisantes
    SESSION session1 = this.startSession();
    try
    {
      this.getService().connect(account1.getCredential().getLogin().getValue(),
                                account1.getCredential().getPassword(), true);
      fail("Should fail account being " + Role.NONE);
    }
    catch(AuthorizationException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account1.getId(), session1, true, false);
      this.stopSession();
    }
    this.updateRole(0, Role.WAIT);
    this.updateRole(1, Role.WAIT);
    account1 = this.getAccount(0);

    // Test de connection avec un mauvais identifiant
    session1 = this.startSession();
    try
    {
      this.getService().connect(account1.getCredential().getLogin().getValue() + "1",
                                account1.getCredential().getPassword(), true);
      fail("Should fail with unknown login or email");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account1.getId(), session1, true, false);
      this.stopSession();
    }

    // Test de connection avec un mauvais mot de passe
    session1 = this.startSession();
    try
    {
      this.getService().connect(account1.getCredential().getLogin().getValue(),
                                this.getGenerator().createPassword(1), true);
      fail("Should fail with wrong password");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account1.getId(), session1, true, false);
      this.stopSession();
    }

    // Test de connection sans rémanence réussie
    session1 = this.startSession();
    try
    {
      CONNECTION result = this.getService().connect(account1.getEmail().getAddress(),
                                                    account1.getCredential().getPassword(),
                                                    false);
      this.checkIdenticalRelationNone(account1, result.getAccount());
      this.checkConnect(account1.getId(), session1, false, true);
      this.stopSession();
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    // Test de connection avec rémanence réussie avec un second compte utilisateur
    ACCOUNT account2 = this.getAccount(1);
    SESSION session2 = this.startSession();
    try
    {
      CONNECTION result = this.getService().connect(account2.getCredential().getLogin().getValue(),
                                                    account2.getCredential().getPassword(),
                                                    true);
      this.checkIdenticalRelationNone(account2, result.getAccount());
      this.checkConnect(account1.getId(), session1, false, true);
      this.checkConnect(account2.getId(), session2, true, true);
      this.stopSession();
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    // Test de connection avec rémanence réussie avec un second identifiant de session
    SESSION session3 = this.startSession();
    try
    {
      CONNECTION result = this.getService().connect(account2.getEmail().getAddress(),
                                                    account2.getCredential().getPassword(),
                                                    true);
      this.checkIdenticalRelationNone(account2, result.getAccount());
      this.checkConnect(account1.getId(), session1, false, true);
      this.checkConnect(account2.getId(), session2, true, true);
      this.checkConnect(account2.getId(), session3, true, true);
      this.stopSession();
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    // Test de connection avec un identifiant de session déjà utilisé et un compte
    // utilisateur différent
    this.startSession(session3.getSessionId());
    try
    {
      this.getService().connect(account1.getEmail().getAddress(),
                                account1.getCredential().getPassword(),
                                true);
      fail("Should fail with already existing session ID");
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account1.getId(), session1, false, true);
      this.checkConnect(account2.getId(), session2, true, true);
      this.checkConnect(account2.getId(), session3, true, true);
      this.stopSession();
    }

    // Test de connection avec rémanence réussie avec un même identifiant de session
    this.startSession(session3.getSessionId());
    try
    {
      CONNECTION result = this.getService().connect(account2.getEmail().getAddress(),
                                                    account2.getCredential().getPassword(),
                                                    true);
      this.checkIdenticalRelationNone(account2, result.getAccount());
      this.checkConnect(account1.getId(), session1, false, true);
      this.checkConnect(account2.getId(), session2, true, true);
      this.checkConnect(account2.getId(), session3, true, true);
      this.stopSession();
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    // Test de connection réussie avec une demande de ré-initialisation de mot
    // de passe sur les deux comptes utilisateur
    REINIT reinit1 = this.getReinitDao().add(this.createReinit(0));
    REINIT reinit2 = this.getReinitDao().add(this.createReinit(1));
    SESSION session4 = this.startSession();
    try
    {
      CONNECTION result = this.getService().connect(account2.getEmail().getAddress(),
                                                    account2.getCredential().getPassword(),
                                                    false);
      this.checkIdenticalRelationNone(account2, result.getAccount());
      this.checkConnect(account1.getId(), session1, false, true);
      this.checkConnect(account2.getId(), session2, true, true);
      this.checkConnect(account2.getId(), session3, true, true);
      this.checkConnect(account2.getId(), session4, false, true);
      assertTrue("Wrong reinit", reinit1.identical(this.getReinitDao().findById(reinit1.getId())));
      assertNull("Wrong reinit", this.getReinitDao().findById(reinit2.getId()));
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of reconnect(String, String), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testReconnect_String_String() throws Bid4WinException
  {
    this.updateRole(0, Role.USER);
    ACCOUNT account = this.getAccount(0);

    SESSION session1 = this.startSession();
    this.getService().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(),
                              false);
    this.checkConnect(account.getId(), session1, false, true);
    this.disconnectSessionAccount();

    // Test de connection sans rémanence activée avec une connexion active
    try
    {
      this.getService().reconnect(session1.getSessionId(), account.getId());
      this.checkConnect(account.getId(), session1, false, true);
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    // Test de connection sans rémanence activée pour la session
    this.stopSession();
    SESSION session2 = this.startSession();
    try
    {
      this.getService().reconnect(session1.getSessionId(), account.getId());
      fail("Should fail with no remanence activated");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account.getId(), session1, false, true);
      this.checkConnect(account.getId(), session2, true, false);
    }

    // Test de connection sans rémanence ni connexion active pour la session
    this.stopSession();
    this.startSession(session1.getSessionId());
    this.getService().endConnection();
    try
    {
      this.getService().reconnect(session1.getSessionId(), account.getId());
      fail("Should fail with no remanence activated");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account.getId(), session1, false, false);
      this.checkConnect(account.getId(), session2, true, false);
    }

    /*
    String sessionId2 = this.createSessionId();
    String sessionId2_2 = this.createSessionId();
    this.getManager().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(),
                              ipAddress, sessionId2, true);
    this.checkConnect(account.getId(), ipAddress, sessionId2, true, true);

    this.updateRole(0, Role.NONE);
    // Test avec un utilisateur aux habilitations insuffisantes
    try
    {
      this.getManager().reconnect(account.getId(), ipAddress, sessionId2_2, sessionId2);
      fail("Should fail account being " + Role.NONE);
    }
    catch(AuthorizationException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account.getId(), ipAddress, sessionId1, false, true);
      this.checkConnect(account.getId(), ipAddress, sessionId1_2, true, false);
    }
    this.updateRole(0, Role.WAIT);
    account = this.getAccount(0);

    // Test de reconnection avec rémanence activée
    try
    {
      ACCOUNT result = this.getManager().reconnect(
          account.getId(), ipAddress, sessionId2_2, sessionId2);
      assertTrue("Wrong account", account.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      this.checkConnect(account.getId(), ipAddress, sessionId1, false, true);
      this.checkConnect(account.getId(), ipAddress, sessionId1_2, true, false);
      this.checkReconnect(account.getId(), ipAddress, sessionId2, sessionId2_2, true);
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    String sessionId3 = this.createSessionId();
    String sessionId3_2 = this.createSessionId();
    this.getManager().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(),
                              ipAddress, sessionId3, true);
    this.checkConnect(account.getId(), ipAddress, sessionId3, true, true);
    // Test de reconnection avec une seconde rémanence activée
    try
    {
      ACCOUNT result = this.getManager().reconnect(
          account.getId(), ipAddress, sessionId3_2, sessionId3);
      assertTrue("Wrong account", account.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      this.checkConnect(account.getId(), ipAddress, sessionId1, false, true);
      this.checkConnect(account.getId(), ipAddress, sessionId1_2, true, false);
      this.checkReconnect(account.getId(), ipAddress, sessionId2, sessionId2_2, true);
      this.checkReconnect(account.getId(), ipAddress, sessionId3, sessionId3_2, true);
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    String sessionId4 = this.createSessionId();
    // Test de reconnection avec une ancienne rémanence
    try
    {
      this.getManager().reconnect(account.getId(), ipAddress, sessionId4, sessionId3);
      fail("Should fail with wrong session ID");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account.getId(), ipAddress, sessionId1, false, true);
      this.checkConnect(account.getId(), ipAddress, sessionId1_2, true, false);
      this.checkConnect(account.getId(), ipAddress, sessionId2, false, true);
      this.checkConnect(account.getId(), ipAddress, sessionId3, false, true);
      this.checkReconnect(account.getId(), ipAddress, sessionId2_2, sessionId4, false);
      this.checkReconnect(account.getId(), ipAddress, sessionId3_2, sessionId4, false);
    }

    String sessionId5 = this.createSessionId();
    this.getManager().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(),
                              ipAddress, sessionId5, true);
    this.checkConnect(account.getId(), ipAddress, sessionId5, true, true);
    // Test de reconnection avec une mauvaise IP
    try
    {
      this.getManager().reconnect(account.getId(), this.getGenerator().createIpAddress(true, true),
                                  sessionId4, sessionId5);
      fail("Should fail with wrong IP address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account.getId(), ipAddress, sessionId1, false, true);
      this.checkConnect(account.getId(), ipAddress, sessionId1_2, true, false);
      this.checkConnect(account.getId(), ipAddress, sessionId2, false, true);
      this.checkConnect(account.getId(), ipAddress, sessionId3, false, true);
      this.checkReconnect(account.getId(), ipAddress, sessionId2_2, sessionId4, false);
      this.checkReconnect(account.getId(), ipAddress, sessionId3_2, sessionId4, false);
      this.checkReconnect(account.getId(), ipAddress, sessionId5, sessionId4, false);
    }

    String sessionId6 = this.createSessionId();
    this.getManager().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(),
                              ipAddress, sessionId6, true);
    this.checkConnect(account.getId(), ipAddress, sessionId6, true, true);
    // Test de reconnection avec une rémanence invalide
    try
    {
      this.getManager().reconnect(account.getId(), ipAddress, sessionId4, sessionId5);
      fail("Should fail with invalid remanence");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account.getId(), ipAddress, sessionId6, true, true);
    }*/
  }
  /**
   * Test of disconnect(), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDisconnect_0args() throws Bid4WinException
  {
    this.updateRole(0, Role.WAIT);
    ACCOUNT account = this.getAccount(0);
    SESSION session0 = this.startSession();
    this.getService().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(), false);
    this.stopSession();
    SESSION session1 = this.startSession();
    this.getService().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(), true);
    this.stopSession();
    SESSION session2 = this.startSession();
    this.getService().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(), true);
    this.checkConnect(account.getId(), session0, false, true);
    this.checkConnect(account.getId(), session1, true, true);
    this.checkConnect(account.getId(), session2, true, true);

    this.getService().disconnect();
    this.checkConnect(account.getId(), session0, false, true);
    this.checkConnect(account.getId(), session1, true, true);
    this.checkDisconnect(session2);

    this.stopSession();
    this.startSession(session0.getSessionId());
    this.getService().disconnect();
    this.checkDisconnect(session0);
    this.checkConnect(account.getId(), session1, true, true);
    this.checkDisconnect(session2);

    this.stopSession();
    this.startSession(session1.getSessionId());
    this.getService().disconnect();
    this.checkDisconnect(session0);
    this.checkDisconnect(session1);
    this.checkDisconnect(session2);
  }

  /**
   * Test of updatePassword(Password, Password, String, String), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdatePassword_Password_Password_String_String() throws Bid4WinException
  {
    fail("TO BO IMPLEMENTED");
    // Test sans utilisateur connecté
 /*   this.updateRole(0, Role.WAIT);
    this.updateRole(1, Role.ADMIN);
    ACCOUNT account = this.getAccount(0);
    String accountId = account.getId();
    Password oldPassword = account.getCredential().getPassword();
    Password newPassword = this.getGenerator().createPassword(1);
    String sessionId1 = this.createSessionId();
    IpAddress ip1 = this.getGenerator().createIpAddress(true);
    this.getManager().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(),
                              ip1, sessionId1, true);
    String sessionId2 = this.createSessionId();
    this.getManager().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(),
                              ip1, sessionId2, true);
    IpAddress ip2 = this.getGenerator().createIpAddress(true);
    String sessionId3 = this.createSessionId();
    this.getManager().connect(account.getEmail().getAddress(),
                              account.getCredential().getPassword(),
                              ip2, sessionId3, true);
    try
    {
      this.getManager().updatePassword(oldPassword, newPassword, accountId, sessionId1);
      fail("Should fail without any connected account");
    }
    catch(AuthenticationException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(accountId, ip1, sessionId1, true, true);
      this.checkConnect(accountId, ip1, sessionId2, true, true);
      this.checkConnect(accountId, ip2, sessionId3, true, true);
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Wrong error :" + ex.getMessage());
    }
    this.connectAccount(1);
    // Test avec un utilisateur connecté différent de celui à modifier
    try
    {
      this.getManager().updatePassword(oldPassword, newPassword, accountId, sessionId1);
      fail("Should fail with connected account not being the one to update");
    }
    catch(AuthorizationException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(accountId, ip1, sessionId1, true, true);
      this.checkConnect(accountId, ip1, sessionId2, true, true);
      this.checkConnect(accountId, ip2, sessionId3, true, true);
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Wrong error :" + ex.getMessage());
    }
    this.disconnectAccount();
    this.connectAccount(0);
    // Test avec des rémanences activées
    try
    {
      ACCOUNT result = this.getManager().updatePassword(
          oldPassword, newPassword, accountId, sessionId2);
      assertEquals("Wrong version", account.getVersion() + 1, result.getVersion());
      assertEquals("Wrong new password", newPassword, result.getCredential().getPassword());
      assertFalse("Should not be valid",
                  this.getRemanenceDao().getOneByUniqueKey(sessionId1).isValid());
      this.checkConnect(accountId, ip1, sessionId2, true, true);
      assertFalse("Should not be valid",
                  this.getRemanenceDao().getOneByUniqueKey(sessionId3).isValid());
    }
    catch(AuthorizationException ex)
    {
      ex.printStackTrace();
      fail("Should not fail");
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Wrong error :" + ex.getMessage());
    }*/
  }
  /**
   * Test of reinitPassword(String), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testReinitPassword_String() throws Bid4WinException
  {
    ACCOUNT account = this.getAccount(0);
    try
    {
      this.getService().reinitPassword(account.getCredential().getLogin().getValue() + "1");
      fail("Should fail with wrong login");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Wrong error :" + ex.getMessage());
    }
    REINIT reinit1 = this.getService().reinitPassword(account.getCredential().getLogin().getValue());
    assertTrue("Wrong account", account.identical(reinit1.getAccount(), new Bid4WinList<Bid4WinRelationNode>()));
    REINIT reinit2 = this.getService().reinitPassword(account.getEmail().getAddress());
    assertTrue("Wrong password reinit", reinit1.identical(reinit2));
  }
  /**
   * Test of getPasswordReinit(String, String), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetPasswordReinit_String_String() throws Bid4WinException
  {
    ACCOUNT account = this.getAccount(0);
    REINIT reinit = this.getService().reinitPassword(account.getCredential().getLogin().getValue());
    try
    {
      String key = IdGenerator.generateId(AccountBasedKey.KEY_PATTERN);
      this.getService().getPasswordReinit(key, account.getId());
      fail("Should fail with wrong key");
    }
    catch(NotFoundEntityException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.getService().getPasswordReinit(reinit.getId(), this.getAccountId(1));
      fail("Should fail with wrong account ID");
    }
    catch(NotFoundEntityException ex)
    {
      System.out.println(ex.getMessage());
    }
    REINIT result = this.getService().getPasswordReinit(reinit.getId(), account.getId());
    assertTrue("Wrong password reinit", reinit.identical(result));
  }
  /**
   * Test of reinitPassword(String, String, Password), of service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testReinitPassword_String_String_Password() throws Bid4WinException
  {
    fail("TO BO IMPLEMENTED");
    /*
    this.updateRole(0, Role.WAIT);
    this.updateRole(1, Role.WAIT);
    ACCOUNT account1 = this.getAccount(0);
    ACCOUNT account2 = this.getAccount(1);
    String sessionId1 = this.createSessionId();
    IpAddress ip1 = this.getGenerator().createIpAddress(true);
    this.getManager().connect(account1.getEmail().getAddress(),
                              account1.getCredential().getPassword(),
                              ip1, sessionId1, true);
    String sessionId2 = this.createSessionId();
    IpAddress ip2 = this.getGenerator().createIpAddress(true);
    this.getManager().connect(account2.getEmail().getAddress(),
                              account2.getCredential().getPassword(),
                              ip2, sessionId2, true);
    REINIT reinit = this.getManager().reinitPassword(account1.getEmail().getAddress());
    try
    {
      this.getManager().reinitPassword(reinit.getId(), account2.getId(),
                                       this.getGenerator().createPassword(2));
      fail("Should fail with wrong account ID");
    }
    catch(NotFoundEntityException ex)
    {
      System.out.println(ex.getMessage());
      this.checkConnect(account1.getId(), ip1, sessionId1, true, true);
      this.checkConnect(account2.getId(), ip2, sessionId2, true, true);
    }
    Password password = this.getGenerator().createPassword(2);
    ACCOUNT result = this.getManager().reinitPassword(
        reinit.getId(), account1.getId(), password);
    assertEquals("Wrong version", account1.getVersion() + 1, result.getVersion());
    assertEquals("Wrong new password", password, result.getCredential().getPassword());
    assertFalse("Should not be valid",
        this.getRemanenceDao().getOneByUniqueKey(sessionId1).isValid());
    this.checkConnect(account2.getId(), ip2, sessionId2, true, true);
    assertNull("Password reinit should be null", this.getReinitDao().findById(reinit.getId()));
  */}

  /**
   * Cette méthode permet de tester la validité de la connexion
   * @param accountId Identifiant du compte utilisateur à connecter
   * @param session Session utilisée pour la connexion
   * @param remanent Utilisation de la rémanence de connexion
   * @param success Indique si la connexion est sensée avoir réussi
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void checkConnect(String accountId, SESSION session,
                              boolean remanent, boolean success)
            throws Bid4WinException
  {
    CONNECTION connection = this.getDao().findById(session.getSessionId());
    // La connexion n'est pas sensée avoir réussi
    if(!success)
    {
      // La connexion ne doit pas exister
      assertNull("Should be null", connection);
    }
    // La connexion est sensée avoir réussi
    else
    {
      // La connexion doit être valide
      assertNotNull("Should not be null", connection);
      assertEquals("Wrong account ID", accountId, connection.getAccount().getId());
      assertEquals("Wrong IP address", session.getIpAddress(), connection.getIpAddress());
      assertTrue("Wrong validity", connection.isActive());
      assertEquals("Wrong remanence", remanent, connection.isRemanent());
    }
  }
  /**
   * Cette méthode permet de tester la validité de la re-connexion
   * @param accountId Identifiant du compte utilisateur à reconnecter
   * @param newSession Session utilisée pour la connexion
   * @param oldSession Session utilisée pour la rémanence
   * @param success Indique si la re-connexion est sensée avoir réussi
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void checkReconnect(String accountId, SESSION newSession,
                                SESSION oldSession, boolean success)
            throws Bid4WinException
  {
    // La re-connexion est sensée avoir réussi
    if(success)
    {
      // L'ancienne rémanence ne doit plus exister
      this.checkConnect(accountId, oldSession, false, true);
      // La nouvelle rémanence doit être valide
      this.checkConnect(accountId, newSession, true, true);
    }
    // La re-connexion n'est pas sensée avoir réussi
    else
    {
      // La nouvelle rémanence ne doit pas exister
      this.checkConnect(accountId, newSession, true, false);
      if(this.getConnectionDao().findById(oldSession.getSessionId()) == null)
      {
        // Les rémanences du compte utilisateur doivent être invalidées
        for(CONNECTION connection : this.getDao().findListByAccountId(accountId))
        {
          assertTrue("Wrong version", 0 < connection.getVersion());
          assertFalse("Wrong validity", connection.isActive());
          assertFalse("Wrong remanence", connection.isRemanent());
        }
      }
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void checkDisconnect(SESSION session) throws Bid4WinException
  {
    assertNull("Should be null", this.getDao().findById(session.getSessionId()));
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
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected REINIT createReinit(int index) throws Bid4WinException
  {
    return this.createReinit(this.getAccount(index));
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract REINIT createReinit(ACCOUNT account) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected IConnectionAbstractDaoStub<CONNECTION, HISTORY, ACCOUNT> getDao()
  {
    return this.getConnectionDao();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IConnectionAbstractDaoStub<CONNECTION, HISTORY, ACCOUNT> getConnectionDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IConnectionHistoryAbstractDaoStub<HISTORY, ACCOUNT> getHistoryDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IPasswordReinitAbstractDaoStub<REINIT, ACCOUNT> getReinitDao();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#getService()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ConnectionAbstractService_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT, ?> getService()
  {
    return (ConnectionAbstractService_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT, ?>)this.getConnectionService();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#removeAll()
   */
  @Override
  protected Bid4WinList<CONNECTION> removeAll() throws PersistenceException
  {
    this.getHistoryDao().removeAll();
    this.getReinitDao().removeAll();
    return super.removeAll();
  }
}
