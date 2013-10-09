package com.bid4win.commons.core.comparator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.Bid4WinObjectStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test du comparateur des objets de base du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinObjectComparatorTest extends Bid4WinCoreTester
{
  /**
   * Test of equals(Bid4WinObject, Bid4WinObject) method, of class Bid4WinObjectComparator.
   */
  @Test
  public void testEquals_Bid4WinObject_Bid4WinObject()
  {
    Bid4WinObjectStub stub1 = new Bid4WinObjectStub("key", "value");
    Bid4WinObjectStub stub2 = new Bid4WinObjectStub("key", "value");
    assertTrue(Bid4WinObjectComparator.getInstanceObject().equals(stub1, stub1));
    assertTrue(Bid4WinObjectComparator.getInstanceObject().equals(stub1, stub2));
    assertTrue(Bid4WinObjectComparator.getInstanceObject().equals(stub2, stub1));
    assertFalse(Bid4WinObjectComparator.getInstanceObject().equals(null, stub1));
    assertFalse(Bid4WinObjectComparator.getInstanceObject().equals(stub1, null));
    assertTrue(Bid4WinObjectComparator.getInstanceObject().equals(null, null));
    stub2 = new Bid4WinObjectStub("key1", "value");
    assertFalse(Bid4WinObjectComparator.getInstanceObject().equals(stub1, stub2));
    assertFalse(Bid4WinObjectComparator.getInstanceObject().equals(stub2, stub1));
  }
}
