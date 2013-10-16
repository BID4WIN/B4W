package com.bid4win.commons.persistence.usertype.core;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les dates en base de donn�es.
 * Elle peut �tre param�tr�e afin de pr�ciser si les horaires doivent faire partie
 * de la gestion grace � l'utilisation du bool�en timeNeeded<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinDateUserType extends Bid4WinDateUserTypeAbstract<Bid4WinDate>
{
  /**
   * Constructeur
   */
  public Bid4WinDateUserType()
  {
    super(Bid4WinDate.class);
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
  public Bid4WinDate typeFromString(String string) throws Bid4WinException
  {
    return this.getDate(string);
  }
  /**
   * Cette fonction d�fini la r�cup�ration de la cha�ne de caract�res correspondant
   * � la date en argument
   * @param date {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#stringFromType(java.io.Serializable)
   */
  @Override
  public String stringFromType(Bid4WinDate date)
  {
    return this.getString(date);
  }
}
