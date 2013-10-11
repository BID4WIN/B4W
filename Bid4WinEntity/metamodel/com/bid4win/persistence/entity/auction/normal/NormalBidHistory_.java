package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.auction.BidHistory_;

/**
 * Metamodel de la classe NormalBidHistory<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(NormalBidHistory.class)
public class NormalBidHistory_ extends BidHistory_
{
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
