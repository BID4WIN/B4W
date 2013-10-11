package com.bid4win.manager.locale.inner.store;

import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
import com.bid4win.manager.resource.store.OutwardlyManagedFileResourceStoreTester;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.resource.InnerContent;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class InnerContentFileStoreTester<RESOURCE extends Bid4WinFileResourceMultiPart<InnerContentType, Language, InnerContent>>
       extends OutwardlyManagedFileResourceStoreTester<RESOURCE, InnerContentType>
{
  /** TODO A COMMENTER */
  public static final InnerContentType TYPE1 = InnerContentType.JSP;
  /** TODO A COMMENTER */
  public static final InnerContentType TYPE2 = InnerContentType.JSP;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType1()
   */
  @Override
  protected InnerContentType getType1()
  {
    return InnerContentFileStoreTester.TYPE1;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType2()
   */
  @Override
  protected InnerContentType getType2()
  {
    return InnerContentFileStoreTester.TYPE2;
  }
}
