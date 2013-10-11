package com.bid4win.commons.persistence.entity.account.security.exception;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class SessionException extends UserException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 3408690200174360238L;

  /**
   * Constructeur
   */
  public SessionException()
  {
    this(ConnectionRef.CONNECTION_SESSION_UNDEFINED_ERROR);
  }
  /**
   * Constructeur
   * @param messageRef Référence du message associé à l'exception
   */
  public SessionException(MessageRef messageRef)
  {
    super(messageRef);
  }
  /**
   * Constructeur
   * @param messageRef Référence du message associé à l'exception
   * @param throwable Exception à l'origine du problème
   */
  public SessionException(MessageRef messageRef, Throwable throwable)
  {
    super(messageRef, throwable);
  }
}
