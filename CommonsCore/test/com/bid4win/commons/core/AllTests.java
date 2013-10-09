package com.bid4win.commons.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.core complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.core.AllTestsPackage.class,
                     com.bid4win.commons.core.collection.AllTests.class,
                     com.bid4win.commons.core.comparator.AllTests.class,
                     com.bid4win.commons.core.io.AllTests.class,
                     com.bid4win.commons.core.security.AllTests.class})
public class AllTests extends Bid4WinCoreTester
{
  // Pas de test spécifique
}