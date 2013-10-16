package com.bid4win.commons.persistence.entity.contact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'un email<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class EmailTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  /**
   * Test of Email(String), of class Email.
   */
  @Test
  public void testEmail_String()
  {
    try
    {
      String value = UtilEmailTest.EMAIL;
      Email instance = new Email(value);
      assertEquals("Bad email address", value, instance.getAddress());

      value = "   " + value.toUpperCase() + "   ";
      instance = new Email(value);
      assertEquals("Bad email address", UtilEmailTest.EMAIL, instance.getAddress());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new Email(UtilEmailTest.EMAIL + "|");
      fail("Instanciation with forbidden character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new Email(null);
      fail("Instanciation with null value should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of equalsInternal(Email), of class Email.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_Email() throws Bid4WinException
  {
    Email email1 = new Email("a@b.cd");
    Email email2 = new Email("a@bc.de");

    assertFalse(email1.equalsInternal(email2));
    assertFalse(email2.equalsInternal(email1));
    assertTrue(email1.equalsInternal(email1));
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
    // Pas de méthode protégée à tester
  }
}
