package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinFileResourcePartStub extends Bid4WinFileResourceStub
       implements Bid4WinFileResourcePart<Bid4WinFileResourcePartStub,
                                          String, Bid4WinObjectTypeStub1>
{
  /** TODO A COMMENTER */
  private Bid4WinObjectTypeStub1 partType;

  /**
   *
   * TODO A COMMENTER
   */
  protected Bid4WinFileResourcePartStub()
  {
    super();
  }
  /**
   *
   * TODO A COMMENTER
   * @param path TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @param partType TODO A COMMENTER
   */
  public Bid4WinFileResourcePartStub(String path, String name, String type, Bid4WinObjectTypeStub1 partType)
  {
    super(path, name, type);
    this.definePartType(partType);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStub#getFullPath()
   */
  @Override
  public String getFullPath() throws UserException
  {
    return super.getFullPath() + "_" + this.getPartType().getCode();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourcePart#getPartType()
   */
  @Override
  public Bid4WinObjectTypeStub1 getPartType()
  {
    return this.partType;
  }
  /**
   *
   * TODO A COMMENTER
   * @param partType TODO A COMMENTER
   */
  public void definePartType(Bid4WinObjectTypeStub1 partType)
  {
    this.partType = partType;
  }

  /**
   *
   * TODO A COMMENTER
   * @param partType {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourcePart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  public Bid4WinFileResourcePartStub getPart(Bid4WinObjectTypeStub1 partType)
  {
    if(this.getPartType().equals(partType))
    {
      return this;
    }
    return new Bid4WinFileResourcePartStub(this.getPath(), this.getName(), this.getType(), partType);
  }
}
