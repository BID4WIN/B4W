package com.bid4win.commons.core.exception;

/**
 * Cette classe d�fini la classe de base de toutes les exceptions de communication
 * du projet BID4WIN<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CommunicationException extends TechnicalException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -4035513613480127824L;

  /**
   * Constructeur par d�faut
   */
  public CommunicationException()
  {
    super();
  }

  /**
   * Constructeur bas� sur un message d'exception
   * @param message Message associ� � l'exception � construire
   */
  public CommunicationException(String message)
  {
    super(message);
  }
  

  /**
   * Constructeur bas� sur une exception pr�c�dente
   * @param throwable Exception ayant caus� le probl�me
   */
  public CommunicationException(Throwable throwable)
  {
    super(throwable);
  }
}

