package com.bid4win.persistence.dao.auction;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.dao.auction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.auction.normal.AllTests.class,
                     com.bid4win.persistence.dao.auction.prebooked.AllTests.class})
public class AllTests extends Bid4WinEntityTester
{
  // Definition dans la super classe
}