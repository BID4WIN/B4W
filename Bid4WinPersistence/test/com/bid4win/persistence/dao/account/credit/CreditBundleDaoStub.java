package com.bid4win.persistence.dao.account.credit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.account.IAccountBasedEntityMultipleDaoStub;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditBundleDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class CreditBundleDaoStub extends CreditBundleDao
       implements IAccountBasedEntityMultipleDaoStub<CreditBundle, Long, Account>
{
  /////////////////////////////////////////////////////////////////////////////
  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER
   * @param accountId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_#findListByAccountId(String)
   */
  @Override
  public Bid4WinList<CreditBundle> findListByAccountId(String accountId)
         throws PersistenceException
  {
    Bid4WinList<CreditBundle> result = super.findListByAccountId(accountId);
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
  public Bid4WinList<CreditBundle> findListByAccount(Account account) throws PersistenceException
  {
    Bid4WinList<CreditBundle> result = super.findListByAccount(account);
    return Bid4WinEntityLoader.getInstance().loadRelation(result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#findOneByAccountId(String)
   */
  @Override
  public CreditBundle findOneByAccountId(String accountId) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByAccountId(accountId));
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
  public CreditBundle findOneByAccount(Account account) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByAccount(account));
  }
  /**
   *
   * TODO A COMMENTER
   * @param bundle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditBundleDao#add(com.bid4win.persistence.entity.account.credit.CreditBundle)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public CreditBundle add(CreditBundle bundle) throws PersistenceException
  {
    return super.add(bundle).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param bundle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditBundleDao#update(com.bid4win.persistence.entity.account.credit.CreditBundle)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public CreditBundle update(CreditBundle bundle) throws PersistenceException
  {
    return super.update(bundle).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param bundle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditBundleDao#historize(com.bid4win.persistence.entity.account.credit.CreditBundle)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public CreditBundle historize(CreditBundle bundle)
         throws PersistenceException, UserException
  {
//    return super.historize(this.getEntityManager().merge(bundle));
    return super.historize(bundle).loadRelation();
  }

  /////////////////////////////////////////////////////////////////////////////
  ////////////////////////// Ajoutées pour les tests //////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public CreditBundle getById(Long id) throws PersistenceException
  {
    return super.getById(id).loadRelation();
  }
  /**
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
   * fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public CreditBundle findById(Long id) throws PersistenceException
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
  public Bid4WinMap<Long, CreditBundle> getById(Bid4WinSet<Long> idSet)
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
  public Bid4WinList<CreditBundle> findAll() throws PersistenceException
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
  public CreditBundle removeById(Long id) throws PersistenceException
  {
    return super.removeById(id);
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
  public Bid4WinSet<CreditBundle> removeListById(Bid4WinSet<Long> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return super.removeListById(idSet);
  }
  /**
   *
   * TODO A COMMENTER
   * @param bundle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public CreditBundle remove(CreditBundle bundle) throws PersistenceException
  {
    return super.remove(bundle);
  }
  /**
   * Cette fonction permet de supprimer la liste de tous les lots de crédits
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<CreditBundle> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<CreditBundle> removeListByAccount(Account account) throws PersistenceException
  {
    Bid4WinSet<CreditBundle> result = new Bid4WinSet<CreditBundle>();
    for(CreditBundle bundle : this.findListByAccount(account))
    {
      result.add(this.remove(bundle));
    }
    return result;
  }
}
