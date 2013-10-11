package com.bid4win.persistence.dao.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.connection.ConnectionHistory;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ConnectionDao")
@Scope("singleton")
public class ConnectionDao
       extends ConnectionAbstractDao_<Connection, ConnectionHistory, Account>
{
  /**
   * Constructeur
   */
  protected ConnectionDao()
  {
    super(Connection.class);
  }
}
