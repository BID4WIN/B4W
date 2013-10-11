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
 * @author Emeric Fill�tre
 */
@Component("ImageUsageStorePart")
@Scope("singleton")
public class ImageUsageStorePart extends ImageFileStorePart
{
  /**
   * Getter de la racine des fichiers stock�s
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
   * Cette m�thode permet de r�cup�rer les sous-r�pertoires de celui d�fini par
   * le chemin en param�tre. Une map vide sera retourn�e si l'emplacement donn�
   * ne r�f�rence pas un r�pertoire ou n'existe pas
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
