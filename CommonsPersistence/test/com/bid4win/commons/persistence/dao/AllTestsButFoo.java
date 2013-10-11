package com.bid4win.commons.persistence.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.dao complet hormis
 * les tests FOO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.dao.account.AllTests.class,
                     com.bid4win.commons.persistence.dao.connection.AllTests.class,
                     com.bid4win.commons.persistence.dao.property.AllTests.class})
public class AllTestsButFoo
{
  // Pas de définition spécifique
}