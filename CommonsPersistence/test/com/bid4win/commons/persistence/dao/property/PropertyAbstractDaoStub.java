package com.bid4win.commons.persistence.dao.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.property.PropertyAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract_Fields;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstractStub;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * Stub du DAO pour les entités de la classe PropertyAbstractStub<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyAbstractDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class PropertyAbstractDaoStub
       extends PropertyAbstractDao_<PropertyAbstractStub, PropertyRootAbstractStub>
       implements IPropertyAbstractDaoStub<PropertyAbstractStub, PropertyRootAbstractStub>
{
  /**
   * Constructeur
   */
  protected PropertyAbstractDaoStub()
  {
    super(PropertyAbstractStub.class);
  }

  /////////////////////////////////////////////////////////////////////////////
  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER
   * @param property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#add(com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyAbstractStub add(PropertyAbstractStub property) throws PersistenceException
  {
    return super.add(property);
  }
  /**
   *
   * TODO A COMMENTER
   * @param property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#update(com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyAbstractStub update(PropertyAbstractStub property) throws PersistenceException
  {
    return super.update(property).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#remove(com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyAbstractStub remove(PropertyAbstractStub property) throws PersistenceException
  {
    return super.remove(property).loadRelation();
  }
  /////////////////////////////////////////////////////////////////////////////
  ////////////////////////// Ajoutées pour les tests //////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Cette fonction permet de récupérer l'unique propriété racine en fonction de
   * son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public PropertyAbstractStub getById(Integer id) throws PersistenceException
  {
    return super.getById(id).loadRelation();
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
  public PropertyAbstractStub findById(Integer id) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
  }
  /**
   * Cette fonction permet de récupérer la propriété unique correspondant à la clé
   * en argument
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.IPropertyAbstractDaoStub#findOneByKey(java.lang.String)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyAbstractStub findOneByKey(String key) throws PersistenceException
  {
    PropertyAbstractStub property = super.findOne(this.getKeyData(key));
    return Bid4WinEntityLoader.getInstance().loadRelation(property);
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher la
   * propriété unique correspondant à la clé en paramètre
   * @param key Clé de la propriété à rechercher
   * @return Les critères permettant de rechercher la propriété unique correspondant
   * à la clé en paramètre
   */
  protected Bid4WinData<PropertyAbstractStub, String> getKeyData(String key)
  {
    return new Bid4WinData<PropertyAbstractStub, String>(PropertyAbstract_Fields.KEY,
                                                         key);
    /*CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<PropertyAbstractStub> criteria = this.createCriteria();
    Root<PropertyAbstractStub> property_ = criteria.from(this.getEntityClass());
    Path<String> key_ = property_.get(PropertyAbstract_.key);
    Predicate condition = builder.equal(key_, key);
    criteria.where(condition);
    return criteria;*/
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
  public Bid4WinMap<Integer, PropertyAbstractStub> getById(Bid4WinSet<Integer> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
  }
  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<PropertyAbstractStub> findAll() throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll());
  }
  /**
   * Cette fonction permet de supprimer une propriété en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyAbstractStub removeById(Integer id) throws PersistenceException
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
  public Bid4WinSet<PropertyAbstractStub> removeListById(Bid4WinSet<Integer> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return super.removeListById(idSet);
  }
  /**
   * Cette fonction permet de supprimer la liste de toutes les propriétés
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<PropertyAbstractStub> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }

  /** Référence du DAO des propriétés racines */
  @Autowired
  @Qualifier("PropertyRootAbstractDaoStub")
  private PropertyRootAbstractDaoStub rootDao;
  /**
   *
   * Getter du DAO des propriétés racines
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#getRootDao()
   */
  @Override
  public PropertyRootAbstractDaoStub getRootDao()
  {
    return this.rootDao;
  }
}
