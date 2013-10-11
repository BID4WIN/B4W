package com.bid4win.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe Name<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Name.class)
public class Name_ extends Bid4WinEmbeddable_
{
  /** Définition du genre de l'utilisateur */
  public static volatile SingularAttribute<Name, Gender> gender;
  /** Définition du prénom de l'utilisateur */
  public static volatile SingularAttribute<Name, String> firstName;
  /** Définition du deuxième prénom de l'utilisateur */
  public static volatile SingularAttribute<Name, String> middleName;
  /** Définition du nom de famille de l'utilisateur */
  public static volatile SingularAttribute<Name, String> lastName;

}
