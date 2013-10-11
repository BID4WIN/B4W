package com.bid4win.commons.persistence.entity.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.resource.store.ResourcePart;

/**
 * Classe de test d'une portion de ressource<BR>
 * <BR>
 * @param <PART> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <STORAGE> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourcePartTester<PART extends ResourcePart<PART, TYPE, PART_TYPE>,
                                         TYPE extends ResourceType<TYPE>,
                                         PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                         STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, USAGE, PART_TYPE, PART>,
                                         USAGE extends ResourceUsageMultiPart<USAGE, TYPE, STORAGE, PART_TYPE, PART>,
                                         ACCOUNT extends AccountAbstract<ACCOUNT>,
                                         GENERATOR extends EntityGenerator<ACCOUNT>>
       extends ResourceTester<PART, TYPE, ACCOUNT, GENERATOR>
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
  protected PART createResource(String path, String name, TYPE type) throws UserException
  {
    STORAGE storage = this.createStorage(type);
    storage.definePath(path);
    storage.defineName(name);
    try
    {
      return this.createResource(storage, this.getPartType());
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param storage TODO A COMMENTER
   * @param partType TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected abstract PART createResource(STORAGE storage, PART_TYPE partType) throws UserException, ModelArgumentException;
  /**
   *
   * TODO A COMMENTER
   * @param usage TODO A COMMENTER
   * @param partType TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected abstract PART createResource(USAGE usage, PART_TYPE partType) throws UserException, ModelArgumentException;
  /**
   *
   * TODO A COMMENTER
   * @param part TODO A COMMENTER
   * @param partType TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected abstract PART createResource(PART part, PART_TYPE partType) throws UserException, ModelArgumentException;
  /**
   *
   * TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract STORAGE createStorage(TYPE type) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract USAGE createUsage(TYPE type) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract PART_TYPE getPartType();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  @SuppressWarnings("unchecked")
  protected PART_TYPE getDefaultPartType()
  {
    return (PART_TYPE)Bid4WinObjectType.getDefaultType(this.getPartType().getClass());
  }

  /**
   * Test of ResourcePart(...) method, of class ResourcePart.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    // Les test sont complètement ré-écrits

    STORAGE storage = this.createStorage(this.getType());
    try
    {
      PART part = this.createResource(storage, this.getPartType());
      assertEquals("Wrong path", storage.getRealPath(), part.getPath());
      assertEquals("Wrong name", storage.getRealName(), part.getName());
      assertEquals("Wrong type", storage.getType(), part.getType());
      assertEquals("Wrong part type", this.getPartType(), part.getPartType());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      this.createResource((STORAGE)null, this.getPartType());
      fail("Should fail with null storage");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createResource(storage, null);
      fail("Should fail with null part type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    USAGE usage = this.createUsage(this.getType());
    try
    {
      PART part = this.createResource(usage, this.getPartType());
      assertEquals("Wrong path", usage.getRealPath(), part.getPath());
      assertEquals("Wrong name", usage.getRealName(), part.getName());
      assertEquals("Wrong type", usage.getType(), part.getType());
      assertEquals("Wrong part type", this.getPartType(), part.getPartType());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      this.createResource((USAGE)null, this.getPartType());
      fail("Should fail with null usage");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createResource(usage, null);
      fail("Should fail with null part type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    PART oldPart = this.createResource(this.createStorage(this.getType()), this.getPartType());
    try
    {
      PART part = this.createResource(oldPart, this.getDefaultPartType());
      assertEquals("Wrong path", oldPart.getPath(), part.getPath());
      assertEquals("Wrong name", oldPart.getName(), part.getName());
      assertEquals("Wrong type", oldPart.getType(), part.getType());
      assertEquals("Wrong part type", this.getDefaultPartType(), part.getPartType());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      this.createResource((PART)null, this.getPartType());
      fail("Should fail with null usage");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createResource(oldPart, null);
      fail("Should fail with null part type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of getRealPath(), of class ResourcePart.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#testGetRealPath_0args()
   */
  @Override
  @Test
  public void testGetRealPath_0args() throws Bid4WinException
  {
    String path = "Path";
    PART part = this.createResource(path, "name", this.getType());
    assertEquals("Wrong real path",
                 UtilFile.concatRelativePath(ResourceRef.RESOURCE,
                                             this.getType().getPathPrefix(),
                                             path),
                 part.getRealPath());
  }
  /**
   * Test of getRealName(), of class ResourcePart.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#testGetRealName_0args()
   */
  @Override
  @Test
  public void testGetRealName_0args() throws Bid4WinException
  {
    String name = "Name";
    PART part = this.createResource("path", name, this.getType());
    assertEquals("Wrong real name", name + "_" + this.getPartType().getCode(),
                 part.getRealName());
  }
}
