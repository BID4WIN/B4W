package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe Auction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Auction.class)
public class Auction_ extends AuctionAbstract_
{
  /** Définition des éléments de planification de la vente aux enchères */
  public static volatile SingularAttribute<Auction<?, ?, ?, ?, ?>, Schedule<?>> schedule;
  /** Définition des conditions de l'historique de vente aux enchères */
  public static volatile SingularAttribute<Auction<?, ?, ?, ?, ?>, Terms<?>> terms;
  /** Définition de la politique d'annulation de la vente aux enchères */
  public static volatile SingularAttribute<Auction<?, ?, ?, ?, ?>, CancelPolicy<?>> cancelPolicy;
  /** Définition de la map des dernières enchères placées sur la vente */
  public static volatile MapAttribute<Auction<?, ?, ?, ?, ?>, Integer, Bid<?, ?, ?>> lastBidMapInternal;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** Défini la profondeur du noeud de relation existant avec la map des dernières enchères */
    Auction_Relations.NODE_LAST_BID_MAP.addNode(Bid_Relations.NODE_AUCTION);
    Auction_Relations.NODE_LAST_BID_MAP.addNode(Bid_Relations.NODE_AUCTION_LINK);
    Auction_Relations.NODE_LAST_BID_MAP.addNode(Bid_Relations.NODE_ACCOUNT);
    Bid4WinEntity_.stopProtection();
  }
}
