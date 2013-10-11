package com.bid4win.manager.account;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinMapComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.collection.CreditMap;

/**
 * Cette classe représente un jeu de crédits (provenant potentiellement de plusieurs
 * lots) utilisés pour un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UsedCredit extends Bid4WinObject<UsedCredit>
{
  /** Compte utilisateur des crédits utilisés */
  private Account account = null;
  /** Utilisation des crédits selon leurs lots de provenance */
  private CreditMap usedCreditMap = null;

  /**
   * Constructeur
   * @param account Compte utilisateur des crédits utilisés
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public UsedCredit(Account account)
         throws UserException
  {
    this(account, null);
  }
  /**
   * Constructeur
   * @param account Compte utilisateur des crédits utilisés
   * @param usedCreditMap Utilisation des crédits selon leurs lots de provenance
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public UsedCredit(Account account, CreditMap usedCreditMap)
         throws UserException
  {
    this.setAccount(account);
    this.setUsedCreditMap(usedCreditMap);
  }

  /**
   * Getter du compte utilisateur des crédits utilisés
   * @return Le compte utilisateur des crédits utilisés
   */
  public Account getAccount()
  {
    return this.account;
  }
  /**
   * Setter du compte utilisateur des crédits utilisés
   * @param account Compte utilisateur des crédits utilisés à positionner
   * @throws UserException Si le compte utilisateur est nul
   */
  private void setAccount(Account account) throws UserException
  {
    this.account = UtilObject.checkNotNull("account", account,
                                           AccountRef.ACCOUNT_MISSING_ERROR);
  }

  /**
   * Getter de l'utilisation des crédits selon leurs lots de provenance
   * @return L'utilisation des crédits selon leurs lots de provenance
   */
  public CreditMap getUsedCreditMap()
  {
    return this.usedCreditMap;
  }
  /**
   * Setter de l'utilisation des crédits selon leurs lots de provenance
   * @param usedCreditMap Utilisation des crédits à positionner
   */
  private void setUsedCreditMap(CreditMap usedCreditMap)
  {
    if(usedCreditMap == null)
    {
      usedCreditMap = new CreditMap(0);
    }
    this.usedCreditMap = usedCreditMap;
  }

  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(UsedCredit toBeCompared)
  {
    return this.getAccount().getId().equals(toBeCompared.getAccount().getId()) &&
           Bid4WinMapComparator.getInstanceMap().equals(this.getUsedCreditMap(),
                                                        toBeCompared.getUsedCreditMap());
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  {
    return super.hashCodeDefault();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    return new StringBuffer();
  }
}
