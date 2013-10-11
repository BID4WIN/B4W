package com.bid4win.commons.persistence.entity.account.security.exception;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class SessionException extends UserException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
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
   * @param messageRef R�f�rence du message associ� � l'exception
   */
  public SessionException(MessageRef messageRef)
  {
    super(messageRef);
  }
  /**
   * Constructeur
   * @param messageRef R�f�rence du message associ� � l'exception
   * @param throwable Exception � l'origine du probl�me
   */
  public SessionException(MessageRef messageRef, Throwable throwable)
  {
    super(messageRef, throwable);
  }
}
