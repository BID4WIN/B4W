package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AuctionAbstractStub
       extends AuctionAbstract<AuctionAbstractStub, BidAbstractStub, ScheduleAbstractStub,
                               TermsAbstractStub, CancelPolicyAbstractStub>
{
  /** TODO A COMMENTER */
  private String id;
  /** TODO A COMMENTER */
  private BidAbstractStub winningBid = null;

  /**
   * Constructeur pour création par introspection
   */
  protected AuctionAbstractStub()
  {
    super();
  }
  /**
   * Constructeur complet de la vente aux enchères
   * @param product Produit vendu aux enchères
   * @param schedule Éléments de planification de base de la vente aux enchères
   * @param terms Conditions de base de la vente aux enchères
   * @throws UserException Si le produit, les éléments de planification ou les
   * conditions en argument sont nuls
   */
  public AuctionAbstractStub(Product product, ScheduleAbstractStub schedule, TermsAbstractStub terms)
         throws UserException
  {
    super(product, schedule, terms);
  }
  /**
   * Constructeur complet de la vente aux enchères avec précision de l'identifiant
   * @param id Identifiant de la vente aux enchères
   * @param product Produit vendu aux enchères
   * @param schedule Éléments de planification de base de la vente aux enchères
   * @param terms Conditions de base de la vente aux enchères
   * @throws UserException Si le produit, les éléments de planification ou les
   * conditions en argument sont nuls
   */
  public AuctionAbstractStub(String id, Product product, ScheduleAbstractStub schedule, TermsAbstractStub terms)
         throws UserException
  {
    super(product, schedule, terms);
    this.setId(id);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#getWinningBid()
   */
  @Override
  public BidAbstractStub getWinningBid() throws UserException
  {
    return UtilObject.checkNotNull("winningBid", this.winningBid,
                                   AuctionRef.AUCTION_BID_MISSING_ERROR);
  }
  /**
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @param bidDate {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#createBid(com.bid4win.persistence.entity.account.Account, com.bid4win.commons.core.Bid4WinDate)
   */
  @Override
  protected BidAbstractStub createBid(Account account, Bid4WinDate bidDate)
            throws ProtectionException, UserException
  {
    this.winningBid = new BidAbstractStub(account, this, this.getBidNb() + 1);
    return this.winningBid;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID#getId()
   */
  @Override
  public String getId()
  {
    return this.id;
  }
  /**
   *
   * TODO A COMMENTER
   * @param id TODO A COMMENTER
   */
  private void setId(String id)
  {
    this.id = id;
  }
}
