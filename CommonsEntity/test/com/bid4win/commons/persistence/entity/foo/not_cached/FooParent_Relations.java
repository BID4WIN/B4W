package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * Définition des relations de la classe FooParent vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooParent_Relations extends Foo_Relations
{
  // Démarre la protection des relations
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
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
