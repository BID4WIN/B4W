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
// * Cette classe d�fini une utilisation de lot de cr�dits impliqu�e dans une vente
// * aux ench�res normale<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class NormalInvolvedBundle
//       extends InvolvedBundle<NormalInvolvedBundle, NormalInvolvedCredit>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private NormalInvolvedBundle()
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
//  protected NormalInvolvedBundle(Long bundleId, NormalInvolvedCredit involvedCredit, int usedNb)
//            throws ModelArgumentException
//  {
//    super(bundleId, involvedCredit, usedNb);
//  }
//}
