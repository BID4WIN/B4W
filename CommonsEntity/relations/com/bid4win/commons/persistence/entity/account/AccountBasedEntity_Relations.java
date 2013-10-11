package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe Bid4WinEntityAutoID vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AccountBasedEntity_Relations extends Bid4WinEntity_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec le compte utilisateur */
  public final static Bid4WinRelation RELATION_ACCOUNT = new Bid4WinRelation("ACCOUNT",
                                                                             Type.SIMPLE);
  /** D�fini le noeud de relation existant avec le compte utilisateur */
  public final static Bid4WinRelationNode NODE_ACCOUNT = new Bid4WinRelationNode(
      RELATION_ACCOUNT);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
