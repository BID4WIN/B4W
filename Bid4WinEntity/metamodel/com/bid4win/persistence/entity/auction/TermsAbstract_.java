package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Metamodel de la classe TermsAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(TermsAbstract.class)
public class TermsAbstract_ extends Bid4WinEmbeddable_
{
  /** D�finition du nombre de cr�dits � utiliser par l'utilisateur � chaque ench�re */
  public static volatile SingularAttribute<TermsAbstract<?>, Integer> creditNbPerBid;
  /** D�finition du montant d'un incr�ment d'ench�re */
  public static volatile SingularAttribute<TermsAbstract<?>, Amount> bidIncrement;
}
