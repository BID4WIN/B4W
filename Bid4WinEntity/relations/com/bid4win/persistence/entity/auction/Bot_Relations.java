package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_Relations;

/**
 * Définition des relations de la classe Bot vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bot_Relations extends AccountBasedEntityMultipleAutoID_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec la vente aux enchères */
  public final static Bid4WinRelation RELATION_AUCTION = new Bid4WinRelation("BOT_AUCTION",
                                                                             Type.SIMPLE);
  /** Défini le noeud de relation existant avec la vente aux enchères */
  public final static Bid4WinRelationNode NODE_AUCTION = new Bid4WinRelationNode(
      RELATION_AUCTION);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
