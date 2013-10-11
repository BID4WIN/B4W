package com.bid4win.persistence.dao.locale.inner;

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
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.resource.IResourceDaoStub;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage_Relations;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage_Relations;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage_Relations;
import com.bid4win.persistence.entity.product.Product_Relations;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentUsageDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class InnerContentUsageDaoStub extends InnerContentUsageDao
       implements IResourceDaoStub<InnerContentUsage, InnerContentType>
{
  /** TODO A COMMENTER */
  private Bid4WinList<Bid4WinRelationNode> nodeList = new Bid4WinList<Bid4WinRelationNode>();
  {
    try
    {
      Bid4WinRelationNode node = ResourceUsage_Relations.NODE_STORAGE.clone();
      node.addNode(ResourceStorage_Relations.NODE_USAGE_SET.clone());
      this.nodeList.add(node);
      node = InnerContentUsage_Relations.NODE_PRODUCT.clone();
      node.addNode(Product_Relations.NODE_INNER_CONTENT_USAGE_LIST.clone());
      this.nodeList.add(node);
    }
    catch(CloneNotSupportedException ex)
    {
      ex.printStackTrace();
    }
  }
  /////////////////////////////////////////////////////////////////////////////
  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#getById(java.lang.Long)
   */
  @Override
  public InnerContentUsage getById(Long id) throws PersistenceException
  {
    return super.getById(id).loadRelation(this.nodeList);
  }
  /**
   *
   * TODO A COMMENTER
   * @param fullPath {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#getOneByFullPath(java.lang.String)
   */
  @Override
  public InnerContentUsage getOneByFullPath(String fullPath) throws PersistenceException, UserException
  {
    return super.getOneByFullPath(fullPath).loadRelation(this.nodeList);
  }
  /**
   *
   * TODO A COMMENTER
   * @param fullPath {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#findOneByFullPath(java.lang.String)
   */
  @Override
  public InnerContentUsage findOneByFullPath(String fullPath) throws PersistenceException, UserException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByFullPath(fullPath),
                                                          this.nodeList);
  }
  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#findAll()
   */
  @Override
  public Bid4WinList<InnerContentUsage> findAll() throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll(),
                                                          this.nodeList);
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#add(com.bid4win.commons.persistence.entity.resource.ResourceUsage)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public InnerContentUsage add(InnerContentUsage root) throws PersistenceException
  {
    return super.add(root);
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#update(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public InnerContentUsage update(InnerContentUsage root) throws PersistenceException
  {
    return super.update(root);
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#remove(com.bid4win.commons.persistence.entity.resource.ResourceUsage)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public InnerContentUsage remove(InnerContentUsage root) throws PersistenceException
  {
    return super.remove(root);
  }
  /////////////////////////////////////////////////////////////////////////////
  ////////////////////////// Ajoutées pour les tests //////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
   * fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public InnerContentUsage findById(Long id) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id),
                                                          this.nodeList);
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
  public Bid4WinMap<Long, InnerContentUsage> getById(Bid4WinSet<Long> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet),
                                                          this.nodeList);
  }
  /**
   * Cette fonction permet de supprimer une entité en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public InnerContentUsage removeById(Long id) throws PersistenceException, NotFoundEntityException
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
  public Bid4WinSet<InnerContentUsage> removeListById(Bid4WinSet<Long> idSet)
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
  public Bid4WinList<InnerContentUsage> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }

  /** Référence du DAO des stockages de ressources */
  @Autowired
  @Qualifier("InnerContentStorageDaoStub")
  private InnerContentStorageDaoStub storageDao;
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#getStorageDao()
   */
  @Override
  public InnerContentStorageDaoStub getStorageDao()
  {
    return this.storageDao;
  }
}
