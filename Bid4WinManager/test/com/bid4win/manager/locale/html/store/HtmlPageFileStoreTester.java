//package com.bid4win.manager.locale.html.store;
//
//import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
//import com.bid4win.manager.resource.store.OutwardlyManagedFileResourceStoreTester;
//import com.bid4win.persistence.entity.locale.Language;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.resource.HtmlPage;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <RESOURCE> TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public abstract class HtmlPageFileStoreTester<RESOURCE extends Bid4WinFileResourceMultiPart<HtmlPageType, Language, HtmlPage>>
//       extends OutwardlyManagedFileResourceStoreTester<RESOURCE, HtmlPageType>
//{
//  /** TODO A COMMENTER */
//  public static final HtmlPageType TYPE1 = HtmlPageType.HTML;
//  /** TODO A COMMENTER */
//  public static final HtmlPageType TYPE2 = HtmlPageType.HTML;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType1()
//   */
//  @Override
//  protected HtmlPageType getType1()
//  {
//    return HtmlPageFileStoreTester.TYPE1;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType2()
//   */
//  @Override
//  protected HtmlPageType getType2()
//  {
//    return HtmlPageFileStoreTester.TYPE2;
//  }
//}
