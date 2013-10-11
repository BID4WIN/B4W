package com.bid4win.manager.locale.inner.store;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentRepositoryPart")
@Scope("singleton")
public class InnerContentRepositoryPart extends InnerContentFileStorePart
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
