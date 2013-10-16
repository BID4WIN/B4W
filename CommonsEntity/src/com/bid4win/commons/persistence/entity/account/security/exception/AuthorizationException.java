package com.bid4win.commons.persistence.entity.account.security.exception;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;

/**
 * Cette classe d�fini la classe de base d'exception d'autorisation<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AuthorizationException extends UserException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 2034997036465140793L;

  /**
   * Constructeur
   */
  public AuthorizationException()
  {
    this(ConnectionRef.PERMISSION_NOT_GRANTED_ERROR);
  }
  /**
   * Constructeur
   * @param messageRef R�f�rence du message associ� � l'exception
   */
  public AuthorizationException(MessageRef messageRef)
  {
    super(messageRef);
  }
  /**
   * Constructeur
   * @param messageRef R�f�rence du message associ� � l'exception
   * @param throwable Exception � l'origine du probl�me
   */
  public AuthorizationException(MessageRef messageRef, Throwable throwable)
  {
    super(messageRef, throwable);
  }

}