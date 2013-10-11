package com.bid4win.service.auction.normal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.service.auction.normal seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.service.auction.normal.NormalAuctionInternalServiceTest.class,
                     com.bid4win.service.auction.normal.NormalAuctionServiceTest.class})
public class AllTestsPackage
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
  @Before
  public void setUp() throws Exception
  {
    // No setup action
  }
  /**
   * Test teardown method
   * @throws Exception Issue not expected during test teardown
   */
  @After
  public void tearDown() throws Exception
  {
    // No teardown action
  }
}