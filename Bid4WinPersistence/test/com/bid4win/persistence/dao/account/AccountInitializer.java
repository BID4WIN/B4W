package com.bid4win.persistence.dao.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditOrigin;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountInitializer")
@Scope("singleton")
public class AccountInitializer
       extends com.bid4win.commons.persistence.dao.account.AccountInitializer_<Account, EntityGenerator>
{
  /** Référence du DAO des comptes utilisateur */
  @Autowired
  @Qualifier("AccountDaoStub")
  private AccountDaoStub dao;

  /**
   *
   * TODO A COMMENTER
   * @param bundleId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditBundle getBundle(long bundleId) throws Bid4WinException
  {
    return this.getDao().getBundleDao().getById(bundleId);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountIndex TODO A COMMENTER
   * @param creditNb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditBundle addBundle(int accountIndex, int creditNb) throws Bid4WinException
  {
    return this.addBundle(accountIndex, this.getGenerator().createCreditOrigin(), 1.23, creditNb);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountIndex TODO A COMMENTER
   * @param origin TODO A COMMENTER
   * @param totalValue TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditBundle addBundle(int accountIndex, CreditOrigin origin, double totalValue, int nb)
         throws Bid4WinException
  {
    Account account = this.getEntity(accountIndex);
    CreditBundle bundle = new CreditBundle(account, origin, totalValue, nb);
    this.getDao().getBundleDao().add(bundle);
    return bundle;
  }

  /**
   * Getter du DAO des comptes utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#getDao()
   */
  @Override
  public AccountDaoStub getDao()
  {
    return this.dao;
  }
}
