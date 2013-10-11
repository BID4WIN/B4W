package com.bid4win.commons.service.connection;

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
@Component("ConnectionServiceReferer")
@Scope("singleton")
public class ConnectionAbstractServiceRefererStub
       extends Bid4WinSelfReferer<ConnectionAbstractServiceStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param service {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferer#setBean(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  @Autowired
  @Qualifier("ConnectionService")
  public void setBean(ConnectionAbstractServiceStub service)
  {
    super.setBean(service);
  }
}
