package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe ResourceStorage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ResourceStorage.class)
public class ResourceStorage_ extends Resource_
{
  /** Definition du set d'utilisations du stockage de ressources */
  public static volatile SetAttribute<ResourceStorage<?, ?, ?>, ResourceUsage<?, ?, ?>> usageSetInternal;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** Défini la profondeur du noeud de relation existant avec les utilisations du stockage de ressources */
    ResourceStorage_Relations.NODE_USAGE_SET.addNode(ResourceUsage_Relations.NODE_STORAGE);
    Bid4WinEntity_.stopProtection();
  }
}
