package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * Définition des relations de la classe ResourceUsage vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ResourceUsage_Relations extends Resource_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec le stockage de l'utilisation de ressource */
  public final static Bid4WinRelation RELATION_STORAGE = new Bid4WinRelation("STORAGE",
                                                                             Type.SIMPLE);
  /** Défini le noeud de relation existant avec le stockage de l'utilisation de ressource */
  public final static Bid4WinRelationNode NODE_STORAGE = new Bid4WinRelationNode(
      ResourceUsage_Relations.RELATION_STORAGE);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
