package com.bid4win.commons.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Classe enrichissant JavaMailSenderImpl de fonctionnalit�s sp�cifiques
 * 
 * @author Maxime Ollagnier
 */
public class MailSender extends JavaMailSenderImpl
{
  /** Adresse e-mail de l'exp�diteur */
  private String fromAddress = null;
  /** Nom de l'exp�diteur */
  private String fromName = null;

  /**
   * Cr�e un un message MIME
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
   * Guetter de l'adresse e-mail exp�diteur
   * @return L'adresse e-mail exp�diteur
   */
  public String getFromAddress()
  {
    return this.fromAddress;
  }
  /**
   * Setter de l'adresse e-mail exp�diteur
   * @param fromAddress L'adresse e-mail exp�diteur
   */
  public void setFromAddress(String fromAddress)
  {
    this.fromAddress = fromAddress;
  }

  /**
   * Guetter du nom de l'exp�diteur
   * @return Nom de l'exp�diteur
   */
  public String getFromName()
  {
    return this.fromName;
  }
  /**
   * Setter du nom de l'exp�diteur
   * @param fromName Nom de l'exp�diteur
   */
  public void setFromName(String fromName)
  {
    this.fromName = fromName;
  }
}
