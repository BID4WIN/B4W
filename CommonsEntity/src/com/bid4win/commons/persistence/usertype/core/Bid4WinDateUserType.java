package com.bid4win.commons.persistence.usertype.core;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * Cette classe défini le 'type utilisateur' gérant les dates en base de données.
 * Elle peut être paramétrée afin de préciser si les horaires doivent faire partie
 * de la gestion grace à l'utilisation du booléen timeNeeded<BR>
 * <BR>
 * @author Emeric Fillâtre
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
   * Cette fonction défini la récupération de la date correspondant à la chaîne
   * de caractères en argument
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
   * Cette fonction défini la récupération de la chaîne de caractères correspondant
   * à la date en argument
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
