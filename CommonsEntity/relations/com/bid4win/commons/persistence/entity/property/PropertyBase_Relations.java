package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe PropertyBase vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class PropertyBase_Relations extends Bid4WinEntity_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec les sous-propri�t�s */
  public final static Bid4WinRelation RELATION_PROPERTY_MAP = new Bid4WinRelation("PROPERTY_MAP",
                                                                                  Type.MAP);
  /** D�fini le noeud de relation existant avec les sous-propri�t�s */
  public final static Bid4WinRelationNode NODE_PROPERTY_MAP = new Bid4WinRelationNode(
      RELATION_PROPERTY_MAP);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
