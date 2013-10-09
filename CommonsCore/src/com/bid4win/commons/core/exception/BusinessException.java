package com.bid4win.commons.core.exception;

/**
 * Cette classe d�fini la classe de base de toutes les exceptions m�tier du projet
 * BID4WIN<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class BusinessException extends Bid4WinException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 8947258560066119037L;
  
  /**
   * Constructeur par d�faut
   */
  public BusinessException()
  {
    super();
  }
  /**
   * Constructeur bas� sur un message d'exception
   * @param message Message associ� � l'exception � construire
   */
  public BusinessException(String message)
  {
    super(message);
  }
  /**
   * Constructeur bas� sur une exception pr�c�dente
   * @param throwable Exception ayant caus� le probl�me
   */
  public BusinessException(Throwable throwable)
  {
    super(throwable);
  }
}
