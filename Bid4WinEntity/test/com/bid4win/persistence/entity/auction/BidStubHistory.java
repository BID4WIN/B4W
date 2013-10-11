package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class BidStubHistory
       extends BidHistory<BidStubHistory, AuctionStub, BidStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected BidStubHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Enchère dont il faut créer l'historique
   * @throws UserException Si l'enchère en argument est nulle
   */
  protected BidStubHistory(BidStub bid)
            throws UserException
  {
    super(bid);
  }
}
