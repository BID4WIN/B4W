package com.bid4win.persistence.entity.account.credit.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement_Relations;

/**
 * Définition des relations de la classe CreditInvolvementAuctionAbstract vers les autres
 * entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditInvolvementAuctionAbstract_Relations extends CreditInvolvement_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec la vente aux enchères sur laquelle sont impliqués les crédits */
  public final static Bid4WinRelation RELATION_AUCTION = new Bid4WinRelation("AUCTION",
                                                                             Type.SIMPLE);
  /** Défini le noeud de relation existant avec la vente aux enchères sur laquelle sont impliqués les crédits */
  public final static Bid4WinRelationNode NODE_AUCTION = new Bid4WinRelationNode(
      RELATION_AUCTION);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
