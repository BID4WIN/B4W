package com.bid4win.commons.persistence.dao.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ResourceStorageDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class ResourceStorageDaoStub
       extends ResourceStorageDao_<ResourceStorageStub, ResourceTypeStub, ResourceUsageStub>
       implements IResourceDaoStub<ResourceStorageStub, ResourceTypeStub>
{
  /**
   * Constructeur
   */
  protected ResourceStorageDaoStub()
  {
    super(ResourceStorageStub.class);
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
  public ResourceStorageStub getById(Long id) throws PersistenceException
  {
    return super.getById(id).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param fullPath {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException  {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#getOneByFullPath(java.lang.String)
   */
  @Override
  public ResourceStorageStub getOneByFullPath(String fullPath)
         throws PersistenceException, UserException
  {
    return super.getOneByFullPath(fullPath).loadRelation();
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
  public ResourceStorageStub findOneByFullPath(String fullPath)
         throws PersistenceException, UserException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByFullPath(fullPath));
  }
  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#findAll()
   */
  @Override
  public Bid4WinList<ResourceStorageStub> findAll() throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll());
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#add(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ResourceStorageStub add(ResourceStorageStub root) throws PersistenceException
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
  public ResourceStorageStub update(ResourceStorageStub root) throws PersistenceException
  {
    return super.update(root);
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#remove(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ResourceStorageStub remove(ResourceStorageStub root) throws PersistenceException
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
  public ResourceStorageStub findById(Long id) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
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
  public Bid4WinMap<Long, ResourceStorageStub> getById(Bid4WinSet<Long> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
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
  public ResourceStorageStub removeById(Long id) throws PersistenceException, NotFoundEntityException
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
  public Bid4WinSet<ResourceStorageStub> removeListById(Bid4WinSet<Long> idSet)
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
  public Bid4WinList<ResourceStorageStub> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }

  /** Référence du DAO des utilisations de ressources */
  private ResourceUsageDaoStub usageDao;
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#getStorageDao()
   */
  @Override
  public ResourceUsageDaoStub getUsageDao()
  {
    return this.usageDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @param usageDao TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  @Autowired
  @Qualifier("ResourceUsageDaoStub")
  public void setUsageDao(ResourceUsageDaoStub usageDao) throws ModelArgumentException
  {
    UtilObject.checkNull("usageDao", this.getUsageDao());
    this.usageDao = usageDao;
  }
}
