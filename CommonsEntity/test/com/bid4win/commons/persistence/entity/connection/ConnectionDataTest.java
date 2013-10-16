package com.bid4win.commons.persistence.entity.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une connexion d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ConnectionDataTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  /**
   * Test of ConnectionData(...) method, of class ConnectionData.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    String sessionId = "01234567890123456789012345678932";
    IpAddress ipAddress = this.getGenerator().createIpAddress(false);
    try
    {
      ConnectionData data = new ConnectionData(sessionId, ipAddress, true);
      assertEquals("Bad session ID", sessionId, data.getSessionId());
      assertTrue("Bad IP address", ipAddress == data.getIpAddress());
      assertTrue("Bad remanence", data.isRemanent());
      assertNull("Bad disconnection reason", data.getDisconnectionReason());
      assertNull("Bad startDate", data.getStartDate());

      data = new ConnectionData(sessionId, ipAddress, false);
      assertEquals("Bad session ID", sessionId, data.getSessionId());
      assertTrue("Bad IP address", ipAddress == data.getIpAddress());
      assertFalse("Bad remanence", data.isRemanent());
      assertNull("Bad disconnection reason", data.getDisconnectionReason());
      assertNull("Bad startDate", data.getStartDate());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new ConnectionData(null, ipAddress, true);
      fail("Instanciation with null session ID should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new ConnectionData(sessionId + "1", ipAddress, true);
      fail("Instanciation with invalid session ID should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new ConnectionData(sessionId.substring(1), ipAddress, true);
      fail("Instanciation with invalid session ID should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      String sessionId2 = IdGenerator.generateId(UtilString.createRepeatedString('B', 32).toString());
      new ConnectionData(sessionId2, ipAddress, true);
      fail("Instanciation with badly formated unique key should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new ConnectionData(sessionId, null, true);
      fail("Instanciation with null IP addess should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of equalsInternal(ConnectionData), of class ConnectionData.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_CLASS_boolean() throws Bid4WinException
  {
    String sessionId1 = "01234567890123456789012345678932";
    String sessionId2 = "12345678901234567890123456789321";
    IpAddress ipAddress = this.getGenerator().createIpAddress(false);
    ConnectionData data1 = new ConnectionData(sessionId1, ipAddress, true);
    ConnectionData data2 = new ConnectionData(sessionId1, ipAddress, true);
    this.checkEquals(data1, data2);
    data2 = new ConnectionData(sessionId2, ipAddress, true);
    this.checkNotEquals(data1, data2);
    data2 = new ConnectionData(sessionId1, this.getGenerator().createIpAddress(true), true);
    this.checkNotEquals(data1, data2);
    data2 = new ConnectionData(sessionId1, ipAddress, !data1.isRemanent());
    this.checkNotEquals(data1, data2);

    data1 = new ConnectionDataStub(data1, new Bid4WinDate());
    data2 = new ConnectionDataStub(data1, data1.getStartDate());
    this.checkEquals(data1, data2);

    data1 = data1.endConnection(DisconnectionReason.AUTO);
    this.checkNotEquals(data1, data2);
    data2 = data2.endConnection(DisconnectionReason.AUTO);
    this.checkEquals(data1, data2);

    data2 = new ConnectionDataStub(data1, data1.getStartDate());
    data2 = data2.endConnection(DisconnectionReason.MANUAL);
    this.checkNotEquals(data1, data2);

    data2 = new ConnectionDataStub(data1, new Bid4WinDate());
    data2 = data2.endConnection(DisconnectionReason.AUTO);
    this.checkNotEquals(data1, data2);

    data2 = new ConnectionDataStub(data1, data1.getStartDate());
    data2 = data2.stopConnection(DisconnectionReason.AUTO);
    this.checkNotEquals(data1, data2);

    data1 = data1.stopConnection(DisconnectionReason.AUTO);
    this.checkEquals(data1, data2);
  }
  /**
   * Test of endConnection(DisconnectionReason), of class ConnectionData.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEndConnection_DisconnectionReason() throws Bid4WinException
  {
    String sessionId = "01234567890123456789012345678932";
    IpAddress ipAddress = this.getGenerator().createIpAddress(false);
    ConnectionData data1 = new ConnectionData(sessionId, ipAddress, true);
    data1 = new ConnectionDataStub(data1, new Bid4WinDate());

    ConnectionData data2 = data1.endConnection(DisconnectionReason.MANUAL);
    ConnectionData data3 = data2.endConnection(DisconnectionReason.PASSWORD);
    assertTrue("Bad remanence", data1.isRemanent());
    assertNull("Bad disconnection reason", data1.getDisconnectionReason());
    assertTrue("Bad remanence", data2.isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.MANUAL,
                 data2.getDisconnectionReason());
    assertTrue("Bad remanence", data3.isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                 data3.getDisconnectionReason());
  }
  /**
   * Test of stopConnection(DisconnectionReason), of class ConnectionData.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testStopConnection_DisconnectionReason() throws Bid4WinException
  {
    String sessionId = "01234567890123456789012345678932";
    IpAddress ipAddress = this.getGenerator().createIpAddress(false);
    ConnectionData data1 = new ConnectionData(sessionId, ipAddress, true);
    data1 = new ConnectionDataStub(data1, new Bid4WinDate());

    ConnectionData data2 = data1.stopConnection(DisconnectionReason.MANUAL);
    ConnectionData data3 = data1.stopConnection(DisconnectionReason.PASSWORD);
    assertTrue("Bad remanence", data1.isRemanent());
    assertNull("Bad disconnection reason", data1.getDisconnectionReason());
    assertFalse("Bad remanence", data2.isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.MANUAL,
                 data2.getDisconnectionReason());
    assertFalse("Bad remanence", data3.isRemanent());
    assertEquals("Bad disconnection reason", DisconnectionReason.PASSWORD,
                 data3.getDisconnectionReason());
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.core.security.ProtectableObjectTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    // Pas de méthode protégée accessible
  }
}
