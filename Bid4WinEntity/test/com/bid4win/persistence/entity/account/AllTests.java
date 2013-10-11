package com.bid4win.persistence.entity.account;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.persistence.entity.account complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.entity.account.AllTestsPackage.class,
                     com.bid4win.persistence.entity.account.credit.AllTests.class,
                     com.bid4win.persistence.entity.account.preference.AllTests.class})
public class AllTests
{
  // Pas de définition spécifique
}