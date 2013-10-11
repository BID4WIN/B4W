//package com.bid4win.persistence.entity.locale.resource;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.io.UtilFile;
//import com.bid4win.commons.persistence.entity.resource.store.FileResourcePart;
//import com.bid4win.persistence.entity.locale.Language;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//
///**
// * Cette classe défini la portion de page HTML pour une langue utilisée sous forme
// * de fichier par les magasins de stockage correspondant<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class HtmlPage extends FileResourcePart<HtmlPage, HtmlPageType, Language>
//{
//  /** Chemin parent des pages HTML utilisé pour les positionner différemment des autres ressources */
//  public final static String PARENT_PATH = "html";
//
//  /**
//   * Constructeur à partir de la référence du stockage d'une page HTML
//   * @param storage Référence du stockage de page HTML
//   * @param language Langue de la page HTML
//   * @throws ModelArgumentException Si la ressource en argument est nulle
//   * @throws UserException Si le nom, l'emplacement de stockage ou la langue de
//   * la portion de page HTML est invalide
//   */
//  public HtmlPage(HtmlPageStorage storage, Language language)
//         throws ModelArgumentException, UserException
//  {
//    super(storage, language);
//  }
//  /**
//   * Constructeur à partir de la référence de l'utilisation d'une page HTML
//   * @param usage Référence de l'utilisation de page HTML
//   * @param language Langue de la page HTML
//   * @throws ModelArgumentException Si l'utilisation d'image en argument est nulle
//   * @throws UserException Si le nom, l'emplacement de stockage ou la langue de
//   * la portion de page HTML est invalide
//   */
//  public HtmlPage(HtmlPageUsage usage, Language language)
//         throws ModelArgumentException, UserException
//  {
//    super(usage, language);
//  }
//  /**
//   * Constructeur à partir d'une page HTML
//   * @param htmlPage Page HTML à partir de laquelle construire la nouvelle
//   * @param language Langue de la page HTML
//   * @throws ModelArgumentException Si la portion de page HTML en argument est nulle
//   * @throws UserException Si le nom ou l'emplacement de stockage de la page HTML
//   * est invalide ou la langue nulle
//   */
//  public HtmlPage(HtmlPage htmlPage, Language language)
//         throws ModelArgumentException, UserException
//  {
//    super(htmlPage, language);
//  }
//
//  /**
//   * Getter de la langue de la portion de page HTML
//   * @return La langue de la portion de page HTML
//   */
//  public Language getLanguage()
//  {
//    return this.getPartType();
//  }
//
//  /**
//   * Getter de la portion de page HTML correspondant à celle courante pour la langue
//   * donnée
//   * @param language {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.core.io.resource.Bid4WinResourcePart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
//   */
//  @Override
//  public HtmlPage getPart(Language language) throws UserException
//  {
//    try
//    {
//      return new HtmlPage(this, language);
//    }
//    catch(ModelArgumentException ex)
//    {
//      // Cette exception n'est lancée que si la ressource en argument est nulle
//      return null;
//    }
//  }
//  /**
//   * Redéfini la méthode pour positionner les images différemment des autres ressources
//   * @return {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.resource.Resource#getRealPath()
//   */
//  @Override
//  public String getRealPath() throws UserException
//  {
//    return UtilFile.concatRelativePath(
//        this.getMessageRef(), HtmlPage.PARENT_PATH, super.getRealPath());
//  }
//}
