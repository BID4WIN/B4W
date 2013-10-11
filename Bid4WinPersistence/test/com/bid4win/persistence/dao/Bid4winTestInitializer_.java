package com.bid4win.persistence.dao;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ID> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4winTestInitializer_<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
       extends com.bid4win.commons.persistence.dao.Bid4winTestInitializer_<ENTITY, ID, Account, EntityGenerator>
{
  // Pas de définition supplémentaire
}
