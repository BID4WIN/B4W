package com.bid4win.persistence.entity.account.credit.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement_Relations;

/**
 * D�finition des relations de la classe CreditInvolvementAuctionAbstract vers les autres
 * entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditInvolvementAuctionAbstract_Relations extends CreditInvolvement_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec la vente aux ench�res sur laquelle sont impliqu�s les cr�dits */
  public final static Bid4WinRelation RELATION_AUCTION = new Bid4WinRelation("AUCTION",
                                                                             Type.SIMPLE);
  /** D�fini le noeud de relation existant avec la vente aux ench�res sur laquelle sont impliqu�s les cr�dits */
  public final static Bid4WinRelationNode NODE_AUCTION = new Bid4WinRelationNode(
      RELATION_AUCTION);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
