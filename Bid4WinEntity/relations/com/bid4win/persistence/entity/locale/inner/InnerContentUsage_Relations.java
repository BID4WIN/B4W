package com.bid4win.persistence.entity.locale.inner;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.locale.LocalizedUsage_Relations;

/**
 * Définition des relations de la classe InnerContentUsage vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class InnerContentUsage_Relations extends LocalizedUsage_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** Défini la relation existant avec le produit de l'utilisation de contenu */
  public final static Bid4WinRelation RELATION_PRODUCT = new Bid4WinRelation("INNER_PRODUCT",
                                                                             Type.SIMPLE);
  /** Défini le noeud de relation existant avec le produit de l'utilisation de contenu */
  public final static Bid4WinRelationNode NODE_PRODUCT = new Bid4WinRelationNode(
      RELATION_PRODUCT);
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
