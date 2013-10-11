package com.bid4win.manager.locale.inner.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentRepository")
@Scope("singleton")
public class InnerContentRepository extends InnerContentFileStore<InnerContentStorage>
{
  /** Référence du magasin de langues de contenus */
  @Autowired
  @Qualifier("InnerContentRepositoryPart")
  private InnerContentRepositoryPart partStore = null;

  /**
   * Getter du magasin de gestion de stockage des langues de contenus sous forme
   * de fichiers
   * @return {@inheritDoc}
   * @see com.bid4win.manager.locale.inner.store.InnerContentFileStore#getPartStore()
   */
  @Override
  public InnerContentRepositoryPart getPartStore()
  {
    return this.partStore;
  }
}
