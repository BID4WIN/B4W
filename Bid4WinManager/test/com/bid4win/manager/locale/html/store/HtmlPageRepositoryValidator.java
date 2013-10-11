//package com.bid4win.manager.locale.html.store;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.manager.resource.store.Bid4WinFileRepositoryMultiPartValidator;
//import com.bid4win.persistence.entity.locale.Language;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.resource.HtmlPage;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("HtmlPageRepositoryValidator")
//@Scope("singleton")
//public class HtmlPageRepositoryValidator
//       extends Bid4WinFileRepositoryMultiPartValidator<HtmlPageStorage, HtmlPageType, Language, HtmlPage>
//{
//  /** Référence du magasin de pages HTML à valider */
//  @Autowired
//  @Qualifier("HtmlPageRepository")
//  private HtmlPageRepository store;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartValidator#getStore()
//   */
//  @Override
//  public HtmlPageRepository getStore()
//  {
//    return this.store;
//  }
//}
