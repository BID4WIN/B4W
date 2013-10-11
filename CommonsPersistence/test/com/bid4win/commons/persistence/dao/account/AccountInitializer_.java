package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.Bid4winTestInitializer_;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Role;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountInitializer_<ACCOUNT extends AccountAbstract<ACCOUNT>,
                                          GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4winTestInitializer_<ACCOUNT, String, ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#createEntity(int)
   */
  @Override
  protected ACCOUNT createEntity(int index) throws Bid4WinException
  {
    return this.getGenerator().createAccount(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @param role TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public void updateRole(int index, Role role) throws Bid4WinException
  {
    ACCOUNT account = this.getEntity(index);
    account.getCredential().defineRole(role);
    this.getDao().update(account);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#getDao()
   */
  @Override
  protected abstract IAccountAbstractDaoStub<ACCOUNT> getDao();
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected int add(ACCOUNT account) throws PersistenceException
  {
    // Supprime toute compte utilisateur potentiellement en conflit avec celui à ajouter
    ACCOUNT old = this.getDao().findOneByLogin(account.getCredential().getLogin());
    if(old != null)
    {
      this.remove(old);
    }
    try
    {
      old = this.getDao().findOneByLoginOrEmail(account.getEmail().getAddress());
    }
    catch(UserException ex)
    {
      throw new PersistenceException(ex);
    }
    if(old != null)
    {
      this.remove(old);
    }
    return super.add(account);
  }
}
