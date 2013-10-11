package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe Bid<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Bid.class)
public class Bid_ extends BidAbstract_
{
  /** D�finition du lien vers la vente sur laquelle est plac�e l'ench�re */
  public static volatile SingularAttribute<Bid<?, ?, ?>, Auction<?, ?, ?, ?, ?>> auctionLink;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
