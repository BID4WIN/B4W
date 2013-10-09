package com.bid4win.commons.core.exception;

/**
 * Cette classe défini la classe de base de toutes les exceptions métier du projet
 * BID4WIN<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class BusinessException extends Bid4WinException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 8947258560066119037L;
  
  /**
   * Constructeur par défaut
   */
  public BusinessException()
  {
    super();
  }
  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public BusinessException(String message)
  {
    super(message);
  }
  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public BusinessException(Throwable throwable)
  {
    super(throwable);
  }
}
