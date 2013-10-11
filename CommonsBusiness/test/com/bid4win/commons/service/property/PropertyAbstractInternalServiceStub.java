package com.bid4win.commons.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.manager.property.PropertyAbstractManagerStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.property.PropertyAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstractStub;
import com.bid4win.commons.service.connection.SessionDataAbstractStub;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
@Component("PropertyAbstractInternalServiceStub")
@Scope("singleton")
public class PropertyAbstractInternalServiceStub
       extends PropertyAbstractInternalService_<PropertyAbstractStub, PropertyRootAbstractStub,
                                                SessionDataAbstractStub, AccountAbstractStub,
                                                PropertyAbstractInternalServiceStub>
{
  /** Référence TODO */
  @Autowired
  @Qualifier("PropertyAbstractManagerStub")
  private PropertyAbstractManagerStub manager;

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
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getManager()
   */
  @Override
  protected PropertyAbstractManagerStub getManager()
  {
    return this.manager;
  }

}
