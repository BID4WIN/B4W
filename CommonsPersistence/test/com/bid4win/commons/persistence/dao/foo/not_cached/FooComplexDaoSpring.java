package com.bid4win.commons.persistence.dao.foo.not_cached;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooComplex;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooComplex_;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooEmbeddable;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooEmbeddable_;

/**
 * DAO pour les entités de la classe FooComplex<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooComplexDaoSpring extends FooDaoSpring<FooComplex>
{
  /**
   * Constructeur
   */
  protected FooComplexDaoSpring()
  {
    super(FooComplex.class);
  }

  /**
   * Cette fonction permet de récupérer la liste d'entités dont l'objets inclus
   * correspondant à la clé en argument
   * @param key Clé des objets inclus à rechercher
   * @return La liste d'entités récupérées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<FooComplex> findListByEmbeddedKey(String key) throws PersistenceException
  {
    return super.findList(this.getCriteriaForEmbeddedKey(key));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher des
   * entités dont l'objets inclus correspondant à la clé en argument
   * @param key Clé des objets inclus à rechercher
   * @return Les critères permettant de rechercher des entités en fonction de la
   * clé de leur objet inclus
   */
  protected CriteriaQuery<FooComplex> getCriteriaForEmbeddedKey(String key)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<FooComplex> criteria = this.createCriteria();
    Root<FooComplex> foo_ = criteria.from(this.getEntityClass());
    Path<String> embeddedKey_ = foo_.get(FooComplex_.embedded).get(FooEmbeddable_.key);
    Predicate condition = builder.equal(embeddedKey_, key);
    criteria.where(condition);

    Path<Bid4WinDate> date_ = foo_.get(FooAbstract_.date);
    Order order = builder.asc(date_);
    criteria.orderBy(order);
    return criteria;
  }

  /**
   * Cette fonction permet de récupérer la liste d'entités dont un des objets inclus
   * dans sa liste correspondant à la clé en argument
   * @param key Clé des objets inclus à rechercher
   * @return La liste d'entités récupérées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<FooComplex> findListByEmbeddedSetKey(String key) throws PersistenceException
  {
    return super.findList(this.getCriteriaForEmbeddedSetKey(key));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher des
   * entités dont un des objets inclus dans sa liste correspondant à la clé en argument
   * @param key Clé des objets inclus à rechercher
   * @return Les critères permettant de rechercher des entités en fonction de la
   * clé de leurs objets inclus
   */
  protected CriteriaQuery<FooComplex> getCriteriaForEmbeddedSetKey(String key)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<FooComplex> criteria = this.createCriteria();
    Root<FooComplex> foo_ = criteria.from(this.getEntityClass());

    Join<FooComplex, FooEmbeddable> embeddableSet_ = foo_.join(FooComplex_.embeddedSetInternal);

    Predicate condition = builder.equal(embeddableSet_, key);
    criteria.where(condition);

    Path<Bid4WinDate> date_ = foo_.get(FooAbstract_.date);
    Order order = builder.asc(date_);

    criteria.orderBy(order);
    criteria.groupBy(foo_.get(FooAbstract_.id));
    return criteria;
  }
}
