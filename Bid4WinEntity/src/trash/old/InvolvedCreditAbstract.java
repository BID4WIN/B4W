package trash.old;
//package com.bid4win.persistence.entity.auction;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Column;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.UtilString;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.account.CreditUsage;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <INVOLVED_BUNDLE> Définition de l'utilisation de lots de crédits sur la
// * vente<BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public abstract class InvolvedCreditAbstract<CLASS extends InvolvedCreditAbstract<CLASS, INVOLVED_BUNDLE>,
//                                             INVOLVED_BUNDLE extends InvolvedBundle<INVOLVED_BUNDLE, CLASS>>
//       extends CreditUsage<CLASS, INVOLVED_BUNDLE>
//{
//  /** Identifiant de la vente aux enchères impliquée dans les crédits utilisés */
//  @Transient
//  private String auctionId = null;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected InvolvedCreditAbstract()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des crédits utilisés
//   * @param auctionId Identifiant de la vente aux enchères impliquée dans les crédits
//   * utilisés
//   * @throws ModelArgumentException Si le compte utilisateur ou l'identifiant de
//   * la vente aux enchères en argument est nul
//   */
//  public InvolvedCreditAbstract(Account account, String auctionId) throws ModelArgumentException
//  {
//    super(account);
//    this.defineAuctionId(auctionId);
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auctionId TODO A COMMENTER
//   * @throws ProtectionException TODO A COMMENTER
//   * @throws ModelArgumentException TODO A COMMENTER
//   */
//  private void defineAuctionId(String auctionId)
//          throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'utilisation de crédits courant
//    this.checkProtection();
//    // Vérifie que l'identifiant de la vente aux enchères en argument n'est pas nul
//    UtilObject.checkNotNull("auctionId", auctionId);
//    // L'identifiant de la vente aux enchères impliquée dans les crédits utilisés
//    this.setAuctionId(UtilString.checkNotEmpty("auctionId",
//                                               UtilString.trimNotNull(auctionId)));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de l'identifiant de la vente aux enchères impliquée dans les crédits
//   * utilisés
//   * @return L'identifiant de la vente aux enchères impliquée dans les crédits
//   * utilisés
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "AUCTION_ID", length = 12, nullable = false, unique = false)
//  public String getAuctionId()
//  {
//    return this.auctionId;
//  }
//  /**
//   * Setter de l'identifiant de la vente aux enchères impliquée dans les crédits
//   * utilisés
//   * @param auctionId Identifiant de la vente aux enchères impliquée dans les crédits
//   * utilisés à positionner
//   */
//  private void setAuctionId(String auctionId)
//  {
//    this.auctionId = auctionId;
//  }
//}
