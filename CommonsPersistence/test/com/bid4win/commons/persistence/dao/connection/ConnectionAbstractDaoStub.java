package com.bid4win.commons.persistence.dao.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractStub;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ConnectionDao")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class ConnectionAbstractDaoStub
       extends ConnectionAbstractDao_<ConnectionAbstractStub, ConnectionHistoryAbstractStub, AccountAbstractStub>
       implements IConnectionAbstractDaoStub<ConnectionAbstractStub, ConnectionHistoryAbstractStub, AccountAbstractStub>
{
  /**
   * Constructeur
   */
  protected ConnectionAbstractDaoStub()
  {
    super(ConnectionAbstractStub.class);
  }

  /////////////////////////////////////////////////////////////////////////////
  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDao_#getById(java.lang.String)
   */
  @Override
  public ConnectionAbstractStub getById(String id) throws PersistenceException
  {
    return super.getById(id).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_#findListByAccountId(String)
   */
  @Override
  public Bid4WinList<ConnectionAbstractStub> findListByAccountId(String accountId)
         throws PersistenceException
  {
    Bid4WinList<ConnectionAbstractStub> result = super.findListByAccountId(accountId);
    return Bid4WinEntityLoader.getInstance().loadRelation(result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_#findListByAccount(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  public Bid4WinList<ConnectionAbstractStub> findListByAccount(AccountAbstractStub account)
         throws PersistenceException
  {
    Bid4WinList<ConnectionAbstractStub> result = super.findListByAccount(account);
    return Bid4WinEntityLoader.getInstance().loadRelation(result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#findOneByAccountId(java.lang.String)
   */
  @Override
  public ConnectionAbstractStub findOneByAccountId(String accountId) throws PersistenceException
  {
    ConnectionAbstractStub result = super.findOneByAccountId(accountId);
    return Bid4WinEntityLoader.getInstance().loadRelation(result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#findOneByAccount(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  public ConnectionAbstractStub findOneByAccount(AccountAbstractStub account)
         throws PersistenceException
  {
    ConnectionAbstractStub result = super.findOneByAccount(account);
    return Bid4WinEntityLoader.getInstance().loadRelation(result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountBasedEntity {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ConnectionAbstractStub add(ConnectionAbstractStub accountBasedEntity)
         throws PersistenceException
  {
    ConnectionAbstractStub connection = super.add(accountBasedEntity);
    this.flush();
    return super.refresh(connection).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param connection {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDao_#update(com.bid4win.commons.persistence.entity.connection.ConnectionAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ConnectionAbstractStub update(ConnectionAbstractStub connection) throws PersistenceException
  {
    return super.update(connection).loadRelation();
  }
  /////////////////////////////////////////////////////////////////////////////
  ////////////////////////// Ajoutées pour les tests //////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
   * fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public ConnectionAbstractStub findById(String id) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
  }
  /**
   * Cette fonction permet de récupérer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  public Bid4WinMap<String, ConnectionAbstractStub> getById(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
  }
  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<ConnectionAbstractStub> findAll() throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll());
  }
  /**
   * Cette fonction permet de supprimer une propriété en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ConnectionAbstractStub removeById(String id) throws PersistenceException
  {
    return super.removeById(id).loadRelation();
  }
  /**
   * Cette fonction permet de supprimer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeListById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  public Bid4WinSet<ConnectionAbstractStub> removeListById(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return super.removeListById(idSet);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountBasedEntity {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ConnectionAbstractStub remove(ConnectionAbstractStub accountBasedEntity)
         throws PersistenceException
  {
    return super.remove(accountBasedEntity);
  }
  /**
   * Cette fonction permet de supprimer la liste de toutes les propriétés
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<ConnectionAbstractStub> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }


  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.connection.IConnectionAbstractDaoStub#getHistoryDaoStub()
   */
  @Override
  public ConnectionHistoryAbstractDaoStub getHistoryDaoStub()
  {
    return (ConnectionHistoryAbstractDaoStub)super.getHistoryDao();
  }
}
