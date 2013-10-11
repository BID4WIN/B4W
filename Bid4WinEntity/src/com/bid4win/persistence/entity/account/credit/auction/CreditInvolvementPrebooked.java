package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
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
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CreditInvolvementPrebooked
       extends CreditInvolvementAuction<CreditInvolvementPrebooked, CreditUsagePrebooked,
                                        PrebookedAuction, CreditInvolvementPrebookedHistory>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CreditInvolvementPrebooked()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur des crédits utilisés
   * @param auction Vente aux enchères sur laquelle sont impliqués les crédits
   * @throws UserException Si le compte utilisateur ou la vente aux enchères est
   * nul
   */
  protected CreditInvolvementPrebooked(Account account, PrebookedAuction auction)
            throws UserException
  {
    super(account, auction);
  }

  /**
  *
  * TODO A COMMENTER
  * @return {@inheritDoc}
  * @see com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction#getAccountRelationToInvolvement()
  */
 @Override
 public Bid4WinRelation getAccountRelationToInvolvement()
 {
   return null;
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
  protected CreditUsagePrebooked createCreditUsage(CreditBundle bundle, int usedNb)
            throws ProtectionException, UserException
  {
    return new CreditUsagePrebooked(bundle, this, usedNb);
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
    return new CreditInvolvementPrebookedHistory(this);
  }
}
