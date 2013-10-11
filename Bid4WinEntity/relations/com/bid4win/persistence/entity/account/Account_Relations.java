package com.bid4win.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountAbstract_Relations;

/**
 * D�finition des relations de la classe Account vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Account_Relations extends AccountAbstract_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** D�fini la relation existant avec le parrain du compte utilisateur */
  public final static Bid4WinRelation RELATION_SPONSOR = new Bid4WinRelation("SPONSOR",
                                                                             Type.SIMPLE);
  /** D�fini la relation existant avec la liste de lots de cr�dits */
  public final static Bid4WinRelation RELATION_CREDIT_BUNDLE_LIST = new Bid4WinRelation("CREDIT_LIST",
                                                                                        Type.LIST);
  /** D�fini la relation existant avec la map des implications de cr�dits sur des ventes aux ench�res normales */
  public final static Bid4WinRelation RELATION_INVOLVEMENT_NORMAL_MAP = new Bid4WinRelation("INVOLVEMENT_NORMAL_MAP",
                                                                                            Type.MAP);
  /** D�fini le noeud de relation existant avec le parrain du compte utilisateur */
  public final static Bid4WinRelationNode NODE_SPONSOR = new Bid4WinRelationNode(
      RELATION_SPONSOR);
  /** D�fini le noeud de relation existant avec la liste de lots de cr�dits */
  public final static Bid4WinRelationNode NODE_CREDIT_BUNDLE_LIST = new Bid4WinRelationNode(
      RELATION_CREDIT_BUNDLE_LIST);
  /** D�fini le noeud de relation existant avec la map des implications de cr�dits sur des ventes aux ench�res normales */
  public final static Bid4WinRelationNode NODE_INVOLVEMENT_NORMAL_MAP = new Bid4WinRelationNode(
      RELATION_INVOLVEMENT_NORMAL_MAP);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
