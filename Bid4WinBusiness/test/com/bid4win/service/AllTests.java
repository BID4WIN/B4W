package com.bid4win.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.service complet.<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.service.account.AllTests.class,
                     com.bid4win.service.auction.AllTests.class,
                     com.bid4win.service.connection.AllTests.class,
                     com.bid4win.service.image.AllTests.class,
                     com.bid4win.service.locale.AllTests.class,
                     com.bid4win.service.product.AllTests.class,
                     com.bid4win.service.property.AllTests.class})
public class AllTests
{
  // Pas de définition spécifique
}