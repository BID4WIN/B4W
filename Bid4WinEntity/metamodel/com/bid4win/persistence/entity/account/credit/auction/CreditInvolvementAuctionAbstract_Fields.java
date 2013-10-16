package com.bid4win.persistence.entity.account.credit.auction;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimpleParent;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement_Fields;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.AuctionAbstract_Fields;

public class CreditInvolvementAuctionAbstract_Fields extends CreditInvolvement_Fields
{
  public static final Bid4WinFieldSimple<CreditInvolvementAuctionAbstract<?, ?, ?, ?, ?>,
                                         AuctionAbstract<?, ?, ?, ?, ?>> AUCTION =
      new Bid4WinFieldSimple<CreditInvolvementAuctionAbstract<?, ?, ?, ?, ?>, AuctionAbstract<?, ?, ?, ?, ?>>(
          CreditInvolvementAuctionAbstract_.auction);
  public static final Bid4WinFieldSimpleToSimpleParent<CreditInvolvementAuctionAbstract<?, ?, ?, ?, ?>,
                                                       AccountBasedEntityMultipleGeneratedID<?, ?>,
                                                       AuctionAbstract<?, ?, ?, ?, ?>,
                                                       String> AUCTION_ID =
      new Bid4WinFieldSimpleToSimpleParent<CreditInvolvementAuctionAbstract<?, ?, ?, ?, ?>,
                                           AccountBasedEntityMultipleGeneratedID<?, ?>,
                                           AuctionAbstract<?, ?, ?, ?, ?>,
                                           String>(
          AUCTION, AuctionAbstract_Fields.ID);

}
