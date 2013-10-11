package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe ResourceUsage vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ResourceUsage_Relations extends Resource_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec le stockage de l'utilisation de ressource */
  public final static Bid4WinRelation RELATION_STORAGE = new Bid4WinRelation("STORAGE",
                                                                             Type.SIMPLE);
  /** D�fini le noeud de relation existant avec le stockage de l'utilisation de ressource */
  public final static Bid4WinRelationNode NODE_STORAGE = new Bid4WinRelationNode(
      ResourceUsage_Relations.RELATION_STORAGE);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
