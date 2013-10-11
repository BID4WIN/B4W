package com.bid4win.persistence.usertype.account;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.core.Bid4WinCoreTester;

/**
 * Classe de test du package com.bid4win.persistence.usertype.account complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.usertype.account.AllTestsPackage.class,
                     com.bid4win.persistence.usertype.account.credit.AllTests.class})
public class AllTests extends Bid4WinCoreTester
{
  // Definition dans la super classe
}