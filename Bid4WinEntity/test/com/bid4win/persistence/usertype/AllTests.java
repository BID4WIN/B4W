package com.bid4win.persistence.usertype;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.core.Bid4WinCoreTester;

/**
 * Classe de test du package com.bid4win.commons.persistence.usertype complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.usertype.account.AllTests.class,
                     com.bid4win.persistence.usertype.auction.AllTests.class,
                     com.bid4win.persistence.usertype.image.AllTests.class,
                     com.bid4win.persistence.usertype.locale.AllTests.class,
                     com.bid4win.persistence.usertype.price.AllTests.class})
public class AllTests extends Bid4WinCoreTester
{
  // Definition dans la super classe
}