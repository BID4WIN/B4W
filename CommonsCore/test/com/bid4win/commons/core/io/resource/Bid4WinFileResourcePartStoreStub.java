package com.bid4win.commons.core.io.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("Bid4WinFileResourcePartStoreStub")
@Scope("singleton")
public class Bid4WinFileResourcePartStoreStub
       extends Bid4WinFileResourceStore<Bid4WinFileResourcePartStub, String>
{
  /** Racine des fichiers stockés */
  @Autowired
  @Qualifier("FileStoreRootPath")
  private String rootPath = null;

  /**
   * Getter de la racine des fichiers stockés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getRootPath()
   */
  @Override
  protected String getRootPath()
  {
    return this.rootPath;
  }
}

