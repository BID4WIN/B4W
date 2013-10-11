package trash.old;
//package com.bid4win.persistence.entity.auction;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.persistence.entity.account.Account;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <AUCTION> Définition de la vente aux enchères impliquée dans les crédits
// * utilisés<BR>
// * @param <INVOLVED_BUNDLE> Définition de l'utilisation de lots de crédits sur la
// * vente<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public abstract class InvolvedCredit<CLASS extends InvolvedCredit<CLASS, INVOLVED_BUNDLE, AUCTION>,
//                                     INVOLVED_BUNDLE extends InvolvedBundle<INVOLVED_BUNDLE, CLASS>,
//                                     AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?>>
//       extends InvolvedCreditAbstract<CLASS, INVOLVED_BUNDLE>
//{
//  /** Lien vers la vente aux enchères impliquée dans les crédits utilisés */
//  @Transient
//  private AUCTION auctionLink = null;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected InvolvedCredit()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des crédits utilisés
//   * @param auction Vente aux enchères impliquée dans les crédits utilisés
//   * @throws ModelArgumentException Si le compte utilisateur ou la vente aux enchères
//   * en argument est nul
//   */
//  public InvolvedCredit(Account account, AUCTION auction) throws ModelArgumentException
//  {
//    super(account, UtilObject.checkNotNull("auction", auction).getId());
//    this.defineAuctionLink(auction);
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction TODO A COMMENTER
//   * @throws ProtectionException TODO A COMMENTER
//   * @throws ModelArgumentException TODO A COMMENTER
//   */
//  private void defineAuctionLink(AUCTION auction)
//          throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'utilisation de crédits courant
//    this.checkProtection();
//    // Vérifie que la vente aux enchères en argument n'est pas nulle
//    UtilObject.checkNotNull("auction", auction);
//    // Vérifie que l'utilisation de crédits courante n'est pas déjà liée
//    UtilObject.checkNull("auctionLink", this.getAuctionLink());
//    // Crée le lien de l'utilisation de crédits vers sa vente aux enchères
//    this.setAuctionLink(auction);
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du lien vers la vente aux enchères impliquée dans les crédits utilisés
//   * @return La vente aux enchères impliquée dans les crédits utilisés
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
//  @JoinColumn(name = "AUCTION_ID_LINK", nullable = false, unique = false)
//  public AUCTION getAuctionLink()
//  {
//    return this.auctionLink;
//  }
//  /**
//   * Setter du lien vers la vente aux enchères impliquée dans les crédits utilisés
//   * @param auction Vente aux enchères impliquée dans les crédits utilisés à lier
//   */
//  private void setAuctionLink(AUCTION auction)
//  {
//    this.auctionLink = auction;
//  }
//}
