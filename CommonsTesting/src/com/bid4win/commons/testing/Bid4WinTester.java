package com.bid4win.commons.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Classe de base des tests du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinTester
{
  /**
   *
   * TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public void checkEquals(Object expected, Object result)
  {
    this.checkEquals("Wrong result", expected, result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public void checkEquals(String message, Object expected, Object result)
  {
    assertEquals(message, expected, result);
  }
  /**
  *
  * TODO A COMMENTER
  * @param expected TODO A COMMENTER
  * @param result TODO A COMMENTER
  */
 public void checkNotEquals(Object expected, Object result)
 {
   this.checkNotEquals("Wrong result", expected, result);
 }
 /**
  *
  * TODO A COMMENTER
  * @param message TODO A COMMENTER
  * @param expected TODO A COMMENTER
  * @param result TODO A COMMENTER
  */
 public void checkNotEquals(String message, Object expected, Object result)
 {
   try
   {
     assertEquals(message, expected, result);
     message += ": Both objects should not be equals ";
     fail(message);
   }
   catch(AssertionError error)
   {
     // Les deux objets sont bien différents
   }
 }


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
