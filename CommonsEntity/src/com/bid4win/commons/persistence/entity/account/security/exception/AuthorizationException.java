package com.bid4win.commons.persistence.entity.account.security.exception;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;

/**
 * Cette classe défini la classe de base d'exception d'autorisation<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AuthorizationException extends UserException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
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
   * @param messageRef Référence du message associé à l'exception
   */
  public AuthorizationException(MessageRef messageRef)
  {
    super(messageRef);
  }
  /**
   * Constructeur
   * @param messageRef Référence du message associé à l'exception
   * @param throwable Exception à l'origine du problème
   */
  public AuthorizationException(MessageRef messageRef, Throwable throwable)
  {
    super(messageRef, throwable);
  }

}