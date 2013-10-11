package com.bid4win.persistence.entity.price;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap_;

/**
 * Metamodel de la classe AmountMap<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(AmountMap.class)
public class AmountMap_ extends Bid4WinEmbeddableWithTypeMap_
{
  /** Définition d TODO A COMMENTER */
  public static volatile SingularAttribute<AmountMap<?>, Bid4WinMap<Currency, Amount>> embeddedMap;
}
