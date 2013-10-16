package com.bid4win.commons.logging;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.testing.Bid4WinTester;

/**
 * Classe de test du package com.bid4win.commons.logging complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.logging.aop.AllTests.class})
public class AllTests extends Bid4WinTester
{
  // Pas de test spécifique
}