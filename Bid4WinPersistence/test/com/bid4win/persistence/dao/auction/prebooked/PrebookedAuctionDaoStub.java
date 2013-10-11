package com.bid4win.persistence.dao.auction.prebooked;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.persistence.dao.auction.IAuctionDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedCancelPolicy;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
import com.bid4win.persistence.entity.sale.Step;

/**
 * Stub du DAO pour les entités de la classe PrebookedAuction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PrebookedAuctionDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class PrebookedAuctionDaoStub extends PrebookedAuctionDao
       implements IAuctionDaoStub<PrebookedAuction, PrebookedBid, PrebookedSchedule,
                                  PrebookedTerms, PrebookedCancelPolicy>
{
  /////////////////////////////////////////////////////////////////////////////
  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getById(java.lang.String)
   */
  @Override
  public PrebookedAuction getById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(id).loadRelation();
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
  public PrebookedAuction findOneByAccountId(String accountId) throws PersistenceException
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
  public PrebookedAuction findOneByAccount(Account account) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByAccount(account));
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_#findListByAccountId(java.lang.String)
   */
  @Override
  public Bid4WinList<PrebookedAuction> findListByAccountId(String accountId) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findListByAccountId(accountId));
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
  public Bid4WinList<PrebookedAuction> findListByAccount(Account account) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findListByAccount(account));
  }
  /**
   *
   * TODO A COMMENTER
   * @param stepArray {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.sale.SaleDao_#findListByStep(com.bid4win.persistence.entity.sale.Step[])
   */
  @Override
  public Bid4WinList<PrebookedAuction> findListByStep(Step ... stepArray)
         throws PersistenceException
  {
    Bid4WinList<PrebookedAuction> list = super.findListByStep(stepArray);
    return Bid4WinEntityLoader.getInstance().loadRelation(list);
  }
  /**
   *
   * TODO A COMMENTER
   * @param statusArray {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#findListByStatus(com.bid4win.persistence.entity.auction.Status[])
   */
  @Override
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<PrebookedAuction> findListByStatus(Status ... statusArray)
         throws PersistenceException
  {
    Bid4WinList<PrebookedAuction> list = super.findListByStatus(statusArray);
    return Bid4WinEntityLoader.getInstance().loadRelation(list);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.sale.SaleDao_#add(com.bid4win.persistence.entity.sale.Sale)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PrebookedAuction add(PrebookedAuction auction) throws PersistenceException
  {
    return super.add(auction).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotPersistedEntityException {@inheritDoc}
   * @see com.bid4win.persistence.dao.sale.SaleDao_#update(com.bid4win.persistence.entity.sale.Sale)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PrebookedAuction update(PrebookedAuction auction) throws PersistenceException, NotPersistedEntityException
  {
    return super.update(auction).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#historizeBids(com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PrebookedAuction historizeBids(PrebookedAuction auction) throws PersistenceException, UserException
  {
    return super.historizeBids(auction);
  }

  /////////////////////////////////////////////////////////////////////////////
  ////////////////////////// Ajoutées pour les tests //////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<PrebookedAuction> findAll() throws PersistenceException
  {
    Bid4WinList<PrebookedAuction> list = super.findAll();
    return Bid4WinEntityLoader.getInstance().loadRelation(list);
  }
  /**
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public PrebookedAuction findById(String id) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
  }
  /**
   * TODO A COMMENTER
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  public Bid4WinMap<String, PrebookedAuction> getById(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
  }
  /**
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PrebookedAuction removeById(String id) throws PersistenceException
  {
    return super.removeById(id);
  }
  /**
   *
   * TODO A COMMENTER
   * @param idList {@inheritDoc}
   * @return {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeListById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<PrebookedAuction> removeListById(Bid4WinSet<String> idList) throws NotFoundEntityException, PersistenceException
  {
    return super.removeListById(idList);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PrebookedAuction remove(PrebookedAuction auction) throws PersistenceException
  {
    return super.remove(auction);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<PrebookedAuction> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }
}
