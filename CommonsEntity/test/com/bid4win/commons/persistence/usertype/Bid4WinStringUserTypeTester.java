package com.bid4win.commons.persistence.usertype;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.exception.Bid4WinException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <USER_TYPE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinStringUserTypeTester<TYPE extends Serializable,
                                                  USER_TYPE extends Bid4WinStringUserType<TYPE>>
       extends Bid4WinCoreTester
{
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract USER_TYPE getUserType();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract TYPE getType() throws Bid4WinException;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected TYPE getExpected() throws Bid4WinException
  {
    return this.getType();
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  @Test
  public void testUserType() throws Bid4WinException
  {
    String string = this.getUserType().stringFromType(this.getType());
    TYPE result = this.getUserType().typeFromString(string);
    assertEquals("Wrong result", this.getExpected(), result);
  }
}
