package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe FooParent vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooParent_Relations extends Foo_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** TODO A COMMENTER */
  public final static Bid4WinRelation RELATION_CHILD = new Bid4WinRelation("CHILD",
                                                                           Type.MAP);
  /** TODO A COMMENTER */
  public final static Bid4WinRelationNode NODE_CHILD = new Bid4WinRelationNode(
      RELATION_CHILD);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
