package com.bid4win.persistence.entity.sale;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID_Relations;

/**
 * D�finition des relations de la classe Sale vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Sale_Relations extends AccountBasedEntityMultipleGeneratedID_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec le produit vendu */
  public final static Bid4WinRelation RELATION_PRODUCT = new Bid4WinRelation("PRODUCT",
                                                                             Type.SIMPLE);
  /** D�fini le noeud de relation existant avec le produit vendu */
  public final static Bid4WinRelationNode NODE_PRODUCT = new Bid4WinRelationNode(
      RELATION_PRODUCT);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
