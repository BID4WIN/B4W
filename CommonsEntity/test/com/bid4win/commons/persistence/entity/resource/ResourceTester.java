package com.bid4win.commons.persistence.entity.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 * Classe de test d'une ressource de base<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceTester<RESOURCE extends Resource<RESOURCE, TYPE>,
                                     TYPE extends ResourceType<TYPE>,
                                     ACCOUNT extends AccountAbstract<ACCOUNT>,
                                     GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinEntityTester<ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @param path TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract RESOURCE createResource(String path, String name, TYPE type) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract TYPE getType();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  @SuppressWarnings("unchecked")
  protected TYPE getDefaultType()
  {
    return (TYPE)Bid4WinObjectType.getDefaultType(this.getType().getClass());
  }

  /**
   * Test of Resource(...) method, of class Resource.
   * @throws Bid4WinException Issue not expected during this test
   */
  @SuppressWarnings("unused")
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    String path = UtilString.EMPTY;
    String name = " name ";
    TYPE type = this.getType();
    try
    {
      RESOURCE resource = this.createResource(path, name, type);
      assertEquals("Bad resource path", path, resource.getPath());
      assertEquals("Bad resource name", UtilString.trimNotNull(name), resource.getName());
      assertEquals("Bad resource type", type, resource.getType());

      path = UtilFile.concatRelativePath(ResourceRef.RESOURCE, "1", "2", "3");
      name = "4.5.6";
      resource = this.createResource(path, name, type);
      assertEquals("Bad resource path", path, resource.getPath());
      assertEquals("Bad resource name", name.replaceAll("\\.", "-"), resource.getName());
      assertEquals("Bad resource type", type, resource.getType());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      this.createResource("/  /", name, type);
      fail("Instanciation with invalid path should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createResource(path, name, null);
      fail("Instanciation with null type should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of sameInternal(Resource, boolean), of class Resource.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    RESOURCE resource1 = this.createResource("path", "name", this.getType());
    RESOURCE resource2 = this.createResource(resource1.getPath(), resource1.getName(), resource1.getType());
    this.checkSame(resource1, resource2);
    this.checkSame(resource2, resource1);
    this.checkIdentical(resource1, resource2);
    this.checkIdentical(resource2, resource1);

    resource2 = this.createResource(resource1.getPath() + "1", resource1.getName(), resource1.getType());
    this.checkNotSame(resource1, resource2);
    this.checkNotSame(resource2, resource1);
    this.checkNotIdentical(resource1, resource2);
    this.checkNotIdentical(resource2, resource1);

    resource2 = this.createResource(resource1.getPath(), resource1.getName() + "1", resource1.getType());
    this.checkNotSame(resource1, resource2);
    this.checkNotSame(resource2, resource1);
    this.checkNotIdentical(resource1, resource2);
    this.checkNotIdentical(resource2, resource1);

    resource2 = this.createResource(resource1.getPath(), resource1.getName(),
                                    this.getDefaultType());
    //if(!resource1.getType().equals(resource2.getType()))
    this.checkNotSame(resource1, resource2);
    this.checkNotSame(resource2, resource1);
    this.checkNotIdentical(resource1, resource2);
    this.checkNotIdentical(resource2, resource1);
  }

  /**
   * Test of definePath(String), of class Resource.
   */
  @Test
  public void testDefinePath_String()
  {
    String path = "path";
    RESOURCE resource = null;
    try
    {
      resource = this.createResource(path, "name", this.getType());
      path = "new" + path;
      resource.definePath(path);
      assertEquals("Wrong path", path, resource.getPath());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of defineName(String), of class Resource.
   */
  @Test
  public void testDefineName_String()
  {
    String name = "name";
    RESOURCE resource = null;
    try
    {
      resource = this.createResource("path", name, this.getType());
      name = "new" + name;
      resource.defineName(name);
      assertEquals("Wrong name", name, resource.getName());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of defineType(TYPE), of class Resource.
   * @throws Bid4WinException Issue not expected during this test
   */
  @SuppressWarnings("unused")
  @Test
  public void testDefineType_TYPE() throws Bid4WinException
  {
    TYPE type = this.getType();
    RESOURCE resource = null;
    try
    {
      resource = this.createResource("path", "name", type);
      type = this.getDefaultType();
      resource.defineType(type);
      assertEquals("Wrong type", type, resource.getType());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }

  /**
   * Test of getRealPath(), of class Resource.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetRealPath_0args() throws Bid4WinException
  {
    String path = "Path";
    RESOURCE resource = this.createResource(path, "name", this.getType());
    assertEquals("Wrong real path", path, resource.getRealPath());
  }
  /**
   * Test of getRealName(), of class Resource.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetRealName_0args() throws Bid4WinException
  {
    String name = "Name";
    RESOURCE resource = this.createResource("path", name, this.getType());
    assertEquals("Wrong real name", name, resource.getRealName());
  }
  /**
   * Test of getFullPath(), of class Resource.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetFullPath_0args() throws Bid4WinException
  {
    TYPE type = this.getType();
    RESOURCE resource = this.createResource("Path", "Name", type);
    String fullPath = UtilFile.addExtension(resource.getRealName(), type.getExtension(), ResourceRef.RESOURCE);
    fullPath = UtilFile.concatRelativePath(ResourceRef.RESOURCE, resource.getRealPath(), fullPath);
    fullPath = fullPath.toLowerCase();
    assertEquals("Wrong full path", fullPath, resource.getFullPath());
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.core.security.ProtectableObjectTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    this.startProtection();
    RESOURCE resource = this.createResource("path", "name", this.getType());
    String path = resource.getPath();
    String name = resource.getName();
    TYPE type = resource.getType();
    this.stopProtection();

    try
    {
      resource.definePath("new" + path);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      resource.defineName("new" + name);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      resource.defineType(type);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    assertEquals("Wrong path", path, resource.getPath());
    assertEquals("Wrong name", name, resource.getName());
    assertEquals("Wrong type", type, resource.getType());
  }
}
