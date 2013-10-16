package com.bid4win.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

public class Name_Fields extends Bid4WinEmbeddable_Fields
{
  public static final Bid4WinFieldSimple<Name, Gender> GENDER =
      new Bid4WinFieldSimple<Name, Gender>(null, Name_.gender);
  public static final Bid4WinFieldSimple<Name, String> FIRST_NAME =
      new Bid4WinFieldSimple<Name, String>(null, Name_.firstName);
  public static final Bid4WinFieldSimple<Name, String> MIDDLE_NAME =
      new Bid4WinFieldSimple<Name, String>(null, Name_.middleName);
  public static final Bid4WinFieldSimple<Name, String> LAST_NAME =
      new Bid4WinFieldSimple<Name, String>(null, Name_.lastName);
}
