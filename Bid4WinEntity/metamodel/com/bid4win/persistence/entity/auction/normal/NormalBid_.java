package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.auction.Bid_;

/**
 * Metamodel de la classe NormalBid<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(NormalBid.class)
public class NormalBid_ extends Bid_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
