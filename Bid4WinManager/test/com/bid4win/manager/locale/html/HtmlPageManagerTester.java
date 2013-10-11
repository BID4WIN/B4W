//package com.bid4win.manager.locale.html;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import com.bid4win.manager.locale.html.store.HtmlPageRepositoryValidator;
//import com.bid4win.manager.locale.html.store.HtmlPageUsageLinkedValidator;
//import com.bid4win.manager.resource.ResourceRepositoryManagerTester;
//import com.bid4win.persistence.dao.locale.html.HtmlPageStorageDaoStub;
//import com.bid4win.persistence.dao.locale.html.HtmlPageUsageDaoStub;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public abstract class HtmlPageManagerTester
//       extends ResourceRepositoryManagerTester<HtmlPageStorage, HtmlPageUsage, HtmlPageType>
//{
//  /** TODO A COMMENTER */
//  @Autowired
//  @Qualifier("HtmlPageStorageDaoStub")
//  private HtmlPageStorageDaoStub dao;
//  /** TODO A COMMENTER */
//  @Autowired
//  @Qualifier("HtmlPageUsageDaoStub")
//  private HtmlPageUsageDaoStub usageDao;
//  /** TODO A COMMENTER */
//  @Autowired
//  @Qualifier("HtmlPageRepositoryValidator")
//  private InnerContentRepositoryValidator storageValidator;
//  /** TODO A COMMENTER */
//  @Autowired
//  @Qualifier("HtmlPageUsageLinkedValidator")
//  private InnerContentUsageLinkedValidator usageValidator;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getType1()
//   */
//  @Override
//  protected HtmlPageType getType1()
//  {
//    return HtmlPageType.HTML;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getType2()
//   */
//  @Override
//  protected HtmlPageType getType2()
//  {
//    return HtmlPageType.HTML;
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getDao()
//   */
//  @Override
//  protected HtmlPageStorageDaoStub getDao()
//  {
//    return this.dao;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getUsageDao()
//   */
//  @Override
//  protected HtmlPageUsageDaoStub getUsageDao()
//  {
//    return this.usageDao;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceManagerTester#getResourceValidator()
//   */
//  @Override
//  protected InnerContentRepositoryValidator getResourceValidator()
//  {
//    return this.storageValidator;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.resource.ResourceRepositoryManagerTester#getRootPathPropertyInitializer()
//   */
//  @Override
//  protected InnerContentRepositoryValidator getRootPathPropertyInitializer()
//  {
//    return this.getResourceValidator();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getUsageValidator()
//   */
//  @Override
//  protected InnerContentUsageLinkedValidator getUsageValidator()
//  {
//    return this.usageValidator;
//  }
//}
