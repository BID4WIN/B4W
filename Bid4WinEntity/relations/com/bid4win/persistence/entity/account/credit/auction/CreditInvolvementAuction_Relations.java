package com.bid4win.persistence.entity.account.credit.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * D�finition des relations de la classe CreditInvolvementAuction vers les autres
 * entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditInvolvementAuction_Relations extends CreditInvolvementAuctionAbstract_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec l'historisation de l'implication de cr�dits */
  public final static Bid4WinRelation RELATION_HISTORY = new Bid4WinRelation("HISTORY",
                                                                             Type.SIMPLE);
  /** D�fini le noeud de relation existant avec l'historisation de l'implication de cr�dits */
  public final static Bid4WinRelationNode NODE_HISTORY = new Bid4WinRelationNode(
      RELATION_HISTORY);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
