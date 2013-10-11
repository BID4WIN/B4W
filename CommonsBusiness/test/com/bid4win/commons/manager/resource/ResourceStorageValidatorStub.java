package com.bid4win.commons.manager.resource;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinResourceValidator;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ResourceStorageValidatorStub")
@Scope("singleton")
public class ResourceStorageValidatorStub
       extends Bid4WinResourceValidator<ResourceStorageStub, ResourceTypeStub>
{
  /** Référence du magasin de ressources à valider */
  @Autowired
  @Qualifier("ResourceStorageStoreStub")
  private ResourceStorageStoreStub store;

  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#cleanStore()
   */
  @Override
  public void cleanStore() throws Bid4WinException
  {
    UtilFile.removeAll(new File(this.getStore().getRootPath()));
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#getStore()
   */
  @Override
  public ResourceStorageStoreStub getStore()
  {
    return this.store;
  }
}
