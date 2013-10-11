package com.bid4win.persistence.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Classe de test du package com.bid4win.persistence.entity complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.entity.account.AllTests.class,
                     com.bid4win.persistence.entity.auction.AllTests.class,
                     com.bid4win.persistence.entity.connection.AllTests.class,
                     com.bid4win.persistence.entity.image.AllTests.class,
                     com.bid4win.persistence.entity.locale.AllTests.class,
                     com.bid4win.persistence.entity.price.AllTests.class,
                     com.bid4win.persistence.entity.product.AllTests.class,
                     com.bid4win.persistence.entity.property.AllTests.class})
public class AllTests
{
  // Pas de définition spécifique
}