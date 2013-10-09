package com.bid4win.commons.core.io.exception;

/**
 * Cette classe défini la classe de base de toutes les exceptions de sortie sur des flux<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinIOWriteException extends Bid4WinIoException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 7184812844950841721L;


  /**
   * Constructeur par défaut
   */
  public Bid4WinIOWriteException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public Bid4WinIOWriteException(String message)
  {
    super(message);
  }
  

  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public Bid4WinIOWriteException(Throwable throwable)
  {
    super(throwable);
  }
}
