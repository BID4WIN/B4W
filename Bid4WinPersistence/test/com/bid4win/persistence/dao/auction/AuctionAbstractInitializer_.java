package com.bid4win.persistence.dao.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.dao.Bid4winTestInitializer_;
import com.bid4win.persistence.dao.product.ProductInitializer;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.persistence.entity.price.ExchangeRates;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractInitializer_<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                                  BID extends BidAbstract<BID, AUCTION, ?>,
                                                  SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                  TERMS extends TermsAbstract<TERMS>,
                                                  CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends Bid4winTestInitializer_<AUCTION, String>
{
  /** Référence de TODO A COMMENTER */
  @Autowired
  @Qualifier("ProductInitializer")
  private ProductInitializer productInitializer;

  /**
   *
   * TODO A COMMENTER
   * @param auctionIndex TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public BID addBid(int auctionIndex, Account account) throws PersistenceException, UserException
  {
    return this.getBidDao().add(this.getEntity(auctionIndex).addBid(account, new Bid4WinDate()));
  }

  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AUCTION validateAuction(int index) throws Bid4WinException
  {
    return this.validateAuction(index, this.createCancelPolicy(),
                                this.getGenerator().createExchangeRates());
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @param cancelPolicy TODO A COMMENTER
   * @param exchangeRates TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AUCTION validateAuction(int index, CANCEL_POLICY cancelPolicy, ExchangeRates exchangeRates)
         throws Bid4WinException
  {
    return this.getDao().update(this.getEntity(index).validate(cancelPolicy, exchangeRates));
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract CANCEL_POLICY createCancelPolicy() throws Bid4WinException;
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AUCTION startAuction(int index) throws Bid4WinException
  {
    AUCTION auction = this.getEntity(index);
    if(auction.isWorking())
    {
      auction = this.validateAuction(index);
    }
    return this.getDao().update(auction.start());
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AUCTION closeAuction(int index) throws Bid4WinException
  {
    return this.getDao().update(this.getEntity(index).close());
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AUCTION cancelAuction(int index) throws Bid4WinException
  {
    return this.getDao().update(this.getEntity(index).cancel());
  }
  /**
   *
   * TODO A COMMENTER
   * @param schedule TODO A COMMENTER
   * @param terms TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public int addAuction(SCHEDULE schedule, TERMS terms) throws Bid4WinException
  {
    return this.addAuction(this.getProductInitializer().getEntity(0), schedule, terms);
  }
  /**
   *
   * TODO A COMMENTER
   * @param product TODO A COMMENTER
   * @param schedule TODO A COMMENTER
   * @param terms TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public int addAuction(Product product, SCHEDULE schedule, TERMS terms)
         throws Bid4WinException
  {
    return this.add(this.createAuction(product, schedule, terms));
  }

  /**
   *
   * TODO A COMMENTER
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#createEntity(int)
   */
  @Override
  protected AUCTION createEntity(int index) throws Bid4WinException
  {
    return this.createAuction(this.getProductInitializer().getEntity(0));
  }
  /**
   *
   * TODO A COMMENTER
   * @param product TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract AUCTION createAuction(Product product) throws Bid4WinException;
  /**
   *
   * TODO A COMMENTER
   * @param product TODO A COMMENTER
   * @param schedule TODO A COMMENTER
   * @param terms TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract AUCTION createAuction(Product product, SCHEDULE schedule, TERMS terms) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public ProductInitializer getProductInitializer()
  {
    return this.productInitializer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IBidAbstractDaoStub<BID, AUCTION, ?> getBidDao();

  /**
   *
   * TODO A COMMENTER
   * @param nb {@inheritDoc}
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#setUp(int)
   */
  @Override
  public void setUp(int nb) throws Exception
  {
    this.getProductInitializer().setUp(1);
    super.setUp(nb);
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#tearDown()
   */
  @Override
  public void tearDown() throws Exception
  {
    super.tearDown();
    this.getProductInitializer().tearDown();
  }
}
