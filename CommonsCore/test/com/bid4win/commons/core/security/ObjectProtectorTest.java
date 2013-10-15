package com.bid4win.commons.core.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.UtilString;
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
  /** TODO A COMMENTER */
  private Bid4WinList<ObjectProtection> protectionList = new Bid4WinList<ObjectProtection>();

  /**
   * Test of startProtection(String), of class ObjectProtector.
   */
  @Test
  public void testStartProtection_String()
  {
    assertFalse("Wrong protection status", ObjectProtector.isProtectionStarted());
    for(int i = 0 ; i < 10 ; i++)
    {
      String protectionId = UtilString.EMPTY + i;
      this.startProtection(protectionId);
      assertNotNull("Wrong protection", ObjectProtector.getProtection());
      this.protectionList.add(ObjectProtector.getProtection());
      assertTrue("Wrong protection status", ObjectProtector.isProtectionStarted());
      assertTrue("Wrong protection ID", ObjectProtector.getProtection().check());

      this.startProtection(protectionId);
      assertNotNull("Wrong protection", ObjectProtector.getProtection());
      this.protectionList.add(ObjectProtector.getProtection());
      assertTrue("Wrong protection status", ObjectProtector.isProtectionStarted());
      assertTrue("Wrong protection ID", ObjectProtector.getProtection().check());
    }
  }
  /**
   * Test of startProtection(), of class ObjectProtector.
   */
  @Test
  public void testStartProtection_0args()
  {
    assertFalse("Wrong protection status", ObjectProtector.isProtectionStarted());
    for(int i = 0 ; i < 10 ; i++)
    {
      String protectionId = this.startProtection();
      assertNotNull("Wrong protection", ObjectProtector.getProtection());
      this.protectionList.add(ObjectProtector.getProtection());
      assertTrue("Wrong protection status", ObjectProtector.isProtectionStarted());
      assertTrue("Wrong protection ID", ObjectProtector.getProtection().check());

      this.startProtection(protectionId);
      assertNotNull("Wrong protection", ObjectProtector.getProtection());
      this.protectionList.add(ObjectProtector.getProtection());
      assertTrue("Wrong protection status", ObjectProtector.isProtectionStarted());
      assertTrue("Wrong protection ID", ObjectProtector.getProtection().check());
    }
  }
  /**
   * Test of stopProtection(String), of class ObjectProtector.
   */
  @Test
  public void testStopProtection_String()
  {
    for(int i = 0 ; i < 10 ; i++)
    {
      String protectionId = UtilString.EMPTY + i;
      this.startProtection(protectionId);
      this.protectionList.add(ObjectProtector.getProtection());
      this.startProtection(protectionId);
      this.protectionList.add(ObjectProtector.getProtection());
    }
    while(!this.getProtectionIdList().isEmpty())
    {
      int protectionNb = this.getProtectionIdList().size();
      String protectionId1 = this.getProtectionIdList().get(protectionNb - 1);
      String protectionId2 = this.getProtectionIdList().getLast();
      if(protectionNb > 1)
      {
        protectionId2 = this.getProtectionIdList().get(protectionNb - 2);
      }
      if(!protectionId1.equals(protectionId2))
      {
        try
        {
          this.stopProtection(protectionId2);
          fail("Should fail with wrong protection ID");
        }
        catch(ProtectionException ex)
        {
          System.out.println(ex.getMessage());
        }
      }
      try
      {
        this.stopProtection(protectionId1);
      }
      catch(ProtectionException ex)
      {
        ex.printStackTrace();
        fail("Should not fail with good protection ID: " + ex.getMessage());
      }
      this.protectionList.removeLast();
      if(ObjectProtector.isProtectionStarted())
      {
        assertEquals("Wrong protection", this.protectionList.getLast(),
                     ObjectProtector.getProtection());
      }
    }
    assertNull("Wrong protection", ObjectProtector.getProtection());
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
      String protectionId = UtilString.EMPTY + i;

      assertFalse("Should not have unknown protection ID",
                  ObjectProtector.hasProtectionId(protectionId));
      this.startProtection(protectionId);
      this.startProtection(protectionId);
      protectionIdList.add(protectionId);
      for(String id : protectionIdList)
      {
        assertTrue("Should have known protection ID", ObjectProtector.hasProtectionId(id));
      }
      assertFalse("Should not have unknown protection ID", ObjectProtector.hasProtectionId(UtilString.EMPTY + (i+1)));
    }
  }
}
