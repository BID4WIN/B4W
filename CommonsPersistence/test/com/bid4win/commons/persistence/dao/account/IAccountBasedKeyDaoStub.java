package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <KEY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IAccountBasedKeyDaoStub<KEY extends AccountBasedKey<KEY, ACCOUNT>,
                                         ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IAccountBasedEntitySingleDaoStub<KEY, String, ACCOUNT>
{
  // Pas de méthode spécifique
}
