package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ResourceStorageMultiPartStub
       extends ResourceStorageMultiPart<ResourceStorageMultiPartStub, ResourceTypeStub, ResourceUsageMultiPartStub, Bid4WinObjectTypeStub1, ResourcePartStub>
{
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 DEFAULT_PART_TYPE = Bid4WinObjectTypeStub1.TYPE1;
  /**
   * Constructeur
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * ressource est invalide
   */
  public ResourceStorageMultiPartStub(String path, String name, ResourceTypeStub type)
         throws UserException
  {
    super(path, name, type);
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
