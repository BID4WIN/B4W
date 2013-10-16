package com.bid4win.commons.persistence.usertype.core;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.usertype.ParameterizedType;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.Bid4WinDateFormat;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les dates en base de donn�es.
 * Elle peut �tre param�tr�e afin de pr�ciser si les horaires doivent faire partie
 * de la gestion grace � l'utilisation du bool�en timeNeeded<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinDateUserTypeAbstract<TYPE extends Serializable>
       extends Bid4WinStringUserType<TYPE> implements ParameterizedType
{
  /** D�finition du bool�en utilis� pour param�trer la gestion des horaires */
  public static final String TIME_PARAMETER = "timeNeeded";

  /** Param�trage de la gestion des horaires du 'type utilisateur' */
  private boolean timeNeeded = false;

  /**
   * Constructeur
   * @param typeClass Classe du type complexe � g�rer
   */
  protected Bid4WinDateUserTypeAbstract(Class<TYPE> typeClass)
  {
    super(typeClass);
  }

  /**
   * Positionnement du param�trage du UserType
   * @param parameters {@inheritDoc}
   * @see org.hibernate.usertype.ParameterizedType#setParameterValues(java.util.Properties)
   */
  @Override
  public void setParameterValues(Properties parameters)
  {
    this.setTimeNeeded(Boolean.parseBoolean(parameters.getProperty(TIME_PARAMETER)));
  }

  /**
   * Cette fonction d�fini la r�cup�ration de la date correspondant � la cha�ne
   * de caract�res en argument
   * @param string Cha�ne � convertir en date
   * @return La date correspondant � la cha�ne en argument
   * @throws Bid4WinException Si un probl�me intervient lors de la conversion
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
   * Cette fonction d�fini la r�cup�ration de la cha�ne de caract�res correspondant
   * � la date en argument
   * @param date Date � convertir en cha�ne de caract�res
   * @return La repr�sentation de la date en param�tre
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
   * Getter du param�trage de la gestion des horaires du UserType
   * @return Le Param�trage de la gestion des horaires du UserType
   */
  public boolean isTimeNeeded()
  {
    return this.timeNeeded;
  }
  /**
   * Setter du param�trage de la gestion des horaires du UserType
   * @param timeNeeded Param�trage de la gestion des horaires du UserType
   */
  private void setTimeNeeded(boolean timeNeeded)
  {
    this.timeNeeded = timeNeeded;
  }
}
