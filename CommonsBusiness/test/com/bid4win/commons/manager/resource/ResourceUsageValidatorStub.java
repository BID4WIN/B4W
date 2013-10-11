package com.bid4win.commons.manager.resource;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinResourceValidator;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ResourceUsageValidatorStub")
@Scope("singleton")
public class ResourceUsageValidatorStub
       extends Bid4WinResourceValidator<ResourceUsageStub, ResourceTypeStub>
{
  /** Référence du magasin de ressources à valider */
  @Autowired
  @Qualifier("ResourceUsageStoreStub")
  private ResourceUsageStoreStub store;

  /**
   *
   * TODO A COMMENTER
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
  public ResourceUsageStoreStub getStore()
  {
    return this.store;
  }
}
