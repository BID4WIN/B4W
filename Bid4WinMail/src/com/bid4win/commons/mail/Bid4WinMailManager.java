package com.bid4win.commons.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.PasswordReinit;

/**
 * Classe singleton du manager de
 * 
 * @author Maxime Ollagnier
 */
@Component("Bid4WinMailManager")
@Scope("singleton")
public class Bid4WinMailManager
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("noReplySender")
  private MailSender noReplySender;

  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("Bid4WinMimeMessageFactory")
  private Bid4WinMimeMessageFactory bid4WinMimeMessageFactory;

  /**
   * Envoi un mail d'activation de compte à l'utilisateur dont le compte est en
   * paramètre
   * @param account Compte de l'utilisateur à qui envoyer le mail
   * @throws CommunicationException Si une erreur se produit lors de la
   * génération ou de l'envoi du mail
   */
  public void sendActivationMail(Account account) throws CommunicationException
  {
    try
    {
      MimeMessage msg = this.bid4WinMimeMessageFactory.getActivationMessage(account, this.noReplySender);
      this.noReplySender.send(msg);
    }
    catch(MessagingException e)
    {
      throw new CommunicationException(e);
    }
  }

  /**
   * Envoi un mail de ré-initialisation du mot de passe
   * @param reinit Ré-initialisation du mot de passe
   * @throws CommunicationException Si une erreur se produit lors de la
   * génération ou de l'envoi du mail
   */
  public void sendPasswordReinitMail(PasswordReinit reinit) throws CommunicationException
  {
    try
    {
      MimeMessage msg = this.bid4WinMimeMessageFactory.getPasswordReinitMessage(reinit.getAccount(), reinit.getId(), this.noReplySender);
      this.noReplySender.send(msg);
    }
    catch(MessagingException e)
    {
      throw new CommunicationException(e);
    }
  }
}
