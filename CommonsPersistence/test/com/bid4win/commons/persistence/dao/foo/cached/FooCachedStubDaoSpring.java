package com.bid4win.commons.persistence.dao.foo.cached;

import com.bid4win.commons.persistence.entity.foo.cached.FooCachedStub;

/**
 * DAO pour les entités de la classe FooCachedStub<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedStubDaoSpring extends FooCachedDaoSpring<FooCachedStub>
{
  /**
   * Constructeur
   */
  protected FooCachedStubDaoSpring()
  {
    super(FooCachedStub.class);
  }
}
