package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Credential_Fields;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.persistence.entity.contact.Email_Fields;

/**
 * Metamodel de la classe Account<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountAbstractStub_Fields extends AccountAbstract_Fields
{
  /** Définition du certificat de connexion du compte utilisateur */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static final Bid4WinFieldSimple<AccountAbstractStub, Credential> CREDENTIAL =
      new Bid4WinFieldSimple<AccountAbstractStub, Credential>(null, AccountAbstractStub_.credential);
  public static final Bid4WinFieldSimpleToSimple<AccountAbstractStub, Credential, String> LOGIN_VALUE =
      new Bid4WinFieldSimpleToSimple<AccountAbstractStub, Credential, String>(CREDENTIAL, Credential_Fields.LOGIN_VALUE);
  /** Définition de l'email du compte utilisateur */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static final Bid4WinFieldSimple<AccountAbstractStub, Email> EMAIL =
      new Bid4WinFieldSimple<AccountAbstractStub, Email>(null, AccountAbstractStub_.email);
  public static final Bid4WinFieldSimpleToSimple<AccountAbstractStub, Email, String> EMAIL_ADDRESS =
      new Bid4WinFieldSimpleToSimple<AccountAbstractStub, Email, String>(EMAIL, Email_Fields.ADDRESS);
}
