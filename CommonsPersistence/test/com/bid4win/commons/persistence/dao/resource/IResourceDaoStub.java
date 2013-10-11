package com.bid4win.commons.persistence.dao.resource;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.IBid4WinDaoStub;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.resource.Resource;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IResourceDaoStub<RESOURCE extends Resource<RESOURCE, TYPE>,
                                  TYPE extends ResourceType<TYPE>>
       extends IBid4WinDaoStub<RESOURCE, Long>
{
  /**
   *
   * TODO A COMMENTER
   * @param fullPath TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public RESOURCE getOneByFullPath(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException;
  /**
   *
   * TODO A COMMENTER
   * @param fullPath TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public RESOURCE findOneByFullPath(String fullPath)
         throws PersistenceException, UserException;

}
