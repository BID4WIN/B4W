package com.bid4win.commons.persistence.entity.account.security;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Definition des accès aux champs de la classe Login<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Login_Fields extends Bid4WinEmbeddable_Fields
{
  /** Definition du champ correspondant à la valeur de l'identifiant de connexion */
  public static final Bid4WinFieldSimple<Login, String> VALUE =
      new Bid4WinFieldSimple<Login, String>(null, Login_.value);
}
