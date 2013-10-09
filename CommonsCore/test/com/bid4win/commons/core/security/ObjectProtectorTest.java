package com.bid4win.commons.core.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ObjectProtectorTest extends Bid4WinCoreTester
{
  /**
   * Test of startProtection(String), of class ObjectProtector.
   */
  @Test
  public void testStartProtection_String()
  {
    Bid4WinList<String> protectionIdList = new Bid4WinList<String>();
    assertNull("Wrong protection ID list", ObjectProtector.findProtectionIdList());
    for(int i = 0 ; i < 10 ; i++)
    {
      String protectionId = "" + i;
      ObjectProtector.startProtection(protectionId);
      protectionIdList.add(protectionId);
      assertEquals("Wrong protection ID", protectionId, ObjectProtector.getProtectionId());
      assertEquals("Wrong protection ID list", protectionIdList,
                   ObjectProtector.findProtectionIdList());
      ObjectProtector.startProtection(protectionId);
      protectionIdList.add(protectionId);
      assertEquals("Wrong protection ID", protectionId, ObjectProtector.getProtectionId());
      assertEquals("Wrong protection ID list", protectionIdList,
                   ObjectProtector.findProtectionIdList());
    }
  }
  /**
   * Test of startProtection(), of class ObjectProtector.
   */
  @Test
  public void testStartProtection_0args()
  {
    Bid4WinList<String> protectionIdList = new Bid4WinList<String>();
    assertNull("Wrong protection ID list", ObjectProtector.findProtectionIdList());
    for(int i = 0 ; i < 10 ; i++)
    {
      String protectionId = ObjectProtector.startProtection();
      protectionIdList.add(protectionId);
      assertEquals("Wrong protection ID", protectionId, ObjectProtector.getProtectionId());
      assertEquals("Wrong protection ID list", protectionIdList,
                   ObjectProtector.findProtectionIdList());
      ObjectProtector.startProtection(protectionId);
      protectionIdList.add(protectionId);
      assertEquals("Wrong protection ID", protectionId, ObjectProtector.getProtectionId());
      assertEquals("Wrong protection ID list", protectionIdList,
                   ObjectProtector.findProtectionIdList());
    }
  }
  /**
   * Test of stopProtection(String), of class ObjectProtector.
   */
  @Test
  public void testStopProtection_String()
  {
    Bid4WinList<String> protectionIdList = new Bid4WinList<String>();
    for(int i = 0 ; i < 10 ; i++)
    {
      String protectionId = "" + i;
      ObjectProtector.startProtection(protectionId);
      protectionIdList.add(protectionId);
      ObjectProtector.startProtection(protectionId);
      protectionIdList.add(protectionId);
    }
    while(ObjectProtector.isProtectionStarted())
    {
      int protectionNb = ObjectProtector.findProtectionIdList().size();
      String protectionId1 = protectionIdList.get(protectionNb - 1);
      String protectionId2 = protectionIdList.getLast();
      if(protectionNb > 1)
      {
        protectionId2 = protectionIdList.get(protectionNb - 2);
      }
      if(!protectionId1.equals(protectionId2))
      {
        try
        {
          ObjectProtector.stopProtection(protectionId2);
          fail("Should fail with wrong protection ID");
        }
        catch(ProtectionException ex)
        {
          System.out.println(ex.getMessage());
        }
      }
      try
      {
        ObjectProtector.stopProtection(protectionId1);
        if(ObjectProtector.isProtectionStarted())
        {
          assertEquals("Wrong protection ID nb", protectionNb - 1,
                       ObjectProtector.findProtectionIdList().size());
        }
      }
      catch(ProtectionException ex)
      {
        ex.printStackTrace();
        fail("Should not fail with good protection ID: " + ex.getMessage());
      }
    }
    assertNull("Wrong protection ID list", ObjectProtector.findProtectionIdList());
  }

  /**
   * Test of hasProtection(String), of class ObjectProtector.
   */
  @Test
  public void testHasProtection_String()
  {
    Bid4WinList<String> protectionIdList = new Bid4WinList<String>();
    for(int i = 0 ; i < 10 ; i++)
    {
      String protectionId = "" + i;

      assertFalse("Should not have unknown protection ID",
                  ObjectProtector.hasProtectionId(protectionId));
      ObjectProtector.startProtection(protectionId);
      ObjectProtector.startProtection(protectionId);
      protectionIdList.add(protectionId);
      for(String id : protectionIdList)
      {
        assertTrue("Should have known protection ID", ObjectProtector.hasProtectionId(id));
      }
      assertFalse("Should not have unknown protection ID", ObjectProtector.hasProtectionId("" + (i+1)));
    }
  }

  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    while(ObjectProtector.isProtectionStarted())
    {
      ObjectProtector.stopProtection(ObjectProtector.getProtectionId());
    }
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    while(ObjectProtector.isProtectionStarted())
    {
      ObjectProtector.stopProtection(ObjectProtector.getProtectionId());
    }
  }
}
