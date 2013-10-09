package com.bid4win.commons.core.exception;

/**
 * Cette classe défini la classe de base de toutes les exceptions runtime qui doivent
 * traiter d'un problème d'instanciation<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class RuntimeInstantiationException extends Bid4WinRuntimeException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 8501725815735051152L;

  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public RuntimeInstantiationException(Throwable throwable)
  {
    super(throwable);
  }
}
