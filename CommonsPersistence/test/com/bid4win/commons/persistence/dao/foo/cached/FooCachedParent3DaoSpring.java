package com.bid4win.commons.persistence.dao.foo.cached;

import com.bid4win.commons.persistence.entity.foo.cached.FooCachedChild3;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedParent3;

/**
 * DAO pour les entités de la classe FooCachedParent3<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedParent3DaoSpring extends FooCachedParentDaoSpring<FooCachedParent3, FooCachedChild3>
{
  /**
   * Constructeur
   */
  public FooCachedParent3DaoSpring()
  {
    super(FooCachedParent3.class);
  }
}
