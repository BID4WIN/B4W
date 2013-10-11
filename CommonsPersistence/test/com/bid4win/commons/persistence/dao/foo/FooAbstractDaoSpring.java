package com.bid4win.commons.persistence.dao.foo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.commons.persistence.dao.IBid4WinDaoStub;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.core.EmbeddableDate;
import com.bid4win.commons.persistence.entity.foo.FooAbstract;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_;

/**
 * DAO pour les entités de la classe FooAbstract<BR>
 * <BR>
 * @param <FOO> Entité gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public abstract class FooAbstractDaoSpring<FOO extends FooAbstract<FOO>>
       extends Bid4WinDao_<FOO, Integer> implements IBid4WinDaoStub<FOO, Integer>
{
  /**
   * Constructeur pour spécialisation
   * @param entityClass Class de l'entité gérée par le DAO
   */
  protected FooAbstractDaoSpring(Class<FOO> entityClass)
  {
    super(entityClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#refresh(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public FOO refresh(FOO entity) throws PersistenceException
  {
    return super.refresh(entity);
  }

  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#lockById(java.lang.Object)
   */
  @Override
  public FOO lockById(Integer id) throws PersistenceException
  {
    return super.lockById(id);
  }

  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public FOO getById(Integer id) throws PersistenceException
  {
    return super.getById(id);
  }
  /**
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
   * fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public FOO findById(Integer id) throws PersistenceException
  {
    return super.findById(id);
  }
  /**
   * Cette fonction permet de récupérer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  public Bid4WinMap<Integer, FOO> getById(Bid4WinSet<Integer> idSet) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(idSet);
  }
  /**
   * Cette fonction permet de récupérer l'unique entité correspondant à la valeur
   * en argument
   * @param value Valeur de l'entité unique à récupérer
   * @return Entité récupérée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public FOO findOneByValue(String value) throws PersistenceException
  {
    return super.findOne(this.getCriteriaForValue(value));
  }
  /**
   * Cette fonction permet de récupérer la liste d'entités correspondant à la valeur
   * en argument
   * @param value Valeur des entités à récupérer
   * @return La liste d'entités récupérées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<FOO> findListByValue(String value) throws PersistenceException
  {
    return super.findList(this.getCriteriaForValue(value));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher des
   * entités en fonction de leur valeur
   * @param value Valeur des entités à rechercher
   * @return Les critères permettant de rechercher des entités en fonction de leur
   * valeur
   */
  protected CriteriaQuery<FOO> getCriteriaForValue(String value)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<FOO> criteria = this.createCriteria();
    Root<FOO> foo_ = criteria.from(this.getEntityClass());
    Path<String> value_ = foo_.get(FooAbstract_.value);
    Predicate condition = builder.equal(value_, value);
    criteria.where(condition);
    Path<Bid4WinDate> date_ = foo_.get(FooAbstract_.date);
    Order order = builder.asc(date_);
    criteria.orderBy(order);
    return criteria;
  }

  /**
   * Cette fonction permet de récupérer la liste d'entités correspondant à la date
   * en argument
   * @param date Date des entités à récupérer
   * @return La liste d'entités récupérées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<FOO> findListByDate(Bid4WinDate date) throws PersistenceException
  {// TODO
  /*  TypedQuery<FOO> query = this.getEntityManager().createQuery(
        "select f from Foo f where f.date >= :edate", this.getEntityClass()).setParameter("edate", date);
    CriteriaQuery<FOO> criteria = this.createCriteria();
    Root<FOO> root = criteria.from(this.getEntityClass());
    CriteriaBuilder builder = this.getCriteriaBuilder();
    Predicate condition = builder.greaterThanOrEqualTo(root.get(Foo_.date),
                                                       date);
                                                       //date.formatYYYYIMMIDD_HHIMMISSISSS());
    criteria.where(condition);
    Order order = this.getCriteriaBuilder().asc(root.get(Foo_.date));
    criteria.orderBy(order);
    TypedQuery<FOO> query2 = this.createQuery(criteria);

    List<FOO> result1 =query.getResultList();
    List<FOO> result2 =query2.getResultList();

    return /*query.getResultList();//*/
    return super.findList(this.getCriteriaForDate(date));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher des
   * entités en fonction de leur date
   * @param date Date des entités à rechercher
   * @return Les critères permettant de rechercher des entités en fonction de leur
   * date
   */
  protected CriteriaQuery<FOO> getCriteriaForDate(Bid4WinDate date)
  {
 /*   CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<FOO> criteria = this.createCriteria();
    Root<FOO> foo_ = criteria.from(this.getEntityClass());
    Path<String> date_ = builder.toString(foo_.get(FooAbstract_.date));

    */

    // TODO
    CriteriaBuilder builder = this.getCriteriaBuilder();
    CriteriaQuery<FOO> criteria = this.createCriteria();
    Root<FOO> root = criteria.from(this.getEntityClass());
    Predicate condition = builder.equal(root.get(FooAbstract_.date),
                                                       date);
                                                       //greaterThanOrEqualTo date.formatYYYYIMMIDD_HHIMMISSISSS());
    criteria.where(condition);
    Order order = builder.asc(root.get(FooAbstract_.date));
    criteria.orderBy(order);

/*    Hibernate.custom(DateUserType.class);*/
    //criteria.
    return criteria;
  }
  /**
   * Cette fonction permet de récupérer la liste d'entités correspondant à la date
   * en argument
   * @param date Date des entités à récupérer
   * @return La liste d'entités récupérées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<FOO> findListByEmbeddedDate(Bid4WinDate date) throws PersistenceException
  {
    try
    {
      return super.findList(this.getCriteriaForEmbeddedDate(date));
    }
    catch(ModelArgumentException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher des
   * entités en fonction de leur date
   * @param date Date des entités à rechercher
   * @return Les critères permettant de rechercher des entités en fonction de leur
   * date
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected CriteriaQuery<FOO> getCriteriaForEmbeddedDate(Bid4WinDate date) throws ModelArgumentException
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();
    CriteriaQuery<FOO> criteria = this.createCriteria();
    Root<FOO> foo_ = criteria.from(this.getEntityClass());
    Path<EmbeddableDate> embeddedDate = this.getEmbeddedDatePath(foo_);
    Predicate condition = builder.greaterThanOrEqualTo(embeddedDate, new EmbeddableDate(date));
      //builder.equal(embeddedDate, new EmbeddedDate(date));
    criteria.where(condition);
    Path<Bid4WinDate> date_ = foo_.get(FooAbstract_.date);
    Order order = builder.asc(date_);
    criteria.orderBy(order);
    return criteria;
  }
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<EmbeddableDate> getEmbeddedDatePath(Root<FOO> root);

  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<FOO> findAll() throws PersistenceException
  {
    return super.findAll();
  }

  /**
   * Cette fonction permet d'ajouter l'entité en argument
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FOO add(FOO entity) throws PersistenceException
  {
    return super.add(entity);
  }
  /**
   * Cette fonction permet d'ajouter l'entité en argument tout en simulant une
   * erreur et donc normalement un rollback de la transaction
   * @param entity Entité à ajouter
   * @throws PersistenceException Exception lancée volontairement et normalement
   * à l'origine du rollback
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void addWithRollback(FOO entity) throws PersistenceException
  {
    this.add(entity);
    throw new PersistenceException();
  }

  /**
   * Cette fonction permet de modifier l'entité en argument
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FOO update(FOO entity) throws PersistenceException
  {
    return super.update(entity);
  }

  /**
   * Cette fonction permet de supprimer l'entité en argument
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FOO remove(FOO entity) throws PersistenceException
  {
    return super.remove(entity);
  }

  /**
   * Cette fonction permet de supprimer une entité en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FOO removeById(Integer id) throws PersistenceException
  {
    return super.removeById(id);
  }

  /**
   * Cette fonction permet de supprimer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeListById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<FOO> removeListById(Bid4WinSet<Integer> idSet) throws PersistenceException, NotFoundEntityException
  {
    return super.removeListById(idSet);
  }

  /**
   * Cette fonction permet de supprimer la liste de toutes les entités
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<FOO> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }
}
