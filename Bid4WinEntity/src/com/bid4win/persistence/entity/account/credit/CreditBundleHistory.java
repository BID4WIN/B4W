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
 * Cette classe défini historique de lot de crédits pour un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
public class CreditBundleHistory extends CreditBundleAbstract<CreditBundleHistory>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CreditBundleHistory()
  {
    super();
  }
  /**
   * Constructeur complet utilisé pour les tests
   * @param account Compte utilisateur du lot de crédits
   * @param origin Provenance des crédits du lot
   * @param totalValue Valeur réelle totale des crédits du lot dans la monnaie par
   * défaut
   * @param nb Nombre de crédits du lot
   * @throws UserException Si le compte utilisateur des crédits ou leur provenance
   * est nul ou si leur valeur réelle unitaire est négative ou si leur nombre est
   * inférieur à un
   */
  protected CreditBundleHistory(Account account, CreditOrigin origin,
                                double totalValue, int nb)
             throws UserException
  {
    super(account, origin, totalValue, nb);
  }
  /**
   * Constructeur complet
   * @param bundle Lot de crédits dont il faut construire l'historique
   * @throws UserException Si le lot de crédits en argument est nul
   */
  public CreditBundleHistory(CreditBundle bundle) throws UserException
  {
    super(UtilObject.checkNotNull("bundle", bundle,
                                  AccountRef.CREDIT_REFERENCE_MISSING_ERROR).getAccount(),
          bundle.getOrigin(), bundle.getUnitValue(), bundle.getInitialNb());
  }

  /**
   * Cette méthode permet de savoir si l'historique courant est bien celui du lot
   * de crédits en argument
   * @param bundle Lot de crédits dont on veut vérifier l'historique
   * @return True si l'historique courant est bien celui du lot de crédits en argument
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
