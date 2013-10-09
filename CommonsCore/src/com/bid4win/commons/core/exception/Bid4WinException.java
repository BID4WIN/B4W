package com.bid4win.commons.core.exception;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.UtilSystem;

/**
 * Cette classe défini la classe de base d'exception du projet BID4WIN<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinException extends Exception
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -9215584809853050629L;

  /**
   * Constructeur par défaut
   */
  public Bid4WinException()
  {
    this("NO MESSAGE");
  }

  /**
   * Constructeur  d'exception
   * @param message Message associé à l'exception à construire
   */
  public Bid4WinException(String message)
  {
    super((UtilString.trimNotNull(message).equals("") ? "NO MESSAGE" : message.trim()));
  }

  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public Bid4WinException(Throwable throwable)
  {
    super("", throwable);
  }

  /**
   * Constructeur basé sur un message et une exception précédente
   * @param message Message associé à l'exception à construire
   * @param throwable Exception ayant causé le problème
   */
  public Bid4WinException(String message, Throwable throwable)
  {
    super((UtilString.trimNotNull(message).equals("") ? "" : message.trim()), throwable);
  }
  
  /**
   * Redéfini la méthode de la classe mère afin de récupérer le message de l'exception
   * et de sa cause
   * @return {@inheritDoc}
   * @see java.lang.Throwable#getMessage()
   */
  @Override
  public String getMessage()
  {
    // Récupère le message de base non vide de l'exception
    String message = this.getMessageBase(false);
    // Récupère la cause et complète le message
    Throwable cause = this.getCause();
    if(cause != null)
    {
      message += " due to " + cause.getClass() + " -> ";
      message += cause.getMessage();
    }
    return message;
  }
  
  /**
   * Cette méthode défini la récupération du message de base de l'exception avec
   * la possibilité de ne pas retourner de message vide mais au moins la classe
   * dont est issue l'exception
   * @param empty Indique si on accepte un message vide ou non
   * @return Le message non vide associé à l'exception
   */
  public String getMessageBase(boolean empty)
  {
    // Récupère le message de l'exception
    String message = super.getMessage();
    // Si celui-ci est vide, on indique l'exception si demandé
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
   * Cette méthode défini la récupération du message de base de l'exception avec
   * la possibilité de ne pas retourner de message vide mais au moins la classe
   * dont est issue l'exception
   * @param empty Indique si on accepte un message vide ou non
   * @return Le message non vide associé à l'exception
   */
  public String getMessageBaseWithoutQuotes(boolean empty)
  {
    return this.getMessageBase(empty).replaceAll("\"", "");
  }
}
