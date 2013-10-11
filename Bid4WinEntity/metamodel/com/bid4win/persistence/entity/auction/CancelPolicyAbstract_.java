package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe CancelPolicyAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(CancelPolicyAbstract.class)
public class CancelPolicyAbstract_ extends Bid4WinEmbeddable_
{
  /** D�finition du seuil du nombre de cr�dits utilis�s sur la vente aux ench�res en dessous duquel elle sera annul�e */
  public static volatile SingularAttribute<TermsAbstract<?>, Integer> creditNbThreshold;
  /** D�finition du nombre de cr�dits bonus par cr�dit pay� utilis� sur une vente aux ench�res annul�e */
  public static volatile SingularAttribute<TermsAbstract<?>, Integer> creditNbPaidBonus;
}
