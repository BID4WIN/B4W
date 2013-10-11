package com.bid4win.commons.persistence.dao.foo.cached;

import com.bid4win.commons.persistence.entity.foo.cached.FooCachedChild1;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedParent1;

/**
 * DAO pour les entit�s de la classe FooCachedParent1<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooCachedParent1DaoSpring extends FooCachedParentDaoSpring<FooCachedParent1, FooCachedChild1>
{
  /**
   * Constructeur
   */
  public FooCachedParent1DaoSpring()
  {
    super(FooCachedParent1.class);
  }
}
