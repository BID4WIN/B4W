package com.bid4win.commons.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.core seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.core.Bid4WinDateTest.class,
                     com.bid4win.commons.core.Bid4WinObjectTest.class,
                     com.bid4win.commons.core.Bid4WinObjectTypeGroupTest.class,
                     com.bid4win.commons.core.Bid4WinObjectTypeTest.class,
                     com.bid4win.commons.core.UtilBooleanTest.class,
                     com.bid4win.commons.core.UtilNumberTest.class,
                     com.bid4win.commons.core.UtilObjectTest.class,
                     com.bid4win.commons.core.UtilStringTest.class,
                     com.bid4win.commons.core.UtilSystemTest.class})
public class AllTestsPackage extends Bid4WinCoreTester
{
  // Pas de test spécifique
}