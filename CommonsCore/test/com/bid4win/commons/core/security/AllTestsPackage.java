package com.bid4win.commons.core.security;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.commons.core.Bid4WinCoreTester;

/**
 * Classe de test du package com.bid4win.commons.core.security seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.core.security.IdGeneratorTest.class,
                     com.bid4win.commons.core.security.ObjectProtectionTest.class,
                     com.bid4win.commons.core.security.ObjectProtectorTest.class})
public class AllTestsPackage extends Bid4WinCoreTester
{
  // Pas de test spécifique
}