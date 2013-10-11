package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Cette classe défini le type d'une ressource<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ResourceType<CLASS extends ResourceType<CLASS>>
       extends Bid4WinObjectType<CLASS>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 5609000437063539644L;

  /** Extension liée au type de ressource */
  private String extension = "";
  /** Potentiel classement spécifique du type de ressource */
  private String pathPrefix = "";

  /**
   * Constructeur d'un type de resource
   * @param code Code du type de resource
   * @param extension Extension liée au type de ressource
   * @param pathPrefixes Potentiels classement spécifiques du type de ressource
   */
  protected ResourceType(String code, String extension, String ... pathPrefixes)
  {
    super(code);
    this.setExtension(extension);
    this.setPathPrefix(pathPrefixes);
  }
  /**
   * Constructeur d'un type de resource
   * @param code Code du type de resource
   * @param extension Extension liée au type de ressource
   * @param parent Parent du type de resource
   * @param pathPrefixes Potentiels classement spécifiques du type de ressource
   */
  protected ResourceType(String code, String extension, CLASS parent, String ... pathPrefixes)
  {
    super(code, parent);
    this.setExtension(extension);
    this.setPathPrefix(pathPrefixes);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#getMessageRefBase()
   */
  @Override
  public MessageRef getMessageRefBase()
  {
    return ResourceRef.RESOURCE_TYPE;
  }

  /**
   * Getter de l'extension liée au type de ressource
   * @return L'extension liée au type de ressource
   */
  public String getExtension()
  {
    return this.extension;
  }
  /**
   * Setter interne de l'extension liée au type de ressource
   * @param extension Extension liée au type de ressource
   */
  private void setExtension(String extension)
  {
    this.extension = UtilString.trimNotNull(extension).toLowerCase();
  }

  /**
   * Getter du potentiel classement spécifique du type de ressource
   * @return Le potentiel classement spécifique du type de ressource
   */
  public String getPathPrefix()
  {
    return this.pathPrefix;
  }
  /**
   * Setter interne du potentiel classement spécifique du type de ressource
   * @param pathPrefixes Potentiels classement spécifiques du type de ressource
   */
  private void setPathPrefix(String ... pathPrefixes)
  {
    try
    {
      this.pathPrefix = UtilFile.concatRelativePath(ResourceRef.RESOURCE,
                                                    pathPrefixes).toLowerCase();
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
    }
  }
}
