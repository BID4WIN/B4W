package trash.old;
//package com.bid4win.persistence.entity.auction.normal;
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
//import com.bid4win.persistence.entity.auction.InvolvedBundle;
//
///**
// * Cette classe défini une utilisation de lot de crédits impliquée dans une vente
// * aux enchères normale<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class NormalInvolvedBundle
//       extends InvolvedBundle<NormalInvolvedBundle, NormalInvolvedCredit>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private NormalInvolvedBundle()
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
//  protected NormalInvolvedBundle(Long bundleId, NormalInvolvedCredit involvedCredit, int usedNb)
//            throws ModelArgumentException
//  {
//    super(bundleId, involvedCredit, usedNb);
//  }
//}
