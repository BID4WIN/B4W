package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntitySingle;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;

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
public interface IAccountBasedEntitySingleDaoStub<ENTITY extends AccountBasedEntitySingle<ENTITY, ID, ACCOUNT>,
                                                  ID,
                                                  ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IAccountBasedEntityDaoStub<ENTITY, ID, ACCOUNT>
{
  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de son compte
   * utilisateur
   * @param account Compte utilisateur de l'entité à rechercher
   * @return L'entité trouvée
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité ne correspond aux critères
   * en argument
   */
  public ENTITY getOneByAccount(ACCOUNT account) throws PersistenceException;
  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de l'identifiant
   * de son compte utilisateur
   * @param accountId Identifiant du compte utilisateur de l'entité à rechercher
   * @return L'entité trouvée
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité ne correspond aux critères
   * en argument
   */
  public ENTITY getOneByAccountId(String accountId) throws PersistenceException;
}
