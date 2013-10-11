package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * Définition des relations de la classe PropertyAbstract vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PropertyAbstract_Relations extends PropertyBase_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec la propriété parent */
  public final static Bid4WinRelation RELATION_ROOT = new Bid4WinRelation("ROOT",
                                                                          Type.SIMPLE);
  /** Défini la relation existant avec la propriété racine */
  public final static Bid4WinRelation RELATION_PROPERTY = new Bid4WinRelation("PROPERTY",
                                                                              Type.SIMPLE);
  /** Défini le noeud de relation existant avec la propriété racine */
  public final static Bid4WinRelationNode NODE_ROOT = new Bid4WinRelationNode(
      PropertyAbstract_Relations.RELATION_ROOT);
  /** Défini le noeud de relation existant avec la propriété parent */
  public final static Bid4WinRelationNode NODE_PROPERTY = new Bid4WinRelationNode(
      PropertyAbstract_Relations.RELATION_PROPERTY);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
