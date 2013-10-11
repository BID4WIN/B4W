package com.bid4win.commons.manager.resource;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.resource.ResourceStorageDaoStub;
import com.bid4win.commons.persistence.dao.resource.ResourceUsageDaoStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ResourceRepositoryManagerStub")
@Scope("singleton")
public class ResourceRepositoryManagerStub
       extends ResourceRepositoryManager_<ResourceStorageStub, ResourceUsageStub, ResourceTypeStub, AccountAbstractStub>
{
  /** Référence TODO */
  @Autowired
  @Qualifier("ResourceStorageDaoStub")
  private ResourceStorageDaoStub resourceDao;
  /** Référence TODO */
  @Autowired
  @Qualifier("ResourceUsageDaoStub")
  private ResourceUsageDaoStub usageDao;
  /** Référence TODO */
  @Autowired
  @Qualifier("ResourceStorageStoreStub")
  private ResourceStorageStoreStub repository;
  /** Référence TODO */
  @Autowired
  @Qualifier("ResourceUsageStoreStub")
  private ResourceUsageStoreStub store;

  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param type {@inheritDoc}
   * @param inputStream {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#createResource(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceType, java.io.InputStream)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ResourceStorageStub createResource(String path, String name, ResourceTypeStub type, InputStream inputStream)
         throws PersistenceException, CommunicationException, UserException
  {
    return super.createResource(path, name, type, inputStream);
  }
  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @param type {@inheritDoc}
   * @param inputStream {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#updateResource(long, com.bid4win.commons.persistence.entity.resource.ResourceType, java.io.InputStream)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ResourceStorageStub updateResource(long id, ResourceTypeStub type, InputStream inputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException, UserException
  {
    return super.updateResource(id, type, inputStream);
  }

  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param storageId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#createUsage(java.lang.String, java.lang.String, long)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ResourceUsageStub createUsage(String path, String name, long storageId)
         throws PersistenceException, UserException
  {
    return super.createUsage(path, name, storageId);
  }
  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#deleteUsage(long)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ResourceUsageStub deleteUsage(long id) throws PersistenceException, NotFoundEntityException
  {
    return super.deleteUsage(id);
  }

  /**
   *
   * TODO A COMMENTER
   * @param fullPath {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#loadUsageInStore(java.lang.String)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void loadUsageInStore(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    super.loadUsageInStore(fullPath);
  }


  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#createResourceEntity(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected ResourceStorageStub createResourceEntity(String path, String name, ResourceTypeStub type)
            throws UserException
  {
    return new ResourceStorageStub(path, name, type);
  }
  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#createUsageEntity(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceStorage)
   */
  @Override
  protected ResourceUsageStub createUsageEntity(String path, String name, ResourceStorageStub storage)
            throws PersistenceException, UserException
  {
    try
    {
      return new ResourceUsageStub(path, name, storage);
    }
    catch(ModelArgumentException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getResourceDao()
   */
  @Override
  protected ResourceStorageDaoStub getResourceDao()
  {
    return this.resourceDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getUsageDao()
   */
  @Override
  protected ResourceUsageDaoStub getUsageDao()
  {
    return this.usageDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#getRepository()
   */
  @Override
  protected ResourceStorageStoreStub getRepository()
  {
    return this.repository;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getStore()
   */
  @Override
  protected ResourceUsageStoreStub getStore()
  {
    return this.store;
  }
}
