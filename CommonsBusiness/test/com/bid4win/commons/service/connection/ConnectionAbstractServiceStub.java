package com.bid4win.commons.service.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractStub;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ConnectionService")
@Scope("singleton")
public class ConnectionAbstractServiceStub
       extends ConnectionAbstractService_<ConnectionAbstractStub,
                                          ConnectionHistoryAbstractStub,
                                          PasswordReinitAbstractStub,
                                          SessionDataAbstractStub,
                                          AccountAbstractStub,
                                          ConnectionAbstractServiceStub>
{
  // TODO A COMMENTER
}
