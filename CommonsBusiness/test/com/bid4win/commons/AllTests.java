package com.bid4win.commons;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons complet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.manager.AllTests.class,
                     com.bid4win.commons.service.AllTests.class})
public class AllTests
{
  // Pas de d�finition sp�cifique
}