//package com.bid4win.service.connection;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.j2ee.Bid4WinSelfReferer;
//
///***
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("ConnectionServiceReferer")
//@Scope("singleton")
//public class ConnectionServiceReferer extends Bid4WinSelfReferer<ConnectionService>
//{
//  /**
//   *
//   * TODO A COMMENTER
//   * @param bean {@inheritDoc}
//   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferer#setBean(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
//   */
//  @Override
//  @Autowired
//  @Qualifier("ConnectionService")
//  public void setBean(ConnectionService bean)
//  {
//    super.setBean(bean);
//  }
//}
