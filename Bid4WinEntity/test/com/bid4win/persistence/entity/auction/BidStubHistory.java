package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class BidStubHistory
       extends BidHistory<BidStubHistory, AuctionStub, BidStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected BidStubHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Ench�re dont il faut cr�er l'historique
   * @throws UserException Si l'ench�re en argument est nulle
   */
  protected BidStubHistory(BidStub bid)
            throws UserException
  {
    super(bid);
  }
}
