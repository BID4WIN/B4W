package com.bid4win.commons.persistence.dao;

import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDaoGeneratedID_<ENTITY extends Bid4WinEntityGeneratedID<ENTITY>>
       extends Bid4WinDao_<ENTITY, String>
{
  /**
   *
   * TODO A COMMENTER
   * @param entityClass TODO A COMMENTER
   */
  protected Bid4WinDaoGeneratedID_(Class<ENTITY> entityClass)
  {
    super(entityClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getIdField()
   */
  /*@Override
  protected Bid4WinFieldSimple<Bid4WinEntityGeneratedID<?>, String> getIdField()
  {
    return Bid4WinEntityGeneratedID_.ID;
  }*/
}
