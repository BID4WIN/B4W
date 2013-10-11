package com.bid4win.persistence.usertype.price;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.core.Bid4WinCoreTester;

/**
 * Classe de test du package com.bid4win.persistence.usertype.price complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.usertype.price.AllTestsPackage.class,
                     com.bid4win.persistence.usertype.price.collection.AllTests.class})
public class AllTests extends Bid4WinCoreTester
{
  // Definition dans la super classe
}