package com.bid4win.commons.persistence.entity.account.security;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe Credential<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Credential.class)
public abstract class Credential_ extends Bid4WinEmbeddable_
{
  /** Définition de l'identifiant du certificat de connexion */
  public static volatile SingularAttribute<Credential, Login> login;
  /** Définition du mot de passe du certificat de connexion */
  public static volatile SingularAttribute<Credential, Password> password;
  /** Définition du rôle du certificat de connexion */
  public static volatile SingularAttribute<Credential, Role> role;
}
