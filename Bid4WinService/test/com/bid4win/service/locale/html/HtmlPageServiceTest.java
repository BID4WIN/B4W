//package com.bid4win.service.locale.html;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//
//import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
//import com.bid4win.manager.locale.html.HtmlPageManager;
//import com.bid4win.manager.locale.html.store.HtmlPageRepositoryValidator;
//import com.bid4win.manager.locale.html.store.HtmlPageUsageLinkedValidator;
//import com.bid4win.persistence.dao.locale.html.HtmlPageStorageDaoStub;
//import com.bid4win.persistence.dao.locale.html.HtmlPageUsageDaoStub;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//import com.bid4win.service.resource.ResourceRepositoryServiceTester;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@RunWith(Bid4WinJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
//public class HtmlPageServiceTest
//       extends ResourceRepositoryServiceTester<HtmlPageStorage, HtmlPageUsage, HtmlPageType>
//{
//  /** TODO A COMMENTER */
//  @Autowired
//  @Qualifier("HtmlPageService")
//  private InnerContentService service;
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
//   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getType1()
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
//   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getType2()
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
//   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#getService()
//   */
//  @Override
//  public InnerContentService getService()
//  {
//    return this.service;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManagerTester#getManager()
//   */
//  @Override
//  public HtmlPageManager getManager()
//  {
//    return this.getService().getManager();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getDao()
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
//   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#getUsageDao()
//   */
//   @Override
//  protected HtmlPageUsageDaoStub getUsageDao()
//  {
//    return this.usageDao;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.service.resource.ResourceServiceTester#getResourceValidator()
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
//   * @see com.bid4win.service.resource.ResourceRepositoryServiceTester#getRootPathPropertyInitializer()
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
//   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#getUsageValidator()
//   */
//  @Override
//  protected InnerContentUsageLinkedValidator getUsageValidator()
//  {
//    return this.usageValidator;
//  }
//}
