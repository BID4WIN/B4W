//package com.bid4win.manager.locale.html.store;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.manager.resource.store.FileResourceUsageMultiPartLinkedValidator;
//import com.bid4win.persistence.entity.locale.Language;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//import com.bid4win.persistence.entity.locale.resource.HtmlPage;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("HtmlPageUsageLinkedValidator")
//@Scope("singleton")
//public class HtmlPageUsageLinkedValidator
//       extends FileResourceUsageMultiPartLinkedValidator<HtmlPageUsage, HtmlPageType, HtmlPageStorage, Language, HtmlPage>
//{
//  /** Référence du magasin de stockage de pages HTML */
//  @Autowired
//  @Qualifier("HtmlPageRepository")
//  private HtmlPageRepository repository = null;
//  /** Référence du magasin de pages HTML à valider */
//  @Autowired
//  @Qualifier("HtmlPageUsageStore")
//  private HtmlPageUsageStore store;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartValidator#getStore()
//   */
//  @Override
//  public HtmlPageUsageStore getStore()
//  {
//    return this.store;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.store.FileResourceUsageMultiPartLinkedValidator#getRepository()
//   */
//  @Override
//  public HtmlPageRepository getRepository()
//  {
//    return this.repository;
//  }
//}
