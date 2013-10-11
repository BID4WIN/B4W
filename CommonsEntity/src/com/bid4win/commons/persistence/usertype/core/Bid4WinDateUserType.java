package com.bid4win.commons.persistence.usertype.core;

import java.util.Properties;

import org.hibernate.usertype.ParameterizedType;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.Bid4WinDateFormat;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserType;

/**
 * Cette classe défini le 'type utilisateur' gérant les dates en base de données.
 * Elle peut être paramétrée afin de préciser si les horaires doivent faire partie
 * de la gestion grace à l'utilisation du booléen timeNeeded<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDateUserType extends Bid4WinStringUserType<Bid4WinDate>
       implements ParameterizedType
{
  /** Définition du booléen utilisé pour paramétrer la gestion des horaires */
  public static final String TIME_PARAMETER = "timeNeeded";

  /** Paramétrage de la gestion des horaires du 'type utilisateur' */
  private boolean timeNeeded = false;

  /**
   * Constructeur
   */
  public Bid4WinDateUserType()
  {
    super(Bid4WinDate.class);
  }

  /**
   * Positionnement du paramétrage du UserType
   * @param parameters {@inheritDoc}
   * @see org.hibernate.usertype.ParameterizedType#setParameterValues(java.util.Properties)
   */
  @Override
  public void setParameterValues(Properties parameters)
  {
    this.setTimeNeeded(Boolean.parseBoolean(parameters.getProperty(TIME_PARAMETER)));
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
    UtilString.trimNotNull(string);
    if(this.isTimeNeeded())
    {
      return Bid4WinDateFormat.parseYYYYIMMIDD_HHIMMISSISSS(string);
    }
    return Bid4WinDateFormat.parseYYYYIMMIDD(string);
  }
  /**
   * Cette fonction défini la récupération de la chaîne de caractères correspondant
   * à la date en argument
   * @param date {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#stringFromType(java.io.Serializable)
   */
  @Override
  public String stringFromType(Bid4WinDate date) throws Bid4WinException
  {
    UtilObject.checkNotNull("date", date);
    if(this.isTimeNeeded())
    {
      return date.formatYYYYIMMIDD_HHIMMISSISSS();
    }
      return date.formatYYYYIMMIDD();
  }

  /**
   * Getter du paramétrage de la gestion des horaires du UserType
   * @return Le Paramétrage de la gestion des horaires du UserType
   */
  public boolean isTimeNeeded()
  {
    return this.timeNeeded;
  }
  /**
   * Setter du paramétrage de la gestion des horaires du UserType
   * @param timeNeeded Paramétrage de la gestion des horaires du UserType
   */
  private void setTimeNeeded(boolean timeNeeded)
  {
    this.timeNeeded = timeNeeded;
  }
}
