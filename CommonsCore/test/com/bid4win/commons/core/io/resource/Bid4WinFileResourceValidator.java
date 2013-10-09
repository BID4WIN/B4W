package com.bid4win.commons.core.io.resource;

import java.io.File;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinFileResourceValidator<RESOURCE extends Bid4WinFileResource<TYPE>,
                                                   TYPE>
       extends Bid4WinResourceValidator<RESOURCE, TYPE>
{
  /**
   * Nettoie entièrement le magasin des ressources à valider
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#cleanStore()
   */
  @Override
  protected final void cleanStore() throws UserException
  {
    UtilFile.removeAll(new File(this.getStore().getRootPath()));
  }

  /**
   * Getter du magasin des ressources à valider
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#getStore()
   */
  @Override
  public abstract Bid4WinFileResourceStore<RESOURCE, TYPE> getStore();
}
