package com.bid4win.commons.manager.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.UtilFileTest;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.commons.core.io.resource.Bid4WinResourceValidator;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.manager.Bid4WinManagerTester;
import com.bid4win.commons.persistence.dao.resource.IResourceDaoStub;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.resource.Resource;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceManagerTester<RESOURCE extends Resource<RESOURCE, TYPE>,
                                            TYPE extends ResourceType<TYPE>,
                                            ACCOUNT extends AccountAbstract<ACCOUNT>,
                                            GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinManagerTester<RESOURCE, Long, ACCOUNT, GENERATOR>
{

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
    File file = this.getTestFile(filename);
    InputStream inputStream = new FileInputStream(file);
    try
    {
      return this.getManager().createResource(path, name, type, inputStream);
    }
    finally
    {
      inputStream.close();
    }
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
   * @see com.bid4win.commons.manager.Bid4WinManagerTester#getManager()
   */
  @Override
  public abstract ResourceManager_<RESOURCE, TYPE, ACCOUNT> getManager();
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
