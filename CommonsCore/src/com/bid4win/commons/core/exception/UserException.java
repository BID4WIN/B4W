package com.bid4win.commons.core.exception;

import com.bid4win.commons.core.reference.MessageRef;

/**
 * Cette classe défini la classe de base d'exception à destination de l'utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UserException extends Bid4WinException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -6817760148019807638L;

  /** Référence du message associé à l'exception */
  private MessageRef messageRef = null;

  /**
   * Constructeur
   * @param messageRef Référence du message associé à l'exception
   */
  public UserException(MessageRef messageRef)
  {
    super(messageRef.getCode());
    this.setMessageRef(messageRef);
  }
  /**
   * Constructeur
   * @param messageRef Référence du message associé à l'exception
   * @param throwable Exception à l'origine du problème
   */
  public UserException(MessageRef messageRef, Throwable throwable)
  {
    super(messageRef.getCode(), throwable);
    this.setMessageRef(messageRef);
  }

  /**
   * Getter de la clé référençant le message associé à l'exception
   * @return La clé référençant le message associé à l'exception
   */
  public String getMessageKey()
  {
    return this.getMessageRef().getCode();
  }
  /**
   * Getter de la référence du message associé à l'exception
   * @return La référence du message associé à l'exception
   */
  public MessageRef getMessageRef()
  {
    return this.messageRef;
  }
  /**
   * Setter de la référence du message associé à l'exception
   * @param messageRef Référence du message associé à l'exception à positionner
   */
  private void setMessageRef(MessageRef messageRef)
  {
    if(messageRef == null)
    {
      messageRef = MessageRef.UNKNOWN_ERROR;
    }
    this.messageRef = messageRef;
  }
}
