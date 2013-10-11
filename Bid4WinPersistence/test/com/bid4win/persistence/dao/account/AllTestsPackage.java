package com.bid4win.persistence.dao.account;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.dao.account seulement<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.account.AccountDaoTest.class})
public class AllTestsPackage extends Bid4WinEntityTester
{
  // Definition dans la super classe
}