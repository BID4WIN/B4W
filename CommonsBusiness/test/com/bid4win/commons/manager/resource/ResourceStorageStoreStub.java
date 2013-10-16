package com.bid4win.commons.manager.resource;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ResourceStorageStoreStub")
@Scope("singleton")
public class ResourceStorageStoreStub
       extends Bid4WinFileResourceStore<ResourceStorageStub, ResourceTypeStub>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getRootPath()
   */
  @Override
  protected String getRootPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE,
                                       new File(UtilString.EMPTY).getAbsolutePath(),
                                       "test", "resources", "StorageStore");
  }

}
