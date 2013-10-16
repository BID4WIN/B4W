package com.bid4win.service;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.persistence.dao.account.AccountInitializer;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.service.connection.SessionData;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ID> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinServiceTester<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
       extends com.bid4win.commons.service.Bid4WinServiceTester<ENTITY, ID, SessionData, Account, Connection, EntityGenerator>
{
  /**
   *
   * TODO A COMMENTER
   * @return  TODO A COMMENTER
   */
  protected final AccountInitializer getAccountInitializer()
  {
    return (AccountInitializer)super.getAccountInitializer_();
  }
}
