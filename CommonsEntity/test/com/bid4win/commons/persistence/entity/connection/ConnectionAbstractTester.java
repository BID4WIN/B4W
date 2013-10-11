package com.bid4win.commons.persistence.entity.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.core.security.ObjectProtector;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester;

/**
 * Classe de test d'une connexion d'un compte utilisateur<BR>
 * <BR>
 * @param <CONNECTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ConnectionAbstractTester<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                               HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                               ACCOUNT extends AccountAbstract<ACCOUNT>,
                                               GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedEntityTester<CONNECTION, ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#createEntity(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected CONNECTION createEntity(ACCOUNT account) throws UserException
  {
    return this.createConnection(account, false);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param randomId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected CONNECTION createConnection(ACCOUNT account, boolean randomId) throws UserException
  {
    try
    {
      String sessionId = "01234567890123456789012345678932";
      if(randomId)
      {
        sessionId = IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString());
      }
      return this.createConnection(sessionId, account, this.getGenerator().createIpAddress(false), false);
    }
    catch(UserException ex)
    {
      throw ex;
    }
    catch(Bid4WinException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param sessionId TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param ipAddress TODO A COMMENTER
   * @param remanent TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract CONNECTION createConnection(String sessionId, ACCOUNT account,
                                                 IpAddress ipAddress, boolean remanent)
            throws UserException;
  /**
   * Test of ConnectionAbstract(...) method, of class ConnectionAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    ACCOUNT account = this.getGenerator().createAccount("123");
    IpAddress ipAddress = this.getGenerator().createIpAddress(false);
    String sessionId = IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString());
    try
    {
      CONNECTION connection = this.createConnection(sessionId, account, ipAddress, true);
      assertTrue("Bad Account", account == connection.getAccount());
      assertEquals("Bad session ID", sessionId, connection.getFingerPrint());
      assertTrue("Bad IP address", ipAddress == connection.getIpAddress());
      assertTrue("Bad activeness", connection.isActive());
      assertTrue("Bad remanence", connection.isRemanent());
      assertNull("Bad disconnection reason", connection.getDisconnectionReason());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      this.createConnection(null, account, ipAddress, true);
      fail("Instanciation with null session ID should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createConnection(sessionId + "1", account, ipAddress, true);
      fail("Instanciation with invalid session ID should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createConnection(sessionId.substring(1), account, ipAddress, true);
      fail("Instanciation with invalid session ID should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      String sessionId2 = IdGenerator.generateId(UtilString.createRepeatedString('B', 32).toString());
      this.createConnection(sessionId2, account, ipAddress, true);
      fail("Instanciation with badly formated unique key should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createConnection(sessionId, account, null, true);
      fail("Instanciation with null IP addess should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of sameInternal(ConnectionAbstract, boolean), of class ConnectionAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testSameInternal_CLASS_boolean()
   */
  @Override
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    super.testSameInternal_CLASS_boolean();

    ACCOUNT account = this.getGenerator().createAccount("123");
    String sessionId1 = IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString());
    String sessionId2 = IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString());
    CONNECTION connection1 = this.createConnection(
        sessionId1, account, this.getGenerator().createIpAddress(true), true);
    CONNECTION connection2 = this.createConnection(
        sessionId2, account, this.getGenerator().createIpAddress(true), true);
    this.checkSame(connection1, connection2);
    this.checkSame(connection2, connection1);
    this.checkNotIdentical(connection1, connection2);
    this.checkNotIdentical(connection2, connection1);

    connection2 = this.createConnection(
        connection1.getId(), account, this.getGenerator().createIpAddress(false), true);
    this.checkNotSame(connection1, connection2);
    this.checkNotSame(connection2, connection1);
    this.checkNotIdentical(connection1, connection2);
    this.checkNotIdentical(connection2, connection1);

    connection2 = this.createConnection(
        connection1.getId(), account, connection1.getIpAddress(), false);
    this.checkNotSame(connection1, connection2);
    this.checkNotSame(connection2, connection1);
    this.checkNotIdentical(connection1, connection2);
    this.checkNotIdentical(connection2, connection1);

    connection2 = this.createConnection(
        connection1.getId(), account, connection1.getIpAddress(), true);
    this.checkSame(connection1, connection2);
    this.checkSame(connection2, connection1);
    this.checkIdentical(connection1, connection2);
    this.checkIdentical(connection2, connection1);

    connection1.endConnection(null);
    this.checkNotSame(connection1, connection2);
    this.checkNotSame(connection2, connection1);
    this.checkNotIdentical(connection1, connection2);
    this.checkNotIdentical(connection2, connection1);

    connection2.endConnection(null);
    this.checkSame(connection1, connection2);
    this.checkSame(connection2, connection1);
    this.checkIdentical(connection1, connection2);
    this.checkIdentical(connection2, connection1);

    connection1.stopConnection(null);
    this.checkNotSame(connection1, connection2);
    this.checkNotSame(connection2, connection1);
    this.checkNotIdentical(connection1, connection2);
    this.checkNotIdentical(connection2, connection1);

    connection2.stopConnection(null);
    this.checkSame(connection1, connection2);
    this.checkSame(connection2, connection1);
    this.checkIdentical(connection1, connection2);
    this.checkIdentical(connection2, connection1);
  }
  /**
   * Test of endConnection(DisconnectionReason), of class ConnectionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEndConnection_DisconnectionReason() throws Bid4WinException
  {
    String uniqueKey = IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString());
    CONNECTION connection = null;
    String protectionId = ObjectProtector.startProtection();
    try
    {
      connection = this.createConnection(
          uniqueKey, this.getGenerator().createAccount("123"), this.getGenerator().createIpAddress(false), true);
      connection.endConnection(DisconnectionReason.PASSWORD);
      assertFalse("Bad activeness", connection.isActive());
      assertTrue("Bad remanence", connection.isRemanent());
      assertTrue("Bad validity", connection.isValid());
      assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                   connection.getDisconnectionReason());

      HISTORY history = connection.getHistory();
      assertNotNull("Wrong history", history);
      assertTrue("Bad account", connection.getAccount() == history.getAccount());
      assertTrue("Bad sessionId", connection.getId() == history.getSessionId());
      assertTrue("Bad IP address", connection.getIpAddress() == history.getIpAddress());
      assertTrue("Bad start date", connection.getStartDate() == history.getStartDate());
      assertTrue("Bad disconnection reason", connection.getDisconnectionReason() == history.getDisconnectionReason());

      connection.endConnection(DisconnectionReason.IP);
      assertFalse("Bad activeness", connection.isActive());
      assertTrue("Bad remanence", connection.isRemanent());
      assertTrue("Bad validity", connection.isValid());
      assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                   connection.getDisconnectionReason());
      assertTrue("Wrong history", history == connection.getHistory());
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    protectionId = ObjectProtector.startProtection();
    try
    {
      connection = this.createConnection(
          uniqueKey, this.getGenerator().createAccount("123"), this.getGenerator().createIpAddress(false), true);
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    try
    {
      connection.endConnection(null);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Bad activeness", connection.isActive());
      assertTrue("Bad remanence", connection.isRemanent());
      assertTrue("Bad validity", connection.isValid());
      assertNull("Bad disconnection reason", connection.getDisconnectionReason());
    }
  }

  /**
   * Test of invalidate(DisconnectionReason), of class ConnectionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testInvalidate_DisconnectionReason() throws Bid4WinException
  {
    String uniqueKey = IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString());
    String protectionId = ObjectProtector.startProtection();
    CONNECTION connection = null;
    try
    {
      connection = this.createConnection(
          uniqueKey, this.getGenerator().createAccount("123"), this.getGenerator().createIpAddress(false), true);
      connection.invalidate(DisconnectionReason.PASSWORD);
      assertFalse("Bad activeness", connection.isActive());
      assertFalse("Bad remanence", connection.isRemanent());
      assertFalse("Bad validity", connection.isValid());
      assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                   connection.getDisconnectionReason());
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    protectionId = ObjectProtector.startProtection();
    connection = this.createConnection(
        uniqueKey, this.getGenerator().createAccount("123"), this.getGenerator().createIpAddress(false), true);
    ObjectProtector.stopProtection(protectionId);
    try
    {
      connection.invalidate(DisconnectionReason.PASSWORD);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Bad activeness", connection.isActive());
      assertTrue("Bad remanence", connection.isRemanent());
      assertTrue("Bad validity", connection.isValid());
      assertNull("Bad disconnection reason", connection.getDisconnectionReason());
    }
  }
}
