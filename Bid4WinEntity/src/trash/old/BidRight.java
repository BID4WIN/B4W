//package trash.old;
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
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;
//import com.bid4win.persistence.entity.account.Account;
//
///**
// * Cette classe d�fini un droit � ench�rir pour un compte utilisateur<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class BidRight<CLASS extends BidRight<CLASS>>
//       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
//{
//  /** Lien vers le compte utilisateur du droit � ench�rir */
//  @Transient
//  private Account accountLink = null;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected BidRight()
//  {
//    super();
//  }
//
//
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du droit � ench�rir
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   */
//  protected BidRight(Account account) throws ModelArgumentException
//  {
//    super(account);
//    this.setAccountLink(account);
//  }
//
//  /**
//   * Constructeur par copie
//   * @param bidRight Droit � ench�rir � recopier
//   * @throws ModelArgumentException Si le droit � ench�rir en argument est nul
//   */
//  protected BidRight(BidRight<?> bidRight) throws ModelArgumentException
//  {
//    super(UtilObject.checkNotNull("bidRight", bidRight).getAccount());
//    if(bidRight.isLinkedToAccount())
//    {
//      this.setAccountLink(bidRight.getAccountLink());
//    }
//  }
//
//  /**
//   * Ajoute � la liste des noeuds de relations du droit � ench�rir le lien vers
//   * son compte utilisateur
//   * TODO A COMPLETER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(BidRight_.NODE_ACCOUNT_LINK);
//    return nodeList;
//  }
//  /**
//   * Permet de r�cup�rer le lien vers le compte utilisateur du droit � ench�rir
//   * s'il correspond � la relation en argument.
//   * TODO A COMPLETER
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
//  {
//    if(relation.equals(BidRight_.RELATION_ACCOUNT_LINK))
//    {
//      return this.getAccountLink();
//    }
//    return super.getRelationSimple(relation);
//  }
//
//  /**
//   * Permet de savoir si le droit � ench�rir courant est li� � un compte utilisateur
//   * @return True si le droit � ench�rir courant est li� � un compte utilisateur,
//   * false sinon
//   */
//  public boolean isLinkedToAccount()
//  {
//    return this.getAccountLink() != null;
//  }
//  /**
//   * Cette m�thode permet de d�lier le droit � ench�rir courant de son compte utilisateur
//   * @return Le compte utilisateur anciennement li� au droit � ench�rir courant
//   * @throws ProtectionException Si le droit � ench�rir courant est prot�g�
//   * @throws ModelArgumentException Si le droit � ench�rir courant n'est li� � aucun
//   * compte utilisateur
//   */
//  public Account unlinkFromAccount() throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection du droit � ench�rir courant
//    this.checkProtection();
//    Account account = UtilObject.checkNotNull("accountLink", this.getAccountLink());
//    this.setAccountLink(null);
//    return account;
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du lien vers le compte utilisateur du droit � ench�rir
//   * @return Le lien vers le compte utilisateur du droit � ench�rir
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
//  @JoinColumn(name = "ACCOUNT_ID_LINK", nullable = true, unique = false)
//  public Account getAccountLink()
//  {
//    return this.accountLink;
//  }
//  /**
//   * Setter du lien vers le compte utilisateur du droit � ench�rir
//   * @param account Compte utilisateur avec lequel lier le droit � ench�rir
//   */
//  private void setAccountLink(Account account)
//  {
//    this.accountLink = account;
//  }
//}
