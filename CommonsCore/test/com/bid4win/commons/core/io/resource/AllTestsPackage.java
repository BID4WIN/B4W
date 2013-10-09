package com.bid4win.commons.core.io.resource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.core.Bid4WinCoreTester;

/**
 * Classe de test du package com.bid4win.commons.core.io.resource seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStoreTest.class,
                     com.bid4win.commons.core.io.resource.Bid4WinFileResourceStoreTest.class})
public class AllTestsPackage extends Bid4WinCoreTester
{
  // Pas de test spécifique
}