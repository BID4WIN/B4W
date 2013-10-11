package com.bid4win.persistence.dao.connection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.dao.connection seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.connection.ConnectionDaoTest.class,
                     com.bid4win.persistence.dao.connection.ConnectionHistoryDaoTest.class,
                     com.bid4win.persistence.dao.connection.PasswordReinitDaoTest.class,
                     com.bid4win.persistence.dao.connection.SubscriptionDaoTest.class})
public class AllTestsPackage extends Bid4WinEntityTester
{
  // Definition dans la super classe
}