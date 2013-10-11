package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe IpAddress<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(IpAddress.class)
public abstract class IpAddress_ extends Bid4WinEmbeddable_
{
  /** Définition de la valeur de l'adresse IP */
  public static volatile SingularAttribute<IpAddress, String> value;
}
