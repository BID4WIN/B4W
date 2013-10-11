package com.bid4win.persistence.entity.price;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType_;

/**
 * Metamodel de la classe Amount<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Amount.class)
public class Amount_ extends Bid4WinEmbeddableWithType_
{
  /** Définition de la monnaie associée au montant */
  public static volatile SingularAttribute<Amount, Currency> type;
  /** Définition de la valeur du montant */
  public static volatile SingularAttribute<Amount, Float> value;
}
