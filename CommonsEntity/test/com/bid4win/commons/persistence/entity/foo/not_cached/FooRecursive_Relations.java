package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe FooRecursive vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooRecursive_Relations extends Foo_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** TODO A COMMENTER */
  public final static Bid4WinRelation RELATION_CHILD = new Bid4WinRelation("CHILD",
                                                                           Type.SET);
  /** TODO A COMMENTER */
  public final static Bid4WinRelationNode NODE_CHILD = new Bid4WinRelationNode(
      RELATION_CHILD);
  /** TODO A COMMENTER */
  public final static Bid4WinRelation RELATION_PARENT = new Bid4WinRelation("PARENT",
                                                                            Type.SIMPLE);
  /** TODO A COMMENTER */
  public final static Bid4WinRelationNode NODE_PARENT = new Bid4WinRelationNode(
      RELATION_PARENT);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
