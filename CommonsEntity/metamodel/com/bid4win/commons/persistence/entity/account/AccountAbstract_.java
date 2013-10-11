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
 * @author Emeric Fill�tre
 */
@StaticMetamodel(AccountAbstract.class)
public abstract class AccountAbstract_ extends Bid4WinEntityGeneratedID_
{
  /** D�finition du certificat de connexion du compte utilisateur */
  public static volatile SingularAttribute<AccountAbstract<?>, Credential> credential;
  /** D�finition de l'email du compte utilisateur */
  public static volatile SingularAttribute<AccountAbstract<?>, Email> email;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
