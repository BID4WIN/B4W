package com.bid4win.commons.core.exception;

/**
 * Cette classe défini la classe de base de toutes les exceptions de communication
 * du projet BID4WIN<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CommunicationException extends TechnicalException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -4035513613480127824L;

  /**
   * Constructeur par défaut
   */
  public CommunicationException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public CommunicationException(String message)
  {
    super(message);
  }
  

  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public CommunicationException(Throwable throwable)
  {
    super(throwable);
  }
}

