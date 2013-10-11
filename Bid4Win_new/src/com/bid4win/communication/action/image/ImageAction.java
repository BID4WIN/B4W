package com.bid4win.communication.action.image;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.factory.image.JSONImageStorageFactory;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.service.image.ImageService;

/**
 * Common Image action class
 *
 * @author Maxime Ollagnier
 */
public class ImageAction extends JSONAction
{
  /** Serial number */
  private static final long serialVersionUID = 896193008231482318L;

  /** Image service reference */
  @Autowired
  @Qualifier("ImageService")
  private ImageService imageService = null;

  /**
   * Renvoie la map d'objet image du répertoire dont le path est spécifié
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("GetImageAction")
  public String GetImageAction() throws Throwable, UserException
  {
    String path = this.findParameter("path");
    Bid4WinList<ImageStorage> imageStorageList = null;
    if(path == null)
    {
      path = "";
    }
    imageStorageList = this.getImageService().getResourceList(path);
    this.putJSONObject(JSONImageStorageFactory.getInstance().get(imageStorageList));
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Renvoi dans le flux de sortie de la Response l'image référencée par le
   * paramètre "id" au format spécifié par le paramètre "format".
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action(value = "GetImageStreamAction", results = {@Result(name = "success", type = "stream", params = {"contentType", "image/jpeg", "bufferSize", "1024", "inputName", "inputStream"})})
  public String GetImageStreamAction() throws Throwable, UserException
  {
    long id = this.getParameterLong("id");
    Format format = Format.getFormat(this.getParameter("format"));
    try
    {
      this.setInputStream(this.getImageService().loadImage(id, format));
    }
    catch(PersistenceException e)
    {
      // TODO log warning
      System.out.println("WARN - ImageResource #" + id + " not found in store.");
      InputStream is = this.getClass().getClassLoader().getResourceAsStream("forbidden_" + format.getCode().toLowerCase() + ".jpeg");
      this.setInputStream(is);
    }
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Sauvegarde le flux uploadé au sein d'une ressource image.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action(value = "SaveImageFileAction", interceptorRefs = {@InterceptorRef("fileUpload"), @InterceptorRef("basicStack")}, results = {@Result(name = "success", location = "/WEB-INF/jsp/ajax/callback.jsp")})
  public String SaveImageFileAction() throws Throwable, UserException
  {
    String path = this.getParameter("path");
    String imageName = this.getParameter("fileName");
    FileInputStream fis = new FileInputStream(this.getUpload());
    this.getImageService().createResource(path, imageName, ImageType.JPEG, fis);
    fis.close();
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Supprime l'image référencée par l'id spécifié
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("RemoveImageAction")
  public String RemoveImageAction() throws Throwable, UserException
  {
    long id = this.getParameterLong("id");
    this.getImageService().deleteResource(id);
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Déplace et/ou renomme l'image référencée par l'id spécifié
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("MoveImageAction")
  public String MoveImageAction() throws Throwable, UserException
  {
    long id = this.getParameterLong("id");
    String path = this.getParameter("path");
    String name = this.getParameter("name");
    this.getImageService().moveResource(id, path, name);
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Returns the ImageService
   *
   * @return the ImageService
   */
  protected ImageService getImageService()
  {
    return this.imageService;
  }
}
