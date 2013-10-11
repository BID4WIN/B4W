package com.bid4win.manager.locale.inner.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinResource;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentUsageStore")
@Scope("singleton")
public class InnerContentUsageStore extends InnerContentFileStore<InnerContentUsage>
{
  /** Référence du magasin de langues de contenus */
  @Autowired
  @Qualifier("InnerContentUsageStorePart")
  private InnerContentUsageStorePart partStore = null;

  /**
   *
   * TODO A COMMENTER
   * @param <STORE> {@inheritDoc}
   * @param <SOURCE> {@inheritDoc}
   * @param store {@inheritDoc}
   * @param source {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.image.store.ImageFileStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceStore, com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart)
   */
  @Override
  public <STORE extends Bid4WinResourceStore<SOURCE, InnerContentType>,
          SOURCE extends Bid4WinResource<InnerContentType>>
         void copy(STORE store, SOURCE source, InnerContentUsage resource)
         throws PersistenceException, UserException
  {
    if(store instanceof InnerContentRepository && source instanceof InnerContentStorage)
    {
      this.copy((InnerContentRepository)store, (InnerContentStorage)source, resource);
    }
    else
    {
      super.copy(store, source, resource);
    }
  }

  /**
   * Getter du magasin de gestion de stockage des langues de contenus sous forme
   * de fichiers
   * @return {@inheritDoc}
   * @see com.bid4win.manager.image.store.ImageFileStore#getPartStore()
   */
  @Override
  public InnerContentUsageStorePart getPartStore()
  {
    return this.partStore;
  }
}
