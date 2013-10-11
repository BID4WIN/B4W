package com.bid4win.commons.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstractStub;
import com.bid4win.commons.service.connection.SessionDataAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyAbstractServiceStub")
@Scope("singleton")
public class PropertyAbstractServiceStub
       extends PropertyAbstractService_<PropertyAbstractStub, PropertyRootAbstractStub,
                                        SessionDataAbstractStub, AccountAbstractStub,
                                        PropertyAbstractServiceStub>
{
  /** Référence TODO */
  @Autowired
  @Qualifier("PropertyAbstractInternalServiceStub")
  private PropertyAbstractInternalServiceStub internal;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractService_#getInternal()
   */
  @Override
  protected PropertyAbstractInternalServiceStub getInternal()
  {
    return this.internal;
  }
}
