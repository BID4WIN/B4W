package com.bid4win.persistence.dao.account.credit.auction;

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
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormalHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsageNormal;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
@Component("CreditInvolvementNormalDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class CreditInvolvementNormalDaoStub extends CreditInvolvementNormalDao
       implements ICreditInvolvementAuctionDaoStub<CreditInvolvementNormal, CreditUsageNormal,
                                                   NormalAuction, CreditInvolvementNormalHistory>
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
  public Bid4WinList<CreditInvolvementNormal> findListByAccountId(String accountId)
         throws PersistenceException
  {
    Bid4WinList<CreditInvolvementNormal> result = super.findListByAccountId(accountId);
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
  public Bid4WinList<CreditInvolvementNormal> findListByAccount(Account account) throws PersistenceException
  {
    Bid4WinList<CreditInvolvementNormal> result = super.findListByAccount(account);
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
  public CreditInvolvementNormal findOneByAccountId(String accountId) throws PersistenceException
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
  public CreditInvolvementNormal findOneByAccount(Account account) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByAccount(account));
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionDao_#findListByAuctionId(java.lang.String)
   */
  @Override
  public Bid4WinList<CreditInvolvementNormal> findListByAuctionId(String auctionId) throws PersistenceException
  {
    Bid4WinList<CreditInvolvementNormal> result = super.findListByAuctionId(auctionId);
    return Bid4WinEntityLoader.getInstance().loadRelation(result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionDao_#findListByAuction(com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  public Bid4WinList<CreditInvolvementNormal> findListByAuction(NormalAuction auction) throws PersistenceException
  {
    Bid4WinList<CreditInvolvementNormal> result = super.findListByAuction(auction);
    return Bid4WinEntityLoader.getInstance().loadRelation(result);
  }

  /**
   *
   * TODO A COMMENTER
   * @param bundle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#add(com.bid4win.persistence.entity.account.credit.CreditInvolvement)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public CreditInvolvementNormal add(CreditInvolvementNormal bundle) throws PersistenceException
  {
    return super.add(bundle).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param bundle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionDao_#update(com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public CreditInvolvementNormal update(CreditInvolvementNormal bundle) throws PersistenceException
  {
    return super.add(bundle).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionDao_#historize(com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<CreditInvolvementNormal> historize(NormalAuction auction)
         throws PersistenceException, UserException
  {
    Bid4WinList<CreditInvolvementNormal> result = super.historize(auction);
    return Bid4WinEntityLoader.getInstance().loadRelation(result);
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
  public CreditInvolvementNormal getById(Long id) throws PersistenceException
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
  public CreditInvolvementNormal findById(Long id) throws PersistenceException
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
  public Bid4WinMap<Long, CreditInvolvementNormal> getById(Bid4WinSet<Long> idSet)
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
  public Bid4WinList<CreditInvolvementNormal> findAll() throws PersistenceException
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
  public CreditInvolvementNormal removeById(Long id) throws PersistenceException
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
  public Bid4WinSet<CreditInvolvementNormal> removeListById(Bid4WinSet<Long> idSet)
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
  public CreditInvolvementNormal remove(CreditInvolvementNormal bundle) throws PersistenceException
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
  public Bid4WinList<CreditInvolvementNormal> removeAll() throws PersistenceException
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
  public Bid4WinSet<CreditInvolvementNormal> removeListByAccount(Account account) throws PersistenceException
  {
    Bid4WinSet<CreditInvolvementNormal> result = new Bid4WinSet<CreditInvolvementNormal>();
    for(CreditInvolvementNormal involvement : this.findListByAccount(account))
    {
      result.add(this.remove(involvement));
    }
    return result;
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<CreditInvolvementNormal> removeListByAuction(NormalAuction auction) throws PersistenceException
  {
    Bid4WinSet<CreditInvolvementNormal> result = new Bid4WinSet<CreditInvolvementNormal>();
    for(CreditInvolvementNormal involvement : this.findListByAuction(auction))
    {
      result.add(this.remove(involvement));
    }
    return result;
  }
}
