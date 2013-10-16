package com.bid4win.commons.persistence.entity.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 * Classe de test d'un stockage de ressource<BR>
 * <BR>
 * @param <STORAGE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceStorageTester<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                            TYPE extends ResourceType<TYPE>,
                                            USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                            ACCOUNT extends AccountAbstract<ACCOUNT>,
                                            GENERATOR extends EntityGenerator<ACCOUNT>>
       extends ResourceTester<STORAGE, TYPE, ACCOUNT, GENERATOR>
{
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
  protected abstract USAGE createUsage(String path, String name, STORAGE storage) throws UserException, ModelArgumentException;

  /**
   * Test of defineType(TYPE), of class ResourceStorage.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#testDefineType_TYPE()
   */
  @Override
  @Test
  public void testDefineType_TYPE() throws Bid4WinException
  {
    super.testDefineType_TYPE();

    TYPE type = this.getType();
    STORAGE storage = this.createResource("path", "name", type);
    Bid4WinList<USAGE> list = new Bid4WinList<USAGE>();
    list.add(this.createUsage("usagePath1", "usageName1", storage));
    list.add(this.createUsage("usagePath2", "usageName2", storage));

    assertEquals("Bad resource type", type, storage.getType());
    for(USAGE usage : list)
    {
      assertEquals("Bad resource type", type, usage.getType());
    }

    type = this.getDefaultType();
    storage.defineType(type);
    assertEquals("Bad resource type", type, storage.getType());
    for(USAGE usage : list)
    {
      assertEquals("Bad resource type", type, usage.getType());
    }
  }
  /**
   * Test of sameInternal(ResourceStorage, boolean), of class ResourceStorage.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Override
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    super.testSameInternal_CLASS_boolean();

    TYPE type = this.getType();
    STORAGE storage1 = this.createResource("path", "name", type);
    STORAGE storage2 = this.createResource(storage1.getPath(), storage1.getName(), type);

    this.checkSame(storage1, storage2);
    this.checkSame(storage2, storage1);
    this.checkIdentical(storage1, storage2);
    this.checkIdentical(storage2, storage1);

    USAGE usage1 = this.createUsage("usagePath1", "usageName1", storage1);
    USAGE usage2 = this.createUsage("usagePath2", "usageName2", storage2);
    this.checkNotSame(storage1, storage2);
    this.checkNotSame(storage2, storage1);
    this.checkNotIdentical(storage1, storage2);
    this.checkNotIdentical(storage2, storage1);

    this.createUsage(usage1.getPath(), usage1.getName(), storage2);
    this.checkNotSame(storage1, storage2);
    this.checkNotSame(storage2, storage1);
    this.checkNotIdentical(storage1, storage2);
    this.checkNotIdentical(storage2, storage1);

    this.createUsage(usage2.getPath(), usage2.getName(), storage1);
    this.checkSame(storage1, storage2);
    this.checkSame(storage2, storage1);
    this.checkIdentical(storage1, storage2);
    this.checkIdentical(storage2, storage1);
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    super.testCheckProtection();

    this.startProtection();
    STORAGE storage = this.createResource("path", "name", this.getType());
    this.stopProtection();
    try
    {
      this.createUsage("usagePath", "usageName", storage);
      fail("Should fail with protected storage");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong usages", 0, storage.getUsages().size());
  }
}
