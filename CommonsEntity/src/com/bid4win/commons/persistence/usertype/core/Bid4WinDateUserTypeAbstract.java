package com.bid4win.commons.persistence.usertype.core;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.usertype.ParameterizedType;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.Bid4WinDateFormat;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserType;

/**
 * Cette classe défini le 'type utilisateur' gérant les dates en base de données.
 * Elle peut être paramétrée afin de préciser si les horaires doivent faire partie
 * de la gestion grace à l'utilisation du booléen timeNeeded<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinDateUserTypeAbstract<TYPE extends Serializable>
       extends Bid4WinStringUserType<TYPE> implements ParameterizedType
{
  /** Définition du booléen utilisé pour paramétrer la gestion des horaires */
  public static final String TIME_PARAMETER = "timeNeeded";

  /** Paramétrage de la gestion des horaires du 'type utilisateur' */
  private boolean timeNeeded = false;

  /**
   * Constructeur
   * @param typeClass Classe du type complexe à gérer
   */
  protected Bid4WinDateUserTypeAbstract(Class<TYPE> typeClass)
  {
    super(typeClass);
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
   * @param string Chaîne à convertir en date
   * @return La date correspondant à la chaîne en argument
   * @throws Bid4WinException Si un problème intervient lors de la conversion
   */
  public Bid4WinDate getDate(String string) throws Bid4WinException
  {
    if(this.isTimeNeeded())
    {
      return Bid4WinDateFormat.parseYYYYIMMIDD_HHIMMISSISSS(string);
    }
    return Bid4WinDateFormat.parseYYYYIMMIDD(string);
  }
  /**
   * Cette fonction défini la récupération de la chaîne de caractères correspondant
   * à la date en argument
   * @param date Date à convertir en chaîne de caractères
   * @return La représentation de la date en paramètre
   */
  public String getString(Bid4WinDate date)
  {
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
