package com.bid4win.commons.core.exception;

/**
 * Cette classe d�fini la classe de base de toutes les exceptions runtime qui doivent
 * traiter d'un probl�me sur les param�tres d'une fonction<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class RuntimeArgumentException extends Bid4WinRuntimeException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 557618918581425138L;

  /**
   * Constructeur par d�faut
   */
  public RuntimeArgumentException()
  {
    super();
  }

  /**
   * Constructeur bas� sur un message d'exception
   * @param message Message associ� � l'exception � construire
   */
  public RuntimeArgumentException(String message)
  {
    super(message);
  }

  /**
   * Constructeur bas� sur une exception pr�c�dente
   * @param throwable Exception ayant caus� le probl�me
   */
  public RuntimeArgumentException(Throwable throwable)
  {
    super(throwable);
  }
}
