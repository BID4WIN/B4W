package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Cette classe d�fini le type d'une ressource<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ResourceType<CLASS extends ResourceType<CLASS>>
       extends Bid4WinObjectType<CLASS>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 5609000437063539644L;

  /** Extension li�e au type de ressource */
  private String extension = "";
  /** Potentiel classement sp�cifique du type de ressource */
  private String pathPrefix = "";

  /**
   * Constructeur d'un type de resource
   * @param code Code du type de resource
   * @param extension Extension li�e au type de ressource
   * @param pathPrefixes Potentiels classement sp�cifiques du type de ressource
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
   * @param extension Extension li�e au type de ressource
   * @param parent Parent du type de resource
   * @param pathPrefixes Potentiels classement sp�cifiques du type de ressource
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
   * Getter de l'extension li�e au type de ressource
   * @return L'extension li�e au type de ressource
   */
  public String getExtension()
  {
    return this.extension;
  }
  /**
   * Setter interne de l'extension li�e au type de ressource
   * @param extension Extension li�e au type de ressource
   */
  private void setExtension(String extension)
  {
    this.extension = UtilString.trimNotNull(extension).toLowerCase();
  }

  /**
   * Getter du potentiel classement sp�cifique du type de ressource
   * @return Le potentiel classement sp�cifique du type de ressource
   */
  public String getPathPrefix()
  {
    return this.pathPrefix;
  }
  /**
   * Setter interne du potentiel classement sp�cifique du type de ressource
   * @param pathPrefixes Potentiels classement sp�cifiques du type de ressource
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
