package com.bid4win.commons.persistence.entity.account.security;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;

/**
 * Definition des accès aux champs de la classe Credential<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Credential_Fields extends Bid4WinEmbeddable_Fields
{
  /** Definition du champ correspondant à l'identifiant du certificat de connexion */
  public static final Bid4WinFieldSimple<Credential, Login> LOGIN =
      new Bid4WinFieldSimple<Credential, Login>(null, Credential_.login);
  /** Definition du champ correspondant à la valeur de l'identifiant du certificat de connexion */
  public static final Bid4WinFieldSimpleToSimple<Credential, Login, String> LOGIN_VALUE =
      new Bid4WinFieldSimpleToSimple<Credential, Login, String>(LOGIN, Login_Fields.VALUE);
}
