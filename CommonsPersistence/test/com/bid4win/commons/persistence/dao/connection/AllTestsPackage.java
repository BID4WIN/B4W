package com.bid4win.commons.persistence.dao.connection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.dao.connection seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDaoTest.class,
                     com.bid4win.commons.persistence.dao.connection.ConnectionHistoryAbstractDaoTest.class})
public class AllTestsPackage
{
  // Pas de définition spécifique
}