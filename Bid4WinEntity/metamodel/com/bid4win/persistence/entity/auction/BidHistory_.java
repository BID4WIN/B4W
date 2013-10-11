package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe BidHistory<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(BidHistory.class)
public class BidHistory_ extends BidAbstract_
{
  /** Définition de la position de l'enchère sur la vente */
  public static volatile SingularAttribute<BidHistory<?, ?, ?>, Bid4WinDate> bidDate;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
