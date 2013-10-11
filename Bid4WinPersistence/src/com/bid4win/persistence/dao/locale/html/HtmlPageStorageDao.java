//package com.bid4win.persistence.dao.locale.html;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.persistence.dao.resource.ResourceStorageDao_;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//
///**
// * DAO pour les entités de la classe HtmlPageStorage<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("HtmlPageStorageDao")
//@Scope("singleton")
//public class HtmlPageStorageDao extends ResourceStorageDao_<HtmlPageStorage, HtmlPageType, HtmlPageUsage>
//{
//  /** Référence du DAO des utilisations de page HTML */
//  @Autowired
//  @Qualifier("HtmlPageUsageDao")
//  private HtmlPageUsageDao usageDao;
//
//  /**
//   * Constructeur
//   */
//  protected HtmlPageStorageDao()
//  {
//    super(HtmlPageStorage.class);
//  }
//
//  /**
//   * Getter du DAO des utilisations de page HTML
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceStorageDao_#getUsageDao()
//   */
//  @Override
//  public HtmlPageUsageDao getUsageDao()
//  {
//    return this.usageDao;
//  }
//}
