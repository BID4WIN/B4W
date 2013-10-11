package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class BidAbstractStubHistory
       extends BidHistory<BidAbstractStubHistory, AuctionAbstractStub, BidAbstractStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected BidAbstractStubHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Ench�re dont il faut cr�er l'historique
   * @throws UserException Si l'ench�re en argument est nulle
   */
  protected BidAbstractStubHistory(BidAbstractStub bid)
            throws UserException
  {
    super(bid);
  }
}
