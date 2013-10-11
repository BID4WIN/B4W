package com.bid4win.commons.persistence.dao.account;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.dao.account seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.dao.account.AccountAbstractDaoTest.class,
                     com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTest.class,
                     com.bid4win.commons.persistence.dao.account.AccountBasedEntitySingleDaoTest.class})
public class AllTestsPackage
{
  // Pas de définition spécifique
}