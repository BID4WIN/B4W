package com.bid4win.manager.resource;

import org.junit.Before;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.UtilFileTest;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <STORAGE> TODO A COMMENTER
 * @param <USAGE> TODO A COMMENTER
 * @param <TYPE> TODO A COMMENTER
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceRepositoryManagerTester<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                                      USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                                      TYPE extends ResourceType<TYPE>>
       extends com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester<STORAGE, USAGE, TYPE, Account, EntityGenerator>
{
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IRootPathPropertyInitializer getRootPathPropertyInitializer();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getTestPath()
   */
  @Override
  protected String getTestPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE,
                                       UtilFileTest.getProjectPath("Bid4WinBusiness"),
                                       "test" , "resources");
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getFilename1()
   */
  @Override
  protected String getFilename1()
  {
    return "image.jpg";
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getFilename2()
   */
  @Override
  protected String getFilename2()
  {
    return "logo02.png";
  }

  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    this.getRootPathPropertyInitializer().setUpProperty();
    super.setUp();
  }
}
