package com.bid4win.commons.persistence.dao.connection;

import com.bid4win.commons.persistence.dao.account.IAccountBasedKeyDaoStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <REINIT> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IPasswordReinitAbstractDaoStub<REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                                ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IAccountBasedKeyDaoStub<REINIT,ACCOUNT>
{
  // Pas de méthode spécifique
}
