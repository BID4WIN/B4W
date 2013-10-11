package com.bid4win.persistence.dao.sale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.sale.Sale;
import com.bid4win.persistence.entity.sale.SaleStep;
import com.bid4win.persistence.entity.sale.Step;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <SALE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class SaleDao_<SALE extends Sale<SALE>>
       extends AccountBasedEntityMultipleDao_<SALE, String, Account>
{
  /**
   * Constructeur
   * @param saleClass Classe de la vente g�r�e par le DAO
   */
  protected SaleDao_(Class<SALE> saleClass)
  {
    super(saleClass);
  }

  /**
   * Cette fonction permet de verrouiller la vente dont l'identifiant est pass�
   * en param�tre
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#lockById(java.lang.Object)
   */
  @Override
  public SALE lockById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.lockById(id);
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique vente en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public SALE getById(String id) throws PersistenceException, NotFoundEntityException
  {
    // R�cup�re la vente aux ench�res avec l'identifiant en param�tre
    return super.getById(id);
  }

  /**
   * Cette m�thode permet de r�cup�rer toutes les ventes dont l'�tape appartient
   * � une de celles en argument ou toutes les ventes si aucune �tape n'est pr�cis�e
   * @param stepArray �tapes des ventes � r�cup�rer
   * @return Les ventes sont l'�tape appartient � une de celles en argument (c'
   * est � dire qu'elle ou un de ses parents correspond � une des �tapes donn�es)
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<SALE> findListByStep(Step ... stepArray) throws PersistenceException
  {
    return this.findList(this.getCriteriaForStep(stepArray));
  }
  /**
   *
   * TODO A COMMENTER
   * @param stepArray TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected CriteriaQuery<SALE> getCriteriaForStep(Step ... stepArray)
            throws PersistenceException
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<SALE> criteria = this.createCriteria();
    Root<SALE> sale_ = criteria.from(this.getEntityClass());
    if(stepArray.length != 0)
    {
      Path<SaleStep> saleStep_ = this.getSaleStepPath(sale_);
      Bid4WinSet<Step> stepSet = new Bid4WinSet<Step>();
      for(Step step : stepArray)
      {
        stepSet.add(step);
        stepSet.addAll(step.getRecursiveSubtypeSet());
      }
      Predicate[] predicates = new Predicate[stepSet.size()];
      int i = 0;
      try
      {
        for(Step step : stepSet)
        {
          predicates[i++] = builder.equal(saleStep_, new SaleStep(step));
        }
      }
      catch(UserException ex)
      {
        throw new PersistenceException(ex);
      }
      Predicate condition = builder.or(predicates);
      criteria.where(condition);
    }
    return criteria;
  }
  /**
   * A d�finir car :
   * Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si d�fini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<SaleStep> getSaleStepPath(Root<SALE> root);

  /**
   * Cette fonction permet d'ajouter la vente en argument
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public SALE add(SALE auction) throws PersistenceException
  {
    // Ajoute la vente en param�tre
    return super.add(auction);
  }
  /**
   * Cette fonction permet de modifier la vente en argument
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotPersistedEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public SALE update(SALE auction) throws PersistenceException, NotPersistedEntityException
  {
    // Modifie la vente en param�tre
    return super.update(auction);
  }
}
