package com.bid4win.commons.service.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.UtilFileTest;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.commons.core.io.resource.Bid4WinResourceValidator;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.dao.resource.IResourceDaoStub;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.resource.Resource;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.service.Bid4WinServiceTester;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <SESSION> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceServiceTester<RESOURCE extends Resource<RESOURCE, TYPE>,
                                            TYPE extends ResourceType<TYPE>,
                                            SESSION extends SessionDataAbstract<ACCOUNT>,
                                            ACCOUNT extends AccountAbstract<ACCOUNT>,
                                            GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinServiceTester<RESOURCE, Long, SESSION, ACCOUNT, GENERATOR>
{
  /**
   * Test of createResource(String, String, TYPE, InputStream), of class ResourceManager
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testCreateResource_String_String_TYPE_InputStream() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction(
        "createResource", String.class, String.class, ResourceType.class, InputStream.class);

    File file1 = this.getTestFile(this.getFilename1());
    InputStream inputStream = new FileInputStream(file1);
    RESOURCE resource1 = null;
    try
    {
      // Creation d'une ressource
      resource1 = this.getService().createResource("", "test1", this.getType1(), inputStream);
      assertTrue("Bad ID", 0 <= resource1.getId());
      assertEquals("Bad version", 0, resource1.getVersion());
      RESOURCE result = this.getDao().findOneByFullPath(resource1.getFullPath());
      assertNotNull("Cannot be found by full path", result);
      assertTrue("Wrong result", resource1.identical(result));
      this.assertEqualsResource(file1, resource1);
    }
    finally
    {
      inputStream.close();
    }

    File file2 = this.getTestFile(this.getFilename2());
    inputStream = new FileInputStream(file2);
    try
    {
      // Tentative de creation d'une ressource déjà existante
      this.getService().createResource(
          resource1.getPath(), resource1.getName(), resource1.getType(), inputStream);
      fail("Should fail with already referenced resource");
    }
    catch(PersistenceException ex)
    {
      System.out.println(ex.getMessage());
    }
    finally
    {
      // L'ancienne ressource ne doit pas avoir été modifiée
      RESOURCE result = this.getDao().findOneByFullPath(resource1.getFullPath());
      assertNotNull("Cannot be found by full path", result);
      assertTrue("Wrong result", resource1.identical(result));
      this.assertEqualsResource(file1, resource1);
      inputStream.close();
    }
    RESOURCE resource2 = null;
    if(!this.getType1().equals(this.getType2()))
    {
      // Creation d'une seconde ressource avec le même path/nom et un type différent
      inputStream = new FileInputStream(file2);
      try
      {
        resource2 = this.getService().createResource(
            resource1.getPath(), resource1.getName(), this.getType2(), inputStream);
        assertTrue("Bad ID", 0 <= resource2.getId());
        assertEquals("Bad version", 0, resource2.getVersion());
        RESOURCE result = this.getDao().findOneByFullPath(resource2.getFullPath());
        assertNotNull("Cannot be found by full path", result);
        assertTrue("Wrong result", resource2.identical(result));
        this.assertEqualsResource(file2, resource2);
        // L'ancienne ressource ne doit pas avoir été modifiée
        result = this.getDao().findOneByFullPath(resource1.getFullPath());
        assertNotNull("Cannot be found by full path", result);
        assertTrue("Wrong result", resource1.identical(result));
        this.assertEqualsResource(file1, resource1);
      }
      finally
      {
        inputStream.close();
      }
    }
  }
  /**
   * Test of deleteResource(long), of class ResourceManager
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testDeleteResource_long() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("deleteResource", long.class);

    // Création de la situation de départ
    RESOURCE resource = this.createStartingResource("", "test", this.getType1(), this.getFilename1());

    // Supprime la ressource
    this.getService().deleteResource(resource.getId());
    assertNull("Should have been deleted", this.getDao().findById(resource.getId()));
    assertFalse("Resource should have been deleted", this.getResourceStore().exists(resource));
  }

  /**
   *
   * TODO A COMMENTER
   * @param path TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @param filename TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  protected RESOURCE createStartingResource(String path, String name, TYPE type, String filename)
            throws Exception
  {
    boolean isConnected = (this.getConnectedAccountId() == null ? false : true);
    if(!isConnected)
    {
      this.connectAccount(0);
    }
    Role role = this.getAccount(0).getCredential().getRole();
    if(!role.equals(this.getService().getAdminRole()))
    {
      this.updateRole(0, this.getService().getAdminRole());
    }
    File file = this.getTestFile(filename);
    InputStream inputStream = new FileInputStream(file);
    try
    {
      return this.getService().createResource(path, name, type, inputStream);
    }
    finally
    {
      inputStream.close();
      if(!role.equals(this.getService().getAdminRole()))
      {
        this.updateRole(0, role);
      }
      if(!isConnected)
      {
        this.disconnectAccount();
      }
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  @Test
  public void testGetSubdirectories_String() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("getSubdirectories", String.class);

    Bid4WinStringRecursiveMap result = this.getService().getSubdirectories("");
    assertEquals("Wrong directories", new Bid4WinStringRecursiveMap(), result);
    result = this.getService().getSubdirectories("1");
    assertEquals("Wrong directories", new Bid4WinStringRecursiveMap(), result);

    Bid4WinStringRecursiveMap root = new Bid4WinStringRecursiveMap();

    int i = 0;
    FileInputStream inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    try
    {
      this.getService().createResource(
          this.concatRelativePath("1", "1_1", "1_1_1"), "" + i++, this.getType1(), inputStream);
    }
    finally
    {
      inputStream.close();
    }
    Bid4WinStringRecursiveMap root_1 = new Bid4WinStringRecursiveMap();
    root.put("1", root_1);
    Bid4WinStringRecursiveMap root_1_1 = new Bid4WinStringRecursiveMap();
    root_1.put("1_1", root_1_1);
    Bid4WinStringRecursiveMap root_1_1_1 = new Bid4WinStringRecursiveMap();
    root_1_1.put("1_1_1", root_1_1_1);

    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    try
    {
      this.getService().createResource(
          this.concatRelativePath("1", "1_1", "1_1_1"), "" + i++, this.getType1(), inputStream);
    }
    finally
    {
      inputStream.close();
    }
    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    try
    {
      this.getService().createResource(
          this.concatRelativePath("1", "1_1", "1_1_2"), "" + i++, this.getType1(), inputStream);
    }
    finally
    {
      inputStream.close();
    }
    Bid4WinStringRecursiveMap root_1_1_2 = new Bid4WinStringRecursiveMap();
    root_1_1.put("1_1_2", root_1_1_2);

    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    try
    {
      this.getService().createResource(
          this.concatRelativePath("1", "1_1"), "" + i++, this.getType1(), inputStream);
    }
    finally
    {
      inputStream.close();
    }
    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    try
    {
      this.getService().createResource(
          this.concatRelativePath("2", "2_1"), "" + i++, this.getType1(), inputStream);
    }
    finally
    {
      inputStream.close();
    }
    Bid4WinStringRecursiveMap root_2 = new Bid4WinStringRecursiveMap();
    root.put("2", root_2);
    Bid4WinStringRecursiveMap root_2_1 = new Bid4WinStringRecursiveMap();
    root_2.put("2_1", root_2_1);

    result = this.getService().getSubdirectories("");
    assertEquals("Wrong directories", root, result);
    result = this.getService().getSubdirectories("1");
    assertEquals("Wrong directories", root_1, result);
    result = this.getService().getSubdirectories(this.concatRelativePath("1", "1_1"));
    assertEquals("Wrong directories", root_1_1, result);
    result = this.getService().getSubdirectories(this.concatRelativePath("1", "1_1", "1_1_1"));
    assertEquals("Wrong directories", new Bid4WinStringRecursiveMap(), result);
    result = this.getService().getSubdirectories(this.concatRelativePath("1", "1_1", "1_1_2"));
    assertEquals("Wrong directories", new Bid4WinStringRecursiveMap(), result);
    result = this.getService().getSubdirectories("2");
    assertEquals("Wrong directories", root_2, result);
    result = this.getService().getSubdirectories(this.concatRelativePath("2", "2_1"));
    assertEquals("Wrong directories", new Bid4WinStringRecursiveMap(), result);
    result = this.getService().getSubdirectories("3");
    assertEquals("Wrong directories", new Bid4WinStringRecursiveMap(), result);
  }

  /**
   * Permet de tester que la ressource en argument correspond bien dans le magasin
   * au fichier donné
   * @param expected Résultat attendu pour la ressource en argument
   * @param resource Ressource à tester
   * @throws Exception Problème lors de la validation de la ressource
   */
  protected void assertEqualsResource(File expected, RESOURCE resource) throws Exception
  {
    this.getResourceValidator().assertEquals(this.getTestPath(), expected, resource);
  }
  /**
   * Getter du repertoire de test
   * @return Le repertoire de test
   * @throws UserException Si le repertoire est invalide
   */
  protected String getTestPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE,
                                       UtilFileTest.getProjectPath("CommonsManager"),
                                       "test" , "resources");
  }
  /**
   * Getter de l'emplacement des fichiers de ressources utilisés pour les tests
   * @return L'emplacement des fichiers de ressources utilisés pour les tests
   * @throws UserException Si l'emplacement est invalide
   */
  protected String getTestResourcePath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE,
                                       this.getTestPath(), "test");
  }
  /**
   * Getter du fichier de test précisé en argument
   * @param filename Nom du fichier de test à récupérer
   * @return Le fichier de test précisé en argument
   * @throws UserException Si l'emplacement du fichier de test est invalide
   */
  protected File getTestFile(String filename) throws UserException
  {
    return UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE,
                                             this.getTestResourcePath(), filename);
  }
  /**
   *
   * TODO A COMMENTER
   * @param paths TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String concatRelativePath(String ... paths) throws UserException
  {
    return UtilFile.concatRelativePath(ResourceRef.RESOURCE, paths);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract String getFilename1();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract String getFilename2();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract TYPE getType1();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract TYPE getType2();

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#getService()
   */
  @Override
  public abstract ResourceService_<RESOURCE, TYPE, SESSION, ACCOUNT, ?> getService();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IResourceDaoStub<RESOURCE, TYPE> getDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Bid4WinResourceValidator<RESOURCE, TYPE> getResourceValidator();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected Bid4WinResourceStore<RESOURCE, TYPE> getResourceStore()
  {
    return this.getResourceValidator().getStore();
  }

  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    this.getResourceValidator().cleanAll(this.getTestPath());
    super.setUp();
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    this.getResourceValidator().cleanAll(this.getTestPath());
    super.tearDown();
  }
}
