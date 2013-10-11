package com.bid4win.manager.resource.store;

import java.io.File;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.core.reference.PropertyRef;
import com.bid4win.commons.manager.resource.store.OutwardlyManagedFileResourceStore;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.service.property.PropertyService;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> Doit d�finir le type de ressources g�r�es<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressources g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinApplicationStore<RESOURCE extends Bid4WinFileResource<TYPE>,
                                              TYPE extends ResourceType<TYPE>>
       extends OutwardlyManagedFileResourceStore<RESOURCE, TYPE>
{
  /** R�f�rence du service de gestion des propri�t�s */
  @Autowired
  @Qualifier("PropertyService")
  private PropertyService propertyService = null;

  /**
   * Cette m�thode permet de r�cup�rer l'emplacement racine du magasin de stockage
   * des ressources du projet
   * @return L'emplacement racine du magasin de stockage des ressources du projet
   * @throws UserException Si l'emplacement d�fini est invalide
   */
  protected String getRepositoryPath() throws UserException
  {
    try
    {
      return this.getRootPathProperty().getValue();
    }
    catch(Bid4WinException ex)
    {
      throw new UserException(ResourceRef.RESOURCE_STORAGE_ROOT_PROPERTY_MISSING_ERROR);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String getWebSupPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getWebContentPath(), "WEB-SUP");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String getWebInfPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getWebContentPath(), "WEB-INF");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String getWebContentPath() throws UserException
  {
    // R�cup�re l'emplacement de la classe courante
    URL classesUrl = this.getClass().getProtectionDomain().getCodeSource().getLocation();
    File classesFile = new File(classesUrl.getFile());
    if(UtilFile.getExtension(classesFile.getName(), ResourceRef.RESOURCE).equals("jar"))
    {
      classesFile = classesFile.getParentFile();
    }
    // Remonte de deux crans, correspondant � l'emplacement de WebContent
    return classesFile.getParentFile().getParentFile().getAbsolutePath();
  }


  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private PropertyService getPropertyService()
  {
    return this.propertyService;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  private Property getRootPathProperty() throws PersistenceException, UserException
  {
    return this.getPropertyService().getProperty(PropertyRef.SERVER_STORE_ROOT_LOCATION.getCode());
  }
}
