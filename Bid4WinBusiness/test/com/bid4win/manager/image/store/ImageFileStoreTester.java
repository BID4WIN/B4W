package com.bid4win.manager.image.store;

import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
import com.bid4win.manager.resource.store.OutwardlyManagedFileResourceStoreTester;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.persistence.entity.image.resource.Image;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ImageFileStoreTester<RESOURCE extends Bid4WinFileResourceMultiPart<ImageType, Format, Image>>
       extends OutwardlyManagedFileResourceStoreTester<RESOURCE, ImageType>
{
  /** TODO A COMMENTER */
  public static final ImageType TYPE1 = ImageType.JPG;
  /** TODO A COMMENTER */
  public static final ImageType TYPE2 = ImageType.PNG;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType1()
   */
  @Override
  protected ImageType getType1()
  {
    return ImageFileStoreTester.TYPE1;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType2()
   */
  @Override
  protected ImageType getType2()
  {
    return ImageFileStoreTester.TYPE2;
  }
}
