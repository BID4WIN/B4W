package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class BidAbstractStub
       extends BidAbstract<BidAbstractStub, AuctionAbstractStub, BidAbstractStubHistory>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected BidAbstractStub()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur de l'ench�re
   * @param auction Vente de l'ench�re
   * @param position Position de l'ench�re dans la vente
   * @throws UserException Si le compte utilisateur ou la vente est nul ou si la
   * position est inf�rieure � un
   */
  protected BidAbstractStub(Account account, AuctionAbstractStub auction, int position)
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
  public BidAbstractStubHistory toHistory() throws UserException
  {
    return new BidAbstractStubHistory(this);
  }
}
