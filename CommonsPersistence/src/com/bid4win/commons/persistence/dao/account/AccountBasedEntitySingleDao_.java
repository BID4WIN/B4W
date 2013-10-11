package com.bid4win.commons.persistence.dao.account;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountAbstract_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntitySingle;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntitySingle_;

/**
 * DAO générique pour les entités de la classe AccountBasedEntitySingle<BR>
 * <BR>
 * @param <ENTITY> Entité gérée par le DAO<BR>
 * @param <ID> Identifiant de l'entité gérée par le DAO<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur lié à l'entité gérée
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AccountBasedEntitySingleDao_<ENTITY extends AccountBasedEntitySingle<ENTITY, ID, ACCOUNT>,
                                         ID,
                                         ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityDao_<ENTITY, ID, ACCOUNT>
{
  /**
   * Constructeur
   * @param entityClass Classe de l'entité gérée par le DAO
   */
  protected AccountBasedEntitySingleDao_(Class<ENTITY> entityClass)
  {
    super(entityClass);
  }

  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de son compte
   * utilisateur
   * @param account Compte utilisateur de l'entité à rechercher
   * @return L'entité trouvée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité ne correspond aux critères
   * en argument
   */
  public ENTITY getOneByAccount(ACCOUNT account) throws PersistenceException
  {
    return super.getOne(this.getCriteriaForAccount(account));
  }
  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de l'identifiant
   * de son compte utilisateur
   * @param accountId Identifiant du compte utilisateur de l'entité à rechercher
   * @return L'entité trouvée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité ne correspond aux critères
   * en argument
   */
  public ENTITY getOneByAccountId(String accountId) throws PersistenceException
  {
    return super.getOne(this.getCriteriaForAccountId(accountId));
  }

  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#getAccountIdPath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<String> getAccountIdPath(Root<ENTITY> root)
  {
    return root.get(AccountBasedEntitySingle_.account).get(AccountAbstract_.id);
  }
}
