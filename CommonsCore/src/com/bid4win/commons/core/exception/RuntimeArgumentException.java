package com.bid4win.commons.core.exception;

/**
 * Cette classe défini la classe de base de toutes les exceptions runtime qui doivent
 * traiter d'un problème sur les paramètres d'une fonction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class RuntimeArgumentException extends Bid4WinRuntimeException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 557618918581425138L;

  /**
   * Constructeur par défaut
   */
  public RuntimeArgumentException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public RuntimeArgumentException(String message)
  {
    super(message);
  }

  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public RuntimeArgumentException(Throwable throwable)
  {
    super(throwable);
  }
}
