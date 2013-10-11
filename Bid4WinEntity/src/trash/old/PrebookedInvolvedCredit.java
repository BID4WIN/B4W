package trash.old;
//package com.bid4win.persistence.entity.auction.prebooked;
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
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.InvolvedCredit;
//
///**
// * Cette classe défini une utilisation de crédits impliquée dans une vente aux
// * enchères avec pré-inscription<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class PrebookedInvolvedCredit
//       extends InvolvedCredit<PrebookedInvolvedCredit, PrebookedInvolvedBundle, PrebookedAuction>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private PrebookedInvolvedCredit()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des crédits utilisés
//   * @param auction Vente aux enchères avec pré-inscription impliquée dans les
//   * crédits utilisés
//   * @throws ProtectionException Si le compte utilisateur en argument est protégé
//   * @throws ModelArgumentException Si le compte utilisateur ou la vente aux enchères
//   * en argument est nul ou si des credits du compte utilisateur donné sont déjà
//   * impliqués dans la même vente aux enchères
//   */
//  public PrebookedInvolvedCredit(Account account, PrebookedAuction auction)
//         throws ProtectionException, ModelArgumentException
//  {
//    super(account, auction);
//    account.addPrebookedInvolvement(this);
//  }
//
//  /**
//   * Cette méthode permet de créer l'utilisation de credits du lot en argument
//   * @param bundleId {@inheritDoc}
//   * @param nb {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see com.bid4win.persistence.entity.account.CreditUsage#createBundleUsage(java.lang.Long, int)
//   */
//  @Override
//  protected PrebookedInvolvedBundle createBundleUsage(Long bundleId, int nb)
//      throws ModelArgumentException
//  {
//    return new PrebookedInvolvedBundle(bundleId, this, nb);
//  }
//}
