package com.bid4win.commons.persistence.dao.account;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountAbstract_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple_;

/**
 * DAO g�n�rique pour les entit�s de la classe AccountBasedEntityMultiple<BR>
 * <BR>
 * @param <ENTITY> Entit� g�r�e par le DAO<BR>
 * @param <ID> Identifiant de l'entit� g�r�e par le DAO<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur li� � l'entit� g�r�e
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AccountBasedEntityMultipleDao_<ENTITY extends AccountBasedEntityMultiple<ENTITY, ID, ACCOUNT>,
                                           ID,
                                           ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityDao_<ENTITY, ID, ACCOUNT>
{
  /**
   * Constructeur
   * @param entityClass Classe de l'entit� g�r�e par le DAO
   */
  protected AccountBasedEntityMultipleDao_(Class<ENTITY> entityClass)
  {
    super(entityClass);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s en fonction de leur
   * compte utilisateur
   * @param account Compte utilisateur des entit�s � rechercher
   * @return La liste d'entit�s trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<ENTITY> findListByAccount(ACCOUNT account) throws PersistenceException
  {
    if(account == null)
    {
      return new Bid4WinList<ENTITY>(0);
    }
    return super.findList(this.getCriteriaForAccount(account));
  }
  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s en fonction de l'identifiant
   * de leur compte utilisateur
   * @param accountId Identifiant du compte utilisateur des entit�s � rechercher
   * @return La liste d'entit�s trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<ENTITY> findListByAccountId(String accountId) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAccountId(accountId));
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
    return root.get(AccountBasedEntityMultiple_.account).get(AccountAbstract_.id);
  }
}
