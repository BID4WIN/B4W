package com.bid4win.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe User<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(User.class)
public class User_ extends Bid4WinEmbeddable_
{
  /** Définition du nom de l'utilisateur */
  public static volatile SingularAttribute<User, Name> name;
  /** Définition de la date de naissance de l'utilisateur */
  public static volatile SingularAttribute<User, Bid4WinDate> birthDate;

}
