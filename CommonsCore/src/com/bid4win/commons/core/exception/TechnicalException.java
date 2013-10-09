package com.bid4win.commons.core.exception;

/**
 * Cette classe d�fini la classe de base de toutes les exceptions techniques du
 * projet BID4WIN<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class TechnicalException extends Bid4WinException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 4636748482635376979L;

  /**
   * Constructeur par d�faut
   */
  public TechnicalException()
  {
    super();
  }

  /**
   * Constructeur bas� sur un message d'exception
   * @param message Message associ� � l'exception � construire
   */
  public TechnicalException(String message)
  {
    super(message);
  }
  
  /**
   * Constructeur bas� sur une exception pr�c�dente
   * @param throwable Exception ayant caus� le probl�me
   */
  public TechnicalException(Throwable throwable)
  {
    super(throwable);
  }

  /**
   * Constructeur bas� sur un message et une exception pr�c�dente
   * @param message Message associ� � l'exception � construire
   * @param throwable Exception ayant caus� le probl�me
   */
  public TechnicalException(String message, Throwable throwable)
  {
    super(message, throwable);
  }
}