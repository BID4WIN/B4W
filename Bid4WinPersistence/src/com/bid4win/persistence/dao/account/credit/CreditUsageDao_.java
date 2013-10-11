package com.bid4win.persistence.dao.account.credit;

import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement;
import com.bid4win.persistence.entity.account.credit.CreditUsage;

/**
 * DAO pour les entit�s de la classe CreditUsage<BR>
 * <BR>
 * @param <USAGE> D�finition du type d'utilisation de cr�dits g�r�e par le DAO<BR>
 * @param <INVOLVEMENT> D�finition du type d'implication des utilisations de cr�dits
 * g�r�es par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditUsageDao_<USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
                             INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, USAGE, CreditBundle, ?>>
       extends CreditUsageAbstractDao_<USAGE, CreditBundle, INVOLVEMENT>
{
  /**
   * Constructeur
   * @param usageClass Classe de l'utilisation de cr�dits g�r�e par le DAO
   */
  protected CreditUsageDao_(Class<USAGE> usageClass)
  {
    super(usageClass);
  }
}
