package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * D�finition des relations de la classe Bid vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid_Relations extends BidAbstract_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec la vente aux ench�res */
  public final static Bid4WinRelation RELATION_AUCTION_LINK = new Bid4WinRelation("AUCTION_LINK",
                                                                                  Type.SIMPLE);
  /** D�fini le noeud de relation existant avec la vente aux ench�res */
  public final static Bid4WinRelationNode NODE_AUCTION_LINK = new Bid4WinRelationNode(
      RELATION_AUCTION_LINK);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
