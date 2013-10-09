package com.bid4win.commons.core.io.resource;

import java.io.File;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinFileResourceMultiPartValidator<RESOURCE extends Bid4WinFileResourceMultiPart<TYPE, PART_TYPE, PART>,
                                                            TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                            PART extends Bid4WinFileResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinResourceMultiPartValidator<RESOURCE, TYPE, PART_TYPE, PART>
{
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#cleanStore()
   */
  @Override
  protected final void cleanStore() throws Bid4WinException
  {
    UtilFile.removeAll(new File(this.getStore().getRootPath()));
  }
  /**
   *
   * TODO A COMMENTER
   * @param resource {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartValidator#getName(com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart)
   */
  @Override
  protected String getName(RESOURCE resource)
  {
    try
    {
      return resource.getFullPath();
    }
    catch(UserException ex)
    {
      return ex.getMessage();
    }
  }
  /**
   * Getter du magasin des ressources à valider
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartValidator#getStore()
   */
  @Override
  public abstract Bid4WinFileResourceMultiPartStore<RESOURCE, TYPE, PART_TYPE, PART> getStore();
}
