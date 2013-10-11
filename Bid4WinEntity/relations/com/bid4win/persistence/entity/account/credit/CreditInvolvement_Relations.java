package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_Relations;

/**
 * D�finition des relations de la classe CreditInvolvement vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditInvolvement_Relations extends AccountBasedEntityMultipleAutoID_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec les utilisations de cr�dits */
  public final static Bid4WinRelation RELATION_USAGE_MAP = new Bid4WinRelation("USAGE_MAP",
                                                                               Type.MAP);
  /** D�fini le noeud de relation existant avec les utilisations de cr�dits */
  public final static Bid4WinRelationNode NODE_USAGE_MAP = new Bid4WinRelationNode(
      RELATION_USAGE_MAP);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
