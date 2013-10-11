package com.bid4win.manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.manager<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.manager.image.AllTests.class,
                     com.bid4win.manager.locale.AllTests.class})
public class AllTests
{
  // Pas de définition spécifique
}