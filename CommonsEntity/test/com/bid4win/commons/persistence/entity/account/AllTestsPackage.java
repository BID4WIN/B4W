package com.bid4win.commons.persistence.entity.account;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.entity.account seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.entity.account.AccountAbstractTest.class,
                     com.bid4win.commons.persistence.entity.account.AccountBasedEntityTest.class,
                     com.bid4win.commons.persistence.entity.account.AccountBasedKeyTest.class})
public class AllTestsPackage
{
  // Pas de définition spécifique
}