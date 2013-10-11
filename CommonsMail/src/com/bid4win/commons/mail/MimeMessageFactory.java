package com.bid4win.commons.mail;

import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Classe de génération de messages MIME.
 * Permet la génération de messages ayant à la fois une version au format "text"
 * et/ou un version au format "html".
 *
 * @author Maxime Ollagnier
 */
public class MimeMessageFactory
{
  /** TODO A COMMENTER */
  private static final String HTML_CONTENT_TYPE = "text/html; charset=UTF-8";

  /** Le moteur de génération de messages */
  private VelocityEngine velocityEngine = null;
  /** TODO A COMMENTER */
  private String rootTemplatePath = "";

  /**
   * Renvoi un message MIME de type "plain/text".
   * @param mailSender Sender à utiliser pour créer le message MIME
   * @param map La table de messages à insérer dans le template
   * @param templateFileName le nom du fichier template à utiliser
   * @return Le message MIME
   * @throws MessagingException Si une erreur intervient lors du traitement du
   * message MIME
   */
  public MimeMessage getMimeTextMessage(MailSender mailSender,
                                        Map<String, Object> map,
                                        String templateFileName) throws MessagingException
  {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    mimeMessage.setText(this.getTextMessage(map, templateFileName));
    return mimeMessage;
  }

  /**
   * Renvoi un message MIME de type "html".
   * @param mailSender Sender à utiliser pour créer le message MIME
   * @param map La table de messages à insérer dans le template
   * @param templateFileName le nom du fichier template à utiliser
   * @return Le message MIME
   * @throws MessagingException Si une erreur intervient lors du traitement du
   * message MIME
   */
  public MimeMessage getMimeHtmlMessage(MailSender mailSender,
                                        Map<String, Object> map,
                                        String templateFileName) throws MessagingException
  {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    mimeMessage.setContent(this.getHtmlMessage(map, templateFileName),
                           MimeMessageFactory.HTML_CONTENT_TYPE);
    return mimeMessage;
  }

  /**
   * Renvoi un message MIME multipart/alternative avec à la fois un corps de
   * type "plain/text" et un corps de type "html".
   * @param mailSender Sender à utiliser pour créer le message MIME
   * @param map La table de messages à insérer dans le template
   * @param templateFileName le nom des fichiers templates à utiliser
   * @return Le message MIME multipart/alternative
   * @throws MessagingException Si une erreur intervient lors du traitement du
   * message MIME
   */
  public MimeMessage getMimeMultipartAlternativeMessage(MailSender mailSender,
                                                        Map<String, Object> map,
                                                        String templateFileName)
         throws MessagingException
  {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    // Crée et ajoute un wrapper MIME multipart/alternative au message
    MimeMultipart mimeMultipart = new MimeMultipart("alternative");
    mimeMessage.setContent(mimeMultipart);

    // Crée et ajoute la partie "plain/text" au wrapper
    BodyPart plainTextPart = new MimeBodyPart();
    plainTextPart.setText(this.getTextMessage(map, templateFileName));
    mimeMultipart.addBodyPart(plainTextPart);
    // Crée et ajoute la partie "html" au wrapper
    BodyPart htmlPart = new MimeBodyPart();
    htmlPart.setContent(this.getHtmlMessage(map, templateFileName),
                        MimeMessageFactory.HTML_CONTENT_TYPE);
    mimeMultipart.addBodyPart(htmlPart);

    return mimeMessage;
  }

  /**
   * Renvoi la chaîne de caractères contenant le message sous sa forme "text"
   * @param map La table de messages à insérer dans le template
   * @param templateFileName le nom du fichier template à utiliser
   * @return La chaîne de caractères contenant le message sous sa forme "text"
   * @throws MessagingException TODO A COMMENTER
   */
  private String getTextMessage(Map<String, Object> map, String templateFileName)
          throws MessagingException
  {
    String fullFileName = this.getTextTemplatePath(templateFileName);
    return VelocityEngineUtils.mergeTemplateIntoString(this.getVelocityEngine(),
                                                       fullFileName, map);
  }

  /**
   * Renvoi la chaîne de caractères contenant le message sous sa forme "html"
   * @param map La table de messages à insérer dans le template
   * @param templateFileName le nom du fichier template à utiliser
   * @return La chaîne de caractères contenant le message sous sa forme "html"
   * @throws MessagingException TODO A COMMENTER
   */
  private String getHtmlMessage(Map<String, Object> map, String templateFileName)
          throws MessagingException
  {
    String fullFileName = this.getHtmlTemplatePath(templateFileName);
    return VelocityEngineUtils.mergeTemplateIntoString(this.getVelocityEngine(),
                                                       fullFileName, map);
  }

  /**
   * Getter du moteur de génération de messages
   * @return Le moteur de génération de messages à utiliser
   */
  public VelocityEngine getVelocityEngine()
  {
    return this.velocityEngine;
  }
  /**
   * Setter du moteur de génération de messages
   * @param velocityEngine Le moteur de génération de messages à setter
   */
  public void setVelocityEngine(VelocityEngine velocityEngine)
  {
    this.velocityEngine = velocityEngine;
    this.velocityEngine.init();
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getRootTemplatePath()
  {
    return this.rootTemplatePath;
  }
  /**
   *
   * TODO A COMMENTER
   * @param rootTemplatePath TODO A COMMENTER
   */
  public void setRootTemplatePath(String rootTemplatePath)
  {
    this.rootTemplatePath = rootTemplatePath;
  }

  /**
   *
   * TODO A COMMENTER
   * @param templateFileName TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws MessagingException TODO A COMMENTER
   */
  public String getTextTemplatePath(String templateFileName) throws MessagingException
  {
    try
    {
      return UtilFile.concatRelativePath(
          ResourceRef.RESOURCE, this.getRootTemplatePath(), "text", templateFileName);
    }
    catch(UserException ex)
    {
      throw new MessagingException("", ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param templateFileName TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws MessagingException TODO A COMMENTER
   */
  public String getHtmlTemplatePath(String templateFileName) throws MessagingException
  {
    try
    {
      return UtilFile.concatRelativePath(
          ResourceRef.RESOURCE, this.getRootTemplatePath(), "html", templateFileName);
    }
    catch(UserException ex)
    {
      throw new MessagingException("", ex);
    }
  }
}
