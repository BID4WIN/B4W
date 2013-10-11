package com.bid4win.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.persistence complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.entity.AllTests.class,
                     com.bid4win.persistence.usertype.AllTests.class,})
public class AllTests
{
  // Pas de définition spécifique
}