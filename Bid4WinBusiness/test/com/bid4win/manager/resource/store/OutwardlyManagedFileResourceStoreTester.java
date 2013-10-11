package com.bid4win.manager.resource.store;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.UtilFileTest;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER
 * @param <TYPE> TODO A COMMENTER
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class OutwardlyManagedFileResourceStoreTester<RESOURCE extends Bid4WinFileResource<TYPE>,
                                                              TYPE extends ResourceType<TYPE>>
       extends com.bid4win.commons.manager.resource.store.OutwardlyManagedFileResourceStoreTester<RESOURCE, TYPE>
{
  /** TODO A COMMENTER */
  public static final String FILE1 = "image.jpg";
  /** TODO A COMMENTER */
  public static final String FILE2 = "logo02.png";

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getFilename1()
   */
  @Override
  protected String getFilename1()
  {
    return OutwardlyManagedFileResourceStoreTester.FILE1;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getFilename2()
   */
  @Override
  protected String getFilename2()
  {
    return OutwardlyManagedFileResourceStoreTester.FILE2;
  }

  /**
   * Getter du repertoire de test
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getTestPath()
   */
  @Override
  protected String getTestPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE,
                                       UtilFileTest.getProjectPath("Bid4WinManager"),
                                       "test" , "resources");
  }
}
