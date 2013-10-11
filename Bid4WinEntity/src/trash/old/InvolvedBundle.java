package trash.old;
//package com.bid4win.persistence.entity.auction;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.MappedSuperclass;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.persistence.entity.account.CreditBundleUsage;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <INVOLVED_CREDIT> Définition de l'utilisation de crédits sur la vente<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class InvolvedBundle<CLASS extends InvolvedBundle<CLASS, INVOLVED_CREDIT>,
//                            INVOLVED_CREDIT extends InvolvedCreditAbstract<INVOLVED_CREDIT, CLASS>>
//       extends CreditBundleUsage<CLASS, INVOLVED_CREDIT>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected InvolvedBundle()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param bundleId Identifiant du lot duquel sont issus les crédits utilisés
//   * @param involvedCredit Utilisation des crédits du lot référencé
//   * @param usedNb Nombre de crédits utilisés
//   * @throws ModelArgumentException Si l'identifiant du lot de crédits ou leur
//   * utilisation est nul ou si le nombre de crédits utilisés est inférieur à un
//   */
//  protected InvolvedBundle(Long bundleId, INVOLVED_CREDIT involvedCredit, int usedNb)
//            throws ModelArgumentException
//  {
//    super(bundleId, involvedCredit, usedNb);
//  }
//}
