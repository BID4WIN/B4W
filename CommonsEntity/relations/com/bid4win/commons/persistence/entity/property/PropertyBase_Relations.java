package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * Définition des relations de la classe PropertyBase vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PropertyBase_Relations extends Bid4WinEntity_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec les sous-propriétés */
  public final static Bid4WinRelation RELATION_PROPERTY_MAP = new Bid4WinRelation("PROPERTY_MAP",
                                                                                  Type.MAP);
  /** Défini le noeud de relation existant avec les sous-propriétés */
  public final static Bid4WinRelationNode NODE_PROPERTY_MAP = new Bid4WinRelationNode(
      RELATION_PROPERTY_MAP);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
