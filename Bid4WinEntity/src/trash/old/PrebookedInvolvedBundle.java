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
// * Cette classe d�fini une utilisation de lot de cr�dits impliqu�e dans une vente
// * aux ench�res avec pr�-inscription<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class PrebookedInvolvedBundle
//extends InvolvedBundle<PrebookedInvolvedBundle, PrebookedInvolvedCredit>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private PrebookedInvolvedBundle()
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
//  protected PrebookedInvolvedBundle(Long bundleId, PrebookedInvolvedCredit involvedCredit, int usedNb)
//            throws ModelArgumentException
//  {
//    super(bundleId, involvedCredit, usedNb);
//  }
//}
