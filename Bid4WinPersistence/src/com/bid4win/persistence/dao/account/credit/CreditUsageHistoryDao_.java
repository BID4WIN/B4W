package com.bid4win.persistence.dao.account.credit;

import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory;

/**
 * DAO pour les entités de la classe CreditUsageHistory<BR>
 * <BR>
 * @param <USAGE> Définition du type d'utilisation de crédits gérée par le DAO<BR>
 * @param <INVOLVEMENT> Définition du type d'implication des utilisations de crédits
 * gérées par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditUsageHistoryDao_<USAGE extends CreditUsageHistory<USAGE, INVOLVEMENT>,
                                    INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, USAGE, CreditBundleHistory, ?>>
       extends CreditUsageAbstractDao_<USAGE, CreditBundleHistory, INVOLVEMENT>
{
  /**
   * Constructeur
   * @param usageClass Classe de l'utilisation de crédits gérée par le DAO
   */
  protected CreditUsageHistoryDao_(Class<USAGE> usageClass)
  {
    super(usageClass);
  }
}
