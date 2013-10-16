package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimpleParent;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_Fields;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract_;

public class BidAbstract_Fields extends  AccountBasedEntityMultipleAutoID_Fields
{
  public static final Bid4WinFieldSimple<BidAbstract<?, ?, ?>, AuctionAbstract<?, ?, ?, ?, ?>> AUCTION =
      new Bid4WinFieldSimple<BidAbstract<?, ?, ?>, AuctionAbstract<?, ?, ?, ?, ?>>(
          BidAbstract_.auction);
  public static final Bid4WinFieldSimpleToSimpleParent<BidAbstract<?, ?, ?>,
                                                       AccountBasedEntityMultipleGeneratedID<?, ?>,
                                                       AuctionAbstract<?, ?, ?, ?, ?>,
                                                       String> AUCTION_ID =
      new Bid4WinFieldSimpleToSimpleParent<BidAbstract<?, ?, ?>,
                                           AccountBasedEntityMultipleGeneratedID<?, ?>,
                                           AuctionAbstract<?, ?, ?, ?, ?>,
                                           String>(
          AUCTION, AuctionAbstract_Fields.ID);

}
