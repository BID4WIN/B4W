package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * Définition des relations de la classe Bid4WinEntityAutoID vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AccountBasedEntity_Relations extends Bid4WinEntity_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec le compte utilisateur */
  public final static Bid4WinRelation RELATION_ACCOUNT = new Bid4WinRelation("ACCOUNT",
                                                                             Type.SIMPLE);
  /** Défini le noeud de relation existant avec le compte utilisateur */
  public final static Bid4WinRelationNode NODE_ACCOUNT = new Bid4WinRelationNode(
      RELATION_ACCOUNT);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
