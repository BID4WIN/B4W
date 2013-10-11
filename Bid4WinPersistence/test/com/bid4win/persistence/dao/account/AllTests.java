package com.bid4win.persistence.dao.account;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.dao.account<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.account.AllTestsPackage.class,
                     com.bid4win.persistence.dao.account.credit.AllTests.class})
public class AllTests extends Bid4WinEntityTester
{
  // Definition dans la super classe
}