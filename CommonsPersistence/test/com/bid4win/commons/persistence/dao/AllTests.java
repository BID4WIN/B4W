package com.bid4win.commons.persistence.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.dao complet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.dao.AllTestsButFoo.class,
                     com.bid4win.commons.persistence.dao.foo.AllTests.class})
public class AllTests
{
  // Pas de d�finition sp�cifique
}