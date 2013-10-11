package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Définition des relations de la classe Bid vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid_Relations extends BidAbstract_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec la vente aux enchères */
  public final static Bid4WinRelation RELATION_AUCTION_LINK = new Bid4WinRelation("AUCTION_LINK",
                                                                                  Type.SIMPLE);
  /** Défini le noeud de relation existant avec la vente aux enchères */
  public final static Bid4WinRelationNode NODE_AUCTION_LINK = new Bid4WinRelationNode(
      RELATION_AUCTION_LINK);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
