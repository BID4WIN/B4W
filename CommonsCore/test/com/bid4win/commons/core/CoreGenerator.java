package com.bid4win.commons.core;

import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CoreGenerator
{
  /** C'est l'instance unique utilis�e comme singleton */
  private final static CoreGenerator instance = new CoreGenerator();
  /**
   * L'objet est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique instance en m�moire
   * @return L'instance du manager
   */
  public static CoreGenerator getInstance()
  {
    return CoreGenerator.instance;
  }

  /**
   * Constructeur prot�g� car accessible seulement comme un singleton
   */
  protected CoreGenerator()
  {
    super();
  }

  /**
   * Cette m�thode permet de cr�er directement une date au format DD/MM/YYYY
   * @param date_DDIMMIYYYY Cha�ne de caract�res au format DD/MM/YYYY
   * @return La date construite � partir de la cha�ne de caract�res en param�tre
   * @throws Bid4WinException Si le parsing de la cha�ne de caract�res �choue
   */
  public Bid4WinDate createDate(String date_DDIMMIYYYY) throws Bid4WinException
  {
    return Bid4WinDateFormat.parseDDIMMIYYYY(date_DDIMMIYYYY);
  }

  /**
   * Cette m�thode permet de cr�er directement une date avec notion d'horaire au
   * format DD/MM/YYYY HH:mm:ss:SSS
   * @param date_DDIMMIYYYY_HHIMMISSISSS Cha�ne de caract�res au format DD/MM/YYYY HH:mm:ss:SSS
   * @return La date construite � partir de la cha�ne de caract�res en param�tre
   * @throws Bid4WinException Si le parsing de la cha�ne de caract�res �choue
   */
  public Bid4WinDate createDateTime(String date_DDIMMIYYYY_HHIMMISSISSS) throws Bid4WinException
  {
    return Bid4WinDateFormat.parseDDIMMIYYYY_HHIMMISSISSS(date_DDIMMIYYYY_HHIMMISSISSS);
  }

}
