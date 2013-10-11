package com.bid4win.commons.persistence.usertype.collection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinObjectTypeSetUserType<TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinSetUserType<TYPE>
{
  /**
   * Constructeur
   */
  protected Bid4WinObjectTypeSetUserType()
  {
    super();
  }

  /**
   *
   * TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.collection.Bid4WinCollectionUserType#elementFromString(java.lang.String)
   */
  @Override
  public TYPE elementFromString(String string) throws Bid4WinException
  {
    return this.getUserType().typeFromString(string);
  }
  /**
   *
   * TODO A COMMENTER
   * @param element TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.collection.Bid4WinCollectionUserType#stringFromElement(java.io.Serializable)
   */
  @Override
  public String stringFromElement(TYPE element) throws Bid4WinException
  {
    return this.getUserType().stringFromType(element);
  }

  /**
   * Getter du type utilisateur en charge de la gestion des elements du set
   * @return Le type utilisateur en charge de la gestion des elements du set
   */
  public abstract Bid4WinObjectTypeUserType<TYPE> getUserType();
}
