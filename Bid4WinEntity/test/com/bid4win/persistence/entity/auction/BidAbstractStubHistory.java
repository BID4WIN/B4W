package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class BidAbstractStubHistory
       extends BidHistory<BidAbstractStubHistory, AuctionAbstractStub, BidAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected BidAbstractStubHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Enchère dont il faut créer l'historique
   * @throws UserException Si l'enchère en argument est nulle
   */
  protected BidAbstractStubHistory(BidAbstractStub bid)
            throws UserException
  {
    super(bid);
  }
}
