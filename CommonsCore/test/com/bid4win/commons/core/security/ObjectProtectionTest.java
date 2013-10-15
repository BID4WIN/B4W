package com.bid4win.commons.core.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ObjectProtectionTest extends Bid4WinCoreTester
{
  /**
   * Test of ObjectProtection(String), of class ObjectProtection.
   */
  @Test
  public void testObjectProtection_String()
  {
    ObjectProtection protection1 = new ObjectProtection(null);
    assertFalse("Protection should not be enabled", protection1.isEnabled());
    protection1 = new ObjectProtection(UtilString.EMPTY);
    assertTrue("Protection should be enabled", protection1.isEnabled());
  }

  /**
   * Test of check(), of class ObjectProtection.
   */
  @Test
  public void testCheck_0args()
  {
    ObjectProtection protection1 = new ObjectProtection (null);
    assertTrue("Should not fail", protection1.check());

    this.startProtection();
    ObjectProtection protection2 = ObjectProtector.getProtection();
    assertTrue("Should not fail", protection1.check());
    assertTrue("Should not fail", protection2.check());

    this.startProtection();
    ObjectProtection protection3 = ObjectProtector.getProtection();
    assertTrue("Should not fail", protection1.check());
    assertTrue("Should not fail", protection2.check());
    assertTrue("Should not fail", protection3.check());

    this.stopProtection();
    ObjectProtection protection4 = ObjectProtector.getProtection();
    assertTrue("Should not fail", protection1.check());
    assertTrue("Should not fail", protection2.check());
    assertFalse("Should fail", protection3.check());
    assertTrue("Should not fail", protection4.check());

    this.stopProtection();
    assertTrue("Should not fail", protection1.check());
    assertFalse("Should fail", protection2.check());
    assertFalse("Should fail", protection3.check());
    assertFalse("Should fail", protection4.check());
  }
}
