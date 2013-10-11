package com.bid4win.persistence.dao.account.credit;

import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory;

/**
 * DAO pour les entit�s de la classe CreditUsageHistory<BR>
 * <BR>
 * @param <USAGE> D�finition du type d'utilisation de cr�dits g�r�e par le DAO<BR>
 * @param <INVOLVEMENT> D�finition du type d'implication des utilisations de cr�dits
 * g�r�es par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditUsageHistoryDao_<USAGE extends CreditUsageHistory<USAGE, INVOLVEMENT>,
                                    INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, USAGE, CreditBundleHistory, ?>>
       extends CreditUsageAbstractDao_<USAGE, CreditBundleHistory, INVOLVEMENT>
{
  /**
   * Constructeur
   * @param usageClass Classe de l'utilisation de cr�dits g�r�e par le DAO
   */
  protected CreditUsageHistoryDao_(Class<USAGE> usageClass)
  {
    super(usageClass);
  }
}
