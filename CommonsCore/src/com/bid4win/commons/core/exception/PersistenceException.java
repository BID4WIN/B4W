package com.bid4win.commons.core.exception;

/**
 * Cette classe défini la classe de base de toutes les exceptions de persistance
 * du projet BID4WIN<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PersistenceException extends TechnicalException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -2216095385124832165L;

  /**
   * Constructeur par défaut
   */
  public PersistenceException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public PersistenceException(String message)
  {
    super(message);
  }
  
  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public PersistenceException(Throwable throwable)
  {
    super(throwable);
  }

  /**
   * Constructeur basé sur un message et une exception précédente
   * @param message Message associé à l'exception à construire
   * @param throwable Exception ayant causé le problème
   */
  public PersistenceException(String message, Throwable throwable)
  {
    super(message, throwable);
  }
}
