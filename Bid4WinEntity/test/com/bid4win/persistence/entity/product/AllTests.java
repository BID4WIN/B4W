package com.bid4win.persistence.entity.product;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.entity.product complet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.entity.product.AllTestsPackage.class})
public class AllTests extends Bid4WinEntityTester
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
}