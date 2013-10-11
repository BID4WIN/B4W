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
 * @author Emeric Fill�tre
 */
public interface IAccountBasedEntityMultipleDaoStub<ENTITY extends AccountBasedEntityMultiple<ENTITY, ID, ACCOUNT>,
                                                    ID,
                                                    ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IAccountBasedEntityDaoStub<ENTITY, ID, ACCOUNT>
{
  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s en fonction de leur
   * compte utilisateur
   * @param account Compte utilisateur des entit�s � rechercher
   * @return La liste d'entit�s trouv�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinList<ENTITY> findListByAccount(ACCOUNT account) throws PersistenceException;
  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s en fonction de l'identifiant
   * de leur compte utilisateur
   * @param accountId Identifiant du compte utilisateur des entit�s � rechercher
   * @return La liste d'entit�s trouv�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinList<ENTITY> findListByAccountId(String accountId) throws PersistenceException;
}
