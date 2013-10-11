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
//import com.bid4win.persistence.entity.auction.InvolvedBundle;
//
///**
// * Cette classe défini une utilisation de lot de crédits impliquée dans une vente
// * aux enchères avec pré-inscription<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class PrebookedInvolvedBundle
//extends InvolvedBundle<PrebookedInvolvedBundle, PrebookedInvolvedCredit>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private PrebookedInvolvedBundle()
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
//  protected PrebookedInvolvedBundle(Long bundleId, PrebookedInvolvedCredit involvedCredit, int usedNb)
//            throws ModelArgumentException
//  {
//    super(bundleId, involvedCredit, usedNb);
//  }
//}
