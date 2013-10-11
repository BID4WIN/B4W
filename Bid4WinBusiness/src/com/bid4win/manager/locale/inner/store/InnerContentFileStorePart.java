package com.bid4win.manager.locale.inner.store;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.manager.resource.store.Bid4WinApplicationStore;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.resource.InnerContent;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class InnerContentFileStorePart extends Bid4WinApplicationStore<InnerContent, InnerContentType>
{
  /**
   * Cette m�thode permet de r�cup�rer les sous-r�pertoires de celui d�fini par
   * le chemin en param�tre. Une map vide sera retourn�e si l'emplacement donn�
   * ne r�f�rence pas un r�pertoire ou n'existe pas
   * @param parentPath {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getSubdirectories(java.lang.String)
   */
  @Override
  public Bid4WinStringRecursiveMap getSubdirectories(String parentPath) throws UserException
  {
    Bid4WinSet<String> pathPrefixSet = new Bid4WinSet<String>();
    for(InnerContentType type : InnerContentType.getInnerContentTypeSet())
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
