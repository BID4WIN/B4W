package com.bid4win.commons.persistence.dao.foo.cached;

import com.bid4win.commons.persistence.entity.foo.cached.FooCachedChild2;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedParent2;

/**
 * DAO pour les entités de la classe FooCachedParent2<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedParent2DaoSpring extends FooCachedParentDaoSpring<FooCachedParent2, FooCachedChild2>
{
  /**
   * Constructeur
   */
  public FooCachedParent2DaoSpring()
  {
    super(FooCachedParent2.class);
  }
}
