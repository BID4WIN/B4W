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

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.commons.core.io.resource.Bid4WinResourceValidator;
import com.bid4win.commons.persistence.dao.resource.IResourceDaoStub;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <STORAGE> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <SESSION> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceRepositoryServiceTester<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                                      USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                                      TYPE extends ResourceType<TYPE>,
                                                      SESSION extends SessionDataAbstract<ACCOUNT, CONNECTION>,
                                                      ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                      CONNECTION extends ConnectionAbstract<CONNECTION, ?, ACCOUNT>,
                                                      GENERATOR extends EntityGenerator<ACCOUNT>>
       extends ResourceServiceTester<STORAGE, TYPE, SESSION, ACCOUNT, CONNECTION, GENERATOR>
{
  /**
   * Test of createUsage(String, String, long), of class ResourceRepositoryManager
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testCreateUsage_String_String_long() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction(
        "createUsage", String.class, String.class, long.class);

    // Création de la situation de départ
    STORAGE storage = this.createStartingResource(UtilString.EMPTY, "test", this.getType1(), this.getFilename1());

    // Creation d'une utilisation de ressource
    USAGE usage1 = this.getService().createUsage(
        "usage1", "usage_" + storage.getName(), storage.getId());
    assertTrue("Bad ID", 0 <= usage1.getId());
    // La version n'est pas zéro car le chemin complet se construit en utilisant
    // l'identifiant. Or la ressource insérée ne le possède pas à ce moment précis
    // et s'ensuit une mise à jour ultérieure via hibernate au flush
    assertEquals("Bad version", 1, usage1.getVersion());
    USAGE result = this.getUsageDao().findOneByFullPath(usage1.getFullPath());
    assertNotNull("Cannot be found by full path", result);
    assertTrue("Wrong result", usage1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", usage1.same(result));
    assertFalse("Usage should not exist in store", this.getUsageStore().exists(usage1));
    // Le stockage associé doit avoir été impacté
    storage = this.getDao().findOneByFullPath(storage.getFullPath());
    assertNotNull("Cannot be found by full path", result);
    assertEquals("Wrong version", 1, storage.getVersion());
    assertEquals("Wrong usage number", 1, storage.getUsages().size());
    assertTrue("Wrong result", storage.identical(result.getStorage()));
    // Création d'une seconde utilisation de ressource
    USAGE usage2 = this.getService().createUsage(
        "usage2", "usage_" + storage.getName(), storage.getId());
    assertTrue("Bad ID", 0 <= usage2.getId());
    // La version n'est pas zéro car le chemin complet se construit en utilisant
    // l'identifiant. Or la ressource insérée ne le possède pas à ce moment précis
    // et s'ensuit une mise à jour ultérieure via hibernate au flush
    assertEquals("Bad version", 1, usage2.getVersion());
    result = this.getUsageDao().findOneByFullPath(usage2.getFullPath());
    assertNotNull("Cannot be found by full path", result);
    assertTrue("Wrong result", usage2.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", usage2.same(result));
    assertFalse("Usage should not exist in store", this.getUsageStore().exists(usage2));
    // Le stockage associé doit avoir été impacté
    storage = this.getDao().findOneByFullPath(storage.getFullPath());
    assertNotNull("Cannot be found by full path", result);
    assertEquals("Wrong version", 2, storage.getVersion());
    assertEquals("Wrong usage number", 2, storage.getUsages().size());
    assertTrue("Wrong result", storage.identical(result.getStorage()));
    // L'utilisation de ressource existante ne doit pas être impactée
    USAGE usage = this.getUsageDao().getById(usage1.getId());
    assertTrue("Wrong result", usage.identical(usage1, new Bid4WinList<Bid4WinRelationNode>()));
    // Tentative de création d'une utilisation de ressource utilisant les mêmes
    // noms et path
    try
    {
      this.getService().createUsage(usage1.getPath(), usage1.getName(), storage.getId());
      fail("Should fail with already referenced usage");
    }
    catch(PersistenceException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  @Test
  public void test_update_of_storage_and_usage() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction(
        "moveResource", long.class, String.class, String.class);
    this.checkAdminRestriction(
        "updateResource", long.class, ResourceType.class, InputStream.class);
    this.checkAdminRestriction(
        "moveUsage", long.class, String.class, String.class);

    // Création de la situation de départ
    File file1 = this.getTestFile(this.getFilename1());
    STORAGE storage1 = this.createStartingResource(UtilString.EMPTY, "test", this.getType1(), file1.getName());
    USAGE usage1 = this.createStartingUsage("usage1", storage1);
    USAGE usage2 = this.createStartingUsage("usage2", storage1);
    storage1 = this.getDao().getById(storage1.getId());

    // Déplace le stockage
    STORAGE storage2 = this.getService().moveResource(
        storage1.getId(), storage1.getPath(), "test2");
    STORAGE storage = this.getDao().getById(storage1.getId());
    assertEquals("Wrong version", storage1.getVersion() + 1, storage.getVersion());
    assertEquals("Wrong usage number", 2, storage.getUsages().size());
    assertTrue("Wrong result", storage.same(storage2, new Bid4WinList<Bid4WinRelationNode>()));
    assertFalse(this.getResourceStore().exists(storage1));
    this.assertEqualsResource(file1, storage2);
    // Les utilisations ne doivent pas être impactées
    USAGE usage = this.getUsageDao().getById(usage1.getId());
    assertTrue("Wrong result", usage.identical(usage1, new Bid4WinList<Bid4WinRelationNode>()));
    usage = this.getUsageDao().getById(usage2.getId());
    assertTrue("Wrong result", usage.identical(usage2, new Bid4WinList<Bid4WinRelationNode>()));
    // Modifie la ressource stockée
    File file2 = this.getTestFile(this.getFilename2());
    FileInputStream inputStream = new FileInputStream(file2);
    storage1 = storage;
    try
    {
      storage2 = this.getService().updateResource(storage1.getId(), this.getType2(), inputStream);
    }
    finally
    {
      inputStream.close();
    }
    storage = this.getDao().getById(storage1.getId());
    assertEquals("Wrong version", storage1.getVersion() + 1, storage.getVersion());
    assertEquals("Wrong forcing", storage1.getVersion() + 1, storage.getUpdateForce());
    assertEquals("Wrong usage number", 2, storage.getUsages().size());
    if(this.getType1().equals(this.getType2()))
    {
      assertEquals("Wrong full path", storage1.getFullPath(), storage2.getFullPath());
    }
    else
    {
      assertFalse(this.getResourceStore().exists(storage1));
    }
    storage1.defineType(this.getType2());
    assertTrue("Wrong result", storage.same(storage1));
    this.assertEqualsResource(file2, storage2);
    // Les utilisations doivent être impactées
    usage = this.getUsageDao().getById(usage1.getId());
    usage1.getStorage().defineType(this.getType2());
    assertEquals("Wrong version", usage1.getVersion() + 1, usage.getVersion());
    assertEquals("Wrong forcing", usage1.getVersion() + 1, usage.getUpdateForce());
    assertTrue("Wrong result", usage.same(usage1, new Bid4WinList<Bid4WinRelationNode>()));
    usage = this.getUsageDao().getById(usage2.getId());
    usage2.getStorage().defineType(this.getType2());
    assertEquals("Wrong version", usage2.getVersion() + 1, usage.getVersion());
    assertEquals("Wrong forcing", usage2.getVersion() + 1, usage.getUpdateForce());
    assertTrue("Wrong result", usage.same(usage2, new Bid4WinList<Bid4WinRelationNode>()));

    usage1 = this.getUsageDao().getById(usage1.getId());
    usage2 = this.getUsageDao().getById(usage2.getId());
    // Déplace une utilisation de ressource
    usage = this.getService().moveUsage(usage1.getId(), "usage1", "usage_" + storage2.getName());
    assertEquals("Wrong version", usage1.getVersion() + 1, usage.getVersion());
    assertEquals("Wrong forcing", usage1.getUpdateForce(), usage.getUpdateForce());
    // Le stockage ne doit pas être impacté
    storage = this.getDao().getById(storage1.getId());
    assertEquals("Wrong version", storage2.getVersion(), storage.getVersion());
    assertEquals("Wrong usage number", 2, storage.getUsages().size());
    assertTrue("Wrong result", storage.same(storage2, new Bid4WinList<Bid4WinRelationNode>()));
    for(USAGE usage3 : storage.getUsages())
    {
      if(usage3.getId().equals(usage1.getId()))
      {
        assertTrue("Wrong result", usage3.identical(usage, new Bid4WinList<Bid4WinRelationNode>()));
      }
      else if(usage3.getId().equals(usage2.getId()))
      {
        assertTrue("Wrong result", usage3.identical(usage2, new Bid4WinList<Bid4WinRelationNode>()));
      }
      else
      {
        fail("Unknown usage");
      }
    }
  }
  /**
   * Test of deleteUsage(long), of class ResourceRepositoryManager
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testDeleteUsage_long() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("deleteUsage", long.class);

    // Création de la situation de départ
    STORAGE storage = this.createStartingResource(UtilString.EMPTY, "test", this.getType1(), this.getFilename1());
    USAGE usage2 = this.createStartingUsage("usage2", storage);
    USAGE usage1 = this.createStartingUsage("usage1", storage);
    storage = this.getDao().getById(storage.getId());

    // Supprime une utilisation de ressource
    this.getService().deleteUsage(usage1.getId());
    assertNull("Should have been deleted", this.getUsageDao().findById(usage1.getId()));
    // Le stockage doit être impacté
    STORAGE result = this.getDao().getById(storage.getId());
    assertEquals("Wrong version", storage.getVersion() + 1, result.getVersion());
    assertEquals("Wrong usage number", 1, result.getUsages().size());
    assertTrue("Wrong result", storage.same(result, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", result.getUsages().iterator().next().identical(usage2, new Bid4WinList<Bid4WinRelationNode>()));
    // L'utilisation de ressource restante ne doit pas être impactée
    USAGE usage = this.getUsageDao().getById(usage2.getId());
    assertTrue("Wrong result", usage.identical(usage2, new Bid4WinList<Bid4WinRelationNode>()));
    // Tentative de suppression de stockage avec des utilisations
    try
    {
      this.getService().deleteResource(storage.getId());
      fail("Should fail with existing usage");
    }
    catch(RuntimeException ex)
    {
      System.out.println(ex.getMessage());
    }
    storage = result;
    // Supprime une utilisation de ressource
    this.getService().deleteUsage(usage2.getId());
    assertNull("Should have been deleted", this.getUsageDao().findById(usage2.getId()));
    // Le stockage doit être impacté
    result = this.getDao().getById(storage.getId());
    assertEquals("Wrong version", storage.getVersion() + 1, result.getVersion());
    assertEquals("Wrong usage number", 0, result.getUsages().size());
    assertTrue("Wrong result", storage.same(result, new Bid4WinList<Bid4WinRelationNode>()));
  }

  /**
   *
   * TODO A COMMENTER
   * @param path TODO A COMMENTER
   * @param storage TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  protected USAGE createStartingUsage(String path, STORAGE storage)
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
    try
    {
      return this.getService().createUsage(
          path, "usage_" + storage.getName(), storage.getId());
    }
    finally
    {
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
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getService()
   */
  @Override
  public abstract ResourceRepositoryService_<STORAGE, USAGE, TYPE, SESSION, ACCOUNT, ?> getService();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IResourceDaoStub<USAGE, TYPE> getUsageDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Bid4WinResourceValidator<USAGE, TYPE> getUsageValidator();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected Bid4WinResourceStore<USAGE, TYPE> getUsageStore()
  {
    return this.getUsageValidator().getStore();
  }
  /**
   * Redéfini la suppression de tous les stockages de ressources pour ajouter la
   * suppression de toutes leurs utilisations
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#removeAll()
   */
  @Override
  protected Bid4WinList<STORAGE> removeAll() throws PersistenceException
  {
    this.getUsageDao().removeAll();
    return super.removeAll();
  }
  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    this.getUsageValidator().cleanAll(this.getTestPath());
    super.setUp();
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    this.getUsageValidator().cleanAll(this.getTestPath());
    super.tearDown();
  }
}
