package com.bid4win.commons.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.j2ee.Bid4WinSelfReferer;

/***
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyAbstractServiceRefererStub")
@Scope("singleton")
public class PropertyAbstractServiceRefererStub
       extends Bid4WinSelfReferer<PropertyAbstractServiceStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param service {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferer#setBean(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  @Autowired
  @Qualifier("PropertyAbstractServiceStub")
  public void setBean(PropertyAbstractServiceStub service)
  {
    super.setBean(service);
  }
}
