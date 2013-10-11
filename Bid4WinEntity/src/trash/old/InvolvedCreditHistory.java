package trash.old;
//package com.bid4win.persistence.entity.auction.history;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.persistence.entity.auction.InvolvedBundle;
//import com.bid4win.persistence.entity.auction.InvolvedCreditAbstract;
//
///**
// * Cette classe défini un historique d'utilisation de crédits impliquée dans une
// * vente aux enchères <BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class InvolvedCreditHistory
//       extends InvolvedCreditAbstract<InvolvedCreditHistory, InvolvedBundleHistory>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private InvolvedCreditHistory()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param involvedCredit Utilisation de crédits dont on souhaite construire l'
//   * historique
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   */
//  public InvolvedCreditHistory(InvolvedCreditAbstract<?, ?> involvedCredit)
//         throws ModelArgumentException
//  {
//    super(involvedCredit.getAccount(), involvedCredit.getAuctionId());
//    for(Object object : involvedCredit.getBundleUsageList())
//    {
//      InvolvedBundle<?, ?> involvedBundle = (InvolvedBundle<?, ?>)object;
//      this.addBundleUsage(involvedBundle.getBundleId(), involvedBundle.getUsedNb());
//    }
//  }
//
//  /**
//   * Cette méthode permet de créer l'historique d'utilisation de credits du lot
//   * en argument
//   * @param bundleId {@inheritDoc}
//   * @param nb {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see com.bid4win.persistence.entity.account.CreditUsage#createBundleUsage(java.lang.Long, int)
//   */
//  @Override
//  protected InvolvedBundleHistory createBundleUsage(Long bundleId, int nb)
//            throws ModelArgumentException
//  {
//    return new InvolvedBundleHistory(bundleId, this, nb);
//  }
//}
