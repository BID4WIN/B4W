package com.bid4win.persistence.entity.account.credit.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Définition des relations de la classe CreditInvolvementAuction vers les autres
 * entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditInvolvementAuction_Relations extends CreditInvolvementAuctionAbstract_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec l'historisation de l'implication de crédits */
  public final static Bid4WinRelation RELATION_HISTORY = new Bid4WinRelation("HISTORY",
                                                                             Type.SIMPLE);
  /** Défini le noeud de relation existant avec l'historisation de l'implication de crédits */
  public final static Bid4WinRelationNode NODE_HISTORY = new Bid4WinRelationNode(
      RELATION_HISTORY);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
