package com.bid4win.commons.persistence.usertype.core;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.core.Bid4WinDateForRequest;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinDateForRequestUserType extends Bid4WinDateUserTypeAbstract<Bid4WinDateForRequest>
{
  /**
   * Constructeur
   */
  public Bid4WinDateForRequestUserType()
  {
    super(Bid4WinDateForRequest.class);
  }

  /**
   * Cette fonction d�fini la r�cup�ration de la date correspondant � la cha�ne
   * de caract�res en argument
   * @param string {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#typeFromString(java.lang.String)
   */
  @Override
  public Bid4WinDateForRequest typeFromString(String string) throws Bid4WinException
  {
    return Bid4WinDateForRequest.getDateForRequest(this.getDate(string));
  }
  /**
   * Cette fonction d�fini la r�cup�ration de la cha�ne de caract�res correspondant
   * � la date en argument
   * @param date {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#stringFromType(java.io.Serializable)
   */
  @Override
  public String stringFromType(Bid4WinDateForRequest date)
  {
    return this.getString(date.getDate());
  }

}
