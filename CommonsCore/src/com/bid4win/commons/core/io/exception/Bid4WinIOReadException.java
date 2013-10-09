package com.bid4win.commons.core.io.exception;

/**
 * Cette classe d�fini la classe de base de toutes les exceptions d'entr�e sur des flux<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinIOReadException extends Bid4WinIoException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 8950359716521679251L;


  /**
   * Constructeur par d�faut
   */
  public Bid4WinIOReadException()
  {
    super();
  }

  /**
   * Constructeur bas� sur un message d'exception
   * @param message Message associ� � l'exception � construire
   */
  public Bid4WinIOReadException(String message)
  {
    super(message);
  }
  

  /**
   * Constructeur bas� sur une exception pr�c�dente
   * @param throwable Exception ayant caus� le probl�me
   */
  public Bid4WinIOReadException(Throwable throwable)
  {
    super(throwable);
  }
}
