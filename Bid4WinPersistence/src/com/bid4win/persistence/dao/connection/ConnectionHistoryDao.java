package com.bid4win.persistence.dao.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.connection.ConnectionHistoryAbstractDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.ConnectionHistory;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ConnectionHistoryDao")
@Scope("singleton")
public class ConnectionHistoryDao
       extends ConnectionHistoryAbstractDao_<ConnectionHistory, Account>
{
  /**
   * Constructeur
   */
  protected ConnectionHistoryDao()
  {
    super(ConnectionHistory.class);
  }
}
