package com.bid4win.commons.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.service<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.service.account.AllTests.class,
                     com.bid4win.commons.service.connection.AllTests.class,
                     com.bid4win.commons.service.property.AllTests.class,
                     com.bid4win.commons.service.resource.AllTests.class})
public class AllTests
{
  // Pas de définition spécifique
}