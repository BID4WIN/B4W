package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ID> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IAccountBasedEntityMultipleDaoStub<ENTITY extends AccountBasedEntityMultiple<ENTITY, ID, ACCOUNT>,
                                                    ID,
                                                    ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IAccountBasedEntityDaoStub<ENTITY, ID, ACCOUNT>
{
  /**
   * Cette fonction permet de récupérer la liste d'entités en fonction de leur
   * compte utilisateur
   * @param account Compte utilisateur des entités à rechercher
   * @return La liste d'entités trouvées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<ENTITY> findListByAccount(ACCOUNT account) throws PersistenceException;
  /**
   * Cette fonction permet de récupérer la liste d'entités en fonction de l'identifiant
   * de leur compte utilisateur
   * @param accountId Identifiant du compte utilisateur des entités à rechercher
   * @return La liste d'entités trouvées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<ENTITY> findListByAccountId(String accountId) throws PersistenceException;
}
