package com.bid4win.persistence.dao.account.credit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.dao.account.credit<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.account.credit.AllTestsPackage.class,
                     com.bid4win.persistence.dao.account.credit.auction.AllTests.class})
public class AllTests extends Bid4WinEntityTester
{
  // Definition dans la super classe
}