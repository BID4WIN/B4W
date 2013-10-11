package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class BidStub extends Bid<BidStub, AuctionStub, BidStubHistory>
{
  /**
   * Constructeur pour création par introspection
   */
  protected BidStub()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur de l'enchère
   * @auction Vente de l'enchère
   * @position Position de l'enchère dans la vente
   * @throws ProtectionException Si la vente aux enchères est protégée
   * @throws UserException Si le compte utilisateur ou la vente est nul ou si la
   * position est inférieure à un
   */
  protected BidStub(Account account, AuctionStub auction, int position)
            throws ProtectionException, UserException
  {
    super(account, auction, position);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.BidAbstract#toHistory()
   */
  @Override
  public BidStubHistory toHistory() throws UserException
  {
    return new BidStubHistory(this);
  }
}
