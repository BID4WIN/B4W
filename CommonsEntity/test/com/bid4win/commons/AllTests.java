package com.bid4win.commons;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.caching.AllTests.class,
                     com.bid4win.commons.persistence.AllTests.class})
public class AllTests
{
  // Pas de définition spécifique
}