package com.bid4win.persistence.dao.product;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.persistence.entity.product.Product;

/**
 * DAO pour les entités de la classe Product<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ProductDao")
@Scope("singleton")
public class ProductDao extends Bid4WinDao_<Product, String>
{
  /**
   *
   * TODO A COMMENTER
   */
  protected ProductDao()
  {
    super(Product.class);
  }

  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public Product getById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(id);
  }
  /**
   *
   * TODO A COMMENTER
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  public Bid4WinMap<String, Product> findById(Bid4WinSet<String> idSet) throws PersistenceException
  {
    return super.findById(idSet);
  }

  /**
   * Cette fonction permet de récupérer l'unique produit en fonction de son nom
   * @param name Nom du produit à rechercher
   * @return Le produit trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ne correspond au nom en argument
   */
/*  public Product getOneByName(String name) throws PersistenceException, NotFoundEntityException
  {
    return super.getOne(this.getCriteriaForName(name));
  }

  /**
   * Cette fonction permet de récupérer l'eventuel produit en fonction de son nom
   * @param name Nom du produit à rechercher
   * @return Le produit éventuellement trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
/*  public Product findOneByName(String name) throws PersistenceException
  {
    return super.findOne(this.getCriteriaForName(name));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher le
   * produit dont le nom  est précisé en argument
   * @param name Nom du à rechercher
   * @return Les critères permettant de rechercher le produit en fonction de son
   * nom
   */
/*  protected CriteriaQuery<Product> getCriteriaForName(String name)
  {
    CriteriaQuery<Product> criteria = this.createCriteria();
    Root<Product> root = criteria.from(this.getEntityClass());
    CriteriaBuilder builder = this.getCriteriaBuilder();
    Predicate condition = builder.equal(root.get(Product_.name), name);
    criteria.where(condition);
    return criteria;
  }*/
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<Product> findAll() throws PersistenceException
  {
    return new Bid4WinList<Product>(super.findAll(), true);
  }
  /**
   * Cette fonction permet d'ajouter le produit en argument
   * @param product {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public Product add(Product product) throws PersistenceException
  {
    return super.add(product);
  }
  /**
   * Cette fonction permet de forcer la modification du produit en argument
   * @param product {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#forceUpdate(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public Product forceUpdate(Product product) throws PersistenceException
  {
    return super.forceUpdate(product);
  }
  /**
   * Cette fonction permet de modifier le produit en argument
   * @param entity {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotPersistedEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public Product update(Product entity) throws PersistenceException, NotPersistedEntityException
  {
    return super.update(entity);
  }
  /**
   * Cette fonction permet de supprimer le produit en argument
   * @param product {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public Product remove(Product product) throws PersistenceException
  {
    return super.remove(product);
  }
}
