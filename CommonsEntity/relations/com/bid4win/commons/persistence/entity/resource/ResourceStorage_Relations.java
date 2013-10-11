package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * Définition des relations de la classe ResourceStorage vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ResourceStorage_Relations extends Resource_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec les utilisations du stockage de ressources */
  public final static Bid4WinRelation RELATION_USAGE_SET = new Bid4WinRelation("USAGE_SET",
                                                                               Type.SET);
  /** Défini le noeud de relation existant avec les utilisations du stockage de ressources */
  public final static Bid4WinRelationNode NODE_USAGE_SET = new Bid4WinRelationNode(
      ResourceStorage_Relations.RELATION_USAGE_SET);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
