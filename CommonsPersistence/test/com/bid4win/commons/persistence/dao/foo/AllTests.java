package com.bid4win.commons.persistence.dao.foo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.dao.foo<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.dao.foo.AllTestsPackage.class,
                     com.bid4win.commons.persistence.dao.foo.cached.AllTests.class,
                     com.bid4win.commons.persistence.dao.foo.not_cached.AllTests.class})
public class AllTests
{
  // Pas de d�finition sp�cifique
}