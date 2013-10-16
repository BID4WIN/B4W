package com.bid4win.commons.persistence.entity.property;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.PropertyRef;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test de l'utilitaire de propriétés<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtilPropertyTest extends Bid4WinCoreTester
{
  /**
   * Test of checkSimpleKey(String, MessageRef), of class Property.
   */
  @Test
  public void testCheckSimpleKey_String_MessageRef()
  {
    try
    {
      UtilProperty.checkSimpleKey("a", PropertyRef.PROPERTY);
      UtilProperty.checkSimpleKey("a_b", PropertyRef.PROPERTY);
      UtilProperty.checkSimpleKey("a_b_c", PropertyRef.PROPERTY);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Check should not have failed with valid keys: " + ex.getMessage());
    }
    try
    {
      UtilProperty.checkSimpleKey(UtilString.EMPTY, PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilProperty.checkSimpleKey(" a", PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilProperty.checkSimpleKey("a ", PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilProperty.checkSimpleKey("_b_c", PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilProperty.checkSimpleKey("a__c", PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilProperty.checkSimpleKey("a_b_", PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkFullKey(String, MessageRef), of class Property.
   */
  @Test
  public void testCheckFullKey_String_MessageRef()
  {
    try
    {
      UtilProperty.checkFullKey("a", PropertyRef.PROPERTY);
      UtilProperty.checkFullKey("a.b", PropertyRef.PROPERTY);
      UtilProperty.checkFullKey("a.b.c", PropertyRef.PROPERTY);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Check should not have failed with valid keys: " + ex.getMessage());
    }
    try
    {
      UtilProperty.checkFullKey(UtilString.EMPTY, PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilProperty.checkFullKey(".b.c", PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilProperty.checkFullKey("a..c", PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilProperty.checkFullKey("a.b.", PropertyRef.PROPERTY);
      fail("Check should have failed with invalid key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
