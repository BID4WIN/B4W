package com.bid4win.commons.core.exception;

/**
 * Cette classe d�fini la classe de base de toutes les exceptions runtime qui doivent
 * traiter d'un probl�me d'instanciation<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class RuntimeInstantiationException extends Bid4WinRuntimeException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 8501725815735051152L;

  /**
   * Constructeur bas� sur une exception pr�c�dente
   * @param throwable Exception ayant caus� le probl�me
   */
  public RuntimeInstantiationException(Throwable throwable)
  {
    super(throwable);
  }
}
