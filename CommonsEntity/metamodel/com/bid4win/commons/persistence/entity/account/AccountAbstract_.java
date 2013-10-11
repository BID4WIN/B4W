package com.bid4win.commons.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * Metamodel de la classe AccountAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(AccountAbstract.class)
public abstract class AccountAbstract_ extends Bid4WinEntityGeneratedID_
{
  /** Définition du certificat de connexion du compte utilisateur */
  public static volatile SingularAttribute<AccountAbstract<?>, Credential> credential;
  /** Définition de l'email du compte utilisateur */
  public static volatile SingularAttribute<AccountAbstract<?>, Email> email;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
