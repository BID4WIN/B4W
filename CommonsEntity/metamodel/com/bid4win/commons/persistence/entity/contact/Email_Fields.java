package com.bid4win.commons.persistence.entity.contact;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Définition des accès aux champs de la classe Email<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Email_Fields extends Bid4WinEmbeddable_Fields
{
  /** Definition du champ correspondant à l'adresse de l'email */
  public static final Bid4WinFieldSimple<Email, String> ADDRESS =
      new Bid4WinFieldSimple<Email, String>(null, Email_.address);
}
