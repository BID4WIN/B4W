package com.bid4win.manager.resource;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IRootPathPropertyInitializer
{
  /**
   *
   * TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public void setUpProperty() throws PersistenceException, ModelArgumentException, UserException;
}
