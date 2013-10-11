package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.IBid4WinDaoStub;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IAccountAbstractDaoStub<ENTITY extends AccountAbstract<ENTITY>>
       extends IBid4WinDaoStub<ENTITY, String>
{
  /**
   *
   * TODO A COMMENTER
   * @param login TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   */
  public ENTITY getOneByLogin(Login login) throws PersistenceException, NotFoundEntityException;
  /**
   *
   * TODO A COMMENTER
   * @param login TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public ENTITY findOneByLogin(Login login) throws PersistenceException;

  /**
   *
   * TODO A COMMENTER
   * @param email TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   */
  public ENTITY getOneByEmail(Email email) throws PersistenceException, NotFoundEntityException;
  /**
   *
   * TODO A COMMENTER
   * @param email TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public ENTITY findOneByEmail(Email email) throws PersistenceException;

  /**
   *
   * TODO A COMMENTER
   * @param loginOrEmail TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public ENTITY getOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException, NotFoundEntityException, UserException;

  /**
   *
   * TODO A COMMENTER
   * @param loginOrEmail TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public ENTITY findOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException, UserException;
}
