package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AuctionStub extends Auction<AuctionStub, BidStub, ScheduleStub, TermsStub, CancelPolicyStub>
{
  /** TODO A COMMENTER */
  private String id;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AuctionStub()
  {
    super();
  }
  /**
   * Constructeur complet de la vente aux ench�res
   * @param product Produit vendu aux ench�res
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @throws UserException Si le produit, les �l�ments de planification ou les
   * conditions en argument sont nuls
   */
  public AuctionStub(Product product, ScheduleStub schedule, TermsStub terms)
         throws UserException
  {
    super(product, schedule, terms);
  }
  /**
   * Constructeur complet de la vente aux ench�res avec pr�cision de l'identifiant
   * @param id Identifiant de la vente aux ench�res
   * @param product Produit vendu aux ench�res
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @throws UserException Si le produit, les �l�ments de planification ou les
   * conditions en argument sont nuls
   */
  public AuctionStub(String id, Product product, ScheduleStub schedule, TermsStub terms)
         throws UserException
  {
    super(product, schedule, terms);
    this.setId(id);
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
  protected BidStub createBid(Account account, Bid4WinDate bidDate)
            throws ProtectionException, UserException
  {
    return new BidStub(account, this, this.getBidNb() + 1);
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
