package com.bid4win.service.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.manager.image.store.ImageRepositoryValidator;
import com.bid4win.manager.image.store.ImageUsageLinkedValidator;
import com.bid4win.persistence.dao.image.ImageStorageDaoStub;
import com.bid4win.persistence.dao.image.ImageUsageDaoStub;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.service.resource.ResourceRepositoryServiceTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ImageServiceTester
       extends ResourceRepositoryServiceTester<ImageStorage, ImageUsage, ImageType>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageStorageDaoStub")
  private ImageStorageDaoStub dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageUsageDaoStub")
  private ImageUsageDaoStub usageDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageRepositoryValidator")
  private ImageRepositoryValidator storageValidator;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageUsageLinkedValidator")
  private ImageUsageLinkedValidator usageValidator;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getType1()
   */
  @Override
  protected ImageType getType1()
  {
    return ImageType.JPG;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getType2()
   */
  @Override
  protected ImageType getType2()
  {
    return ImageType.PNG;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getDao()
   */
  @Override
  protected ImageStorageDaoStub getDao()
  {
    return this.dao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#getUsageDao()
   */
  @Override
  protected ImageUsageDaoStub getUsageDao()
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
  protected ImageRepositoryValidator getResourceValidator()
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
  protected ImageUsageLinkedValidator getUsageValidator()
  {
    return this.usageValidator;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.resource.ResourceRepositoryServiceTester#getRootPathPropertyInitializer()
   */
  @Override
  protected ImageRepositoryValidator getRootPathPropertyInitializer()
  {
    return this.getResourceValidator();
  }
}
