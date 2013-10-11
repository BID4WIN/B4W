package com.bid4win.commons.persistence.entity.contact;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe Email<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Email.class)
public class Email_ extends Bid4WinEmbeddable_
{
  /** Définition de l'adresse de l'email */
  public static volatile SingularAttribute<Email, String> address;
}
