package com.bid4win.commons.manager.resource.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.resource.FileResourceStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("OutwardlyManagedFileResourceStoreStub")
@Scope("singleton")
public class OutwardlyManagedFileResourceStoreStub
       extends OutwardlyManagedFileResourceStore<FileResourceStub, ResourceTypeStub>
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
