package com.bid4win.commons.core.exception;

import com.bid4win.commons.core.reference.MessageRef;

/**
 * Cette classe d�fini la classe de base d'exception � destination de l'utilisateur<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UserException extends Bid4WinException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -6817760148019807638L;

  /** R�f�rence du message associ� � l'exception */
  private MessageRef messageRef = null;

  /**
   * Constructeur
   * @param messageRef R�f�rence du message associ� � l'exception
   */
  public UserException(MessageRef messageRef)
  {
    super(messageRef.getCode());
    this.setMessageRef(messageRef);
  }
  /**
   * Constructeur
   * @param messageRef R�f�rence du message associ� � l'exception
   * @param throwable Exception � l'origine du probl�me
   */
  public UserException(MessageRef messageRef, Throwable throwable)
  {
    super(messageRef.getCode(), throwable);
    this.setMessageRef(messageRef);
  }

  /**
   * Getter de la cl� r�f�ren�ant le message associ� � l'exception
   * @return La cl� r�f�ren�ant le message associ� � l'exception
   */
  public String getMessageKey()
  {
    return this.getMessageRef().getCode();
  }
  /**
   * Getter de la r�f�rence du message associ� � l'exception
   * @return La r�f�rence du message associ� � l'exception
   */
  public MessageRef getMessageRef()
  {
    return this.messageRef;
  }
  /**
   * Setter de la r�f�rence du message associ� � l'exception
   * @param messageRef R�f�rence du message associ� � l'exception � positionner
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
