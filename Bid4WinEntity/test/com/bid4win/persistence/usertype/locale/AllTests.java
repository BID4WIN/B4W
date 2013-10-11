package com.bid4win.persistence.usertype.locale;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.core.Bid4WinCoreTester;

/**
 * Classe de test du package com.bid4win.persistence.usertype.locale complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.usertype.locale.AllTestsPackage.class,
                     com.bid4win.persistence.usertype.locale.collection.AllTests.class,
                     com.bid4win.persistence.usertype.locale.inner.AllTests.class})
public class AllTests extends Bid4WinCoreTester
{
  // Definition dans la super classe
}