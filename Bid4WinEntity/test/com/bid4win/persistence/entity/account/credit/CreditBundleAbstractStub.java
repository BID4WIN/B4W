package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.price.Amount;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditBundleAbstractStub extends CreditBundleAbstract<CreditBundleAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CreditBundleAbstractStub()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de crédits
   * @param origin Provenance des crédits du lot
   * @param unitValue Valeur réelle unitaire des crédits du lot
   * @param nb Nombre de crédits du lot
   * @throws UserException Si le compte utilisateur des crédits, leur provenance
   * ou leur valeur réelle unitaire est null ou si leur nombre est inférieur à un
   */
  public CreditBundleAbstractStub(Account account, CreditOrigin origin,
                                  double unitValue, int nb)
         throws UserException
  {
    super(account, origin, unitValue, nb);
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de crédits
   * @param origin Provenance des crédits du lot
   * @param unitValue Valeur réelle unitaire des crédits du lot
   * @param nb Nombre de crédits du lot
   * @throws UserException Si le compte utilisateur des crédits, leur provenance
   * ou leur valeur réelle unitaire est null ou si leur nombre est inférieur à un
   */
  public CreditBundleAbstractStub(Account account, CreditOrigin origin,
                                  Amount unitValue, int nb)
         throws UserException
  {
    super(account, origin, unitValue, nb);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public CreditBundleHistory toHistory() throws UserException
  {
    return new CreditBundleHistory(
        this.getAccount(), this.getOrigin(), this.getUnitValue().getValue(), this.getNb());
  }
}
