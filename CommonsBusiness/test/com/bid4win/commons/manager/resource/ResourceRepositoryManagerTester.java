package com.bid4win.commons.manager.resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.commons.core.io.resource.Bid4WinResourceValidator;
import com.bid4win.commons.persistence.dao.resource.IResourceDaoStub;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <STORAGE> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceRepositoryManagerTester<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                                      USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                                      TYPE extends ResourceType<TYPE>,
                                                      ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                      GENERATOR extends EntityGenerator<ACCOUNT>>
       extends ResourceManagerTester<STORAGE, TYPE, ACCOUNT, GENERATOR>
{
  /**
   * Test of loadUsageInStore(USAGE), of class ResourceRepositoryManager.
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testLoadUsageInStore_String() throws Exception
  {
    // Création de la situation de départ
    File file1 = this.getTestFile(this.getFilename1());
    STORAGE storage = this.createStartingResource(UtilString.EMPTY, "test", this.getType1(), file1.getName());
    USAGE usage1 = this.createStartingUsage("usage1", storage);
    storage = this.getDao().getById(storage.getId());

    // Test de la présence de l'utilisation de ressource
    assertFalse("Usage should not exist in store", this.getUsageStore().exists(usage1));
    this.getManager().loadUsageInStore(usage1.getFullPath());
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage1));
    this.assertEqualsUsage(file1, usage1);

    File file2 = this.getTestFile(this.getFilename2());
    // Modification de la ressource stockée
    FileInputStream inputStream = new FileInputStream(file2);
    try
    {
      storage = this.getManager().updateResource(storage.getId(), this.getType2(), inputStream);
    }
    finally
    {
      inputStream.close();
    }
    USAGE usage2 = storage.getUsage(usage1.getId());

    // Test de la présence de l'ancienne et de la nouvelle utilisation de ressource
    assertFalse("Full path should have changed", usage1.equals(usage2.getFullPath()));
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage1));
    assertFalse("Usage should not exist in store", this.getUsageStore().exists(usage2));
    this.getManager().loadUsageInStore(usage2.getFullPath());
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage1));
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage2));
    this.assertEqualsUsage(file2, usage2);

    // Retire l'utilisation de ressource
    this.getManager().deleteUsage(usage2.getId());
    // Test de la présence de l'ancienne et de la nouvelle utilisation de ressource
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage1));
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage2));

    // Recrée l'utilisation de ressource
    USAGE usage3 = this.getManager().createUsage(
        "usage", "usage_" + storage.getName(), storage.getId());

    // Test de la présence des utilisation de ressource
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage1));
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage2));
    assertFalse("Usage should not exist in store", this.getUsageStore().exists(usage3));
    this.getManager().loadUsageInStore(usage3.getFullPath());
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage1));
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage2));
    assertTrue("Usage should exist in store", this.getUsageStore().exists(usage3));
    this.assertEqualsUsage(file2, usage3);
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
      return this.getManager().createUsage(
          path, "usage_" + storage.getName(), storage.getId());
  }
  /**
   * Permet de tester que l'utilisation de ressource en argument correspond bien
   * dans le magasin au fichier donné
   * @param expected Résultat attendu pour l'utilisation de ressource en argument
   * @param usage Utilisation de ressource à tester
   * @throws Exception Problème lors de la validation de l'utilisation de ressource
   */
  protected void assertEqualsUsage(File expected, USAGE usage) throws Exception
  {
    this.getUsageValidator().assertEquals(this.getTestPath(), expected, usage);
  }

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
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.Bid4WinManagerTester#getManager()
   */
  @Override
  public abstract ResourceRepositoryManager_<STORAGE, USAGE, TYPE, ACCOUNT> getManager();

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
