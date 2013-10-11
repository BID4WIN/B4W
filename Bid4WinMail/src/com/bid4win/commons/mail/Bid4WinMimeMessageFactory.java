package com.bid4win.commons.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.reference.PropertyRef;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.service.property.PropertyService;

/**
 * Classe de génération de messages MIME. Permet la génération de messages ayant
 * à la fois une version au format "text" et/ou un version au format "html".
 *
 * @author Maxime Ollagnier
 */
@Component("Bid4WinMimeMessageFactory")
@Scope("singleton")
public class Bid4WinMimeMessageFactory
{
  /** Nom du fichier template de mail d'activation */
  private static final String ACTIVATION_TEMPLATE_FILE_NAME = "mail-template-activation.vm";

  /** Nom du fichier template de mail d'activation */
  private static final String PASSWORD_REINIT_TEMPLATE_FILE_NAME = "mail-template-password-reinit.vm";

  /** Sujet de l'e-mail d'activation de compte utilisateur */
  private static final String ACTIVATION_SUBJECT = "mail.activation.subject";

  /** Sujet de l'e-mail de ré-initialisation de mot de passe */
  private static final String PASSWORD_REINIT_SUBJECT = "mail.password_reinit.subject";

  /** Usine à message MIME */
  @Autowired
  @Qualifier("mimeMessageFactory")
  private MimeMessageFactory mimeMessageFactory = null;
  /** Référence du PropertyService */
  @Autowired
  @Qualifier("PropertyService")
  private PropertyService propertyService = null;

  /**
   * Renvoi un message MIME d'activation de compte utilisateur
   * @param account Compte utilisateur destinataire du message
   * @param mailSender Sender à utiliser pour créer le message MIME
   * @return Le message MIME généré
   * @throws MessagingException Si une erreur intervient lors du traitement du
   * message MIME
   */
  public MimeMessage getActivationMessage(Account account, MailSender mailSender) throws MessagingException
  {
    MimeMessage msg = this.getMessage(account, ACTIVATION_SUBJECT, mailSender, ACTIVATION_TEMPLATE_FILE_NAME);
    msg.setRecipients(RecipientType.TO, InternetAddress.parse(account.getEmail().getAddress(), false));
    return msg;
  }

  /**
   * Renvoi un message MIME d'activation de compte utilisateur
   * @param account Compte utilisateur destinataire du message
   * @param reinitKey La clef de ré-initialisation du mot de passe
   * @param mailSender Sender à utiliser pour créer le message MIME
   * @return Le message MIME généré
   * @throws MessagingException Si une erreur intervient lors du traitement du
   * message MIME
   */
  public MimeMessage getPasswordReinitMessage(Account account, String reinitKey, MailSender mailSender) throws MessagingException
  {
    Map<String, Object> map = new HashMap<String, Object>();
    String rootUrl = "";
    try
    {
      rootUrl = this.getPropertyService().getProperty(PropertyRef.SERVER_ROOT_LOCATION.getCode()).getValue();
    }
    catch(Throwable th)
    {
      th.printStackTrace();
    }
    String actionName = "PasswordReinitPromptAction";
    String pageName = "PasswordReinitForm";
    String reinitUrl = rootUrl + actionName +
                       "?accountId=" + account.getId() +
                       "&reinitKey=" + reinitKey + "#" + pageName;
    map.put("reinitUrl", reinitUrl);
    MimeMessage msg = this.getMessage(account, PASSWORD_REINIT_SUBJECT, mailSender, map, PASSWORD_REINIT_TEMPLATE_FILE_NAME);
    return msg;
  }

  /**
   * Renvoi un message MIME multipart/alternative avec à la fois un corps de
   * type "plain/text" et un corps de type "html".
   * @param account Compte utilisateur destinataire du message
   * @param subject Sujet du message
   * @param mailSender Sender à utiliser pour créer le message MIME
   * @param templateFileName le nom des fichiers templates à utiliser
   * @return Le message MIME généré
   * @throws MessagingException Si une erreur intervient lors du traitement du
   * message MIME
   */
  public MimeMessage getMessage(Account account, String subject, MailSender mailSender, String templateFileName) throws MessagingException
  {
    return this.getMessage(account, subject, mailSender, null, templateFileName);
  }

  /**
   * Renvoi un message MIME multipart/alternative avec à la fois un corps de
   * type "plain/text" et un corps de type "html".
   * @param account Compte utilisateur destinataire du message
   * @param subject Sujet du message
   * @param mailSender Sender à utiliser pour créer le message MIME
   * @param map La table de Strings à insérer dans les messages template
   * @param templateFileName le nom des fichiers templates à utiliser
   * @return Le message MIME généré
   * @throws MessagingException Si une erreur intervient lors du traitement du
   * message MIME
   */
  public MimeMessage getMessage(Account account, String subject, MailSender mailSender, Map<String, Object> map, String templateFileName) throws MessagingException
  {
    if(map == null)
    {
      map = new HashMap<String, Object>();
    }
    map.put("subject", subject);
    map.put("account", account);
    MimeMessage msg = this.getMimeMessageFactory().getMimeMultipartAlternativeMessage(mailSender, map, templateFileName);
    msg.setRecipients(RecipientType.TO, InternetAddress.parse(account.getEmail().getAddress(), false));
    msg.setSubject(subject);
    return msg;
  }

  /**
   * Getter de la référence de la MimeMessageFactory
   * @return La référence de la MimeMessageFactory
   */
  private MimeMessageFactory getMimeMessageFactory()
  {
    return this.mimeMessageFactory;
  }
  /**
   * Renvoi le PropertyService
   * @return Le PropertyService
   */
  protected PropertyService getPropertyService()
  {
    return this.propertyService;
  }
}
