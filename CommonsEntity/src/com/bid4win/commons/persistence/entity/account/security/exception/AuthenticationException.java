package com.bid4win.commons.persistence.entity.account.security.exception;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;

/**
 * Cette classe défini la classe de base d'exception d'autentification<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AuthenticationException extends UserException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -6272322348734172317L;

  /** Raison liée à la potentielle déconnexion */
  private DisconnectionReason reason = null;

  /**
   * Constructeur
   * @param messageRef Référence du message associé à l'exception
   * @param reason Raison liée à la potentielle déconnexion
   */
  public AuthenticationException(MessageRef messageRef, DisconnectionReason reason)
  {
    super(messageRef);
    this.setReason(reason);
  }

  /**
   * Getter de la raison liée à la potentielle déconnexion
   * @return La raison liée à la potentielle déconnexion
   */
  public DisconnectionReason getReason()
  {
    return this.reason;
  }
  /**
   * Setter de la raison liée à la potentielle déconnexion
   * @param reason Raison liée à la potentielle déconnexion à positionner
   */
  private void setReason(DisconnectionReason reason)
  {
    if(reason == null)
    {
      reason = DisconnectionReason.NONE;
    }
    this.reason = reason;
  }
}
