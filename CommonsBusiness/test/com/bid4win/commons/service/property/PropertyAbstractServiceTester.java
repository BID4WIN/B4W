package com.bid4win.commons.service.property;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.property.IPropertyAbstractDaoStub;
import com.bid4win.commons.persistence.dao.property.IPropertyRootAbstractDaoStub;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityCollectionComparator;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract_Relations;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.service.Bid4WinServiceTester;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <PROPERTY_ROOT> TODO A COMMENTER<BR>
 * @param <SESSION> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <CONNECTION> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractServiceTester<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                                    PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                                    SESSION extends SessionDataAbstract<ACCOUNT, CONNECTION>,
                                                    ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                    CONNECTION extends ConnectionAbstract<CONNECTION, ?, ACCOUNT>,
                                                    GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinServiceTester<PROPERTY_ROOT, Integer, SESSION, ACCOUNT, CONNECTION, GENERATOR>
{
  /**
   * Test of getProperty(String), of class service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetProperty_String() throws Bid4WinException
  {
    String key = "a.b.c.d";
    PROPERTY property = this.addProperty(key, "toto");
    this.addProperty(key + ".e", "tata");
    // Test avec une propriété existante
    try
    {
      PROPERTY result = this.getService().getProperty(property.getFullKey());
      this.checkIdenticalRelationNone(property, result);
      assertTrue("Wrong result", property.same(
          result, new Bid4WinList<Bid4WinRelationNode>(PropertyAbstract_Relations.NODE_ROOT,
                                                       PropertyAbstract_Relations.NODE_PROPERTY)));
      try
      {
        result.getPropertyNb();
      }
      catch(Throwable th)
      {
        fail("Should not fail");
      }
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    // Test avec une propriété inexistante
    try
    {
      this.getService().getProperty(key + "1");
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Wrong error: " + ex.getMessage());
    }
  }

  /**
   * Test of getProperties(), of class service.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetProperties_0args() throws Bid4WinException
  {
    String key = "a.b.c.d";
    this.addProperty(key, "toto");
    this.addProperty(key + ".e", "tata");
    this.addProperty(key + ".d2", "titi");
    this.addProperty("a2", "test");

    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("getProperties");

    try
    {
      Bid4WinCollection<PROPERTY> result = this.getService().getProperties();
      assertTrue("Wrong result",
                 Bid4WinEntityCollectionComparator.getInstanceEntityCollection().identical(
                     this.getRoot().getProperties(), result));
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of load(Class<>, Bid4WinSet<>), of class PropertyAbstractManager.
   * @throws Bid4WinException Issue not expected during this test
   */
 /* @Test
  public void testLoad_ClassOf_Bid4WinSetOf() throws Bid4WinException
  {
    String key = "a.b.c.d";
    PROPERTY a = this.addProperty(key, "toto");
    try
    {
      Bid4WinSet<Integer> idSet = new Bid4WinSet<Integer>(2);
      Bid4WinMap<Integer, PROPERTY_ROOT> resultMap =
          this.getService().load(this.getService().getPropertyRootClass(), idSet);
      assertEquals("Should be empty", 0, resultMap.size());

      idSet.add(this.getManager().getPropertyRootId() + 1);
      resultMap = this.getManager().load(this.getManager().getPropertyRootClass(), idSet);
      assertEquals("Should be empty", 0, resultMap.size());

      idSet.add(this.getManager().getPropertyRootId());
      resultMap = this.getManager().load(this.getManager().getPropertyRootClass(), idSet);
      assertEquals("Should not be empty", 1, resultMap.size());
      PROPERTY_ROOT result = resultMap.get(this.getManager().getPropertyRootId());
      assertNotNull("Wrong result", result);
      assertTrue("Wrong result", result.identical(this.getRoot()));
      assertTrue("Wrong result", a.same(result.getProperty(a.getFullKey())));
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }*/

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected PROPERTY_ROOT getRoot() throws PersistenceException
  {
    return this.getDao().getRoot();
  }
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException  TODO A COMMENTER
   */
  protected PROPERTY addProperty(String key, String value) throws Bid4WinException
  {
    return this.getPropertyDao().add(this.createProperty(key, value, this.getRoot()));
  }
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException  TODO A COMMENTER
   */
  protected PROPERTY getProperty(String key) throws Bid4WinException
  {
    return this.getRoot().getProperty(key);
  }
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected PROPERTY createProperty(String key, String value, PROPERTY_ROOT root) throws Bid4WinException
  {
    return root.addProperty(key, value);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#getService()
   */
  @Override
  public abstract PropertyAbstractService_<PROPERTY, PROPERTY_ROOT, SESSION, ACCOUNT, ?> getService();

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IPropertyRootAbstractDaoStub<PROPERTY_ROOT, PROPERTY> getDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IPropertyAbstractDaoStub<PROPERTY, PROPERTY_ROOT> getPropertyDao();
}
