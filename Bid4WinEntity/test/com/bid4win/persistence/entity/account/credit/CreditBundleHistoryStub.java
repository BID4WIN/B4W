package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditBundleHistoryStub extends CreditBundleHistory
{
  /** TODO A COMMENTER */
  private static Long ID = 0L;
  /** TODO A COMMENTER */
  private Long id = ID++;

  /**
   * Constructeur pour création par introspection
   */
  protected CreditBundleHistoryStub()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bundle Lot de crédits dont il faut construire l'historique
   * @throws UserException Si le lot de crédits en argument est nul
   */
  public CreditBundleHistoryStub(CreditBundleStub bundle) throws UserException
  {
    super(bundle);
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
}
