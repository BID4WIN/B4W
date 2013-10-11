package com.bid4win.commons.caching;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinEntityCacheTest extends Bid4WinCoreTester
{
  /**
   * Test of cache(Class, ENTITY) method, of class Bid4WinEntityCache.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testCache_Class_ENTITY()
  {
    Bid4WinEntityCache cache = new Bid4WinEntityCache();

    CachedEntity1 entity1_1 = new CachedEntity1("1");
    CachedEntity1 entity1_2 = new CachedEntity1("2");
    CachedEntity1 entity1_3 = new CachedEntity1("3");
    CachedEntity2 entity2_1 = new CachedEntity2(entity1_1.getId());
    CachedEntity2 entity2_2 = new CachedEntity2(entity1_2.getId());
    CachedEntity2 entity2_3 = new CachedEntity2(entity1_3.getId());
    try
    {
      cache.cache(CachedEntity.class, entity1_1);
      fail("Should fail with wrong class definition");
    }
    catch(IllegalArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    CachedEntity1 result1 = cache.cache(CachedEntity1.class, entity1_1);
    assertTrue("Bad result", entity1_1 == result1);
    result1 = cache.cache(CachedEntity1.class, entity1_2);
    assertTrue("Bad result", entity1_2 == result1);
    result1 = cache.cache(CachedEntity1.class, entity1_3);
    assertTrue("Bad result", entity1_3 == result1);
    CachedEntity2 result2 = cache.cache(CachedEntity2.class, entity2_1);
    assertTrue("Bad result", entity2_1 == result2);
    result2 = cache.cache(CachedEntity2.class, entity2_2);
    assertTrue("Bad result", entity2_2 == result2);
    result2 = cache.cache(CachedEntity2.class, entity2_3);
    assertTrue("Bad result", entity2_3 == result2);

    assertTrue("Bad result", entity1_1 == cache.get(CachedEntity1.class, entity1_1.getId()));
    assertTrue("Bad result", entity1_2 == cache.get(CachedEntity1.class, entity1_2.getId()));
    assertTrue("Bad result", entity1_3 == cache.get(CachedEntity1.class, entity1_3.getId()));
    assertTrue("Bad result", entity2_1 == cache.get(CachedEntity2.class, entity2_1.getId()));
    assertTrue("Bad result", entity2_2 == cache.get(CachedEntity2.class, entity2_2.getId()));
    assertTrue("Bad result", entity2_3 == cache.get(CachedEntity2.class, entity2_3.getId()));

    CachedEntity2 entity2_3_bis = new CachedEntity2(entity2_3.getId());
    result2 = cache.cache(CachedEntity2.class, entity2_3_bis);
    assertTrue("Bad result", entity2_3 == result2);
    assertTrue("Bad result", entity2_3 == cache.get(CachedEntity2.class, entity2_3.getId()));

    entity2_3_bis.setVersion(entity2_3_bis.getVersion() + 1);
    result2 = cache.cache(CachedEntity2.class, entity2_3_bis);
    assertTrue("Bad result", entity2_3_bis == result2);
    assertTrue("Bad result", entity2_3_bis == cache.get(CachedEntity2.class, entity2_3.getId()));
  }

  /**
   * Test of uncache(Class, ID) method, of class Bid4WinEntityCache.
   */
  @Test
  public void testUncache_Class_ID()
  {
    Bid4WinEntityCache cache = new Bid4WinEntityCache();

    CachedEntity1 entity1_1 = new CachedEntity1("1");
    CachedEntity1 entity1_2 = new CachedEntity1("2");
    CachedEntity1 entity1_3 = new CachedEntity1("3");
    entity1_3.setVersion(2);
    CachedEntity2 entity2_1 = new CachedEntity2(entity1_1.getId());
    CachedEntity2 entity2_2 = new CachedEntity2(entity1_2.getId());
    CachedEntity2 entity2_3 = new CachedEntity2(entity1_3.getId());
    entity2_3.setVersion(entity1_3.getVersion() + 1);
    cache.cache(CachedEntity1.class, entity1_1);
    cache.cache(CachedEntity1.class, entity1_2);
    cache.cache(CachedEntity1.class, entity1_3);
    cache.cache(CachedEntity2.class, entity2_1);
    cache.cache(CachedEntity2.class, entity2_2);
    cache.cache(CachedEntity2.class, entity2_3);
    assertTrue("Bad result", entity1_1 == cache.get(CachedEntity1.class, entity1_1.getId()));
    assertTrue("Bad result", entity1_2 == cache.get(CachedEntity1.class, entity1_2.getId()));
    assertTrue("Bad result", entity1_3 == cache.get(CachedEntity1.class, entity1_3.getId()));
    assertTrue("Bad result", entity2_1 == cache.get(CachedEntity2.class, entity2_1.getId()));
    assertTrue("Bad result", entity2_2 == cache.get(CachedEntity2.class, entity2_2.getId()));
    assertTrue("Bad result", entity2_3 == cache.get(CachedEntity2.class, entity2_3.getId()));

    CachedEntity2 test = new CachedEntity2(entity2_3.getId());
    test.setVersion(entity2_3.getVersion() - 1);
    cache.uncache(CachedEntity2.class, test);
    assertTrue("Bad result", entity1_3 == cache.get(CachedEntity1.class, entity1_3.getId()));
    assertTrue("Bad result", entity2_3 == cache.get(CachedEntity2.class, entity2_3.getId()));

    test = new CachedEntity2(entity2_3.getId());
    test.setVersion(entity2_3.getVersion() + 1);
    cache.uncache(CachedEntity2.class, test);
    assertTrue("Bad result", entity1_3 == cache.get(CachedEntity1.class, entity1_3.getId()));
    assertTrue("Bad result", entity2_3 == cache.get(CachedEntity2.class, entity2_3.getId()));

    test = new CachedEntity2(entity2_3.getId());
    test.setVersion(entity2_3.getVersion());
    cache.uncache(CachedEntity2.class, test);
    assertTrue("Bad result", entity1_3 == cache.get(CachedEntity1.class, entity1_3.getId()));
    assertNull("Bad result", cache.get(CachedEntity2.class, entity2_3.getId()));

    cache.uncache(CachedEntity2.class, entity2_3);
    assertTrue("Bad result", entity1_3 == cache.get(CachedEntity1.class, entity1_3.getId()));
    assertNull("Bad result", cache.get(CachedEntity2.class, entity2_3.getId()));
  }
}
