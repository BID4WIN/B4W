package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntity;
import com.bid4win.commons.persistence.dao.IBid4WinDaoStub;

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
public interface IAccountBasedEntityDaoStub<ENTITY extends AccountBasedEntity<ENTITY, ID, ACCOUNT>,
                                            ID,
                                            ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IBid4WinDaoStub<ENTITY, ID>
{
  /**
   * Cette fonction permet de récupérer l'eventuelle entité en fonction de son
   * compte utilisateur
   * @param account Compte utilisateur de l'entité à rechercher
   * @return L'entité éventuellement trouvée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public ENTITY findOneByAccount(ACCOUNT account) throws PersistenceException;
  /**
   * Cette fonction permet de récupérer l'eventuelle entité en fonction de l'
   * identifiant de son compte utilisateur
   * @param accountId Identifiant du compte utilisateur de l'entité à rechercher
   * @return L'entité éventuellement trouvée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public ENTITY findOneByAccountId(String accountId) throws PersistenceException;
}
