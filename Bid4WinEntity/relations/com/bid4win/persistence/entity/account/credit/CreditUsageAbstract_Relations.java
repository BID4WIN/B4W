package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Définition des relations de la classe CreditUsageAbstract vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditUsageAbstract_Relations extends Bid4WinEntityAutoID_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec le lot de provenance des crédits utilisés */
  public final static Bid4WinRelation RELATION_BUNDLE = new Bid4WinRelation("BUNDLE",
                                                                            Type.SIMPLE);
  /** Défini la relation existant avec l'implication des crédits utilisés */
  public final static Bid4WinRelation RELATION_INVOLVEMENT = new Bid4WinRelation("INVOLVEMENT",
                                                                                 Type.SIMPLE);
  /** Défini le noeud de relation existant avec le lot de provenance des crédits utilisés */
  public final static Bid4WinRelationNode NODE_BUNDLE = new Bid4WinRelationNode(
      RELATION_BUNDLE);
  /** Défini le noeud de relation existant avec l'implication des crédits utilisés */
  public final static Bid4WinRelationNode NODE_INVOLVEMENT = new Bid4WinRelationNode(
      RELATION_INVOLVEMENT);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
