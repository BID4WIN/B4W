package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * D�finition des relations de la classe CreditUsageAbstract vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditUsageAbstract_Relations extends Bid4WinEntityAutoID_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec le lot de provenance des cr�dits utilis�s */
  public final static Bid4WinRelation RELATION_BUNDLE = new Bid4WinRelation("BUNDLE",
                                                                            Type.SIMPLE);
  /** D�fini la relation existant avec l'implication des cr�dits utilis�s */
  public final static Bid4WinRelation RELATION_INVOLVEMENT = new Bid4WinRelation("INVOLVEMENT",
                                                                                 Type.SIMPLE);
  /** D�fini le noeud de relation existant avec le lot de provenance des cr�dits utilis�s */
  public final static Bid4WinRelationNode NODE_BUNDLE = new Bid4WinRelationNode(
      RELATION_BUNDLE);
  /** D�fini le noeud de relation existant avec l'implication des cr�dits utilis�s */
  public final static Bid4WinRelationNode NODE_INVOLVEMENT = new Bid4WinRelationNode(
      RELATION_INVOLVEMENT);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
