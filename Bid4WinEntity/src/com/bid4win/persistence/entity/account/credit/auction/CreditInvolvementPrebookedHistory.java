package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
public class CreditInvolvementPrebookedHistory
       extends CreditInvolvementAuctionHistory<CreditInvolvementPrebookedHistory,
                                               CreditUsagePrebookedHistory,
                                               PrebookedAuction,
                                               CreditInvolvementPrebooked>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CreditInvolvementPrebookedHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param involvement Implication de crédits à historiser
   * @throws UserException Si le compte utilisateur ou la vente aux enchères est
   * nul
   */
  protected CreditInvolvementPrebookedHistory(CreditInvolvementPrebooked involvement)
            throws UserException
  {
    super(involvement);
  }

  /**
   *
   * TODO A COMMENTER
   * @param bundle {@inheritDoc}
   * @param usedNb {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvement#createCreditUsage(com.bid4win.persistence.entity.account.credit.CreditBundleAbstract, int)
   */
  @Override
  protected CreditUsagePrebookedHistory createCreditUsage(CreditBundleHistory bundle, int usedNb)
            throws ProtectionException, UserException
  {
    return new CreditUsagePrebookedHistory(bundle, this, usedNb);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvement#createHistory()
   */
  @Override
  public CreditInvolvementPrebookedHistory createHistory() throws UserException
  {
    throw new UserException(AuctionRef.AUCTION_HISTORIZED_ERROR);
  }
}
