package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntitySingle;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntitySingle_Fields;

/**
 * DAO g�n�rique pour les entit�s de la classe AccountBasedEntitySingle<BR>
 * <BR>
 * @param <ENTITY> Entit� g�r�e par le DAO<BR>
 * @param <ID> Identifiant de l'entit� g�r�e par le DAO<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur li� � l'entit� g�r�e
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AccountBasedEntitySingleDao_<ENTITY extends AccountBasedEntitySingle<ENTITY, ID, ACCOUNT>,
                                                   ID,
                                                   ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityDao_<ENTITY, ID, ACCOUNT>
{
  /**
   * Constructeur
   * @param entityClass Classe de l'entit� g�r�e par le DAO
   */
  protected AccountBasedEntitySingleDao_(Class<ENTITY> entityClass)
  {
    super(entityClass);
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique entit� en fonction de son compte
   * utilisateur
   * @param account Compte utilisateur de l'entit� � rechercher
   * @return L'entit� trouv�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entit� ne correspond aux crit�res
   * en argument
   */
  public ENTITY getOneByAccount(ACCOUNT account) throws PersistenceException
  {
    if(account == null)
    {
      throw new NotFoundEntityException(this.getEntityClass());
    }
    return super.getOne(this.getAccountIdData(account.getId()));
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� en fonction de l'identifiant
   * de son compte utilisateur
   * @param accountId Identifiant du compte utilisateur de l'entit� � rechercher
   * @return L'entit� trouv�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entit� ne correspond aux crit�res
   * en argument
   */
  public ENTITY getOneByAccountId(String accountId) throws PersistenceException
  {
    return super.getOne(this.getAccountIdData(accountId));
  }

  /**
  *
  * TODO A COMMENTER
  * @return {@inheritDoc}
  * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#getAccountFied()
  */
 @Override
 protected Bid4WinFieldSimple<? super ENTITY, ? super ACCOUNT> getAccountFied()
 {
   return AccountBasedEntitySingle_Fields.ACCOUNT;
 }

  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#getAccountIdPath(javax.persistence.criteria.Root)
   */
 /* @Override
  protected Path<String> getAccountIdPath(Root<ENTITY> root)
  {
    return root.get(AccountBasedEntitySingle_.account).get(AccountAbstract_.id);
  }*/
}
