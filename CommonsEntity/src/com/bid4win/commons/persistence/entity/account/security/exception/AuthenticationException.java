package com.bid4win.commons.persistence.entity.account.security.exception;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;

/**
 * Cette classe d�fini la classe de base d'exception d'autentification<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AuthenticationException extends UserException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -6272322348734172317L;

  /** Raison li�e � la potentielle d�connexion */
  private DisconnectionReason reason = null;

  /**
   * Constructeur
   * @param messageRef R�f�rence du message associ� � l'exception
   * @param reason Raison li�e � la potentielle d�connexion
   */
  public AuthenticationException(MessageRef messageRef, DisconnectionReason reason)
  {
    super(messageRef);
    this.setReason(reason);
  }

  /**
   * Getter de la raison li�e � la potentielle d�connexion
   * @return La raison li�e � la potentielle d�connexion
   */
  public DisconnectionReason getReason()
  {
    return this.reason;
  }
  /**
   * Setter de la raison li�e � la potentielle d�connexion
   * @param reason Raison li�e � la potentielle d�connexion � positionner
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
