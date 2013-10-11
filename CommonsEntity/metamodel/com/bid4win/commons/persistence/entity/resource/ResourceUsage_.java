package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe ResourceUsage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ResourceUsage.class)
public class ResourceUsage_ extends Resource_
{
  /** Definition du stockage de l'utilisation de ressource */
  public static volatile SingularAttribute<ResourceUsage<?, ?, ?>, ResourceStorage<?, ?, ?>> storage;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
