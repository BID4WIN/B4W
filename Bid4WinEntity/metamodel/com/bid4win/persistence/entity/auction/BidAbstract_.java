package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;

/**
 * Metamodel de la classe BidAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(BidAbstract.class)
public class BidAbstract_ extends AccountBasedEntityMultipleAutoID_
{
  /** D�finition de la position de l'ench�re sur la vente */
  public static volatile SingularAttribute<BidAbstract<?, ?, ?>, Integer> position;
  /** D�finition de la vente de l'ench�re */
  public static volatile SingularAttribute<BidAbstract<?, ?, ?>, AuctionAbstract<?, ?, ?, ?, ?>> auction;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
