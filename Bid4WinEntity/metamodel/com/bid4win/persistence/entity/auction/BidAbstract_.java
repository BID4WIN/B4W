package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;

/**
 * Metamodel de la classe BidAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(BidAbstract.class)
public class BidAbstract_ extends AccountBasedEntityMultipleAutoID_
{
  /** Définition de la position de l'enchère sur la vente */
  public static volatile SingularAttribute<BidAbstract<?, ?, ?>, Integer> position;
  /** Définition de la vente de l'enchère */
  public static volatile SingularAttribute<BidAbstract<?, ?, ?>, AuctionAbstract<?, ?, ?, ?, ?>> auction;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
