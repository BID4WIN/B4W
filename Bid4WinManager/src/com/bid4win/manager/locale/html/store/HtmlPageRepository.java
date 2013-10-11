//package com.bid4win.manager.locale.html.store;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("HtmlPageRepository")
//@Scope("singleton")
//public class HtmlPageRepository extends HtmlPageFileStore<HtmlPageStorage>
//{
//  /** Référence du magasin de langues de pages HTML */
//  @Autowired
//  @Qualifier("HtmlPageRepositoryPart")
//  private HtmlPageRepositoryPart partStore = null;
//
//  /**
//   * Getter du magasin de gestion de stockage des langues de pages HTML sous forme
//   * de fichiers
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.locale.html.store.HtmlPageFileStore#getPartStore()
//   */
//  @Override
//  public HtmlPageRepositoryPart getPartStore()
//  {
//    return this.partStore;
//  }
//}
