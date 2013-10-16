package com.bid4win.commons.j2ee;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.testing.Bid4WinTester;

/**
 * Classe de test du package com.bid4win.commons.j2ee seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.j2ee.Bid4WinSelfReferencedBeanTest.class})
public class AllTestsPackage extends Bid4WinTester
{
  // Pas de test spécifique
}