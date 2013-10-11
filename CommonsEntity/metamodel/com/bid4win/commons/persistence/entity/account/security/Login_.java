package com.bid4win.commons.persistence.entity.account.security;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe Login<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Login.class)
public abstract class Login_ extends Bid4WinEmbeddable_
{
  /** Définition de la valeur de l'identifiant de connexion */
  public static volatile SingularAttribute<Login, String> value;
}
