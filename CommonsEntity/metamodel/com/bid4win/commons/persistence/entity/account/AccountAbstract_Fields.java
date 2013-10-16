package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Credential_Fields;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.persistence.entity.contact.Email_Fields;

/**
 * Définition des accès aux champs de la classe AccountAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountAbstract_Fields extends Bid4WinEntityGeneratedID_Fields
{
  /** Definition du champ correspondant au certificat de connexion du compte utilisateur */
  public static final Bid4WinFieldSimple<AccountAbstract<?>, Credential> CREDENTIAL =
      new Bid4WinFieldSimple<AccountAbstract<?>, Credential>(null, AccountAbstract_.credential);
  /** Definition du champ correspondant à la valeur de l'identifiant de connexion du compte utilisateur */
  public static final Bid4WinFieldSimpleToSimple<AccountAbstract<?>, Credential, String> LOGIN_VALUE =
      new Bid4WinFieldSimpleToSimple<AccountAbstract<?>, Credential, String>(CREDENTIAL, Credential_Fields.LOGIN_VALUE);

  /** Definition du champ correspondant l'email du compte utilisateur */
  public static final Bid4WinFieldSimple<AccountAbstract<?>, Email> EMAIL =
      new Bid4WinFieldSimple<AccountAbstract<?>, Email>(null, AccountAbstract_.email);
  /** Definition du champ correspondant à l'adresse email du compte utilisateur */
  public static final Bid4WinFieldSimpleToSimple<AccountAbstract<?>, Email, String> EMAIL_ADDRESS =
      new Bid4WinFieldSimpleToSimple<AccountAbstract<?>, Email, String>(EMAIL, Email_Fields.ADDRESS);
}
