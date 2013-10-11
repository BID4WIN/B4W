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
public class CreditBundleStub extends CreditBundle
{
  /** TODO A COMMENTER */
  private static Long ID = 0L;
  /** TODO A COMMENTER */
  private Long id = ID++;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditBundleStub()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de cr�dits
   * @param origin Provenance des cr�dits du lot
   * @param unitValue Valeur r�elle unitaire des cr�dits du lot
   * @param nb Nombre de cr�dits du lot
   * @throws ProtectionException Si le comte utilisateur en argument est prot�g�
   * @throws UserException Si le compte utilisateur des cr�dits, leur provenance
   * ou leur valeur r�elle unitaire est null ou si leur nombre est inf�rieur � un
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
