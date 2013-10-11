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
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * @param <INVOLVED_BUNDLE> D�finition de l'utilisation de lots de cr�dits sur la
// * vente<BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public abstract class InvolvedCreditAbstract<CLASS extends InvolvedCreditAbstract<CLASS, INVOLVED_BUNDLE>,
//                                             INVOLVED_BUNDLE extends InvolvedBundle<INVOLVED_BUNDLE, CLASS>>
//       extends CreditUsage<CLASS, INVOLVED_BUNDLE>
//{
//  /** Identifiant de la vente aux ench�res impliqu�e dans les cr�dits utilis�s */
//  @Transient
//  private String auctionId = null;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected InvolvedCreditAbstract()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des cr�dits utilis�s
//   * @param auctionId Identifiant de la vente aux ench�res impliqu�e dans les cr�dits
//   * utilis�s
//   * @throws ModelArgumentException Si le compte utilisateur ou l'identifiant de
//   * la vente aux ench�res en argument est nul
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
//    // V�rifie la protection de l'utilisation de cr�dits courant
//    this.checkProtection();
//    // V�rifie que l'identifiant de la vente aux ench�res en argument n'est pas nul
//    UtilObject.checkNotNull("auctionId", auctionId);
//    // L'identifiant de la vente aux ench�res impliqu�e dans les cr�dits utilis�s
//    this.setAuctionId(UtilString.checkNotEmpty("auctionId",
//                                               UtilString.trimNotNull(auctionId)));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de l'identifiant de la vente aux ench�res impliqu�e dans les cr�dits
//   * utilis�s
//   * @return L'identifiant de la vente aux ench�res impliqu�e dans les cr�dits
//   * utilis�s
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "AUCTION_ID", length = 12, nullable = false, unique = false)
//  public String getAuctionId()
//  {
//    return this.auctionId;
//  }
//  /**
//   * Setter de l'identifiant de la vente aux ench�res impliqu�e dans les cr�dits
//   * utilis�s
//   * @param auctionId Identifiant de la vente aux ench�res impliqu�e dans les cr�dits
//   * utilis�s � positionner
//   */
//  private void setAuctionId(String auctionId)
//  {
//    this.auctionId = auctionId;
//  }
//}
