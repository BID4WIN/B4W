package com.bid4win.commons.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.manager.resource.ResourceRepositoryManagerStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageStub;
import com.bid4win.commons.service.connection.SessionDataAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ResourceRepositoryServiceStub")
@Scope("singleton")
public class ResourceRepositoryServiceStub
       extends ResourceRepositoryService_<ResourceStorageStub, ResourceUsageStub,
                                          ResourceTypeStub, SessionDataAbstractStub,
                                          AccountAbstractStub, ResourceRepositoryServiceStub>
{
  /** Référence TODO */
  @Autowired
  @Qualifier("ResourceRepositoryManagerStub")
  private ResourceRepositoryManagerStub manager;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceRepositoryService_#getManager()
   */
  @Override
  protected ResourceRepositoryManagerStub getManager()
  {
    return this.manager;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.ADMIN;
  }
}
