package com.bid4win.commons.core.security;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.exception.Bid4WinException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ProtectableObjectTester extends Bid4WinCoreTester
{
  /**
   * Test of checkProtection() method, of class Bid4WinProtectableObject.
   * @throws Bid4WinException Issue not expected during this test
   */
  public abstract void testCheckProtection() throws Bid4WinException;
}
