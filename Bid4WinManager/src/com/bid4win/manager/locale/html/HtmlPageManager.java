//package com.bid4win.manager.locale.html;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.manager.resource.ResourceRepositoryMultiPartManager_;
//import com.bid4win.commons.persistence.entity.account.security.Role;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.locale.Language;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//import com.bid4win.persistence.entity.locale.resource.HtmlPage;
//
///**
// * Manager de gestion des pages HTML et de leurs utilisations incluant la gestion
// * des transactions ainsi que celle des habilitations<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("HtmlPageManager")
//@Scope("singleton")
//public class HtmlPageManager
//       extends ResourceRepositoryMultiPartManager_<HtmlPageStorage, HtmlPageUsage, HtmlPageType, Language, HtmlPage, Account>
//{
//  /** Référence du manager interne de gestion des images et de leurs utilisations */
//  @Autowired
//  @Qualifier("HtmlPageManagerInternal")
//  private HtmlPageManagerInternal internal = null;
//
//  /**
//   * L'oscar du rôle d'administration des images et de leurs utilisations est
//   * attribué aux gestionnaires métier
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManager_#getAdminRole()
//   */
//  @Override
//  public Role getAdminRole()
//  {
//    return Role.I18N;
//  }
//  /**
//   * Getter de la référence du manager interne de gestion des produits
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getInternal()
//   */
//  @Override
//  protected HtmlPageManagerInternal getInternal()
//  {
//    return this.internal;
//  }
//}
