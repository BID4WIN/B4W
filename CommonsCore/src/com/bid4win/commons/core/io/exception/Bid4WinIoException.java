package com.bid4win.commons.core.io.exception;

import com.bid4win.commons.core.exception.CommunicationException;

/**
 * Cette classe défini la classe de base de toutes les exceptions d'entrée/sortie
 * sur des flux<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinIoException extends CommunicationException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -8154214646745166504L;


  /**
   * Constructeur par défaut
   */
  public Bid4WinIoException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public Bid4WinIoException(String message)
  {
    super(message);
  }
  

  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public Bid4WinIoException(Throwable throwable)
  {
    super(throwable);
  }
}
