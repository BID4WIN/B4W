package com.bid4win.persistence.entity.product;

import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * D�finition des relations de la classe Product vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Product_Relations extends Bid4WinEntityGeneratedID_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec les utilisations d'image */
  public final static Bid4WinRelation RELATION_IMAGE_USAGE_LIST = new Bid4WinRelation("IMAGE_USAGE_LIST",
                                                                                      Type.LIST);
  /** D�fini le noeud de relation existant avec les utilisations d'image */
  public final static Bid4WinRelationNode NODE_IMAGE_USAGE_LIST = new Bid4WinRelationNode(
      RELATION_IMAGE_USAGE_LIST);
  /** D�fini la relation existant avec les utilisations de contenu internationalis� */
  public final static Bid4WinRelation RELATION_INNER_CONTENT_USAGE_LIST = new Bid4WinRelation("INNER_CONTENT_USAGE_LIST",
                                                                                          Type.LIST);
  /** D�fini le noeud de relation existant avec les utilisations de contenu internationalis� */
  public final static Bid4WinRelationNode NODE_INNER_CONTENT_USAGE_LIST = new Bid4WinRelationNode(
      RELATION_INNER_CONTENT_USAGE_LIST);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
