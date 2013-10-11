package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;
import com.bid4win.persistence.entity.account.Account;

/**
 * Metamodel de la classe Bot<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Bot.class)
public class Bot_ extends AccountBasedEntityMultipleAutoID_
{
  /** D�finition de la position minimum autoris�e des ench�res du robot dans la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> minBidPosition;
  /** D�finition de la position maximum autoris�e des ench�res du robot dans la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> maxBidPosition;
  /** D�finition de la derni�re position d'ench�re du robot dans la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> lastBidPosition;
  /** D�finition du nombre d'ench�res maximum autoris�es pour le robot sur la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> maxBidNb;
  /** D�finition du nombre d'ench�res positionn�es par le robot sur la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> usedBidNb;
  /** D�finition de la vente du robot d'ench�res */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Auction<?, ?, ?, ?, ?>> auction;
  /** D�finition du compte utilisateur du robot d'ench�res (afin d'avoir acc�s � ses champs sp�cifiques pour les requ�tes) */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Account> accountLink;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}