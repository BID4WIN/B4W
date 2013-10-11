package com.bid4win.commons.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Classe enrichissant JavaMailSenderImpl de fonctionnalités spécifiques
 * 
 * @author Maxime Ollagnier
 */
public class MailSender extends JavaMailSenderImpl
{
  /** Adresse e-mail de l'expéditeur */
  private String fromAddress = null;
  /** Nom de l'expéditeur */
  private String fromName = null;

  /**
   * Crée un un message MIME
   * @return {@inheritDoc}
   * @see org.springframework.mail.javamail.JavaMailSenderImpl#createMimeMessage()
   */
  @Override
  public MimeMessage createMimeMessage()
  {
    MimeMessage mimeMessage = super.createMimeMessage();
    InternetAddress from = null;
    try
    {
      from = new InternetAddress(this.getFromAddress(), this.getFromName());
      mimeMessage.setFrom(from);
    }
    catch(UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    catch(MessagingException e)
    {
      e.printStackTrace();
    }
    return mimeMessage;
  }

  /**
   * Guetter de l'adresse e-mail expéditeur
   * @return L'adresse e-mail expéditeur
   */
  public String getFromAddress()
  {
    return this.fromAddress;
  }
  /**
   * Setter de l'adresse e-mail expéditeur
   * @param fromAddress L'adresse e-mail expéditeur
   */
  public void setFromAddress(String fromAddress)
  {
    this.fromAddress = fromAddress;
  }

  /**
   * Guetter du nom de l'expéditeur
   * @return Nom de l'expéditeur
   */
  public String getFromName()
  {
    return this.fromName;
  }
  /**
   * Setter du nom de l'expéditeur
   * @param fromName Nom de l'expéditeur
   */
  public void setFromName(String fromName)
  {
    this.fromName = fromName;
  }
}
