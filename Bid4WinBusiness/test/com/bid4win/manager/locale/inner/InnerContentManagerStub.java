package com.bid4win.manager.locale.inner;

import java.io.InputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentManagerStub")
@Scope("singleton")
public class InnerContentManagerStub extends InnerContentManager
{
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
  public InnerContentStorage createResource(String path, String name, InnerContentType type, InputStream inputStream)
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
  public InnerContentStorage updateResource(long id, InnerContentType type, InputStream inputStream)
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
  public InnerContentUsage createUsage(String path, String name, long storageId)
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
  public InnerContentUsage deleteUsage(long id) throws PersistenceException, NotFoundEntityException
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
}
