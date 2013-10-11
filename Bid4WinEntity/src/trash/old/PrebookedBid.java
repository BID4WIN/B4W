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
//import com.bid4win.persistence.entity.auction.Bid;
//
///**
// * Cette classe défini une enchère placée sur une vente avec pré-inscription<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class PrebookedBid extends Bid<PrebookedBid, PrebookedAuction>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private PrebookedBid()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur de l'enchère
//   * @auction Vente de l'enchère avec pré-inscription
//   * @position Position de l'enchère dans la vente normale
//   * @throws ProtectionException Si la vente aux enchères est protégée
//   * @throws ModelArgumentException Si le compte utilisateur ou la vente en argument
//   * est nul ou si la position est inférieure à un
//   */
//  protected PrebookedBid(Account account, PrebookedAuction auction, int position)
//            throws ProtectionException, ModelArgumentException, UserException
//  {
//    super(account, auction, position);
//  }
//}
