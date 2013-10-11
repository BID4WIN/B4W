package com.bid4win.commons.persistence.dao.account;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntity;

/**
 * DAO générique pour les entités de la classe AccountBasedEntity<BR>
 * <BR>
 * @param <ENTITY> Entité gérée par le DAO<BR>
 * @param <ID> Identifiant de l'entité gérée par le DAO<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur lié à l'entité gérée
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedEntityDao_<ENTITY extends AccountBasedEntity<ENTITY, ID, ACCOUNT>,
                                            ID,
                                            ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinDao_<ENTITY, ID>
{
  /**
   * Constructeur
   * @param entityClass Classe de l'entité gérée par le DAO
   */
  protected AccountBasedEntityDao_(Class<ENTITY> entityClass)
  {
    super(entityClass);
  }

  /**
   * Cette fonction permet de récupérer l'eventuelle entité en fonction de son
   * compte utilisateur
   * @param account Compte utilisateur de l'entité à rechercher
   * @return L'entité éventuellement trouvée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public ENTITY findOneByAccount(ACCOUNT account) throws PersistenceException
  {
    return super.findOne(this.getCriteriaForAccount(account));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher les
   * entités dont le compte utilisateur est précisé en argument
   * @param account Compte utilisateur des entités à rechercher
   * @return Les critères permettant de rechercher les entités en fonction de leur
   * compte utilisateur
   */
  protected CriteriaQuery<ENTITY> getCriteriaForAccount(ACCOUNT account)
  {
    return this.getCriteriaForAccountId(account.getId());
  }
  /**
   * Cette fonction permet de récupérer l'eventuelle entité en fonction de l'
   * identifiant de son compte utilisateur
   * @param accountId Identifiant du compte utilisateur de l'entité à rechercher
   * @return L'entité éventuellement trouvée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public ENTITY findOneByAccountId(String accountId) throws PersistenceException
  {
    return super.findOne(this.getCriteriaForAccountId(accountId));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher les
   * entités dont l'identifiant du compte utilisateur est précisé en argument
   * @param accountId Identifiant du compte utilisateur des entités à rechercher
   * @return Les critères permettant de rechercher les entités en fonction de l'
   * identifiant de leur compte utilisateur
   */
  protected CriteriaQuery<ENTITY> getCriteriaForAccountId(String accountId)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<ENTITY> criteria = this.createCriteria();
    Root<ENTITY> entity_ = criteria.from(this.getEntityClass());
    Path<String> accountId_ = this.getAccountIdPath(entity_);
    Predicate condition = builder.equal(accountId_, accountId);
    criteria.where(condition);
    return criteria;
  }

  /**
   *
   * TODO A COMMENTER
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<String> getAccountIdPath(Root<ENTITY> root);

  /**
   * Cette fonction permet d'ajouter l'entité en argument
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public ENTITY add(ENTITY entity) throws PersistenceException
  {
    // Ajoute l'entité en paramètre
    return super.add(entity);
  }
}
