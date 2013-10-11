package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe PropertyAbstract vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class PropertyAbstract_Relations extends PropertyBase_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec la propri�t� parent */
  public final static Bid4WinRelation RELATION_ROOT = new Bid4WinRelation("ROOT",
                                                                          Type.SIMPLE);
  /** D�fini la relation existant avec la propri�t� racine */
  public final static Bid4WinRelation RELATION_PROPERTY = new Bid4WinRelation("PROPERTY",
                                                                              Type.SIMPLE);
  /** D�fini le noeud de relation existant avec la propri�t� racine */
  public final static Bid4WinRelationNode NODE_ROOT = new Bid4WinRelationNode(
      PropertyAbstract_Relations.RELATION_ROOT);
  /** D�fini le noeud de relation existant avec la propri�t� parent */
  public final static Bid4WinRelationNode NODE_PROPERTY = new Bid4WinRelationNode(
      PropertyAbstract_Relations.RELATION_PROPERTY);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
