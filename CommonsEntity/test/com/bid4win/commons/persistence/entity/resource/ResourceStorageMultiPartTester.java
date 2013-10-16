package com.bid4win.commons.persistence.entity.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.resource.store.ResourcePart;

/**
 * Classe de test d'un stockage de ressource divisé en portions<BR>
 * <BR>
 * @param <STORAGE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceStorageMultiPartTester<STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, USAGE, PART_TYPE, PART>,
                                                     TYPE extends ResourceType<TYPE>,
                                                     USAGE extends ResourceUsageMultiPart<USAGE, TYPE, STORAGE, PART_TYPE, PART>,
                                                     PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                     PART extends ResourcePart<PART, TYPE, PART_TYPE>,
                                                     ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                     GENERATOR extends EntityGenerator<ACCOUNT>>
       extends ResourceStorageTester<STORAGE, TYPE, USAGE, ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract PART_TYPE getPartType();

  /**
   * Test of addPartType(PART_TYPE), of class ResourceStorageMultiPart.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddPartType_PARTTYPE() throws Bid4WinException
  {
    STORAGE storage = this.createResource("1", "2", this.getType());
    this.createUsage("usagePath1", "usageName1", storage);
    this.createUsage("usagePath2", "usageName2", storage);

    assertTrue(storage.hasPartType(storage.getPartTypeDefault()));
    assertFalse(storage.hasPartType(this.getPartType()));
    for(USAGE usage : storage.getUsages())
    {
      assertTrue(usage.hasPartType(storage.getPartTypeDefault()));
      assertFalse(usage.hasPartType(this.getPartType()));
    }

    storage.addPartType(this.getPartType());
    assertTrue(storage.hasPartType(storage.getPartTypeDefault()));
    assertTrue(storage.hasPartType(this.getPartType()));
    for(USAGE usage : storage.getUsages())
    {
      assertTrue(usage.hasPartType(storage.getPartTypeDefault()));
      assertTrue(usage.hasPartType(this.getPartType()));
    }

    try
    {
      storage.addPartType(null);
      fail("Should fail with null part type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue(storage.hasPartType(storage.getPartTypeDefault()));
      assertTrue(storage.hasPartType(this.getPartType()));
      for(USAGE usage : storage.getUsages())
      {
        assertTrue(usage.hasPartType(storage.getPartTypeDefault()));
        assertTrue(usage.hasPartType(this.getPartType()));
      }
    }

    try
    {
      storage.addPartType(this.getPartType());
      fail("Should fail with already referenced part type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue(storage.hasPartType(storage.getPartTypeDefault()));
      assertTrue(storage.hasPartType(this.getPartType()));
      for(USAGE usage : storage.getUsages())
      {
        assertTrue(usage.hasPartType(storage.getPartTypeDefault()));
        assertTrue(usage.hasPartType(this.getPartType()));
      }
    }
  }
  /**
   * Test of removePartType(PART_TYPE), of class ResourceStorageMultiPart.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemovePartType_PARTTYPE() throws Bid4WinException
  {
    STORAGE storage = this.createResource("1", "2", this.getType());
    this.createUsage("usagePath1", "usageName1", storage);
    this.createUsage("usagePath2", "usageName2", storage);
    storage.addPartType(this.getPartType());

    try
    {
      storage.removePartType(null);
      fail("Should fail with null part type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue(storage.hasPartType(storage.getPartTypeDefault()));
      assertTrue(storage.hasPartType(this.getPartType()));
      for(USAGE usage : storage.getUsages())
      {
        assertTrue(usage.hasPartType(storage.getPartTypeDefault()));
        assertTrue(usage.hasPartType(this.getPartType()));
      }
    }

    try
    {
      storage.removePartType(storage.getPartTypeDefault());
      fail("Should fail with default part type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue(storage.hasPartType(storage.getPartTypeDefault()));
      assertTrue(storage.hasPartType(this.getPartType()));
      for(USAGE usage : storage.getUsages())
      {
        assertTrue(usage.hasPartType(storage.getPartTypeDefault()));
        assertTrue(usage.hasPartType(this.getPartType()));
      }
    }

    storage.removePartType(this.getPartType());
    assertTrue(storage.hasPartType(storage.getPartTypeDefault()));
    assertFalse(storage.hasPartType(this.getPartType()));
    for(USAGE usage : storage.getUsages())
    {
      assertTrue(usage.hasPartType(storage.getPartTypeDefault()));
      assertFalse(usage.hasPartType(this.getPartType()));
    }

    try
    {
      storage.removePartType(this.getPartType());
      fail("Should fail with not referenced part type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue(storage.hasPartType(storage.getPartTypeDefault()));
      assertFalse(storage.hasPartType(this.getPartType()));
      for(USAGE usage : storage.getUsages())
      {
        assertTrue(usage.hasPartType(storage.getPartTypeDefault()));
        assertFalse(usage.hasPartType(this.getPartType()));
      }
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorageTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    super.testCheckProtection();

    this.startProtection();
    STORAGE storage = this.createResource("path", "name", this.getType());
    String protectionId = this.startProtection();
    USAGE usage = this.createUsage("usagePath", "usageName", storage);
    this.stopProtection();
    this.stopProtection();
    this.startProtection(protectionId);

    try
    {
      storage.addPartType(this.getPartType());
      fail("Should fail with protected storage");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong part types", 1, storage.getPartTypes().size());
    assertFalse("Wrong part types", storage.getPartTypes().contains(this.getPartType()));
    assertEquals("Wrong part types", 1, usage.getPartTypes().size());
    assertFalse("Wrong part types", usage.getPartTypes().contains(this.getPartType()));
  }
}
