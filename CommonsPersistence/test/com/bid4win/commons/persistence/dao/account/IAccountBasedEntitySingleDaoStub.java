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
 * @author Emeric Fill�tre
 */
public interface IAccountBasedEntitySingleDaoStub<ENTITY extends AccountBasedEntitySingle<ENTITY, ID, ACCOUNT>,
                                                  ID,
                                                  ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IAccountBasedEntityDaoStub<ENTITY, ID, ACCOUNT>
{
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� en fonction de son compte
   * utilisateur
   * @param account Compte utilisateur de l'entit� � rechercher
   * @return L'entit� trouv�e
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� ne correspond aux crit�res
   * en argument
   */
  public ENTITY getOneByAccount(ACCOUNT account) throws PersistenceException;
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� en fonction de l'identifiant
   * de son compte utilisateur
   * @param accountId Identifiant du compte utilisateur de l'entit� � rechercher
   * @return L'entit� trouv�e
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� ne correspond aux crit�res
   * en argument
   */
  public ENTITY getOneByAccountId(String accountId) throws PersistenceException;
}
