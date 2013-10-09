package com.bid4win.commons.core.exception;

/**
 * Cette classe défini la classe de base de toutes les exceptions techniques du
 * projet BID4WIN<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class TechnicalException extends Bid4WinException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 4636748482635376979L;

  /**
   * Constructeur par défaut
   */
  public TechnicalException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public TechnicalException(String message)
  {
    super(message);
  }
  
  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public TechnicalException(Throwable throwable)
  {
    super(throwable);
  }

  /**
   * Constructeur basé sur un message et une exception précédente
   * @param message Message associé à l'exception à construire
   * @param throwable Exception ayant causé le problème
   */
  public TechnicalException(String message, Throwable throwable)
  {
    super(message, throwable);
  }
}