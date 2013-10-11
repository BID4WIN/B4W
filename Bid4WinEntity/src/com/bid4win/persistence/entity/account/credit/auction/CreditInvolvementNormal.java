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
import com.bid4win.persistence.entity.account.Account_Relations;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CreditInvolvementNormal
       extends CreditInvolvementAuction<CreditInvolvementNormal, CreditUsageNormal,
                                        NormalAuction, CreditInvolvementNormalHistory>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditInvolvementNormal()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur des cr�dits utilis�s
   * @param auction Vente aux ench�res sur laquelle sont impliqu�s les cr�dits
   * @throws UserException Si la vente aux ench�res ou le compte utilisateur est
   * nul ou si ce dernier r�f�rence d�j� une implication sur la m�me ench�re
   */
  public CreditInvolvementNormal(Account account, NormalAuction auction) throws UserException
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
    return Account_Relations.RELATION_INVOLVEMENT_NORMAL_MAP;
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
  protected CreditUsageNormal createCreditUsage(CreditBundle bundle, int usedNb)
            throws ProtectionException, UserException
  {
    return new CreditUsageNormal(bundle, this, usedNb);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvement#createHistory()
   */
  @Override
  public CreditInvolvementNormalHistory createHistory() throws UserException
  {
    return new CreditInvolvementNormalHistory(this);
  }

}
