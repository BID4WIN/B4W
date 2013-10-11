package trash;
//package com.bid4win.persistence.dao.auction.history;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
//import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
//import com.bid4win.persistence.dao.auction.IAuctionAbstractDaoStub;
//import com.bid4win.persistence.entity.auction.AuctionAbstract;
//import com.bid4win.persistence.entity.auction.history.AuctionHistory;
//import com.bid4win.persistence.entity.auction.history.BidHistory;
//import com.bid4win.persistence.entity.auction.history.ScheduleHistory;
//import com.bid4win.persistence.entity.auction.history.TermsHistory;
//
///**
//*
//* TODO A COMMENTER<BR>
//* <BR>
//* @author Emeric Fillâtre
//*/
//@Component("AuctionHistoryDaoStub")
//@Scope("singleton")
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class AuctionHistoryDaoStub extends AuctionHistoryDao
//       implements IAuctionAbstractDaoStub<AuctionHistory, BidHistory, ScheduleHistory, TermsHistory>
//{
//  /** Référence du DAO des historiques d'enchères */
//  @Autowired
//  @Qualifier("BidHistoryDaoStub")
//  private BidHistoryDaoStub bidDao;
//
//  /////////////////////////////////////////////////////////////////////////////
//  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
//  ////////////  de la transaction et le chargement des relations   ////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   *
//   * TODO A COMMENTER
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getById(java.lang.String)
//   */
//  @Override
//  public AuctionHistory getById(String id) throws PersistenceException, NotFoundEntityException
//  {
//    return super.getById(id).loadRelation();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#add(com.bid4win.persistence.entity.auction.AuctionAbstract)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public AuctionHistory add(AuctionHistory auction) throws PersistenceException
//  {
//    return super.add(auction).loadRelation();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotPersistedEntityException {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#update(com.bid4win.persistence.entity.auction.AuctionAbstract)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public AuctionHistory update(AuctionHistory auction) throws PersistenceException, NotPersistedEntityException
//  {
//    return super.update(auction).loadRelation();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auctionId {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.IAuctionAbstractDaoStub#historize(java.lang.String)
//   */
//  public AuctionHistory historize(String auctionId) throws PersistenceException, NotFoundEntityException
//  {// TODO
//    throw new PersistenceException("Cannot historize auction history");
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.history.AuctionHistoryDao#create(com.bid4win.persistence.entity.auction.AuctionAbstract)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public AuctionHistory create(AuctionAbstract<?, ?, ?, ?> auction)
//         throws PersistenceException, UserException
//  {
//    return super.create(auction);
//  }
//  /////////////////////////////////////////////////////////////////////////////
//  ////////////////////////// Ajoutées pour les tests //////////////////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
//   */
//  @Override
//  public Bid4WinList<AuctionHistory> findAll() throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll());
//  }
//  /**
//   * TODO A COMMENTER
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
//   */
//  @Override
//  public AuctionHistory findById(String id) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
//  }
//  /**
//   * TODO A COMMENTER
//   * @param idSet {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(com.bid4win.commons.core.collection.Bid4WinSet)
//   */
//  @Override
//  public Bid4WinMap<String, AuctionHistory> getById(Bid4WinSet<String> idSet)
//         throws PersistenceException, NotFoundEntityException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
//  }
//  /**
//   * TODO A COMMENTER
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public AuctionHistory removeById(String id) throws PersistenceException
//  {
//    return super.removeById(id);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param idList {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeListById(com.bid4win.commons.core.collection.Bid4WinSet)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid4WinSet<AuctionHistory> removeListById(Bid4WinSet<String> idList) throws NotFoundEntityException, PersistenceException
//  {
//    return super.removeListById(idList);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public AuctionHistory remove(AuctionHistory auction) throws PersistenceException
//  {
//    this.getBidDao().removeListByAuction(auction);
//    return super.remove(auction);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid4WinList<AuctionHistory> removeAll() throws PersistenceException
//  {
//    return super.removeAll();
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.history.AuctionHistoryDao#getBidDao()
//   */
//  @Override
//  protected BidHistoryDaoStub getBidDao()
//  {
//    return this.bidDao;
//  }
//}
