package com.bid4win.persistence.entity.price;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;

/**
 * Cette classe défini une monnaie comparable à une énumération<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Currency extends Bid4WinObjectType<Currency>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -6783568812041762141L;

  /** Cette monnaie correspond à l'euro et est la monnaie par défaut*/
  public final static Currency EURO = new Currency("EURO", "€");
  /** Cette monnaie correspond au dollar américain */
  public final static Currency US_DOLLAR = new Currency("US DOLLAR", "$");
  /** Cette monnaie correspond à la livre anglaise */
  public final static Currency UK_POUND = new Currency("UK POUND", "£");

  /** Définition de la monnaie par défaut */
  public final static Currency DEFAULT = Bid4WinObjectType.getDefaultType(Currency.class);

  /**
   * Getter de la monnaie correspondant au code en paramètre
   * @param code Code de la monnaie à retourner
   * @return La monnaie correspond au code en paramètre
   * @throws UserException Si aucune monnaie ne correspond au code en argument
   */
  public static Currency getCurrency(String code) throws UserException
  {
    return Bid4WinObjectType.getType(Currency.class, code);
  }
  /**
   * Cette méthode permet de récupérer toutes les monnaies existantes
   * @return Toutes les monnaies définies
   */
  public static Bid4WinCollection<Currency> getCurrencies()
  {
    return Bid4WinObjectType.getTypes(Currency.class);
  }

  /** TODO A COMMENTER */
  private String htmlCode = null;
  /** TODO A COMMENTER */
  private String textCode = null;

  /**
   * Constructeur
   * @param code Code de la monnaie
   * @param textCode TODO A COMMENTER
   */
  private Currency(String code, String textCode)
  {
    super(code);
    this.setTextCode(textCode);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceType#getMessageRefBase()
   */
  @Override
  public MessageRef getMessageRefBase()
  {
    return CurrencyRef.CURRENCY;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getHtmlCode()
  {
    return this.htmlCode;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getTextCode()
  {
    return this.textCode;
  }
  /**
   *
   * TODO A COMMENTER
   * @param textCode TODO A COMMENTER
   */
  private void setTextCode(String textCode)
  {
    this.textCode = textCode;
    this.htmlCode = UtilString.text2HTML(textCode);
  }
}
