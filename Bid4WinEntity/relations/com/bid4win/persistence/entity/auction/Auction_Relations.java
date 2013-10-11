package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * D�finition des relations de la classe Auction vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Auction_Relations extends AuctionAbstract_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec la map des derni�res ench�res */
  public final static Bid4WinRelation RELATION_LAST_BID_MAP = new Bid4WinRelation("LAST_BID_MAP",
                                                                                  Type.MAP);
  /** D�fini le noeud de relation existant avec la map des derni�res ench�res */
  public final static Bid4WinRelationNode NODE_LAST_BID_MAP = new Bid4WinRelationNode(
      RELATION_LAST_BID_MAP);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
