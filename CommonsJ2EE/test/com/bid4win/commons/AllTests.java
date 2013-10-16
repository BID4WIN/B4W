package com.bid4win.commons;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.testing.Bid4WinTester;

/**
 * Classe de test du package com.bid4win.commons complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.j2ee.AllTests.class,
                     com.bid4win.commons.logging.AllTests.class})
public class AllTests extends Bid4WinTester
{
  // Pas de test spécifique
}