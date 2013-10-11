package com.bid4win.commons.service.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.service.connection.SessionDataAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountService")
@Scope("singleton")
public class AccountAbstractServiceStub
       extends AccountAbstractService_<SessionDataAbstractStub, AccountAbstractStub,
                                       AccountAbstractServiceStub>
{
  // TODO A COMMENTER
}
