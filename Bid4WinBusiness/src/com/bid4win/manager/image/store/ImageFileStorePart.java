package com.bid4win.manager.image.store;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.manager.resource.store.Bid4WinApplicationStore;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.resource.Image;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ImageFileStorePart extends Bid4WinApplicationStore<Image, ImageType>
{
  /**
   * Cette méthode permet de récupérer les sous-répertoires de celui défini par
   * le chemin en paramètre. Une map vide sera retournée si l'emplacement donné
   * ne référence pas un répertoire ou n'existe pas
   * @param parentPath {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getSubdirectories(java.lang.String)
   */
  @Override
  public Bid4WinStringRecursiveMap getSubdirectories(String parentPath) throws UserException
  {
    Bid4WinSet<String> pathPrefixSet = new Bid4WinSet<String>();
    for(ImageType type : ImageType.getImageTypeSet())
    {
      pathPrefixSet.add(type.getPathPrefix());
    }
    Bid4WinStringRecursiveMap result = new Bid4WinStringRecursiveMap();
    for(String pathPrefix : pathPrefixSet)
    {
      result.add(super.getSubdirectories(UtilFile.concatRelativePath(
          ResourceRef.RESOURCE, pathPrefix, parentPath)));
    }
    return result;
  }
}
