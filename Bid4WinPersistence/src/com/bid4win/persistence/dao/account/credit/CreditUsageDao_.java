package com.bid4win.persistence.dao.account.credit;

import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement;
import com.bid4win.persistence.entity.account.credit.CreditUsage;

/**
 * DAO pour les entités de la classe CreditUsage<BR>
 * <BR>
 * @param <USAGE> Définition du type d'utilisation de crédits gérée par le DAO<BR>
 * @param <INVOLVEMENT> Définition du type d'implication des utilisations de crédits
 * gérées par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditUsageDao_<USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
                             INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, USAGE, CreditBundle, ?>>
       extends CreditUsageAbstractDao_<USAGE, CreditBundle, INVOLVEMENT>
{
  /**
   * Constructeur
   * @param usageClass Classe de l'utilisation de crédits gérée par le DAO
   */
  protected CreditUsageDao_(Class<USAGE> usageClass)
  {
    super(usageClass);
  }
}
