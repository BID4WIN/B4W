//package com.bid4win.communication.action.htmlPage;
//
//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.InterceptorRef;
//import org.apache.struts2.convention.annotation.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.communication.action.json.JSONAction;
//import com.bid4win.communication.json.factory.htmlPage.JSONHtmlPageFactory;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.service.locale.html.HtmlPageService;
//
///**
// * Common HtmlPage action class
// *
// * @author Maxime Ollagnier
// */
//public class HtmlPageAction extends JSONAction
//{
//  /** Serial number */
//  private static final long serialVersionUID = 896193568231482318L;
//
//  /** HtmlPage service reference */
//  @Autowired
//  @Qualifier("HtmlPageService")
//  private HtmlPageService htmlPageService = null;
//
//  /**
//   * Renvoie la map d'objet htmlPage du répertoire dont le path est spécifié
//   *
//   * @return SUCCESS
//   * @throws UserException si une erreur utilisateur se produit.
//   * @throws Throwable si une erreur technique se produit.
//   */
//  @Action("GetHtmlPageAction")
//  public String GetHtmlPageAction() throws Throwable, UserException
//  {
//    String path = this.findParameter("path");
//    Bid4WinList<HtmlPageStorage> htmlPageStorageList = null;
//    if(path == null)
//    {
//      path = "";
//    }
//    htmlPageStorageList = this.getHtmlPageService().getResourceList(path);
//    this.putJSONObject(JSONHtmlPageFactory.getInstance().get(htmlPageStorageList));
//    this.setSuccess(true);
//    return SUCCESS;
//  }
//
//  /**
//   * Renvoi dans le flux de sortie de la Response l'htmlPage référencée par le
//   * paramètre "id" au format spécifié par le paramètre "format".
//   * TODO
//   *
//   * @return SUCCESS
//   * @throws UserException si une erreur utilisateur se produit.
//   * @throws Throwable si une erreur technique se produit.
//   */
//  public String GetHtmlPageTextAction() throws Throwable, UserException
//  {
////    long id = this.getParameterLong("id");
////    Language language = Language.getLanguage(this.getParameter("languageCode"));
////    try
////    {
////      // TODO writer
////      HtmlPage htmlPage = this.getHtmlPageService().loadPart(id, partType, outputstream)
////      this.setInputStream(this.getHtmlPageService().loadHtmlPage(id, format));
////    }
////    catch(PersistenceException e)
////    {
////      // TODO log warning
////      System.out.println("WARN - HtmlPageResource #" + id + " not found in store.");
////      InputStream is = this.getClass().getClassLoader().getResourceAsStream("forbidden_" + format.getCode().toLowerCase() + ".jpeg");
////      this.setInputStream(is);
////    }
////    this.setSuccess(true);
//    return SUCCESS;
//  }
//
//  /**
//   * Sauvegarde le flux uploadé au sein d'une ressource htmlPage.
//   *
//   * @return SUCCESS
//   * @throws UserException si une erreur utilisateur se produit.
//   * @throws Throwable si une erreur technique se produit.
//   */
//  @Action(value = "SaveHtmlPageFileAction", interceptorRefs = {@InterceptorRef("fileUpload"), @InterceptorRef("basicStack")}, results = {@Result(name = "success", location = "/WEB-INF/jsp/ajax/callback.jsp")})
//  public String SaveHtmlPageFileAction() throws Throwable, UserException
//  {
//<<<<<<< .mine
////    String path = this.getParameter("path");
////    String htmlPageName = this.getParameter("fileName");
////    FileInputStream fis = new FileInputStream(this.getUpload());
////    this.getHtmlPageService().createResource(path, htmlPageName, HtmlPageType.JPEG, fis);
////    fis.close();
////    this.setSuccess(true);
//=======
//    String path = this.getParameter("path");
//    String htmlPageName = this.getParameter("fileName");
//    FileInputStream fis = new FileInputStream(this.getUpload());
//    this.getHtmlPageService().createResource(path, htmlPageName, HtmlPageType.HTML, fis);
//    fis.close();
//    this.setSuccess(true);
//>>>>>>> .r1889
//    return SUCCESS;
//  }
//
//  /**
//   * Supprime l'htmlPage référencée par l'id spécifié
//   *
//   * @return SUCCESS
//   * @throws UserException si une erreur utilisateur se produit.
//   * @throws Throwable si une erreur technique se produit.
//   */
//  @Action("RemoveHtmlPageAction")
//  public String RemoveHtmlPageAction() throws Throwable, UserException
//  {
//    long id = this.getParameterLong("id");
//    this.getHtmlPageService().deleteResource(id);
//    this.setSuccess(true);
//    return SUCCESS;
//  }
//
//  /**
//   * Déplace et/ou renomme l'htmlPage référencée par l'id spécifié
//   *
//   * @return SUCCESS
//   * @throws UserException si une erreur utilisateur se produit.
//   * @throws Throwable si une erreur technique se produit.
//   */
//  @Action("MoveHtmlPageAction")
//  public String MoveHtmlPageAction() throws Throwable, UserException
//  {
//    long id = this.getParameterLong("id");
//    String path = this.getParameter("path");
//    String name = this.getParameter("name");
//    this.getHtmlPageService().moveResource(id, path, name);
//    this.setSuccess(true);
//    return SUCCESS;
//  }
//
//  /**
//   * Returns the HtmlPageService
//   *
//   * @return the HtmlPageService
//   */
//  protected HtmlPageService getHtmlPageService()
//  {
//    return this.htmlPageService;
//  }
//}
