package com.bid4win.commons.persistence.dao.foo.not_cached;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.dao.foo.not_cached seulement<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.dao.foo.not_cached.FooComplexDaoSpringTest.class,
                     com.bid4win.commons.persistence.dao.foo.not_cached.FooParent1DaoSpringTest.class,
                     com.bid4win.commons.persistence.dao.foo.not_cached.FooParent2DaoSpringTest.class,
                     com.bid4win.commons.persistence.dao.foo.not_cached.FooRecursiveDaoSpringTest.class,
                     com.bid4win.commons.persistence.dao.foo.not_cached.FooStubDaoSpringTest.class})
public class AllTestsPackage
{
  // Pas de d�finition sp�cifique
}