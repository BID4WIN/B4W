package com.bid4win.commons.persistence.dao.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.Bid4WinDaoTester;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <PROPERTY_ROOT> TODO A COMMENTER<BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractDaoTester<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                                PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                                ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinDaoTester<PROPERTY, Integer, ACCOUNT, GENERATOR>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IPropertyAbstractDaoStub<PROPERTY, PROPERTY_ROOT> getDao();
  /**
   * Getter du DAO de la racine des propriétés
   * @return Le DAO de la racine des propriétés
   */
  protected abstract IPropertyRootAbstractDaoStub<PROPERTY_ROOT, PROPERTY> getRootDao();

  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract PROPERTY createProperty(String key, String value) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @param property TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected abstract PROPERTY createProperty(String key, String value, PROPERTY property)
            throws UserException, ModelArgumentException;
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected abstract PROPERTY createProperty(String key, String value, PROPERTY_ROOT root)
            throws UserException,  ModelArgumentException;

  /**
   * Test of add(PROPERTY), of class PropertyAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAdd_PROPERTY() throws Bid4WinException
  {
    try
    {
      this.add(this.createProperty("KEY", "VALUE"));
      fail("Should fail if neither parent nor root could be found");
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable th)
    {
      th.printStackTrace();
    }
    PROPERTY_ROOT root = this.getRoot();
    PROPERTY a1 = this.createProperty("fr", "value a1", root);
    PROPERTY b1 = this.createProperty("b1", "value b1", a1);
    PROPERTY b2 = this.createProperty("b2", "value b2", a1);
    PROPERTY c1 = this.createProperty("c1", "value c1", b2);

    try
    {
      this.add(a1);
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable th)
    {
      th.printStackTrace();
      fail("Should not fail");
    }

    PROPERTY_ROOT result_root = this.getRoot();
    assertEquals("Wrong version", root.getVersion() + 1, result_root.getVersion());
    assertTrue("Wrong result", result_root.same(root));

    PROPERTY result_a1 = this.getById(a1.getId());
    assertTrue("Wrong ID", 0 < a1.getId());
    assertEquals("Wrong version", 0, a1.getVersion());
    assertFalse("Wrong result", result_a1.identical(a1));
    assertTrue("Wrong result", result_a1.identical(a1, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_a1.same(a1));
    result_a1 = result_root.getProperty(a1.getKey());

    PROPERTY result_b1 = this.getById(b1.getId());
    assertTrue("Wrong ID", 0 < b1.getId());
    assertEquals("Wrong version", 0, b1.getVersion());
    assertFalse("Wrong result", result_b1.identical(b1));
    assertTrue("Wrong result", result_b1.identical(b1, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_b1.same(b1));
    result_b1 = result_a1.getProperty(b1.getKey());

    PROPERTY result_b2 = this.getById(b2.getId());
    assertTrue("Wrong ID", 0 < b2.getId());
    assertEquals("Wrong version", 0, b2.getVersion());
    assertFalse("Wrong result", result_b2.identical(b2));
    assertTrue("Wrong result", result_b2.identical(b2, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_b2.same(b2));
    result_b2 = result_a1.getProperty(b2.getKey());

    PROPERTY result_c1 = this.getById(c1.getId());
    assertTrue("Wrong ID", 0 < c1.getId());
    assertEquals("Wrong version", 0, c1.getVersion());
    assertFalse("Wrong result", result_c1.identical(c1));
    assertTrue("Wrong result", result_c1.identical(c1, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_c1.same(c1));
    result_c1 = result_b2.getProperty(c1.getKey());

    PROPERTY b3 = this.createProperty("b3", "value b3", result_a1);
    PROPERTY c2 = this.createProperty("c2", "value c2", b3);
    PROPERTY c3 = this.createProperty("c3", "value c3", b3);

    try
    {
      this.add(b3);
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable th)
    {
      th.printStackTrace();
      fail("Should not fail");
    }
    PROPERTY_ROOT result_rootBis = this.getRoot();
    assertEquals("Wrong version", result_root.getVersion() + 1, result_rootBis.getVersion());
    assertTrue("Wrong result", result_rootBis.same(result_root));

    PROPERTY result_a1Bis = this.getById(a1.getId());
    assertEquals("Wrong version", result_a1.getVersion() + 1, result_a1Bis.getVersion());
    assertTrue("Wrong result", result_a1Bis.same(result_a1));

    PROPERTY result_b1Bis = this.getById(b1.getId());
    assertTrue("Wrong result", result_b1Bis.identical(result_b1, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_b1Bis.same(b1));

    PROPERTY result_b2Bis = this.getById(b2.getId());
    assertTrue("Wrong result", result_b2Bis.identical(result_b2, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_b2Bis.same(b2));

    PROPERTY result_c1Bis = this.getById(c1.getId());
    assertTrue("Wrong result", result_c1Bis.identical(result_c1, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_c1Bis.same(c1));

    PROPERTY result_b3 = this.getById(b3.getId());
    assertTrue("Wrong ID", 0 < b3.getId());
    assertEquals("Wrong version", 0, b3.getVersion());
    assertFalse("Wrong result", result_b3.identical(b3));
    assertTrue("Wrong result", result_b3.identical(b3, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_b3.same(b3));

    PROPERTY result_c2 = this.getById(c2.getId());
    assertTrue("Wrong ID", 0 < c2.getId());
    assertEquals("Wrong version", 0, c2.getVersion());
    assertFalse("Wrong result", result_c2.identical(c2));
    assertTrue("Wrong result", result_c2.identical(c2, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_c2.same(c2));

    PROPERTY result_c3 = this.getById(c3.getId());
    assertTrue("Wrong ID", 0 < c3.getId());
    assertEquals("Wrong version", 0, c3.getVersion());
    assertFalse("Wrong result", result_c3.identical(c3));
    assertTrue("Wrong result", result_c3.identical(c3, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_c3.same(c3));
  }

  /**
   * Test of update(PROPERTY), of class PropertyAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdate_PROPERTY() throws Bid4WinException
  {
    // Ajoute une propriété pour le test
    PROPERTY_ROOT root = this.getRoot();
    PROPERTY a = this.createProperty("fr", "value a", root);
    this.add(a);
    // Récupère la propriété ajoutée
    PROPERTY a_result = this.getById(a.getId());

    PROPERTY_ROOT result_root = this.getRoot();
    assertEquals("Wrong version", root.getVersion() + 1, result_root.getVersion());
    assertTrue("Wrong result", result_root.same(root));

    // Vérifie la propriété ajoutée
    assertTrue("Wrong ID", 0 < a_result.getId());
    assertEquals("Wrong version", 0, a_result.getVersion());
    assertFalse("Wrong result", a_result.identical(a));
    assertTrue("Wrong result", a_result.identical(a, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", a_result.same(a));

    root = this.getRoot();
    a = root.getProperty(a.getKey());
    // Modifie la propriété
    a.defineValue("new value a");
    a = this.update(a);
    // Récupère la propriété ajoutée
    a_result = this.getById(a.getId());

    result_root = this.getRoot();
    assertEquals("Wrong version", root.getVersion() + 1, result_root.getVersion());
    assertTrue("Wrong result", result_root.same(root));

    // Effectue les tests
    assertTrue("Wrong ID", 0 < a_result.getId());
    assertEquals("Wrong version", 1, a_result.getVersion());
    assertTrue("Wrong result", a_result.identical(a));
    assertEquals("Wrong property set size", 0, a_result.getPropertyNb());

    root = this.getRoot();
    a = root.getProperty(a.getKey());
    // Ajoute une propriété à la propriété
    PROPERTY b = this.createProperty("b", "value b", a);
    this.createProperty("c1", "value c1", b);
    this.createProperty("c2", "value c2", b);
    this.add(b);

    result_root = this.getRoot();
    assertEquals("Wrong version", root.getVersion() + 1, result_root.getVersion());
    // Récupère la propriété modifiée
    a_result = this.getById(a.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < a_result.getId());
    assertEquals("Wrong version", 2, a_result.getVersion());
    assertFalse("Wrong result", a_result.identical(a, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", a_result.same(a));
    assertEquals("Wrong property set size", 1, a_result.getPropertyNb());
    assertTrue("Wrong property set", a_result.getProperty(b.getKey()).same(b, new Bid4WinList<Bid4WinRelationNode>()));
    // Récupère la propriété ajoutée
    PROPERTY b_result = this.findOneByKey(b.getKey());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < b_result.getId());
    assertEquals("Wrong version", 0, b_result.getVersion());
    assertFalse("Wrong result", b_result.identical(b));
    assertTrue("Wrong result", b_result.identical(b, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", b_result.same(b));
    assertEquals("Wrong property set size", 2, b_result.getPropertyNb());
    for(PROPERTY c : b.getPropertySet())
    {
      // Récupère la propriété ajoutée
      PROPERTY c_result = this.findOneByKey(c.getKey());
      // Effectue les tests
      assertTrue("Wrong ID", 0 < c_result.getId());
      assertEquals("Wrong version", 0, c_result.getVersion());
      assertFalse("Wrong result", c_result.identical(c));
      assertTrue("Wrong result", c_result.identical(c, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", c_result.same(c));
      assertEquals("Wrong property set size", 0, c_result.getPropertyNb());
    }
    root = this.getRoot();
    a = root.getProperty(a.getKey());

    // Modifie la propriété
    a.defineValue("new value a again");
    a = this.update(a);

    result_root = this.getRoot();
    assertEquals("Wrong version", root.getVersion() + 1, result_root.getVersion());
    assertTrue("Wrong result", result_root.getProperty(a.getKey()).same(a));

    // Récupère la propriété modifiée
    a_result = this.getById(a.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < a_result.getId());
    assertEquals("Wrong version", 3, a_result.getVersion());
    assertTrue("Wrong result", a_result.identical(a));
    assertEquals("Wrong property set size", 1, a_result.getPropertyNb());
    assertTrue("Wrong property set", a_result.getProperty(b.getKey()).same(b, new Bid4WinList<Bid4WinRelationNode>()));

    // Récupère la propriété non modifiée
    b_result = this.findOneByKey(b.getKey());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < b_result.getId());
    assertEquals("Wrong version", 0, b_result.getVersion());
    assertFalse("Wrong result", b_result.identical(b));
    assertTrue("Wrong result", b_result.identical(b, new Bid4WinList<Bid4WinRelationNode>()));
    assertFalse("Wrong result", b_result.same(b));
    assertEquals("Wrong property set size", 2, b_result.getPropertyNb());
  }

  /**
   * Test of remove(PROPERTY), of class PropertyAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemove_PROPERTY() throws Bid4WinException
  {
    // Ajoute une propriété pour le test
    PROPERTY a = this.createProperty("fr", "value a", this.getRoot());
    a = this.add(a);

    // Supprime la propriété
    this.removeById(a.getId());
    PROPERTY_ROOT root = this.getRoot();
    assertEquals("Wrong version", 2, root.getVersion());
    try
    {
      // Vérifie la propriété supprimée
      this.getById(a.getId());
      fail("Property should have been removed");
    }
    catch(Exception ex)
    {
      System.out.println(ex.getMessage());
    }

    // Ajoute une propriété pour le test
    a = this.createProperty("fr", "value a", this.getRoot());
    PROPERTY b = this.createProperty("b", "value b", a);
    this.createProperty("c1", "value c1", b);
    this.createProperty("c2", "value c2", b);
    this.add(a);
    // Récupère la propriété ajoutée
    PROPERTY result_a = this.getById(a.getId());
    // Vérifie la propriété ajoutée
    assertTrue("Wrong ID", 0 < result_a.getId());
    assertEquals("Wrong version", 0, result_a.getVersion());
    assertFalse("Wrong result", result_a.identical(a));
    assertTrue("Wrong result", result_a.identical(a, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result_a.same(a));
    assertNull("Should not have parent property", result_a.getProperty());
    assertEquals("Wrong property set size", 1, result_a.getPropertyNb());

    // Supprime la sous-propriété
    this.removeById(b.getId());
    // Récupère la propriété modifiée
    result_a = this.getById(a.getId());
    // Vérifie la propriété modifiée
    assertTrue("Wrong ID", 0 < result_a.getId());
    assertEquals("Wrong version", 1, result_a.getVersion());
    assertNull("Should not have parent property", result_a.getProperty());
    assertEquals("Wrong property set size", 0, result_a.getPropertyNb());
    try
    {
      // Vérifie la propriété supprimée
      this.getById(b.getId());
      fail("Property should have been removed");
    }
    catch(Exception ex)
    {
      System.out.println(ex.getMessage());
    }
    for(PROPERTY c : b.getPropertySet())
    {
      try
      {
        // Vérifie la propriété supprimée
        this.getById(c.getId());
        fail("Property should have been removed");
      }
      catch(Exception ex)
      {
        System.out.println(ex.getMessage());
      }
    }
    // Récupère la version de la racine
    int version = this.getRoot().getVersion();

    // Supprime la sous-propriété
    this.removeById(a.getId());
    // Récupère la propriété racine modifiée
    PROPERTY_ROOT root_result = this.getRoot();
    // Vérifie la propriété racine modifiée
    assertEquals("Wrong version", version + 1, root_result.getVersion());
    assertNull("Should contain removed property", root_result.getProperty(a.getKey()));
    try
    {
      // Vérifie la propriété supprimée
      this.getById(a.getId());
      fail("Property should have been removed");
    }
    catch(Exception ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test setup method
   * @throws Exception Issue not expected during test setup
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    try
    {
      this.removeAllRoot();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      throw ex;
    }
    super.setUp();
  }
  /**
   * Test teardown method
   * @throws Exception Issue not expected during test setup
   */
  @Override
  @Before
  public void tearDown() throws Exception
  {
    try
    {
      this.removeAllRoot();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      throw ex;
    }
    super.tearDown();
  }

  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected PROPERTY findOneByKey(String key) throws PersistenceException
  {
    return this.getDao().findOneByKey(key);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected PROPERTY_ROOT getRoot() throws PersistenceException
  {
    return this.getRootDao().getRoot();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected List<PROPERTY_ROOT> removeAllRoot() throws PersistenceException
  {
    return this.getRootDao().removeAll();
  }
}
