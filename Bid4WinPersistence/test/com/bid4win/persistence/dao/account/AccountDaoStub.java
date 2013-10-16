package com.bid4win.persistence.dao.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.account.IAccountAbstractDaoStub;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLinker;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntity_Relations;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.persistence.dao.account.credit.CreditBundleDaoStub;
import com.bid4win.persistence.dao.account.credit.CreditBundleHistoryDaoStub;
import com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementNormalDaoStub;
import com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementNormalHistoryDaoStub;
import com.bid4win.persistence.dao.auction.normal.NormalAuctionDaoStub;
import com.bid4win.persistence.dao.auction.normal.NormalBidDaoStub;
import com.bid4win.persistence.dao.auction.normal.NormalBidHistoryDaoStub;
import com.bid4win.persistence.dao.auction.normal.NormalBotDaoStub;
import com.bid4win.persistence.dao.auction.prebooked.PrebookedAuctionDaoStub;
import com.bid4win.persistence.dao.auction.prebooked.PrebookedBidDaoStub;
import com.bid4win.persistence.dao.auction.prebooked.PrebookedBidHistoryDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;

/**
 * Stub du DAO pour les entités de la classe Account<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class AccountDaoStub extends AccountDao implements IAccountAbstractDaoStub<Account>
{
  /** Référence du DAO des lots de crédits */
  @Autowired
  @Qualifier("CreditBundleDaoStub")
  private CreditBundleDaoStub bundleDao;
  /** Référence du DAO des historiques de lots de crédits */
  @Autowired
  @Qualifier("CreditBundleHistoryDaoStub")
  private CreditBundleHistoryDaoStub bundleHistoryDao;
  /** Référence du DAO des ventes aux enchères normales */
  @Autowired
  @Qualifier("NormalAuctionDaoStub")
  private NormalAuctionDaoStub normalAuctionDao;
  /** Référence du DAO des enchères normales */
  @Autowired
  @Qualifier("NormalBidDaoStub")
  private NormalBidDaoStub normalBidDao;
  /** Référence du DAO des historiques d'enchères normales */
  @Autowired
  @Qualifier("NormalBidHistoryDaoStub")
  private NormalBidHistoryDaoStub normalBidHistoryDao;
  /** Référence du DAO des robots d'enchères normales */
  @Autowired
  @Qualifier("NormalBotDaoStub")
  private NormalBotDaoStub normalBotDao;
  /** Référence du DAO des implications sur des ventes aux d'enchères normales */
  @Autowired
  @Qualifier("CreditInvolvementNormalDaoStub")
  private CreditInvolvementNormalDaoStub normalInvolvementDao;
  /** Référence du DAO des implications sur des ventes aux d'enchères normales */
  @Autowired
  @Qualifier("CreditInvolvementNormalHistoryDaoStub")
  private CreditInvolvementNormalHistoryDaoStub normalInvolvementHistoryDao;
  /** Référence du DAO des ventes aux enchères avec pré-inscription */
  @Autowired
  @Qualifier("PrebookedAuctionDaoStub")
  private PrebookedAuctionDaoStub prebookedAuctionDao;
  /** Référence du DAO des enchères avec pré-inscription */
  @Autowired
  @Qualifier("PrebookedBidDaoStub")
  private PrebookedBidDaoStub prebookedBidDao;
  /** Référence du DAO des historiques d'enchères avec pré-inscription */
  @Autowired
  @Qualifier("PrebookedBidHistoryDaoStub")
  private PrebookedBidHistoryDaoStub prebookedBidHistoryDao;

  /////////////////////////////////////////////////////////////////////////////
  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getById(java.lang.String)
   */
  @Override
  public Account getById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(id).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param login {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getOneByLogin(com.bid4win.commons.persistence.entity.account.security.Login)
   */
  @Override
  public Account getOneByLogin(Login login) throws PersistenceException
  {
    return super.getOneByLogin(login).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param login {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#findOneByLogin(com.bid4win.commons.persistence.entity.account.security.Login)
   */
  @Override
  public Account findOneByLogin(Login login) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByLogin(login));
  }
  /**
   *
   * TODO A COMMENTER
   * @param email {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getOneByEmail(com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  public Account getOneByEmail(Email email) throws PersistenceException
  {
    return super.getOneByEmail(email).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param email {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#findOneByEmail(com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  public Account findOneByEmail(Email email) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByEmail(email));
  }
  /**
   *
   * TODO A COMMENTER
   * @param loginOrEmail {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.AccountDao#getOneByLoginOrEmail(java.lang.String)
   */
  @Override
  public Account getOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException, NotFoundEntityException//, UserException
  {
    return super.getOneByLoginOrEmail(loginOrEmail).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param loginOrEmail {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.AccountDao#findOneByLoginOrEmail(java.lang.String)
   */
  @Override
  public Account findOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException//, UserException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByLoginOrEmail(loginOrEmail));
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#findAll()
   */
  @Override
  public Bid4WinList<Account> findAll() throws PersistenceException
  {
    Bid4WinList<Account> list = super.findAll();
    return Bid4WinEntityLoader.getInstance().loadRelation(list);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#add(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account add(Account account) throws PersistenceException
  {
    return super.add(account);
  }
  /**
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#update(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account update(Account account) throws PersistenceException
  {
    return super.update(account);
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
  public Account findById(String id) throws PersistenceException
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
  public Bid4WinMap<String, Account> getById(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
  }
  /**
   * Cette fonction permet de supprimer un compte utilisateur en fonction de son
   * identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account removeById(String id) throws PersistenceException
  {
    return super.removeById(id);
  }
  /**
   * Il est nécessaire de dé-référencer le lot de droits à enchérir avant de le supprimer
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account remove(Account account) throws PersistenceException
  {
    try
    {
      this.getNormalBidDao().removeListByAccount(account);
      this.getNormalBidHistoryDao().removeListByAccount(account);
      this.getNormalBotDao().removeListByAccount(account);
      this.getNormalInvolvementDao().removeListByAccount(account);
      this.getNormalInvolvementHistoryDao().removeListByAccount(account);
      for(NormalAuction auction : this.getNormalAuctionDao().findListByAccount(account))
      {
//        if(auction.getAccount() != null)
//        {
          this.getNormalAuctionDao().update(Bid4WinEntityLinker.unlink(
              auction, AccountBasedEntity_Relations.RELATION_ACCOUNT));
//        }
      }
      this.getPrebookedBidDao().removeListByAccount(account);
      this.getPrebookedBidHistoryDao().removeListByAccount(account);
      for(PrebookedAuction auction : this.getPrebookedAuctionDao().findListByAccount(account))
      {
//        if(auction.getAccount() != null)
//        {
          this.getPrebookedAuctionDao().update(Bid4WinEntityLinker.unlink(
              auction, AccountBasedEntity_Relations.RELATION_ACCOUNT));
//        }
      }
      this.getBundleDao().removeListByAccount(account);
      this.getBundleHistoryDao().removeListByAccount(account);
    }
    catch(UserException ex)
    {
      throw new PersistenceException();
    }
    return super.remove(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @param idList TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<Account> removeListById(Bid4WinSet<String> idList) throws NotFoundEntityException, PersistenceException
  {
    return super.removeListById(idList);
  }
  /**
   * Cette fonction permet de supprimer un compte utilisateur en fonction de son
   * identifiant de connection
   * @param login Identifiant de connexion du compte utilisateur à supprimer
   * @return Le compte utilisateur supprimé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si le compte utilisateur n'a pas pu être trouvé
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account removeByLogin(Login login) throws PersistenceException, ModelArgumentException
  {
    Account account = UtilObject.checkNotNull("account", this.findOneByLogin(login));
    return this.remove(account);
  }
  /**
   * Cette fonction permet de supprimer la liste de tous les comptes utilisateur
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<Account> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected CreditBundleDaoStub getBundleDao()
  {
    return this.bundleDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected CreditBundleHistoryDaoStub getBundleHistoryDao()
  {
    return this.bundleHistoryDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public NormalAuctionDaoStub getNormalAuctionDao()
  {
    return this.normalAuctionDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public NormalBidDaoStub getNormalBidDao()
  {
    return this.normalBidDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public NormalBidHistoryDaoStub getNormalBidHistoryDao()
  {
    return this.normalBidHistoryDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public NormalBotDaoStub getNormalBotDao()
  {
    return this.normalBotDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public CreditInvolvementNormalDaoStub getNormalInvolvementDao()
  {
    return this.normalInvolvementDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public CreditInvolvementNormalHistoryDaoStub getNormalInvolvementHistoryDao()
  {
    return this.normalInvolvementHistoryDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public PrebookedAuctionDaoStub getPrebookedAuctionDao()
  {
    return this.prebookedAuctionDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public PrebookedBidDaoStub getPrebookedBidDao()
  {
    return this.prebookedBidDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public PrebookedBidHistoryDaoStub getPrebookedBidHistoryDao()
  {
    return this.prebookedBidHistoryDao;
  }
}
