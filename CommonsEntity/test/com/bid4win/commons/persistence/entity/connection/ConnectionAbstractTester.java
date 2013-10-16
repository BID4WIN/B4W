package com.bid4win.commons.persistence.entity.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.IdGenerator;
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
  /** TODO A COMMENTER */
  private Bid4WinDate date = new Bid4WinDate();

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
      ConnectionData data = new ConnectionData(sessionId, this.getGenerator().createIpAddress(false), false);
      data = new ConnectionDataStub(data, this.date);
      return this.createConnection(data, account);
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
  protected abstract CONNECTION createConnection(ConnectionData data, ACCOUNT account)
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
    ConnectionData data = new ConnectionData(IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString()),
                                             this.getGenerator().createIpAddress(false), true);
    try
    {
      CONNECTION connection = this.createConnection(data, account);
      assertTrue("Bad Account", account == connection.getAccount());
      assertTrue("Bad data", data == connection.getData());
      assertTrue("Bad activeness", connection.isActive());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      this.createConnection(null, account);
      fail("Instanciation with null session data should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createConnection(data, null);
      fail("Instanciation with null account should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
 /*   try
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
    }*/
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

    ConnectionData data1 = new ConnectionData(IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString()),
                                              this.getGenerator().createIpAddress(false), true);
    data1 = new ConnectionDataStub(data1, new Bid4WinDate());
    ConnectionData data2 = new ConnectionData(IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString()),
                                              data1.getIpAddress(), data1.isRemanent());
    data2 = new ConnectionDataStub(data2, data1.getStartDate());
    CONNECTION connection1 = this.createConnection(data1, account);
    CONNECTION connection2 = this.createConnection(data2, account);
    this.checkNotSame(connection1, connection2);
    this.checkNotSame(connection2, connection1);
    this.checkNotIdentical(connection1, connection2);
    this.checkNotIdentical(connection2, connection1);


    connection2 = this.createConnection(data1, account);
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
    ConnectionData data = new ConnectionData(IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString()),
        this.getGenerator().createIpAddress(false), true);
    data = new ConnectionDataStub(data, new Bid4WinDate());

    CONNECTION connection = this.createConnection(data, this.getGenerator().createAccount("123"));
    connection.endConnection(DisconnectionReason.PASSWORD);
    assertFalse("Bad activeness", connection.isActive());
    assertTrue("Bad remanence", connection.getData().isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                 connection.getData().getDisconnectionReason());

    HISTORY history = connection.getHistory();
    assertNotNull("Wrong history", history);
    assertTrue("Bad account", connection.getAccount() == history.getAccount());
    assertTrue("Bad sessionId", connection.getData().getSessionId() == history.getData().getSessionId());
    assertTrue("Bad IP address", connection.getData().getIpAddress() == history.getData().getIpAddress());
    assertTrue("Bad start date", connection.getData().getStartDate() == history.getData().getStartDate());
    assertTrue("Bad disconnection reason", connection.getData().getDisconnectionReason() == history.getData().getDisconnectionReason());

    connection.endConnection(DisconnectionReason.REMANENCE);
    assertFalse("Bad activeness", connection.isActive());
    assertTrue("Bad remanence", connection.getData().isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                 connection.getData().getDisconnectionReason());
    assertTrue("Wrong history", history == connection.getHistory());
  }
  /**
   * Test of stopConnection(DisconnectionReason), of class ConnectionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testStopConnection_DisconnectionReason() throws Bid4WinException
  {
    ConnectionData data = new ConnectionData(IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString()),
        this.getGenerator().createIpAddress(false), true);
    data = new ConnectionDataStub(data, new Bid4WinDate());

    CONNECTION connection = this.createConnection(data, this.getGenerator().createAccount("123"));
    connection.stopConnection(DisconnectionReason.PASSWORD);
    assertFalse("Bad activeness", connection.isActive());
    assertFalse("Bad remanence", connection.getData().isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                 connection.getData().getDisconnectionReason());

    HISTORY history = connection.getHistory();
    assertNotNull("Wrong history", history);
    assertTrue("Bad account", connection.getAccount() == history.getAccount());
    assertTrue("Bad sessionId", connection.getData().getSessionId() == history.getData().getSessionId());
    assertTrue("Bad IP address", connection.getData().getIpAddress() == history.getData().getIpAddress());
    assertTrue("Bad start date", connection.getData().getStartDate() == history.getData().getStartDate());
    assertTrue("Bad disconnection reason", connection.getData().getDisconnectionReason() == history.getData().getDisconnectionReason());

    connection = this.createConnection(data, this.getGenerator().createAccount("123"));
    connection.endConnection(DisconnectionReason.REMANENCE);
    assertFalse("Bad activeness", connection.isActive());
    assertTrue("Bad remanence", connection.getData().isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.REMANENCE,
                 connection.getData().getDisconnectionReason());

    history = connection.getHistory();
    assertNotNull("Wrong history", history);
    assertTrue("Bad account", connection.getAccount() == history.getAccount());
    assertTrue("Bad sessionId", connection.getData().getSessionId() == history.getData().getSessionId());
    assertTrue("Bad IP address", connection.getData().getIpAddress() == history.getData().getIpAddress());
    assertTrue("Bad start date", connection.getData().getStartDate() == history.getData().getStartDate());
    assertTrue("Bad disconnection reason", connection.getData().getDisconnectionReason() == history.getData().getDisconnectionReason());

    connection.stopConnection(DisconnectionReason.PASSWORD);
    assertFalse("Bad activeness", connection.isActive());
    assertFalse("Bad remanence", connection.getData().isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                 connection.getData().getDisconnectionReason());
    assertTrue("Wrong history", history == connection.getHistory());
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    super.testCheckProtection();
    this.startProtection();
    ConnectionData data = new ConnectionData(IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString()),
        this.getGenerator().createIpAddress(false), true);
    data = new ConnectionDataStub(data, new Bid4WinDate());
    CONNECTION connection = this.createConnection(data, this.getGenerator().createAccount("123"));
    this.stopProtection();
    try
    {
      connection.endConnection(DisconnectionReason.AUTO);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      connection.stopConnection(DisconnectionReason.AUTO);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertTrue("Bad activeness", connection.isActive());
    assertTrue("Bad remanence", connection.getData().isRemanent());
    assertNull("Bad disconnection reason", connection.getData().getDisconnectionReason());
    assertNull("Bad history", connection.getHistory());
  }
}
