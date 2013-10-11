package com.bid4win.commons.persistence.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.entity complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.entity.AllTestsPackage.class,
                     com.bid4win.commons.persistence.entity.account.AllTests.class,
                     com.bid4win.commons.persistence.entity.connection.AllTests.class,
                     com.bid4win.commons.persistence.entity.contact.AllTests.class,
                     com.bid4win.commons.persistence.entity.property.AllTests.class,
                     com.bid4win.commons.persistence.entity.resource.AllTests.class})
public class AllTests
{
  // Pas de définition spécifique
}