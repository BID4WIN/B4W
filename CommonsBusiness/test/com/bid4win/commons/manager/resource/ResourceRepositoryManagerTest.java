package com.bid4win.commons.manager.resource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.persistence.dao.resource.ResourceStorageDaoStub;
import com.bid4win.commons.persistence.dao.resource.ResourceUsageDaoStub;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ResourceRepositoryManagerTest
       extends ResourceRepositoryManagerTester<ResourceStorageStub, ResourceUsageStub,
                                               ResourceTypeStub, AccountAbstractStub, EntityGeneratorStub>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ResourceRepositoryManagerStub")
  private ResourceRepositoryManagerStub manager;
  /** Référence TODO */
  @Autowired
  @Qualifier("ResourceStorageDaoStub")
  private ResourceStorageDaoStub resourceDao;
  /** Référence TODO */
  @Autowired
  @Qualifier("ResourceUsageDaoStub")
  private ResourceUsageDaoStub usageDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ResourceStorageValidatorStub")
  private ResourceStorageValidatorStub storageValidator;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ResourceUsageValidatorStub")
  private ResourceUsageValidatorStub usageValidator;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManagerTester#getFilename1()
   */
  @Override
  protected String getFilename1()
  {
    return "test1.jpg";
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManagerTester#getFilename2()
   */
  @Override
  protected String getFilename2()
  {
    return "test2.jpg";
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManagerTester#getType1()
   */
  @Override
  protected ResourceTypeStub getType1()
  {
    return ResourceTypeStub.TYPE1;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManagerTester#getType2()
   */
  @Override
  protected ResourceTypeStub getType2()
  {
    return ResourceTypeStub.TYPE2;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getManager()
   */
  @Override
  public ResourceRepositoryManagerStub getManager()
  {
    return this.manager;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManagerTester#getDao()
   */
  @Override
  protected ResourceStorageDaoStub getDao()
  {
    return this.resourceDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getUsageDao()
   */
  @Override
  protected ResourceUsageDaoStub getUsageDao()
  {
    return this.usageDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManagerTester#getResourceValidator()
   */
  @Override
  protected ResourceStorageValidatorStub getResourceValidator()
  {
    return this.storageValidator;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getUsageValidator()
   */
  @Override
  protected ResourceUsageValidatorStub getUsageValidator()
  {
    return this.usageValidator;
  }
}
