package com.bid4win.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;
import com.bid4win.commons.persistence.entity.account.AccountAbstract_Fields;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Credential_Fields;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.persistence.entity.contact.Email_Fields;
import com.bid4win.commons.persistence.entity.core.Bid4WinDateForRequest;

/**
 * Metamodel de la classe Account<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Account_Fields extends AccountAbstract_Fields
{
/*  public static final Bid4WinFieldSimple<Account, Account> SPONSOR =
      new Bid4WinFieldSimple<Account, Account>(null, Account_.sponsor);
  public static final Bid4WinFieldSimpleToSimpleParent<Account, Bid4WinEntityGeneratedID<?>, Account, String> SPONSOR_ID =
      new Bid4WinFieldSimpleToSimpleParent<Account, Bid4WinEntityGeneratedID<?>, Account, String>(
          SPONSOR, Account_Fields.ID);*/

  public static final Bid4WinFieldSimple<Account, User> USER =
      new Bid4WinFieldSimple<Account, User>(null, Account_.user);
  public static final Bid4WinFieldSimpleToSimple<Account, User, Name> NAME =
      new Bid4WinFieldSimpleToSimple<Account, User, Name>(USER, User_Fields.NAME);
  public static final Bid4WinFieldSimpleToSimple<Account, User, Gender> GENDER =
      new Bid4WinFieldSimpleToSimple<Account, User, Gender>(USER, User_Fields.GENDER);
  public static final Bid4WinFieldSimpleToSimple<Account, User, String> FIRST_NAME =
      new Bid4WinFieldSimpleToSimple<Account, User, String>(USER, User_Fields.FIRST_NAME);
  public static final Bid4WinFieldSimpleToSimple<Account, User, String> MIDDLE_NAME =
      new Bid4WinFieldSimpleToSimple<Account, User, String>(USER, User_Fields.MIDDLE_NAME);
  public static final Bid4WinFieldSimpleToSimple<Account, User, String> LAST_NAME =
      new Bid4WinFieldSimpleToSimple<Account, User, String>(USER, User_Fields.LAST_NAME);

  public static final Bid4WinFieldSimpleToSimple<Account, User, Bid4WinDateForRequest> BIRTH_DATE =
      new Bid4WinFieldSimpleToSimple<Account, User, Bid4WinDateForRequest>(USER, User_Fields.BIRTH_DATE);


  /** Définition du certificat de connexion du compte utilisateur */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static final Bid4WinFieldSimple<Account, Credential> CREDENTIAL =
      new Bid4WinFieldSimple<Account, Credential>(null, Account_.credential);
  public static final Bid4WinFieldSimpleToSimple<Account, Credential, String> LOGIN_VALUE =
      new Bid4WinFieldSimpleToSimple<Account, Credential, String>(CREDENTIAL, Credential_Fields.LOGIN_VALUE);
  /** Définition de l'email du compte utilisateur */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static final Bid4WinFieldSimple<Account, Email> EMAIL =
      new Bid4WinFieldSimple<Account, Email>(null, Account_.email);
  public static final Bid4WinFieldSimpleToSimple<Account, Email, String> EMAIL_ADDRESS =
      new Bid4WinFieldSimpleToSimple<Account, Email, String>(EMAIL, Email_Fields.ADDRESS);
}
