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
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * @param <AUCTION> D�finition de la vente aux ench�res impliqu�e dans les cr�dits
// * utilis�s<BR>
// * @param <INVOLVED_BUNDLE> D�finition de l'utilisation de lots de cr�dits sur la
// * vente<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public abstract class InvolvedCredit<CLASS extends InvolvedCredit<CLASS, INVOLVED_BUNDLE, AUCTION>,
//                                     INVOLVED_BUNDLE extends InvolvedBundle<INVOLVED_BUNDLE, CLASS>,
//                                     AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?>>
//       extends InvolvedCreditAbstract<CLASS, INVOLVED_BUNDLE>
//{
//  /** Lien vers la vente aux ench�res impliqu�e dans les cr�dits utilis�s */
//  @Transient
//  private AUCTION auctionLink = null;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected InvolvedCredit()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des cr�dits utilis�s
//   * @param auction Vente aux ench�res impliqu�e dans les cr�dits utilis�s
//   * @throws ModelArgumentException Si le compte utilisateur ou la vente aux ench�res
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
//    // V�rifie la protection de l'utilisation de cr�dits courant
//    this.checkProtection();
//    // V�rifie que la vente aux ench�res en argument n'est pas nulle
//    UtilObject.checkNotNull("auction", auction);
//    // V�rifie que l'utilisation de cr�dits courante n'est pas d�j� li�e
//    UtilObject.checkNull("auctionLink", this.getAuctionLink());
//    // Cr�e le lien de l'utilisation de cr�dits vers sa vente aux ench�res
//    this.setAuctionLink(auction);
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du lien vers la vente aux ench�res impliqu�e dans les cr�dits utilis�s
//   * @return La vente aux ench�res impliqu�e dans les cr�dits utilis�s
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
//   * Setter du lien vers la vente aux ench�res impliqu�e dans les cr�dits utilis�s
//   * @param auction Vente aux ench�res impliqu�e dans les cr�dits utilis�s � lier
//   */
//  private void setAuctionLink(AUCTION auction)
//  {
//    this.auctionLink = auction;
//  }
//}
