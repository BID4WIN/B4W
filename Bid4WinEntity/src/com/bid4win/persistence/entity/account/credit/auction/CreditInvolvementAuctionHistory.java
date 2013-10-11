package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <HISTORIZED> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CreditInvolvementAuctionHistory<CLASS extends CreditInvolvementAuctionHistory<CLASS, USAGE, AUCTION, HISTORIZED>,
                                                      USAGE extends CreditUsageHistory<USAGE, CLASS>,
                                                      AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                      HISTORIZED extends CreditInvolvementAuction<HISTORIZED, ? extends CreditUsage<?, ?>, AUCTION, CLASS>>
       extends CreditInvolvementAuctionAbstract<CLASS, USAGE, CreditBundleHistory, AUCTION, CLASS>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CreditInvolvementAuctionHistory()
  {
    super();
  }
  /**
   *
   * TODO A COMMENTER
   * @param involvement TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected CreditInvolvementAuctionHistory(HISTORIZED involvement) throws UserException
  {
    super(involvement.getAccount(), involvement.getAuction());
    for(CreditUsage<?, ?> usage : involvement.getUsageSet())
    {
      this.addUsage(UtilObject.checkNotNull("history", usage.getBundle().getHistory(),
                                            AccountRef.ACCOUNT_CREDIT_NOT_HISTORIZED_ERROR),
                    usage.getUsedNb());
    }
  }
}
