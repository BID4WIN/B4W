package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Metamodel de la classe TermsAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(TermsAbstract.class)
public class TermsAbstract_ extends Bid4WinEmbeddable_
{
  /** Définition du nombre de crédits à utiliser par l'utilisateur à chaque enchère */
  public static volatile SingularAttribute<TermsAbstract<?>, Integer> creditNbPerBid;
  /** Définition du montant d'un incrément d'enchère */
  public static volatile SingularAttribute<TermsAbstract<?>, Amount> bidIncrement;
}
