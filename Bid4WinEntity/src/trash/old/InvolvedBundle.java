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
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * @param <INVOLVED_CREDIT> D�finition de l'utilisation de cr�dits sur la vente<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class InvolvedBundle<CLASS extends InvolvedBundle<CLASS, INVOLVED_CREDIT>,
//                            INVOLVED_CREDIT extends InvolvedCreditAbstract<INVOLVED_CREDIT, CLASS>>
//       extends CreditBundleUsage<CLASS, INVOLVED_CREDIT>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected InvolvedBundle()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param bundleId Identifiant du lot duquel sont issus les cr�dits utilis�s
//   * @param involvedCredit Utilisation des cr�dits du lot r�f�renc�
//   * @param usedNb Nombre de cr�dits utilis�s
//   * @throws ModelArgumentException Si l'identifiant du lot de cr�dits ou leur
//   * utilisation est nul ou si le nombre de cr�dits utilis�s est inf�rieur � un
//   */
//  protected InvolvedBundle(Long bundleId, INVOLVED_CREDIT involvedCredit, int usedNb)
//            throws ModelArgumentException
//  {
//    super(bundleId, involvedCredit, usedNb);
//  }
//}
