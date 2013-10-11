//package com.bid4win.manager.locale.html.store;
//
//import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
//import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore;
//import com.bid4win.commons.manager.resource.store.OutwardlyManagedFileResourceStore;
//import com.bid4win.persistence.entity.locale.Language;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.resource.HtmlPage;
//
///**
// * Cette classe défini le magasin de base des pages HTML stockées sous la forme
// * de fichiers. Il permet de gérer les différentes langues utilisables et leur
// * ressources associées<BR>
// * <BR>
// * @param <RESOURCE> Doit définir le type de ressources gérées<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public abstract class HtmlPageFileStore<RESOURCE extends Bid4WinFileResourceMultiPart<HtmlPageType, Language, HtmlPage>>
//       extends Bid4WinFileResourceMultiPartStore<RESOURCE, HtmlPageType, Language, HtmlPage>
//{
//  /**
//   * Getter de la langue de page HTML par défaut
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore#getPartTypeDefault()
//   */
//  @Override
//  public Language getPartTypeDefault()
//  {
//    return HtmlPageStorage.DEFAULT_LANGUAGE;
//  }
//
//  /**
//   * Getter du magasin de gestion de stockage de toutes les langues de page HTML
//   * stockées sous forme de fichiers
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore#getPartStore()
//   */
//  @Override
//  public abstract OutwardlyManagedFileResourceStore<HtmlPage, HtmlPageType> getPartStore();
//}
