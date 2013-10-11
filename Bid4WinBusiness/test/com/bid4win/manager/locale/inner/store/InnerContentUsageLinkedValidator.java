package com.bid4win.manager.locale.inner.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.manager.resource.store.FileResourceUsageMultiPartLinkedValidator;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.locale.resource.InnerContent;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentUsageLinkedValidator")
@Scope("singleton")
public class InnerContentUsageLinkedValidator
       extends FileResourceUsageMultiPartLinkedValidator<InnerContentUsage, InnerContentType, InnerContentStorage, Language, InnerContent>
{
  /** Référence du magasin de stockage de pages HTML */
  @Autowired
  @Qualifier("InnerContentRepository")
  private InnerContentRepository repository = null;
  /** Référence du magasin de pages HTML à valider */
  @Autowired
  @Qualifier("InnerContentUsageStore")
  private InnerContentUsageStore store;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartValidator#getStore()
   */
  @Override
  public InnerContentUsageStore getStore()
  {
    return this.store;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.store.FileResourceUsageMultiPartLinkedValidator#getRepository()
   */
  @Override
  public InnerContentRepository getRepository()
  {
    return this.repository;
  }
}
