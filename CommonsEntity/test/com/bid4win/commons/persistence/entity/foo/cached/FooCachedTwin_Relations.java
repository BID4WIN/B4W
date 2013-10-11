package com.bid4win.commons.persistence.entity.foo.cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;

/**
 * D�finition des relations de la classe FooCachedTwin vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooCachedTwin_Relations extends FooCached_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /** TODO A COMMENTER */
  public final static Bid4WinRelation RELATION_TWIN = new Bid4WinRelation("TWIN",
                                                                          Type.SIMPLE);
  /** TODO A COMMENTER */
  public final static Bid4WinRelationNode NODE_TWIN = new Bid4WinRelationNode(
      RELATION_TWIN);
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
