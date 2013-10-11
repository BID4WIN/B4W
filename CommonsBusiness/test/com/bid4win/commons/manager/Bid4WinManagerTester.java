package com.bid4win.commons.manager;

import com.bid4win.commons.Bid4WinBusinessTester;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ID> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinManagerTester<ENTITY extends Bid4WinEntity<ENTITY, ID>,
                                           ID,
                                           ACCOUNT extends AccountAbstract<ACCOUNT>,
                                           GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinBusinessTester<ENTITY, ID, ACCOUNT, GENERATOR>
{
  /**
   * Getter du manager à tester
   * @return Le manager à tester
   */
  protected abstract Bid4WinManager_<ACCOUNT, ?> getManager();
}
