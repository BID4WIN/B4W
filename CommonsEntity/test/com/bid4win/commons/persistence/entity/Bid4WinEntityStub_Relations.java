package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe Bid4WinEntityStub vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityStub_Relations extends Bid4WinEntity_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** TODO A COMMENTER */
  public final static Bid4WinRelation RELATION_PARENT = new Bid4WinRelation("PARENT",
                                                                            Type.SIMPLE);
  /** TODO A COMMENTER */
  public final static Bid4WinRelation RELATION_SON = new Bid4WinRelation("SON",
                                                                         Type.SIMPLE);
  /** TODO A COMMENTER */
  public final static Bid4WinRelation RELATION_BOSS = new Bid4WinRelation("BOSS",
                                                                          Type.SIMPLE);
  /** TODO A COMMENTER */
  public final static Bid4WinRelation RELATION_EMPLOYE = new Bid4WinRelation("EMPLOYE",
                                                                             Type.SET);

  /** TODO A COMMENTER */
  public final static Bid4WinRelationNode NODE_PARENT = new Bid4WinRelationNode(
      Bid4WinEntityStub_Relations.RELATION_PARENT);
  /** TODO A COMMENTER */
  public final static Bid4WinRelationNode NODE_SON = new Bid4WinRelationNode(
      Bid4WinEntityStub_Relations.RELATION_SON);
  /** TODO A COMMENTER */
  public final static Bid4WinRelationNode NODE_BOSS = new Bid4WinRelationNode(
      Bid4WinEntityStub_Relations.RELATION_BOSS);
  /** TODO A COMMENTER */
  public final static Bid4WinRelationNode NODE_EMPLOYE = new Bid4WinRelationNode(
      Bid4WinEntityStub_Relations.RELATION_EMPLOYE);

  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
