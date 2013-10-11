//package com.bid4win.service.locale.html;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.service.resource.ResourceRepositoryMultiPartService_;
//import com.bid4win.manager.locale.html.HtmlPageManager;
//import com.bid4win.persistence.entity.account.Account;
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
//@Component("HtmlPageService")
//@Scope("singleton")
//public class HtmlPageService
//       extends ResourceRepositoryMultiPartService_<HtmlPageStorage, HtmlPageUsage, HtmlPageType, Language, HtmlPage, Account>
//{
//  /** Référence du manager de gestion des pages HTML et de leurs utilisations */
//  @Autowired
//  @Qualifier("HtmlPageManager")
//  private HtmlPageManager manager = null;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.service.resource.ResourceRepositoryService_#getManager()
//   */
//  @Override
//  protected HtmlPageManager getManager()
//  {
//    return this.manager;
//  }
//}
