package com.bid4win.commons.core.collection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.core.Bid4WinCoreTester;

/**
 * Classe de test du package com.bid4win.commons.core.collection seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.core.collection.Bid4WinSameTypeMapTest.class,
                     com.bid4win.commons.core.collection.Bid4WinSetTest.class,
                     com.bid4win.commons.core.collection.Bid4WinSortedPropertiesTest.class,
                     com.bid4win.commons.core.collection.Bid4WinStringRecursiveMapTest.class})
public class AllTestsPackage extends Bid4WinCoreTester
{
  // Pas de test spécifique
}