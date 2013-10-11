package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe CancelPolicyAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CancelPolicyAbstract.class)
public class CancelPolicyAbstract_ extends Bid4WinEmbeddable_
{
  /** Définition du seuil du nombre de crédits utilisés sur la vente aux enchères en dessous duquel elle sera annulée */
  public static volatile SingularAttribute<TermsAbstract<?>, Integer> creditNbThreshold;
  /** Définition du nombre de crédits bonus par crédit payé utilisé sur une vente aux enchères annulée */
  public static volatile SingularAttribute<TermsAbstract<?>, Integer> creditNbPaidBonus;
}
