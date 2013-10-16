package com.bid4win.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;
import com.bid4win.commons.persistence.entity.core.Bid4WinDateForRequest;

public class User_Fields extends Bid4WinEmbeddable_Fields
{
  public static final Bid4WinFieldSimple<User, Name> NAME =
      new Bid4WinFieldSimple<User, Name>(null, User_.name);
  public static final Bid4WinFieldSimpleToSimple<User, Name, Gender> GENDER =
      new Bid4WinFieldSimpleToSimple<User, Name, Gender>(NAME, Name_Fields.GENDER);
  public static final Bid4WinFieldSimpleToSimple<User, Name, String> FIRST_NAME =
      new Bid4WinFieldSimpleToSimple<User, Name, String>(NAME, Name_Fields.FIRST_NAME);
  public static final Bid4WinFieldSimpleToSimple<User, Name, String> MIDDLE_NAME =
      new Bid4WinFieldSimpleToSimple<User, Name, String>(NAME, Name_Fields.MIDDLE_NAME);
  public static final Bid4WinFieldSimpleToSimple<User, Name, String> LAST_NAME =
      new Bid4WinFieldSimpleToSimple<User, Name, String>(NAME, Name_Fields.LAST_NAME);
  /** Definition du champ correspondant à la date de naissance de l'utilisateur */
  public static final Bid4WinFieldSimple<User, Bid4WinDateForRequest> BIRTH_DATE =
      new Bid4WinFieldSimple<User, Bid4WinDateForRequest>(null, User_.birthDateForRequest);
}
