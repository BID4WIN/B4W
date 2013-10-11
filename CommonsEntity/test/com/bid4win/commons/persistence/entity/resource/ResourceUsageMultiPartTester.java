package com.bid4win.commons.persistence.entity.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.resource.store.ResourcePart;

/**
 * Classe de test d'une utilisation d'une utilisation de ressource<BR>
 * <BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <STORAGE> TODO A COMMENTER<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceUsageMultiPartTester<USAGE extends ResourceUsageMultiPart<USAGE, TYPE, STORAGE, PART_TYPE, PART>,
                                                   TYPE extends ResourceType<TYPE>,
                                                   STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, USAGE, PART_TYPE, PART>,
                                                   PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                   PART extends ResourcePart<PART, TYPE, PART_TYPE>,
                                                   ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                   GENERATOR extends EntityGenerator<ACCOUNT>>
       extends ResourceUsageTester<USAGE, TYPE, STORAGE, ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract PART_TYPE getPartType();

  /**
   * Test of  ResourceUsageMultiPart(...) method, of class ResourceUsageMultiPart.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    STORAGE storage = this.createStorage(this.getType());
    storage.addPartType(this.getPartType());

    String path = "path";
    String name = "name";
    try
    {
      USAGE usage = this.createResource(path, name, storage);
      assertEquals("Bad resource path", path, usage.getPath());
      assertEquals("Bad resource name", name, usage.getName());
      assertEquals("Bad resource type", storage.getType(), usage.getType());
      assertTrue("Bad resource storage", storage == usage.getStorage());
      assertEquals("Bad part type set", storage.getPartTypeSet(), usage.getPartTypeSet());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }
}
