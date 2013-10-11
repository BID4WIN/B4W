package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe Auction<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Auction.class)
public class Auction_ extends AuctionAbstract_
{
  /** D�finition des �l�ments de planification de la vente aux ench�res */
  public static volatile SingularAttribute<Auction<?, ?, ?, ?, ?>, Schedule<?>> schedule;
  /** D�finition des conditions de l'historique de vente aux ench�res */
  public static volatile SingularAttribute<Auction<?, ?, ?, ?, ?>, Terms<?>> terms;
  /** D�finition de la politique d'annulation de la vente aux ench�res */
  public static volatile SingularAttribute<Auction<?, ?, ?, ?, ?>, CancelPolicy<?>> cancelPolicy;
  /** D�finition de la map des derni�res ench�res plac�es sur la vente */
  public static volatile MapAttribute<Auction<?, ?, ?, ?, ?>, Integer, Bid<?, ?, ?>> lastBidMapInternal;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** D�fini la profondeur du noeud de relation existant avec la map des derni�res ench�res */
    Auction_Relations.NODE_LAST_BID_MAP.addNode(Bid_Relations.NODE_AUCTION);
    Auction_Relations.NODE_LAST_BID_MAP.addNode(Bid_Relations.NODE_AUCTION_LINK);
    Auction_Relations.NODE_LAST_BID_MAP.addNode(Bid_Relations.NODE_ACCOUNT);
    Bid4WinEntity_.stopProtection();
  }
}
