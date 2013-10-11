package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityLinker
{
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param <ID> TODO A COMMENTER
   * @param entity TODO A COMMENTER
   * @param link TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public static <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         ENTITY unlink(ENTITY entity, Bid4WinRelation link)
         throws ProtectionException, UserException
  {
    entity.unlinkFrom(link);
    return entity;
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param <ID> TODO A COMMENTER
   * @param entity TODO A COMMENTER
   * @param link TODO A COMMENTER
   * @param backLink TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public static <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         ENTITY unlink(ENTITY entity, Bid4WinRelation link, Bid4WinRelation backLink)
         throws ProtectionException, UserException
  {
    entity.unlinkFrom(link, backLink);
    return entity;
  }
}
