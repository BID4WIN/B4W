package com.bid4win.manager.image.store;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ImageRepositoryPart")
@Scope("singleton")
public class ImageRepositoryPart extends ImageFileStorePart
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
    return this.getRepositoryPath();
  }
}
