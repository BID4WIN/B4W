package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.resource.store.ResourcePart;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ResourcePartStub
       extends ResourcePart<ResourcePartStub, ResourceTypeStub, Bid4WinObjectTypeStub1>
{
  /**
   * Constructeur à partir de la référence d'un stockage de ressource
   * @param resource Référence du stockage de ressource
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  public ResourcePartStub(ResourceStorageMultiPart<?, ResourceTypeStub, ?, Bid4WinObjectTypeStub1, ResourcePartStub> resource,
                          Bid4WinObjectTypeStub1 partType)
         throws ModelArgumentException, UserException
  {
    super(resource, partType);
  }
  /**
   * Constructeur à partir de la référence d'une utilisation de ressource
   * @param resource Référence de l'utilisation de ressource
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  public ResourcePartStub(ResourceUsageMultiPart<?, ResourceTypeStub, ?, Bid4WinObjectTypeStub1, ResourcePartStub> resource,
                          Bid4WinObjectTypeStub1 partType)
         throws ModelArgumentException, UserException
  {
    super(resource, partType);
  }
  /**
   * Constructeur à partir d'une portion de ressource
   * @param part Portion de ressource à partir de laquelle construire la nouvelle
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la portion de ressource en argument est nulle
   * @throws UserException Si le nom ou l'emplacement de stockage de la portion
   * de ressource est invalide ou le type de portion nul
   */
  public ResourcePartStub(ResourcePartStub part, Bid4WinObjectTypeStub1 partType)
         throws ModelArgumentException, UserException
  {
    super(part, partType);
  }

  /**
   *
   * TODO A COMMENTER
   * @param partType {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourcePart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  public ResourcePartStub getPart(Bid4WinObjectTypeStub1 partType) throws UserException
  {
    try
    {
      return new ResourcePartStub(this, partType);
    }
    catch(ModelArgumentException ex)
    {
      // Cette exception n'est lancée que si la ressource en argument est nulle
      return null;
    }
  }
}
