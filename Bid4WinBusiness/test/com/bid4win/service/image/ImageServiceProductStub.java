package com.bid4win.service.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.manager.image.ImageManagerProductStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ImageServiceProductStub")
@Scope("singleton")
public class ImageServiceProductStub extends ImageService
{
  /** Référence du manager de gestion des produits */
  @Autowired
  @Qualifier("ImageManagerProductStub")
  private ImageManagerProductStub manager = null;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.image.ImageService#getManager()
   */
  @Override
  protected ImageManagerProductStub getManager()
  {
    return this.manager;
  }
}
