package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement_;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 * Metamodel de la classe CreditInvolvementAuctionAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(CreditInvolvementAuctionAbstract.class)
public class CreditInvolvementAuctionAbstract_ extends CreditInvolvement_
{
  /** D�finition de l'identifiant de la vente aux ench�res sur laquelle sont impliqu�s les cr�dits */
  public static volatile SingularAttribute<CreditInvolvementAuctionAbstract<?, ?, ?, ?, ?>, String> auctionId;
  /** D�finition de la vente aux ench�res sur laquelle sont impliqu�s les cr�dits */
  public static volatile SingularAttribute<CreditInvolvementAuctionAbstract<?, ?, ?, ?, ?>, AuctionAbstract<?, ?, ?, ?, ?>> auction;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
