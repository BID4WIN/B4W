package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ResourceUsageMultiPartStub
       extends ResourceUsageMultiPart<ResourceUsageMultiPartStub, ResourceTypeStub, ResourceStorageMultiPartStub, Bid4WinObjectTypeStub1, ResourcePartStub>
{
  /**
   * Constructeur
   * @param path Emplacement d'utilisation de la ressource
   * @param name Nom d'utilisation de la ressource
   * @param storage Stockage de la ressource associée à cette utilisation
   * @throws ProtectionException Si le stockage de ressource en argument est protégé
   * @throws ModelArgumentException Si le stockage de ressource en argument est nul
   * @throws UserException Si le nom ou l'emplacement d'utilisation de la ressource
   * est invalide
   */
  public ResourceUsageMultiPartStub(String path, String name, ResourceStorageMultiPartStub storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, storage);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart#getPartTypeDefault()
   */
  @Override
  public Bid4WinObjectTypeStub1 getPartTypeDefault()
  {
    return ResourceStorageMultiPartStub.DEFAULT_PART_TYPE;
  }
  /**
   *
   * TODO A COMMENTER
   * @param partType {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
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
