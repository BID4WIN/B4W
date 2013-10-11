package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Définition des relations de la classe Auction vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Auction_Relations extends AuctionAbstract_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec la map des dernières enchères */
  public final static Bid4WinRelation RELATION_LAST_BID_MAP = new Bid4WinRelation("LAST_BID_MAP",
                                                                                  Type.MAP);
  /** Défini le noeud de relation existant avec la map des dernières enchères */
  public final static Bid4WinRelationNode NODE_LAST_BID_MAP = new Bid4WinRelationNode(
      RELATION_LAST_BID_MAP);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
