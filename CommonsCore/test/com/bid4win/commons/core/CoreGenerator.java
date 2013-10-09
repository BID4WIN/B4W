package com.bid4win.commons.core;

import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CoreGenerator
{
  /** C'est l'instance unique utilisée comme singleton */
  private final static CoreGenerator instance = new CoreGenerator();
  /**
   * L'objet est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique instance en mémoire
   * @return L'instance du manager
   */
  public static CoreGenerator getInstance()
  {
    return CoreGenerator.instance;
  }

  /**
   * Constructeur protégé car accessible seulement comme un singleton
   */
  protected CoreGenerator()
  {
    super();
  }

  /**
   * Cette méthode permet de créer directement une date au format DD/MM/YYYY
   * @param date_DDIMMIYYYY Chaîne de caractères au format DD/MM/YYYY
   * @return La date construite à partir de la chaîne de caractères en paramètre
   * @throws Bid4WinException Si le parsing de la chaîne de caractères échoue
   */
  public Bid4WinDate createDate(String date_DDIMMIYYYY) throws Bid4WinException
  {
    return Bid4WinDateFormat.parseDDIMMIYYYY(date_DDIMMIYYYY);
  }

  /**
   * Cette méthode permet de créer directement une date avec notion d'horaire au
   * format DD/MM/YYYY HH:mm:ss:SSS
   * @param date_DDIMMIYYYY_HHIMMISSISSS Chaîne de caractères au format DD/MM/YYYY HH:mm:ss:SSS
   * @return La date construite à partir de la chaîne de caractères en paramètre
   * @throws Bid4WinException Si le parsing de la chaîne de caractères échoue
   */
  public Bid4WinDate createDateTime(String date_DDIMMIYYYY_HHIMMISSISSS) throws Bid4WinException
  {
    return Bid4WinDateFormat.parseDDIMMIYYYY_HHIMMISSISSS(date_DDIMMIYYYY_HHIMMISSISSS);
  }

}
