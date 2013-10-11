package trash;
//package com.bid4win.persistence.dao.auction.history;
//
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
//import com.bid4win.persistence.dao.auction.IBidAbstractDaoStub;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.history.AuctionHistory;
//import com.bid4win.persistence.entity.auction.history.BidHistory;
//import com.bid4win.persistence.entity.auction.history.ScheduleHistory;
//import com.bid4win.persistence.entity.auction.history.TermsHistory;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("BidHistoryDaoStub")
//@Scope("singleton")
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class BidHistoryDaoStub extends BidHistoryDao
//       implements IBidAbstractDaoStub<BidHistory, AuctionHistory, ScheduleHistory, TermsHistory>
//{
//  /////////////////////////////////////////////////////////////////////////////
//  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
//  ////////////  de la transaction et le chargement des relations   ////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   *
//   * TODO A COMMENTER
//   * @param accountId {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#findOneByAccountId(java.lang.String)
//   */
//  @Override
//  public BidHistory findOneByAccountId(String accountId) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByAccountId(accountId));
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param account {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#findOneByAccount(com.bid4win.commons.persistence.entity.account.AccountAbstract)
//   */
//  @Override
//  public BidHistory findOneByAccount(Account account) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByAccount(account));
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param accountId {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_#findListByAccountId(java.lang.String)
//   */
//  @Override
//  public Bid4WinList<BidHistory> findListByAccountId(String accountId) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findListByAccountId(accountId));
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param account {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_#findListByAccount(com.bid4win.commons.persistence.entity.account.AccountAbstract)
//   */
//  @Override
//  public Bid4WinList<BidHistory> findListByAccount(Account account) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findListByAccount(account));
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auctionId {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#findListByAuctionId(java.lang.String)
//   */
//  @Override
//  public Bid4WinList<BidHistory> findListByAuctionId(String auctionId) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findListByAuctionId(auctionId));
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#findListByAuction(com.bid4win.persistence.entity.auction.AuctionAbstract)
//   */
//  @Override
//  public Bid4WinList<BidHistory> findListByAuction(AuctionHistory auction) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findListByAuction(auction));
//  }
//  /**
//   * TODO A COMMENTER
//   * @param bid {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#add(com.bid4win.persistence.entity.auction.BidAbstract)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public BidHistory add(BidHistory bid) throws PersistenceException
//  {
//    return super.add(bid);
//  }
//  /////////////////////////////////////////////////////////////////////////////
//  ////////////////////////// Ajoutées pour les tests //////////////////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   *
//   * TODO A COMMENTER
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
//   */
//  @Override
//  public BidHistory getById(Long id) throws PersistenceException, NotFoundEntityException
//  {
//    return super.getById(id).loadRelation();
//  }
//  /**
//   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
//   * fonction de son identifiant
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
//   */
//  @Override
//  public BidHistory findById(Long id) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
//  }
//  /**
//   * Cette fonction permet de récupérer le set d'entités dont les identifiants
//   * sont précisés en argument
//   * @param idSet {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(com.bid4win.commons.core.collection.Bid4WinSet)
//   */
//  @Override
//  public Bid4WinMap<Long, BidHistory> getById(Bid4WinSet<Long> idSet)
//         throws PersistenceException, NotFoundEntityException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
//  }
//  /**
//   * Cette fonction permet de récupérer la liste complète des entités
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
//   */
//  @Override
//  public Bid4WinList<BidHistory> findAll() throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll());
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param bid {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public BidHistory update(BidHistory bid) throws PersistenceException
//  {
//    return super.update(bid);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
//   */
//  @Override
//  @Transactional(readOnly = false)
//  public BidHistory removeById(Long id) throws PersistenceException
//  {
//    return super.removeById(id);
//  }
//  /**
//   * Cette fonction permet de supprimer le set d'entités dont les identifiants
//   * sont précisés en argument
//   * @param idSet {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeListById(com.bid4win.commons.core.collection.Bid4WinSet)
//   */
//  @Override
//  public Bid4WinSet<BidHistory> removeListById(Bid4WinSet<Long> idSet)
//         throws PersistenceException, NotFoundEntityException
//  {
//    return super.removeListById(idSet);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param account TODO A COMMENTER
//   * @return TODO A COMMENTER
//   * @throws PersistenceException TODO A COMMENTER
//   * @throws NotFoundEntityException TODO A COMMENTER
//   */
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid4WinSet<BidHistory> removeListByAccount(Account account)
//         throws PersistenceException, NotFoundEntityException
//  {
//    Bid4WinSet<BidHistory> result = new Bid4WinSet<BidHistory>(this.findListByAccount(account));
//    for(BidHistory history : result)
//    {
//      result.add(this.remove(history));
//    }
//    return result;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction TODO A COMMENTER
//   * @return TODO A COMMENTER
//   * @throws PersistenceException TODO A COMMENTER
//   * @throws NotFoundEntityException TODO A COMMENTER
//   */
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid4WinSet<BidHistory> removeListByAuction(AuctionHistory auction)
//         throws PersistenceException, NotFoundEntityException
//  {
//    Bid4WinSet<BidHistory> result = new Bid4WinSet<BidHistory>(this.findListByAuction(auction));
//    for(BidHistory history : result)
//    {
//      result.add(this.remove(history));
//    }
//    return result;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param bid {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public BidHistory remove(BidHistory bid) throws PersistenceException
//  {
//    return super.remove(bid);
//  }
//  /**
//   * Cette fonction permet de supprimer la liste de tout l'historique des connexions
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
//   */
//  @Override
//  @Transactional(readOnly = false)
//  public Bid4WinList<BidHistory> removeAll() throws PersistenceException
//  {
//    return super.removeAll();
//  }
//}
