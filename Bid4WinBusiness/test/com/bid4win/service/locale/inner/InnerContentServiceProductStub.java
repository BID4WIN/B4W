package com.bid4win.service.locale.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.manager.locale.inner.InnerContentManagerProductStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentServiceProductStub")
@Scope("singleton")
public class InnerContentServiceProductStub extends InnerContentService
{
  /** Référence du manager de gestion des produits */
  @Autowired
  @Qualifier("InnerContentManagerProductStub")
  private InnerContentManagerProductStub manager = null;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.locale.inner.InnerContentService#getManager()
   */
  @Override
  protected InnerContentManagerProductStub getManager()
  {
    return this.manager;
  }
}
