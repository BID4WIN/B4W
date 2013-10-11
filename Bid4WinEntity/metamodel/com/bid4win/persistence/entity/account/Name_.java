package com.bid4win.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe Name<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Name.class)
public class Name_ extends Bid4WinEmbeddable_
{
  /** D�finition du genre de l'utilisateur */
  public static volatile SingularAttribute<Name, Gender> gender;
  /** D�finition du pr�nom de l'utilisateur */
  public static volatile SingularAttribute<Name, String> firstName;
  /** D�finition du deuxi�me pr�nom de l'utilisateur */
  public static volatile SingularAttribute<Name, String> middleName;
  /** D�finition du nom de famille de l'utilisateur */
  public static volatile SingularAttribute<Name, String> lastName;

}
