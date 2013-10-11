//package com.bid4win.manager.locale.html.store;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.io.resource.Bid4WinResource;
//import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
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
//@Component("HtmlPageUsageStore")
//@Scope("singleton")
//public class HtmlPageUsageStore extends HtmlPageFileStore<HtmlPageUsage>
//{
//  /** Référence du magasin de langues de pages HTML */
//  @Autowired
//  @Qualifier("HtmlPageUsageStorePart")
//  private HtmlPageUsageStorePart partStore = null;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param <STORE> {@inheritDoc}
//   * @param <SOURCE> {@inheritDoc}
//   * @param store {@inheritDoc}
//   * @param source {@inheritDoc}
//   * @param resource {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.manager.image.store.ImageFileStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceStore, com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart)
//   */
//  @Override
//  public <STORE extends Bid4WinResourceStore<SOURCE, HtmlPageType>,
//          SOURCE extends Bid4WinResource<HtmlPageType>>
//         void copy(STORE store, SOURCE source, HtmlPageUsage resource)
//         throws PersistenceException, UserException
//  {
//    if(store instanceof HtmlPageRepository && source instanceof HtmlPageStorage)
//    {
//      this.copy((HtmlPageRepository)store, (HtmlPageStorage)source, resource);
//    }
//    else
//    {
//      super.copy(store, source, resource);
//    }
//  }
//
//  /**
//   * Getter du magasin de gestion de stockage des langues de pages HTML sous forme
//   * de fichiers
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.image.store.ImageFileStore#getPartStore()
//   */
//  @Override
//  public HtmlPageUsageStorePart getPartStore()
//  {
//    return this.partStore;
//  }
//}
