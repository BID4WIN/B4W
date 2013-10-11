package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * Définition des relations de la classe CreditBundle vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditBundle_Relations extends CreditBundleAbstract_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec le compte utilisateur du lot de crédits */
  public final static Bid4WinRelation RELATION_ACCOUNT_LINK = new Bid4WinRelation("ACCOUNT_LINK",
                                                                                  Type.SIMPLE);
  /** Défini la relation existant avec l'historisation du lot de crédits */
  public final static Bid4WinRelation RELATION_HISTORY = new Bid4WinRelation("HISTORY",
                                                                             Type.SIMPLE);
  /** Défini le noeud de relation existant avec le compte utilisateur du lot de crédits */
  public final static Bid4WinRelationNode NODE_ACCOUNT_LINK = new Bid4WinRelationNode(
      RELATION_ACCOUNT_LINK);
  /** Défini le noeud de relation existant avec l'historisation du lot de crédits */
  public final static Bid4WinRelationNode NODE_HISTORY = new Bid4WinRelationNode(
      RELATION_HISTORY);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
