package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement_;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 * Metamodel de la classe CreditInvolvementAuctionAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditInvolvementAuctionAbstract.class)
public class CreditInvolvementAuctionAbstract_ extends CreditInvolvement_
{
  /** Définition de l'identifiant de la vente aux enchères sur laquelle sont impliqués les crédits */
  public static volatile SingularAttribute<CreditInvolvementAuctionAbstract<?, ?, ?, ?, ?>, String> auctionId;
  /** Définition de la vente aux enchères sur laquelle sont impliqués les crédits */
  public static volatile SingularAttribute<CreditInvolvementAuctionAbstract<?, ?, ?, ?, ?>, AuctionAbstract<?, ?, ?, ?, ?>> auction;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
