package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;
import com.bid4win.persistence.entity.account.Account;

/**
 * Metamodel de la classe Bot<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Bot.class)
public class Bot_ extends AccountBasedEntityMultipleAutoID_
{
  /** Définition de la position minimum autorisée des enchères du robot dans la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> minBidPosition;
  /** Définition de la position maximum autorisée des enchères du robot dans la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> maxBidPosition;
  /** Définition de la dernière position d'enchère du robot dans la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> lastBidPosition;
  /** Définition du nombre d'enchères maximum autorisées pour le robot sur la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> maxBidNb;
  /** Définition du nombre d'enchères positionnées par le robot sur la vente */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Integer> usedBidNb;
  /** Définition de la vente du robot d'enchères */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Auction<?, ?, ?, ?, ?>> auction;
  /** Définition du compte utilisateur du robot d'enchères (afin d'avoir accès à ses champs spécifiques pour les requêtes) */
  public static volatile SingularAttribute<Bot<?, ?, ?>, Account> accountLink;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}