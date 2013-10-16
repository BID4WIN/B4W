package com.bid4win.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;
import com.bid4win.commons.persistence.entity.core.Bid4WinDateForRequest;

/**
 * Metamodel de la classe User<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(User.class)
public class User_ extends Bid4WinEmbeddable_
{
  /** D�finition du nom de l'utilisateur */
  public static volatile SingularAttribute<User, Name> name;
  /** D�finition de la date de naissance de l'utilisateur */
  public static volatile SingularAttribute<User, Bid4WinDate> birthDate;
  /** D�finition de la date de naissance de l'utilisateur utilisable pour les requ�tes*/
  public static volatile SingularAttribute<User, Bid4WinDateForRequest> birthDateForRequest;

}
