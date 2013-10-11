package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditInvolvementHistoryStub
       extends CreditInvolvement<CreditInvolvementHistoryStub, CreditUsageHistoryStub, CreditBundleHistory, CreditInvolvementHistoryStub>
{
  /**
   * Constructeur complet
   * @param account Compte utilisateur des cr�dits utilis�s
   * @throws UserException Si le compte utilisateur ou le type d'implication est nul
   */
  public CreditInvolvementHistoryStub(Account account) throws UserException
  {
    super(account);
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
  protected CreditUsageHistoryStub createCreditUsage(CreditBundleHistory bundle, int usedNb)
      throws ProtectionException, UserException
  {
    return new CreditUsageHistoryStub(bundle, this, usedNb);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvement#createHistory()
   */
  @Override
  public CreditInvolvementHistoryStub createHistory() throws UserException
  {
    return new CreditInvolvementHistoryStub(this.getAccount());
  }
}
