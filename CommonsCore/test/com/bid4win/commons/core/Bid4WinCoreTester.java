package com.bid4win.commons.core;

import org.junit.After;
import org.junit.Before;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.security.ObjectProtector;
import com.bid4win.commons.testing.Bid4WinTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinCoreTester extends Bid4WinTester
{
  /** TODO A COMMENTER */
  private Bid4WinList<String> protectionIdList = new Bid4WinList<String>();

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public CoreGenerator getGenerator()
  {
    return CoreGenerator.getInstance();
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected Bid4WinList<String> getProtectionIdList()
  {
    return this.protectionIdList;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected String startProtection()
  {
    String protectionId = ObjectProtector.startProtection();
    this.getProtectionIdList().add(protectionId);
    return protectionId;
  }
  /**
   *
   * TODO A COMMENTER
   * @param protectionId TODO A COMMENTER
   */
  protected void startProtection(String protectionId)
  {
    ObjectProtector.startProtection(protectionId);
    this.getProtectionIdList().add(protectionId);
  }
  /**
   *
   * TODO A COMMENTER
   * @param protectionId TODO A COMMENTER
   */
  protected void stopProtection(String protectionId)
  {
    ObjectProtector.stopProtection(protectionId);
    this.getProtectionIdList().removeLast();
  }
  /**
   *
   * TODO A COMMENTER
   */
  protected void stopProtection()
  {
    this.stopProtection(this.getProtectionIdList().getLast());
  }

  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    this.getProtectionIdList().clear();
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    super.tearDown();
    while(!this.getProtectionIdList().isEmpty())
    {
      this.stopProtection();
    }
  }
}
