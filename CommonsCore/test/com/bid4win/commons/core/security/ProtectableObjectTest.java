package com.bid4win.commons.core.security;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test des objets protégeables du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ProtectableObjectTest extends ProtectableObjectTester
{
  /**
   * Test of checkProtection() method, of class Bid4WinProtectableObject.
   * @see com.bid4win.commons.core.security.ProtectableObjectTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection()
  {
    ProtectableObject object1 = new ProtectableObject();
    String protectionId = this.startProtection();
    ProtectableObject object2 = new ProtectableObject();
    try
    {
      object1.checkProtection();
      object2.checkProtection();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    this.stopProtection(protectionId);
    try
    {
      object1.checkProtection();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      object2.checkProtection();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.startProtection("TOTO");
    try
    {
      object1.checkProtection();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      object2.checkProtection();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.startProtection(protectionId);
    this.startProtection("TOTO");
    try
    {
      object1.checkProtection();
      object2.checkProtection();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }

  /**
   * Test of protectFromNothing() method, of class Bid4WinProtectableObject.
   */
  @Test
  public void testProtectFromNothing_0args()
  {
    ProtectableObject object = new ProtectableObject();
    String protectionId = null;
    try
    {
      protectionId = object.protectFromNothing();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      object.checkProtection();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.startProtection(protectionId);
    try
    {
      object.checkProtection();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      object.protectFromNothing();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of protectFromExisting() method, of class Bid4WinProtectableObject.
   */
  @Test
  public void testProtectFromExisting_0args()
  {
    ProtectableObject object = new ProtectableObject();
    try
    {
      object.protectFromExisting();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      object.checkProtection();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    String protectionId = this.startProtection();
    try
    {
      object.protectFromExisting();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      object.checkProtection();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      object.protectFromExisting();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.stopProtection(protectionId);
    try
    {
      object.checkProtection();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void testCheckHierarchy_Class()
  {
    ProtectableObjectStub1 stub1 = new ProtectableObjectStub1();
    ProtectableObjectStub2 stub2 = new ProtectableObjectStub2();

    try
    {
      stub1.accessFromHierarchy1(stub1);
      stub1.accessFromHierarchy1(stub2);
      stub2.accessFromHierarchy1(stub1);
      stub2.accessFromHierarchy1(stub2);

      stub1.accessFromHierarchy2(stub1);
      stub2.accessFromHierarchy2(stub1);
      stub2.accessFromHierarchy2(stub2);
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      stub1.accessFromHierarchy2(stub2);
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      stub1.accessibleFromHierarchy1();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      stub1.accessibleFromHierarchy2();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    try
    {
      stub1.accessibleFromTest();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      stub1.accessFromTest();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
