package com.bid4win.commons.core.exception;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.UtilSystem;

/**
 * Cette classe d�fini la classe de base d'exception du projet BID4WIN<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinException extends Exception
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -9215584809853050629L;

  /**
   * Constructeur par d�faut
   */
  public Bid4WinException()
  {
    this("NO MESSAGE");
  }

  /**
   * Constructeur  d'exception
   * @param message Message associ� � l'exception � construire
   */
  public Bid4WinException(String message)
  {
    super((UtilString.trimNotNull(message).equals("") ? "NO MESSAGE" : message.trim()));
  }

  /**
   * Constructeur bas� sur une exception pr�c�dente
   * @param throwable Exception ayant caus� le probl�me
   */
  public Bid4WinException(Throwable throwable)
  {
    super("", throwable);
  }

  /**
   * Constructeur bas� sur un message et une exception pr�c�dente
   * @param message Message associ� � l'exception � construire
   * @param throwable Exception ayant caus� le probl�me
   */
  public Bid4WinException(String message, Throwable throwable)
  {
    super((UtilString.trimNotNull(message).equals("") ? "" : message.trim()), throwable);
  }
  
  /**
   * Red�fini la m�thode de la classe m�re afin de r�cup�rer le message de l'exception
   * et de sa cause
   * @return {@inheritDoc}
   * @see java.lang.Throwable#getMessage()
   */
  @Override
  public String getMessage()
  {
    // R�cup�re le message de base non vide de l'exception
    String message = this.getMessageBase(false);
    // R�cup�re la cause et compl�te le message
    Throwable cause = this.getCause();
    if(cause != null)
    {
      message += " due to " + cause.getClass() + " -> ";
      message += cause.getMessage();
    }
    return message;
  }
  
  /**
   * Cette m�thode d�fini la r�cup�ration du message de base de l'exception avec
   * la possibilit� de ne pas retourner de message vide mais au moins la classe
   * dont est issue l'exception
   * @param empty Indique si on accepte un message vide ou non
   * @return Le message non vide associ� � l'exception
   */
  public String getMessageBase(boolean empty)
  {
    // R�cup�re le message de l'exception
    String message = super.getMessage();
    // Si celui-ci est vide, on indique l'exception si demand�
    if(!empty)
    {
      if(message.equals(""))
      {
        message = "Exception " + UtilSystem.getCanonicalClassName(this);
      }
      else
      {
        message = "\"" + message + "\"";
      }
    }
    return message;
  }
  
  /**
   * Cette m�thode d�fini la r�cup�ration du message de base de l'exception avec
   * la possibilit� de ne pas retourner de message vide mais au moins la classe
   * dont est issue l'exception
   * @param empty Indique si on accepte un message vide ou non
   * @return Le message non vide associ� � l'exception
   */
  public String getMessageBaseWithoutQuotes(boolean empty)
  {
    return this.getMessageBase(empty).replaceAll("\"", "");
  }
}
