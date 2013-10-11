package com.bid4win.persistence.dao.sale;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.IAccountBasedEntityMultipleDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.sale.Sale;
import com.bid4win.persistence.entity.sale.Step;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <SALE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface ISaleDaoStub<SALE extends Sale<SALE>>
       extends IAccountBasedEntityMultipleDaoStub<SALE, String, Account>
{
  /**
   *
   * TODO A COMMENTER
   * @param stepArray TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinList<SALE> findListByStep(Step ... stepArray) throws PersistenceException;
}
