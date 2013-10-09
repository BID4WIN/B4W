package com.bid4win.commons.core.io.exception;

/**
 * Cette classe défini la classe de base de toutes les exceptions d'entrée sur des flux<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinIOReadException extends Bid4WinIoException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 8950359716521679251L;


  /**
   * Constructeur par défaut
   */
  public Bid4WinIOReadException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public Bid4WinIOReadException(String message)
  {
    super(message);
  }
  

  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public Bid4WinIOReadException(Throwable throwable)
  {
    super(throwable);
  }
}
