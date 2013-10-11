package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditBundleStub extends CreditBundle
{
  /** TODO A COMMENTER */
  private static Long ID = 0L;
  /** TODO A COMMENTER */
  private Long id = ID++;

  /**
   * Constructeur pour création par introspection
   */
  protected CreditBundleStub()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de crédits
   * @param origin Provenance des crédits du lot
   * @param unitValue Valeur réelle unitaire des crédits du lot
   * @param nb Nombre de crédits du lot
   * @throws ProtectionException Si le comte utilisateur en argument est protégé
   * @throws UserException Si le compte utilisateur des crédits, leur provenance
   * ou leur valeur réelle unitaire est null ou si leur nombre est inférieur à un
   */
  public CreditBundleStub(Account account, CreditOrigin origin,
                          double unitValue, int nb)
         throws ProtectionException, UserException
  {
    super(account, origin, unitValue, nb);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditBundle#historize()
   */
  @Override
  public CreditBundleHistoryStub historize() throws ProtectionException, UserException
  {
    this.defineHistory(new CreditBundleHistoryStub(this));
    return this.getHistory();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID#getId()
   */
  @Override
  public Long getId()
  {
    return this.id;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getVersion()
   */
  @Override
  public int getVersion()
  {
    return 0;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditBundle#getHistory()
   */
  @Override
  public CreditBundleHistoryStub getHistory()
  {
    return (CreditBundleHistoryStub)super.getHistory();
  }
}
