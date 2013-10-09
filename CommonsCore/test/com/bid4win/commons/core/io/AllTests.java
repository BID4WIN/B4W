package com.bid4win.commons.core.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.core.Bid4WinCoreTester;

/**
 * Classe de test du package com.bid4win.commons.core.io complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.core.io.AllTestsPackage.class,
                     com.bid4win.commons.core.io.resource.AllTests.class,})
public class AllTests extends Bid4WinCoreTester
{
  // Pas de test spécifique
}