package com.bid4win.persistence.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.dao complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.account.AllTests.class,
                     com.bid4win.persistence.dao.auction.AllTests.class,
                     com.bid4win.persistence.dao.connection.AllTests.class,
                     com.bid4win.persistence.dao.locale.AllTests.class,
                     com.bid4win.persistence.dao.property.AllTests.class})
public class AllTests extends Bid4WinEntityTester
{
  // Definition dans la super classe
}