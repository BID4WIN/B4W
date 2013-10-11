package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe ResourceStorage vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ResourceStorage_Relations extends Resource_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec les utilisations du stockage de ressources */
  public final static Bid4WinRelation RELATION_USAGE_SET = new Bid4WinRelation("USAGE_SET",
                                                                               Type.SET);
  /** D�fini le noeud de relation existant avec les utilisations du stockage de ressources */
  public final static Bid4WinRelationNode NODE_USAGE_SET = new Bid4WinRelationNode(
      ResourceStorage_Relations.RELATION_USAGE_SET);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
