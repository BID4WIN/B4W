package com.bid4win.manager.locale.inner.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.manager.resource.store.Bid4WinFileRepositoryMultiPartValidator;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.resource.InnerContent;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentRepositoryValidator")
@Scope("singleton")
public class InnerContentRepositoryValidator
       extends Bid4WinFileRepositoryMultiPartValidator<InnerContentStorage, InnerContentType, Language, InnerContent>
{
  /** Référence du magasin de pages HTML à valider */
  @Autowired
  @Qualifier("InnerContentRepository")
  private InnerContentRepository store;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartValidator#getStore()
   */
  @Override
  public InnerContentRepository getStore()
  {
    return this.store;
  }
}
