package com.bid4win.persistence.entity.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.Bid4WinEntityTester;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.collection.CreditMap;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.ExchangeRates;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class AuctionAbstractTest extends Bid4WinEntityTester
{
  /**
   * Test of AuctionAbstract(Product, SCHEDULE, TERMS) method, of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAuctionAbstract_Product_SCHEDULE_TERMS() throws Bid4WinException
  {
    Product product = this.getGenerator().createProduct();
    ScheduleAbstractStub schedule = this.getGenerator().createScheduleAbstract();
    TermsAbstractStub terms = this.getGenerator().createTermsAbstract();
    try
    {
      AuctionAbstractStub auction = new AuctionAbstractStub(product, schedule, terms);
      assertTrue("Bad product", product == auction.getProduct());
      assertEquals("Bad product price", product.getPrice(), auction.getProductPrice());
      assertTrue("Bad schedule", schedule == auction.getSchedule());
      assertTrue("Bad terms", terms == auction.getTerms());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new AuctionAbstractStub(null, schedule, terms);
      fail("Should fail with null product");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new AuctionAbstractStub(product, null, terms);
      fail("Should fail with null schedule");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new AuctionAbstractStub(product, schedule, null);
      fail("Should fail with null terms");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of sameInternal(AuctionAbstract, boolean), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSameInternal_AuctionAbstract_boolean() throws Bid4WinException
  {
    Product product1 = this.getGenerator().createProduct();
    Product product2 = this.getGenerator().createProduct();
    ScheduleAbstractStub schedule = this.getGenerator().createScheduleAbstract();
    TermsAbstractStub terms = this.getGenerator().createTermsAbstract();

    AuctionAbstractStub auction1 = new AuctionAbstractStub("1", product1, schedule, terms);
    AuctionAbstractStub auction2 = new AuctionAbstractStub("1", product1, schedule, terms);
    assertTrue(auction1.same(auction2));
    assertTrue(auction2.same(auction1));
    assertTrue(auction1.identical(auction2));
    assertTrue(auction2.identical(auction1));

    product2.defineReference(product1.getReference() + "2");
    auction2 = new AuctionAbstractStub(product2, schedule, terms);
    assertFalse(auction1.same(auction2));
    assertFalse(auction2.same(auction1));
    assertFalse(auction1.identical(auction2));
    assertFalse(auction2.identical(auction1));

    product2.defineReference(product1.getReference());
    assertTrue(auction1.same(auction2));
    assertTrue(auction2.same(auction1));
    assertFalse(auction1.identical(auction2));
    assertFalse(auction2.identical(auction1));

    auction1.defineBidNb(1);
    assertFalse(auction1.same(auction2));
    assertFalse(auction2.same(auction1));
    assertFalse(auction1.identical(auction2));
    assertFalse(auction2.identical(auction1));

    auction2.defineBidNb(1);
    assertTrue(auction1.same(auction2));
    assertTrue(auction2.same(auction1));
    assertFalse(auction1.identical(auction2));
    assertFalse(auction2.identical(auction1));

    schedule = this.getGenerator().createScheduleAbstract();
    auction2 = new AuctionAbstractStub("1", auction1.getProduct(), schedule, auction1.getTerms());
    assertFalse(auction1.same(auction2));
    assertFalse(auction2.same(auction1));
    assertFalse(auction1.identical(auction2));
    assertFalse(auction2.identical(auction1));

    terms = this.getGenerator().createTermsAbstract(terms.getCreditNbPerBid() + 1,
                                                    terms.getBidIncrement().getValue());
    schedule = new ScheduleAbstractStub(new Bid4WinDate());
    auction2 = new AuctionAbstractStub("1", auction1.getProduct(), auction1.getSchedule(), terms);
    assertFalse(auction1.same(auction2));
    assertFalse(auction2.same(auction1));
    assertFalse(auction1.identical(auction2));
    assertFalse(auction2.identical(auction1));
  }
  /**
   * Test of defineBidNb(int), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefineBidNb_int() throws Bid4WinException
  {
    Product product = this.getGenerator().createProduct();
    ScheduleAbstractStub schedule = this.getGenerator().createScheduleAbstract();
    TermsAbstractStub terms = this.getGenerator().createTermsAbstract();
    AuctionAbstractStub auction = new AuctionAbstractStub(product, schedule, terms);
    auction.defineBidNb(123);
    assertEquals("Wrong bid nb", 123, auction.getBidNb());
    auction.defineBidNb(0);
    assertEquals("Wrong bid nb", 0, auction.getBidNb());
    try
    {
      auction.defineBidNb(-1);
      fail("Should fail with negative number");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of historizeBids(), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHistorizeBids_0args() throws Bid4WinException
  {
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    assertFalse("Should not be historized", auction.isBidHistorized());
    try
    {
      auction.historizeBids();
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertFalse("Should not be historized", auction.isBidHistorized());
    }

    auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates());
    try
    {
      auction.historizeBids();
      fail("Should fail with wrong status");
      assertFalse("Should not be historized", auction.isBidHistorized());
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    auction.start();
    try
    {
      auction.historizeBids();
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertFalse("Should not be historized", auction.isBidHistorized());
    }
    auction.addBid(this.getGenerator().createAccount(), new Bid4WinDate());
    auction.close();
    auction.historizeBids();
    assertTrue("Should be historized", auction.isBidHistorized());
    try
    {
      auction.historizeBids();
      fail("Should fail with historized auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    auction = this.getGenerator().createAuctionAbstract().validate(
        new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates()).start().cancel();
    assertFalse("Should not be historized", auction.isBidHistorized());
    auction.historizeBids();
    assertTrue("Should be historized", auction.isBidHistorized());
  }
  /**
   * Test of historizeInvolvements(Amount), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHistorizeInvolvements_Amount() throws Bid4WinException
  {
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    CreditBundle bundle = this.getGenerator().createCreditBundle(
        this.getGenerator().createAccount(), 123.456, 1);
    Amount amount1 = bundle.getUnitValue();
    Amount amount2 = new Amount(456.789);
    assertFalse("Should not be historized", auction.isInvolvementHistorized());
    assertEquals("Wrong involvement", new Amount(0), auction.getInvolvementValue());
    try
    {
      auction.historizeInvolvements(amount2);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertFalse("Should not be historized", auction.isInvolvementHistorized());
      assertEquals("Wrong involvement", new Amount(0), auction.getInvolvementValue());
    }

    auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates());
    try
    {
      auction.historizeInvolvements(amount2);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertFalse("Should not be historized", auction.isInvolvementHistorized());
      assertEquals("Wrong involvement", new Amount(0), auction.getInvolvementValue());
    }

    auction.start();
    CreditMap map = new CreditMap(1);
    map.put(bundle, 1);
    auction.addInvolvedCredit(map);
    try
    {
      auction.historizeInvolvements(amount2);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertFalse("Should not be historized", auction.isInvolvementHistorized());
      assertEquals("Wrong involvement", amount1, auction.getInvolvementValue());
    }

    auction.addBid(this.getGenerator().createAccount(), new Bid4WinDate());
    auction.close();
    auction.historizeInvolvements(amount2);
    assertTrue("Should be historized", auction.isInvolvementHistorized());
    assertEquals("Wrong involvement", amount2, auction.getInvolvementValue());
    try
    {
      auction.historizeInvolvements(amount1);
      fail("Should fail with historized auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong involvement", amount2, auction.getInvolvementValue());
    }

    auction = this.getGenerator().createAuctionAbstract().validate(
        new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates()).start();
    auction.addInvolvedCredit(map);
    auction.cancel();
    assertFalse("Should not be historized", auction.isInvolvementHistorized());

    try
    {
      auction.historizeInvolvements(null);
      fail("Should fail with null amount");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertFalse("Should not be historized", auction.isInvolvementHistorized());
      assertEquals("Wrong involvement", amount1, auction.getInvolvementValue());
    }

    auction.historizeInvolvements(amount2);
    assertTrue("Should be historized", auction.isInvolvementHistorized());
    assertEquals("Wrong involvement", amount2, auction.getInvolvementValue());
  }
  /**
   * Test of addBid(Account, Bid4WinDate), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddBid_Account_Bid4WinDate() throws Bid4WinException
  {
    Account account1 = this.getGenerator().createAccount();
    Account account2 = this.getGenerator().createAccount();

    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    assertEquals("Wrong bid nb", 0, auction.getBidNb());

    try
    {
      auction.addBid(account1, new Bid4WinDate());
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong bid nb", 0, auction.getBidNb());
    }
    auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates());
    try
    {
      auction.addBid(account1, new Bid4WinDate());
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong bid nb", 0, auction.getBidNb());
    }

    auction.start();
    BidAbstractStub bid = auction.addBid(account1, new Bid4WinDate());
    assertEquals("Wrong bid nb", 1, auction.getBidNb());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong account", account1 == bid.getAccount());

    /*auction.pause();
    try
    {
      auction.addBid(account1, amount);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong bid nb", 1, auction.getBidNb());
    }
    auction.start();*/

    auction.addBid(account1, new Bid4WinDate());
    assertEquals("Wrong bid nb", 2, auction.getBidNb());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong account", account1 == bid.getAccount());

    try
    {
      auction.addBid(null, new Bid4WinDate());
      fail("Should fail with null bidder");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong bid nb", 2, auction.getBidNb());
      assertTrue("Wrong auction", auction == bid.getAuction());
      assertTrue("Wrong account", account1 == bid.getAccount());
    }
    try
    {
      auction.addBid(account1, null);
      fail("Should fail with null bid date");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong bid nb", 2, auction.getBidNb());
      assertTrue("Wrong auction", auction == bid.getAuction());
      assertTrue("Wrong account", account1 == bid.getAccount());
    }

    bid = auction.addBid(account2, new Bid4WinDate());
    assertEquals("Wrong bid nb", 3, auction.getBidNb());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong account", account2 == bid.getAccount());

    bid = auction.addBid(account1, new Bid4WinDate());
    assertEquals("Wrong bid nb", 4, auction.getBidNb());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong account", account1 == bid.getAccount());

    auction.close();
    try
    {
      auction.addBid(account1, new Bid4WinDate());
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong bid nb", 4, auction.getBidNb());
      assertTrue("Wrong auction", auction == bid.getAuction());
      assertTrue("Wrong account", account1 == bid.getAccount());
    }
  }
  /**
   * Test of addInvolvedCredit(Bid4WinMap), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddInvolvedCredit_Bid4WinMap() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount();
    CreditBundle bundle1 = this.getGenerator().createCreditBundle(account, 1.23, 1);
    CreditBundle bundle2 = this.getGenerator().createCreditBundle(account, 4.56, 1);

    Amount involvement = new Amount(0);
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());

    CreditMap map = new CreditMap();
    map.put(bundle1, 1);
    map.put(bundle2, 2);
    try
    {
      auction.addInvolvedCredit(map);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());
    }
    auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates());
    try
    {
      auction.addInvolvedCredit(map);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());
    }

    auction.start();
    auction.addInvolvedCredit(map);
    involvement = involvement.add(map.getTotalValue());
    assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());

/*    auction.pause();
    try
    {
      auction.addBid(account1, amount);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong bid nb", 1, auction.getBidNb());
      assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());
    }
    auction.start();*/

    auction.addInvolvedCredit(new CreditMap());
    assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());

    auction.addInvolvedCredit(null);
    assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());

    map.clear();
    map.put(bundle1, 0);
    map.put(bundle2, 1);
    involvement = involvement.add(bundle2.getUnitValue());

    auction.addInvolvedCredit(map);
    assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());

    auction.addBid(this.getGenerator().createAccount(), new Bid4WinDate());
    auction.close();
    try
    {
      auction.addInvolvedCredit(map);
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong involvements value", involvement, auction.getInvolvementValue());
    }
  }
  /**
   * Test of validateProductPrice(), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testValidateProductPrice_0args() throws Bid4WinException
  {
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    Product product = auction.getProduct();
    Price productPrice = product.getPrice();
    assertEquals("Wrong product price", productPrice, auction.getProductPrice());
    product.definePrice(this.getGenerator().createPrice(productPrice.getValue() + 1));
    assertEquals("Wrong product price", productPrice, auction.getProductPrice());
    assertFalse("Wrong product price", product.getPrice().equals(auction.getProductPrice()));
    auction.validateProductPrice();
    assertFalse("Wrong product price", productPrice.equals(auction.getProductPrice()));
    assertEquals("Wrong product price", product.getPrice(), auction.getProductPrice());
    auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates());
    try
    {
      auction.validateProductPrice();
      fail("Should fail with validated auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of validate(CANCEL_POLICY, ExchangeRates), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testValidate_CANCELPOLICY_ExchangeRates() throws Bid4WinException
  {
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    CancelPolicyAbstractStub cancelPolicy = new CancelPolicyAbstractStub(1, 2);
    ExchangeRates exchangeRates = this.getGenerator().createExchangeRates(0);
    try
    {
      auction.validate(null, exchangeRates);
      fail("Should fail with null cancel policy");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong status", Status.WORKING, auction.getStatus());
      assertEquals("Wrong product price", auction.getProduct().getPrice(), auction.getProductPrice());
      assertNull("Wrong cancel policy", auction.getCancelPolicy());
      assertNull("Wrong exchange rates", auction.getExchangeRates());
    }
    try
    {
      auction.validate(cancelPolicy, null);
      fail("Should fail with null exchange rates");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong status", Status.WORKING, auction.getStatus());
      assertEquals("Wrong product price", auction.getProduct().getPrice(), auction.getProductPrice());
      assertNull("Wrong cancel policy", auction.getCancelPolicy());
      assertNull("Wrong exchange rates", auction.getExchangeRates());
    }

    auction.validate(cancelPolicy, exchangeRates);
    assertEquals("Wrong status", Status.WAITING, auction.getStatus());
    assertEquals("Wrong product price", auction.getProduct().getPrice(), auction.getProductPrice());
    assertEquals("Wrong cancel policy", cancelPolicy, auction.getCancelPolicy());
    assertEquals("Wrong exchange rates", exchangeRates, auction.getExchangeRates());

    cancelPolicy = new CancelPolicyAbstractStub(3, 4);
    exchangeRates = this.getGenerator().createExchangeRates(1);
    auction.validate(cancelPolicy, exchangeRates);
    assertEquals("Wrong status", Status.WAITING, auction.getStatus());
    assertEquals("Wrong product price", auction.getProduct().getPrice(), auction.getProductPrice());
    assertEquals("Wrong cancel policy", cancelPolicy, auction.getCancelPolicy());
    assertEquals("Wrong exchange rates", exchangeRates, auction.getExchangeRates());

    auction.getProduct().definePrice(new Price(1.23));
    auction.unvalidate(auction.getSchedule(), auction.getTerms());
    auction.validateProductPrice();
    assertEquals("Wrong product price", auction.getProduct().getPrice(), auction.getProductPrice());

    auction.validate(cancelPolicy, exchangeRates);
    assertEquals("Wrong status", Status.WAITING, auction.getStatus());
    assertFalse("Wrong product price", auction.getProduct().getPrice() == auction.getProductPrice());
    assertEquals("Wrong cancel policy", cancelPolicy, auction.getCancelPolicy());
    assertEquals("Wrong exchange rates", exchangeRates, auction.getExchangeRates());

    auction.start();
    try
    {
      auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates(0));
      fail("Should fail with started auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong status", Status.STARTED, auction.getStatus());
      assertEquals("Wrong cancel policy", cancelPolicy, auction.getCancelPolicy());
      assertEquals("Wrong exchange rates", exchangeRates, auction.getExchangeRates());
    }
  }
  /**
   * Test of unvalidate(SCHEDULE, TERMS), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnvalidate_SCHEDULE_TERMS() throws Bid4WinException
  {
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    ScheduleAbstractStub oldSchedule = auction.getSchedule();
    ScheduleAbstractStub newSchedule = new ScheduleAbstractStub(new Bid4WinDate());
    TermsAbstractStub oldTerms = auction.getTerms();
    TermsAbstractStub newTerms = new TermsAbstractStub(oldTerms.getCreditNbPerBid() + 1, 1);

    auction.unvalidate(newSchedule, newTerms);
    assertEquals("Wrong status", Status.WORKING, auction.getStatus());
    assertEquals("Wrong schedule", newSchedule, auction.getSchedule());
    assertEquals("Wrong terms", newTerms, auction.getTerms());
    auction.unvalidate(oldSchedule, oldTerms);
    assertEquals("Wrong status", Status.WORKING, auction.getStatus());
    assertEquals("Wrong schedule", oldSchedule, auction.getSchedule());
    assertEquals("Wrong terms", oldTerms, auction.getTerms());

    try
    {
      auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates());
      auction.unvalidate(null, newTerms);
      fail("Should fail with null schedule");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong status", Status.WAITING, auction.getStatus());
      assertEquals("Wrong schedule", oldSchedule, auction.getSchedule());
      assertEquals("Wrong terms", oldTerms, auction.getTerms());
    }
    try
    {
      auction.unvalidate(newSchedule, null);
      fail("Should fail with null terms");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong status", Status.WAITING, auction.getStatus());
      assertEquals("Wrong schedule", oldSchedule, auction.getSchedule());
      assertEquals("Wrong terms", oldTerms, auction.getTerms());
    }

    auction.unvalidate(newSchedule, newTerms);
    assertEquals("Wrong status", Status.WORKING, auction.getStatus());
    assertEquals("Wrong schedule", newSchedule, auction.getSchedule());
    assertEquals("Wrong terms", newTerms, auction.getTerms());

    try
    {
      auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates()).start();
      auction.unvalidate(oldSchedule, oldTerms);
      fail("Should fail with started auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong status", Status.STARTED, auction.getStatus());
      assertEquals("Wrong schedule", newSchedule, auction.getSchedule());
      assertEquals("Wrong terms", newTerms, auction.getTerms());
    }
  }
  /**
   * Test of defineStatus(Status), of class AuctionAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefineStatus_Status() throws Bid4WinException
  {
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();

    Status status = Status.WORKING;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    status = Status.WAITING;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    status = Status.WORKING;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    try
    {
      auction.defineStatus(Status.STARTED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.PAUSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CLOSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CANCELED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    status = Status.WAITING;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    status = Status.WAITING;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    try
    {
      auction.defineStatus(Status.PAUSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CLOSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CANCELED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    status = Status.STARTED;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    try
    {
      auction.defineStatus(Status.WORKING);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.WAITING);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    status = Status.PAUSED;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    try
    {
      auction.defineStatus(Status.WORKING);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.WAITING);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CLOSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CANCELED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    status = Status.STARTED;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    status = Status.CLOSED;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    try
    {
      auction.defineStatus(Status.WORKING);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.WAITING);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.STARTED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.PAUSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CLOSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CANCELED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    auction = this.getGenerator().createAuctionAbstract();
    auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates()).start();
    status = Status.CANCELED;
    auction.defineStatus(status);
    assertEquals("Wrong status", status, auction.getStatus());
    try
    {
      auction.defineStatus(Status.WORKING);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.WAITING);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.STARTED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.PAUSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CLOSED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      auction.defineStatus(Status.CANCELED);
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
