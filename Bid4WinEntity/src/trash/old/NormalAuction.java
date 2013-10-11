package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Cacheable;
//import javax.persistence.Entity;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.Auction;
//import com.bid4win.persistence.entity.product.Product;
//
///**
// * Cette classe d�fini une vente aux ench�res normale<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class NormalAuction
//       extends Auction<NormalAuction, NormalBid, NormalSchedule, NormalTerms>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private NormalAuction()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet de la vente aux ench�res normale
//   * @param product Produit vendu aux ench�res
//   * @param schedule �l�ments de planification la vente aux ench�res normale
//   * @param terms Conditions de la vente aux ench�res normale
//   * @throws ModelArgumentException Si le produit, les �l�ments de planification
//   * ou les conditions en argument sont nuls
//   */
//  public NormalAuction(Product product, NormalSchedule schedule, NormalTerms terms)
//         throws ModelArgumentException
//  {
//    super(product, schedule, terms);
//  }
//
//  /**
//   * Cette m�thode permet de cr�er une ench�re sur la vente normale courante
//   * @param account {@inheritDoc}
//   * @param position {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws ProtectionException {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.Auction#createBid(com.bid4win.persistence.entity.account.Account, int)
//   */
//  @Override
//  protected NormalBid createBid(Account account, int position)
//            throws ProtectionException, ModelArgumentException, UserException
//  {
//    return new NormalBid(account, this, position);
//  }
//}
