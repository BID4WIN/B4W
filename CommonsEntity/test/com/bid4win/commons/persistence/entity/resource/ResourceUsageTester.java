package com.bid4win.commons.persistence.entity.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 * Classe de test d'une utilisation de ressource<BR>
 * <BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <STORAGE> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceUsageTester<USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                          TYPE extends ResourceType<TYPE>,
                                          STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                          ACCOUNT extends AccountAbstract<ACCOUNT>,
                                          GENERATOR extends EntityGenerator<ACCOUNT>>
        extends ResourceTester<USAGE, TYPE, ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#createResource(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected USAGE createResource(String path, String name, TYPE type) throws UserException
  {
    try
    {
      USAGE usage = this.createResource(path, name, this.createStorage(type));
      usage.unlinkFromStorage();
      return usage;
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param path TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param storage TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected abstract USAGE createResource(String path, String name, STORAGE storage) throws UserException, ModelArgumentException;
  /**
   *
   * TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract STORAGE createStorage(TYPE type) throws UserException;

  /**
   * Test of defineType(TYPE), of class ResourceUsage.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#testDefineType_TYPE()
   */
  @Override
  @Test
  public void testDefineType_TYPE() throws Bid4WinException
  {
    super.testDefineType_TYPE();

    TYPE type = this.getType();
    STORAGE storage = this.createStorage(type);
    USAGE usage = this.createResource("usagePath1", "usageName1", storage);
    assertEquals("Bad resource type", type, usage.getType());

    usage.defineType(this.getDefaultType());
    assertEquals("Bad resource type", type, usage.getType());

    usage.unlinkFromStorage();
    usage.defineType(this.getDefaultType());
    assertEquals("Bad resource type", this.getDefaultType(), usage.getType());
  }
  /**
   * Test of ResourceUsage(...) method, of class ResourceUsage.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    String path = "path";
    String name = "name";
    TYPE type = this.getType();
    STORAGE storage = this.createStorage(type);
    try
    {
      USAGE usage = this.createResource(path, name, storage);
      assertEquals("Bad resource path", path, usage.getPath());
      assertEquals("Bad resource name", name, usage.getName());
      assertEquals("Bad resource type", type, usage.getType());
      assertTrue("Bad resource storage", storage == usage.getStorage());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      this.createResource(path, name, (STORAGE)null);
      fail("Instanciation with null storage should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of unlinkFromStorage(), of class ResourceUsage.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFromStorage_0args() throws Bid4WinException
  {
    TYPE type = this.getType();
    STORAGE storage = this.createStorage(type);
    USAGE usage = this.createResource("path", "name", storage);

    assertTrue("Wrong storage", storage == usage.getStorage());
    assertTrue("Wrong storage link", storage.getUsageList().contains(usage));
    try
    {
      STORAGE result = usage.unlinkFromStorage();
      assertNull("Wrong storage", usage.getStorage());
      assertFalse("Wrong storage link", storage.getUsageList().contains(usage));
      assertTrue("Wrong result", storage == result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("First unlink should not fail: " + ex.getMessage());
    }
    try
    {
      usage.unlinkFromStorage();
      fail("Second unlink should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of getRealName(), of class ResourceUsage.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#testGetRealName_0args()
   */
  @Override
  @Test
  public void testGetRealName_0args() throws Bid4WinException
  {
    String name = "Name";
    TYPE type = this.getType();
    STORAGE storage = this.createStorage(type);
    USAGE usage = this.createResource("path", name, storage);
    assertEquals("Wrong real name",
                 name + "_" + usage.getId() + "_" + usage.getUpdateForce(),
                 usage.getRealName());
  }
}
