package com.bid4win.service.locale.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.manager.locale.inner.store.InnerContentRepositoryValidator;
import com.bid4win.manager.locale.inner.store.InnerContentUsageLinkedValidator;
import com.bid4win.persistence.dao.locale.inner.InnerContentStorageDaoStub;
import com.bid4win.persistence.dao.locale.inner.InnerContentUsageDaoStub;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.service.resource.ResourceRepositoryServiceTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class InnerContentServiceTester
       extends ResourceRepositoryServiceTester<InnerContentStorage, InnerContentUsage, InnerContentType>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentStorageDaoStub")
  private InnerContentStorageDaoStub dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentUsageDaoStub")
  private InnerContentUsageDaoStub usageDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentRepositoryValidator")
  private InnerContentRepositoryValidator storageValidator;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentUsageLinkedValidator")
  private InnerContentUsageLinkedValidator usageValidator;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getType1()
   */
  @Override
  protected InnerContentType getType1()
  {
    return InnerContentType.JSP;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getType2()
   */
  @Override
  protected InnerContentType getType2()
  {
    return InnerContentType.JSP;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getDao()
   */
  @Override
  protected InnerContentStorageDaoStub getDao()
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
  protected InnerContentUsageDaoStub getUsageDao()
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
  protected InnerContentRepositoryValidator getResourceValidator()
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
  protected InnerContentUsageLinkedValidator getUsageValidator()
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
  protected InnerContentRepositoryValidator getRootPathPropertyInitializer()
  {
    return this.getResourceValidator();
  }
}
