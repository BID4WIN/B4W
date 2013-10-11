package com.bid4win.persistence.dao.account.credit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.dao.account.credit seulement<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.account.credit.CreditBundleDaoTest.class,
                     com.bid4win.persistence.dao.account.credit.CreditBundleHistoryDaoTest.class})
public class AllTestsPackage extends Bid4WinEntityTester
{
  // Definition dans la super classe
}