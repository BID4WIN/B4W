package com.bid4win.commons.logging.aop;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.testing.Bid4WinTester;

/**
 * Classe de test du package com.bid4win.commons.logging.aop seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.logging.aop.MethodTracerTest.class})
public class AllTestsPackage extends Bid4WinTester
{
  // Pas de test spécifique
}