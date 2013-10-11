//package com.bid4win.commons.manager.connection;
//
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
//import com.bid4win.commons.persistence.entity.connection.IpAddress;
//import com.bid4win.commons.security.exception.SessionException;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("SessionHandler")
//@Scope("singleton")
//public class SessionHandlerAbstractStub
//       extends SessionHandlerAbstract<SessionDataAbstractStub, AccountAbstractStub>
//{
//  /**
//   *
//   * TODO A COMMENTER
//   * @param sessionId {@inheritDoc}
//   * @param ipAddress {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws SessionException {@inheritDoc}
//   * @see com.bid4win.commons.manager.connection.SessionHandlerAbstract#createSessionData(java.lang.String, com.bid4win.commons.persistence.entity.connection.IpAddress)
//   */
//  @Override
//  protected SessionDataAbstractStub createSessionData(String sessionId, IpAddress ipAddress)
//            throws SessionException
//  {
//    return new SessionDataAbstractStub(sessionId, ipAddress);
//  }
//}
