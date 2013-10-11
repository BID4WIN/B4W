package com.bid4win.manager.image.store;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.persistence.entity.image.UsageType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ImageUsageStorePart")
@Scope("singleton")
public class ImageUsageStorePart extends ImageFileStorePart
{
  /**
   * Getter de la racine des fichiers stockés
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getRootPath()
   */
  @Override
  protected String getRootPath() throws UserException
  {
    return this.getWebSupPath();
  }

  /**
   * Cette méthode permet de récupérer les sous-répertoires de celui défini par
   * le chemin en paramètre. Une map vide sera retournée si l'emplacement donné
   * ne référence pas un répertoire ou n'existe pas
   * @param parentPath {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.image.store.ImageFileStorePart#getSubdirectories(java.lang.String)
   */
  @Override
  public Bid4WinStringRecursiveMap getSubdirectories(String parentPath) throws UserException
  {
    Bid4WinStringRecursiveMap result = new Bid4WinStringRecursiveMap();
    for(UsageType type : UsageType.getUsageTypeSet())
    {
      result.add(super.getSubdirectories(UtilFile.concatRelativePath(
          ResourceRef.RESOURCE, type.getCode().toLowerCase(), parentPath)));
    }
    return result;
  }
}
