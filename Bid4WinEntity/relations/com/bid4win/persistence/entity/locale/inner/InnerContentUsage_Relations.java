package com.bid4win.persistence.entity.locale.inner;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.locale.LocalizedUsage_Relations;

/**
 * D�finition des relations de la classe InnerContentUsage vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class InnerContentUsage_Relations extends LocalizedUsage_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec le produit de l'utilisation de contenu */
  public final static Bid4WinRelation RELATION_PRODUCT = new Bid4WinRelation("INNER_PRODUCT",
                                                                             Type.SIMPLE);
  /** D�fini le noeud de relation existant avec le produit de l'utilisation de contenu */
  public final static Bid4WinRelationNode NODE_PRODUCT = new Bid4WinRelationNode(
      RELATION_PRODUCT);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
