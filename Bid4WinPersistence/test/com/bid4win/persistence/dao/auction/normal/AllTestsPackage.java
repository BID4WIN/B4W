package com.bid4win.persistence.dao.auction.normal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence.dao.auction.normal seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.auction.normal.NormalAuctionTest.class,
                     com.bid4win.persistence.dao.auction.normal.NormalBidDaoTest.class,
                     com.bid4win.persistence.dao.auction.normal.NormalBotDaoTest.class})
public class AllTestsPackage extends Bid4WinEntityTester
{
  // Definition dans la super classe
}