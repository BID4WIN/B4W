package com.bid4win.commons.service.resource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.manager.resource.ResourceStorageValidatorStub;
import com.bid4win.commons.manager.resource.ResourceUsageValidatorStub;
import com.bid4win.commons.persistence.dao.resource.ResourceStorageDaoStub;
import com.bid4win.commons.persistence.dao.resource.ResourceUsageDaoStub;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageStub;
import com.bid4win.commons.service.connection.SessionDataAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ResourceRepositoryServiceTest
       extends ResourceRepositoryServiceTester<ResourceStorageStub, ResourceUsageStub, ResourceTypeStub,
                                               SessionDataAbstractStub, AccountAbstractStub, EntityGeneratorStub>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ResourceRepositoryServiceStub")
  private ResourceRepositoryServiceStub service;
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
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getFilename1()
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
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getFilename2()
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
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getType1()
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
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getType2()
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
   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#getService()
   */
  @Override
  public ResourceRepositoryServiceStub getService()
  {
    return this.service;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getDao()
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
   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#getUsageDao()
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
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getResourceValidator()
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
   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#getUsageValidator()
   */
  @Override
  protected ResourceUsageValidatorStub getUsageValidator()
  {
    return this.usageValidator;
  }
}
