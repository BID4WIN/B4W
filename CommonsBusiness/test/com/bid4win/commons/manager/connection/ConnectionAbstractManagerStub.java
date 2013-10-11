package com.bid4win.commons.manager.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractStub;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstractStub;
import com.bid4win.commons.service.connection.SessionDataAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ConnectionManager")
@Scope("singleton")
public class ConnectionAbstractManagerStub
       extends ConnectionAbstractManager_<ConnectionAbstractStub,
                                          ConnectionHistoryAbstractStub,
                                          PasswordReinitAbstractStub,
                                          SessionDataAbstractStub,
                                          AccountAbstractStub>
{

  /**
   *
   * TODO A COMMENTER
   * @param session {@inheritDoc}
   * @param account {@inheritDoc}
   * @param remanent {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.connection.ConnectionAbstractManager_#createConnectionEntity(com.bid4win.commons.service.connection.SessionDataAbstract, com.bid4win.commons.persistence.entity.account.AccountAbstract, boolean)
   */
  @Override
  protected ConnectionAbstractStub createConnectionEntity(SessionDataAbstractStub session,
                                                          AccountAbstractStub account,
                                                          boolean remanent)
            throws UserException
  {
    return new ConnectionAbstractStub(
        session.getSessionId(), account, session.getIpAddress(), remanent);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.connection.ConnectionAbstractManager_#createReinitEntity(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected PasswordReinitAbstractStub createReinitEntity(AccountAbstractStub account)
            throws UserException
  {
    return new PasswordReinitAbstractStub(account);
  }

}
