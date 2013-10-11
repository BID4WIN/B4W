package com.bid4win.persistence.entity.account.credit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.persistence.entity.account.credit seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.entity.account.credit.CreditBundleAbstractTest.class,
                     com.bid4win.persistence.entity.account.credit.CreditBundleHistoryTest.class,
                     com.bid4win.persistence.entity.account.credit.CreditBundleTest.class,
                     com.bid4win.persistence.entity.account.credit.CreditInvolvementTest.class})
public class AllTestsPackage
{
  // Pas de définition spécifique
}