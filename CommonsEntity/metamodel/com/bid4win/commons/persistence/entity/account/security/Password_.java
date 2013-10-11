package com.bid4win.commons.persistence.entity.account.security;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe Password<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Password.class)
public abstract class Password_ extends Bid4WinEmbeddable_
{
  /** Définition de la valeur du mot de passe */
  public static volatile SingularAttribute<Password, String> value;
}
