package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.auction.BidHistory_;

/**
 * Metamodel de la classe PrebookedBidHistory<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(PrebookedBidHistory.class)
public class PrebookedBidHistory_ extends BidHistory_
{
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
