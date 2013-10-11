package com.bid4win.commons.persistence.dao.foo.cached;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.dao.foo.not_cached seulement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.dao.foo.cached.FooCachedParent1DaoSpringTest.class,
                     com.bid4win.commons.persistence.dao.foo.cached.FooCachedParent2DaoSpringTest.class,
                     com.bid4win.commons.persistence.dao.foo.cached.FooCachedParent3DaoSpringTest.class,
                     com.bid4win.commons.persistence.dao.foo.cached.FooCachedStubDaoSpringTest.class,
                     com.bid4win.commons.persistence.dao.foo.cached.FooCachedTwinDaoSpringTest.class})
public class AllTestsPackage
{
  // Pas de définition spécifique
}