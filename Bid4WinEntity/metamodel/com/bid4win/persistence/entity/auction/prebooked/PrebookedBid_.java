package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.auction.Bid_;

/**
 * Metamodel de la classe PrebookedBid<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(PrebookedBid.class)
public class PrebookedBid_ extends Bid_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
