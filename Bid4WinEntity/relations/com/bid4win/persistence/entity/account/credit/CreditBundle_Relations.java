package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe CreditBundle vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditBundle_Relations extends CreditBundleAbstract_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec le compte utilisateur du lot de cr�dits */
  public final static Bid4WinRelation RELATION_ACCOUNT_LINK = new Bid4WinRelation("ACCOUNT_LINK",
                                                                                  Type.SIMPLE);
  /** D�fini la relation existant avec l'historisation du lot de cr�dits */
  public final static Bid4WinRelation RELATION_HISTORY = new Bid4WinRelation("HISTORY",
                                                                             Type.SIMPLE);
  /** D�fini le noeud de relation existant avec le compte utilisateur du lot de cr�dits */
  public final static Bid4WinRelationNode NODE_ACCOUNT_LINK = new Bid4WinRelationNode(
      RELATION_ACCOUNT_LINK);
  /** D�fini le noeud de relation existant avec l'historisation du lot de cr�dits */
  public final static Bid4WinRelationNode NODE_HISTORY = new Bid4WinRelationNode(
      RELATION_HISTORY);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
