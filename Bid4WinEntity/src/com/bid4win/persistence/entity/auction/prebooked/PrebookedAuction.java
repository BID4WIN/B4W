package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.product.Product;

/**
 * Cette classe défini une vente aux enchères avec pré-inscription<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PrebookedAuction
       extends Auction<PrebookedAuction, PrebookedBid, PrebookedSchedule, PrebookedTerms, PrebookedCancelPolicy>
{
  /**
   * Constructeur pour création par introspection
   */
  protected PrebookedAuction()
  {
    super();
  }
  /**
   * Constructeur complet de la vente aux enchères avec pré-inscription
   * @param product Produit vendu aux enchères
   * @param schedule Éléments de planification la vente aux enchères avec pré-inscription
   * @param terms Conditions de la vente aux enchères avec pré-inscription
   * @throws UserException Si le produit, les éléments de planification ou les
   * conditions en argument sont nuls
   */
  public PrebookedAuction(Product product, PrebookedSchedule schedule, PrebookedTerms terms)
         throws UserException
  {
    super(product, schedule, terms);
  }

  /**
   * Cette méthode permet de créer une enchère sur la vente avec pré-inscription
   * courante
   * @param account {@inheritDoc}
   * @param bidDate {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#createBid(com.bid4win.persistence.entity.account.Account, com.bid4win.commons.core.Bid4WinDate)
   */
  @Override
  protected PrebookedBid createBid(Account account, Bid4WinDate bidDate)
            throws ProtectionException, UserException
  {
    return new PrebookedBid(account, this, this.getBidNb() + 1);
  }
}
