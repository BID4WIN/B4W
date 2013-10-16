package com.bid4win.persistence.entity.account.credit;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.persistence.entity.account.Account;

/**
 * Cette classe d�fini historique de lot de cr�dits pour un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
public class CreditBundleHistory extends CreditBundleAbstract<CreditBundleHistory>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditBundleHistory()
  {
    super();
  }
  /**
   * Constructeur complet utilis� pour les tests
   * @param account Compte utilisateur du lot de cr�dits
   * @param origin Provenance des cr�dits du lot
   * @param totalValue Valeur r�elle totale des cr�dits du lot dans la monnaie par
   * d�faut
   * @param nb Nombre de cr�dits du lot
   * @throws UserException Si le compte utilisateur des cr�dits ou leur provenance
   * est nul ou si leur valeur r�elle unitaire est n�gative ou si leur nombre est
   * inf�rieur � un
   */
  protected CreditBundleHistory(Account account, CreditOrigin origin,
                                double totalValue, int nb)
             throws UserException
  {
    super(account, origin, totalValue, nb);
  }
  /**
   * Constructeur complet
   * @param bundle Lot de cr�dits dont il faut construire l'historique
   * @throws UserException Si le lot de cr�dits en argument est nul
   */
  public CreditBundleHistory(CreditBundle bundle) throws UserException
  {
    super(UtilObject.checkNotNull("bundle", bundle,
                                  AccountRef.CREDIT_REFERENCE_MISSING_ERROR).getAccount(),
          bundle.getOrigin(), bundle.getUnitValue(), bundle.getInitialNb());
  }

  /**
   * Cette m�thode permet de savoir si l'historique courant est bien celui du lot
   * de cr�dits en argument
   * @param bundle Lot de cr�dits dont on veut v�rifier l'historique
   * @return True si l'historique courant est bien celui du lot de cr�dits en argument
   */
  public boolean isHistoryOf(CreditBundle bundle)
  {
    return Bid4WinComparator.getInstance().equals(this.getAccount().getId(),
                                                  bundle.getAccount().getId()) &&
           Bid4WinComparator.getInstance().equals(this.getOrigin(), bundle.getOrigin()) &&
           Bid4WinComparator.getInstance().equals(this.getUnitValue(), bundle.getUnitValue()) &&
           this.getNb() == bundle.getInitialNb();
  }
}
