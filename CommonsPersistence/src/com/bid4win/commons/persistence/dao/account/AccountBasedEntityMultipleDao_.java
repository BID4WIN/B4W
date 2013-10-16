package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple_Fields;

/**
 * DAO générique pour les entités de la classe AccountBasedEntityMultiple<BR>
 * <BR>
 * @param <ENTITY> Entité gérée par le DAO<BR>
 * @param <ID> Identifiant de l'entité gérée par le DAO<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur lié à l'entité gérée
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AccountBasedEntityMultipleDao_<ENTITY extends AccountBasedEntityMultiple<ENTITY, ID, ACCOUNT>,
                                            ID,
                                            ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityDao_<ENTITY, ID, ACCOUNT>
{
  /**
   * Constructeur
   * @param entityClass Classe de l'entité gérée par le DAO
   */
  protected AccountBasedEntityMultipleDao_(Class<ENTITY> entityClass)
  {
    super(entityClass);
  }

  /**
   * Cette fonction permet de récupérer la liste d'entités en fonction de leur
   * compte utilisateur
   * @param account Compte utilisateur des entités à rechercher
   * @return La liste d'entités trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<ENTITY> findListByAccount(ACCOUNT account) throws PersistenceException
  {
    if(account == null)
    {
      return new Bid4WinList<ENTITY>(0);
    }
    return super.findList(this.getAccountIdData(account.getId()), null);
  }
  /**
   * Cette fonction permet de récupérer la liste d'entités en fonction de l'identifiant
   * de leur compte utilisateur
   * @param accountId Identifiant du compte utilisateur des entités à rechercher
   * @return La liste d'entités trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<ENTITY> findListByAccountId(String accountId) throws PersistenceException
  {
    return super.findList(this.getAccountIdData(accountId), null);
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
    return AccountBasedEntityMultiple_Fields.ACCOUNT;
  }


  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#getAccountIdPath(javax.persistence.criteria.Root)
   */
/*  @Override
  protected Path<String> getAccountIdPath(Root<ENTITY> root)
  {
    return root.get(AccountBasedEntityMultiple_.account).get(AccountAbstract_.id);
  }*/
}
