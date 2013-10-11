package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe Bid<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Bid.class)
public class Bid_ extends BidAbstract_
{
  /** Définition du lien vers la vente sur laquelle est placée l'enchère */
  public static volatile SingularAttribute<Bid<?, ?, ?>, Auction<?, ?, ?, ?, ?>> auctionLink;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
