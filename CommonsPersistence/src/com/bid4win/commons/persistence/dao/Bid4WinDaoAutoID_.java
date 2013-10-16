package com.bid4win.commons.persistence.dao;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDaoAutoID_<ENTITY extends Bid4WinEntityAutoID<ENTITY>>
       extends Bid4WinDao_<ENTITY, Long>
{
  /**
   *
   * TODO A COMMENTER
   * @param entityClass TODO A COMMENTER
   */
  protected Bid4WinDaoAutoID_(Class<ENTITY> entityClass)
  {
    super(entityClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getIdField()
   */
 /* @Override
  protected Bid4WinFieldSimple<Bid4WinEntityAutoID<?>, Long> getIdField()
  {
    return Bid4WinEntityAutoID_.ID;
  }*/
}
