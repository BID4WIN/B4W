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
// * Cette classe d�fini une utilisation de cr�dits impliqu�e dans une vente aux
// * ench�res avec pr�-inscription<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class PrebookedInvolvedCredit
//       extends InvolvedCredit<PrebookedInvolvedCredit, PrebookedInvolvedBundle, PrebookedAuction>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private PrebookedInvolvedCredit()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des cr�dits utilis�s
//   * @param auction Vente aux ench�res avec pr�-inscription impliqu�e dans les
//   * cr�dits utilis�s
//   * @throws ProtectionException Si le compte utilisateur en argument est prot�g�
//   * @throws ModelArgumentException Si le compte utilisateur ou la vente aux ench�res
//   * en argument est nul ou si des credits du compte utilisateur donn� sont d�j�
//   * impliqu�s dans la m�me vente aux ench�res
//   */
//  public PrebookedInvolvedCredit(Account account, PrebookedAuction auction)
//         throws ProtectionException, ModelArgumentException
//  {
//    super(account, auction);
//    account.addPrebookedInvolvement(this);
//  }
//
//  /**
//   * Cette m�thode permet de cr�er l'utilisation de credits du lot en argument
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
