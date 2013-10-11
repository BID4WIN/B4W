package com.bid4win.persistence.entity.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test conditions de base d'une vente aux enchères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class TermsAbstractTest
{
  /**
   * Test of TermsAbstract(int, double) method, of class Bid.
   */
  @Test
  public void testTermsAbstract_int_double()
  {
    try
    {
      TermsAbstractStub terms = new TermsAbstractStub(0, 0.01);
      assertEquals("Bad credit nb per bid", 0, terms.getCreditNbPerBid());
      assertTrue("Bad bid increment value", 0.01 == terms.getBidIncrement().getValue());
      terms = new TermsAbstractStub(10, 1.234);
      assertEquals("Bad credit nb per bid", 10, terms.getCreditNbPerBid());
      assertTrue("Bad bid increment value", 1.23 == terms.getBidIncrement().getValue());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail(ex.getMessage());
    }
    try
    {
      new TermsAbstractStub(-1, 0.01);
      fail("Should fail with negative credit nb");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new TermsAbstractStub(0, 0.001);
      fail("Should fail with zero bid increment value");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
