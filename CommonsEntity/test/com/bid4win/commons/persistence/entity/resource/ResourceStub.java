package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.core.exception.UserException;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
public class ResourceStub extends Resource<ResourceStub, ResourceTypeStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected ResourceStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * ressource est invalide
   */
  protected ResourceStub(String path, String name, ResourceTypeStub type) throws UserException
  {
    super(path, name, type);
  }
}
