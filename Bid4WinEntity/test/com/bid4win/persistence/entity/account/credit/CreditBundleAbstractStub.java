package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.price.Amount;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditBundleAbstractStub extends CreditBundleAbstract<CreditBundleAbstractStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditBundleAbstractStub()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de cr�dits
   * @param origin Provenance des cr�dits du lot
   * @param unitValue Valeur r�elle unitaire des cr�dits du lot
   * @param nb Nombre de cr�dits du lot
   * @throws UserException Si le compte utilisateur des cr�dits, leur provenance
   * ou leur valeur r�elle unitaire est null ou si leur nombre est inf�rieur � un
   */
  public CreditBundleAbstractStub(Account account, CreditOrigin origin,
                                  double unitValue, int nb)
         throws UserException
  {
    super(account, origin, unitValue, nb);
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de cr�dits
   * @param origin Provenance des cr�dits du lot
   * @param unitValue Valeur r�elle unitaire des cr�dits du lot
   * @param nb Nombre de cr�dits du lot
   * @throws UserException Si le compte utilisateur des cr�dits, leur provenance
   * ou leur valeur r�elle unitaire est null ou si leur nombre est inf�rieur � un
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
