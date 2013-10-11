package com.bid4win.communication.action.directory;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.factory.JSONStringRecursiveMapFactory;
import com.bid4win.service.image.ImageService;

/**
 * Common Directory action class
 *
 * @author Maxime Ollagnier
 */
public class DirectoryAction extends JSONAction
{
  /** Serial number */
  private static final long serialVersionUID = 896193008231482318L;

  /** Image store type */
  private static final String IMAGE_STORE_TYPE = "image";

  /** HTML page store type */
  private static final String HTML_PAGE_STORE_TYPE = "html_page";

  /** Image service reference */
  @Autowired
  @Qualifier("ImageService")
  private ImageService imageService = null;

  /**
   * Renvoie l'arborescence des sous répertoires du path spécifié pour le type
   * de magasin spécifié
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("GetDirectoryAction")
  public String GetDirectoryAction() throws Throwable, UserException
  {
    String storeType = this.getParameter("storeType");
    String path = this.findParameter("path");
    if(path == null)
    {
      path = "";
    }
    Bid4WinStringRecursiveMap directoryNameRecursiveMap = null;
    if(storeType.equals(IMAGE_STORE_TYPE))
    {
      directoryNameRecursiveMap = this.getImageService().getSubdirectories(path);
    }
    else if(storeType.equals(HTML_PAGE_STORE_TYPE))
    {
      // HTML page
    }
    else
    {
      throw new UserException(ResourceRef.RESOURCE_TYPE_INVALID_ERROR);
    }
    this.putJSONObject(JSONStringRecursiveMapFactory.getInstance().get(directoryNameRecursiveMap));
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
