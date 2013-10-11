package com.bid4win.persistence.entity.auction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.entity.auction seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.entity.auction.AuctionAbstractTest.class,
                     com.bid4win.persistence.entity.auction.AuctionTest.class,
                     com.bid4win.persistence.entity.auction.BidAbstractTest.class,
                     com.bid4win.persistence.entity.auction.BidHistoryTest.class,
                     com.bid4win.persistence.entity.auction.BidTest.class,
                     com.bid4win.persistence.entity.auction.ScheduleAbstractTest.class,
                     com.bid4win.persistence.entity.auction.ScheduleTest.class,
                     com.bid4win.persistence.entity.auction.TermsAbstractTest.class})
public class AllTestsPackage extends Bid4WinEntityTester
{
  /**
   * Class setup method
   * @throws Exception Issue not expected during class setup
   */
  @BeforeClass
  public static void setUpClass() throws Exception
  {
    // No setup action
  }
  /**
   * Class teardown method
   * @throws Exception Issue not expected during class teardown
   */
  @AfterClass
  public static void tearDownClass() throws Exception
  {
    // No teardown action
  }
  /**
   * Test setup method
   * @throws Exception Issue not expected during test setup
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    // No setup action
  }
  /**
   * Test teardown method
   * @throws Exception Issue not expected during test teardown
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    // No teardown action
  }
}