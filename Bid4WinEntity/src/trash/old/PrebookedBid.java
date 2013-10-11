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
// * Cette classe d�fini une ench�re plac�e sur une vente avec pr�-inscription<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class PrebookedBid extends Bid<PrebookedBid, PrebookedAuction>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private PrebookedBid()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur de l'ench�re
//   * @auction Vente de l'ench�re avec pr�-inscription
//   * @position Position de l'ench�re dans la vente normale
//   * @throws ProtectionException Si la vente aux ench�res est prot�g�e
//   * @throws ModelArgumentException Si le compte utilisateur ou la vente en argument
//   * est nul ou si la position est inf�rieure � un
//   */
//  protected PrebookedBid(Account account, PrebookedAuction auction, int position)
//            throws ProtectionException, ModelArgumentException, UserException
//  {
//    super(account, auction, position);
//  }
//}
