package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinFileResourceMultiPartStub extends Bid4WinFileResourceStub
       implements Bid4WinFileResourceMultiPart<String, Bid4WinObjectTypeStub1,
                                               Bid4WinFileResourcePartStub>
{
  /**
   *
   * TODO A COMMENTER
   */
  protected Bid4WinFileResourceMultiPartStub()
  {
     super();
  }
  /**
   *
   * TODO A COMMENTER
   * @param path TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param type TODO A COMMENTER
   */
  public Bid4WinFileResourceMultiPartStub(String path, String name, String type)
  {
    super(path, name, type);
  }

 /**
   *
   * TODO A COMMENTER
   * @param partType {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  public Bid4WinFileResourcePartStub getPart(Bid4WinObjectTypeStub1 partType)
  {
    return new Bid4WinFileResourcePartStub(this.getPath(), this.getName(), this.getType(), partType);
  }
}
