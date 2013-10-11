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
 * DAO pour les entit�s de la classe FooComplex<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * Cette fonction permet de r�cup�rer la liste d'entit�s dont l'objets inclus
   * correspondant � la cl� en argument
   * @param key Cl� des objets inclus � rechercher
   * @return La liste d'entit�s r�cup�r�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinList<FooComplex> findListByEmbeddedKey(String key) throws PersistenceException
  {
    return super.findList(this.getCriteriaForEmbeddedKey(key));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s dont l'objets inclus correspondant � la cl� en argument
   * @param key Cl� des objets inclus � rechercher
   * @return Les crit�res permettant de rechercher des entit�s en fonction de la
   * cl� de leur objet inclus
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
   * Cette fonction permet de r�cup�rer la liste d'entit�s dont un des objets inclus
   * dans sa liste correspondant � la cl� en argument
   * @param key Cl� des objets inclus � rechercher
   * @return La liste d'entit�s r�cup�r�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinList<FooComplex> findListByEmbeddedSetKey(String key) throws PersistenceException
  {
    return super.findList(this.getCriteriaForEmbeddedSetKey(key));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s dont un des objets inclus dans sa liste correspondant � la cl� en argument
   * @param key Cl� des objets inclus � rechercher
   * @return Les crit�res permettant de rechercher des entit�s en fonction de la
   * cl� de leurs objets inclus
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
