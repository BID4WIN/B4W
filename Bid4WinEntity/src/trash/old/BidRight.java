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
// * Cette classe défini un droit à enchérir pour un compte utilisateur<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class BidRight<CLASS extends BidRight<CLASS>>
//       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
//{
//  /** Lien vers le compte utilisateur du droit à enchérir */
//  @Transient
//  private Account accountLink = null;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected BidRight()
//  {
//    super();
//  }
//
//
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du droit à enchérir
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
//   * @param bidRight Droit à enchérir à recopier
//   * @throws ModelArgumentException Si le droit à enchérir en argument est nul
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
//   * Ajoute à la liste des noeuds de relations du droit à enchérir le lien vers
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
//   * Permet de récupérer le lien vers le compte utilisateur du droit à enchérir
//   * s'il correspond à la relation en argument.
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
//   * Permet de savoir si le droit à enchérir courant est lié à un compte utilisateur
//   * @return True si le droit à enchérir courant est lié à un compte utilisateur,
//   * false sinon
//   */
//  public boolean isLinkedToAccount()
//  {
//    return this.getAccountLink() != null;
//  }
//  /**
//   * Cette méthode permet de délier le droit à enchérir courant de son compte utilisateur
//   * @return Le compte utilisateur anciennement lié au droit à enchérir courant
//   * @throws ProtectionException Si le droit à enchérir courant est protégé
//   * @throws ModelArgumentException Si le droit à enchérir courant n'est lié à aucun
//   * compte utilisateur
//   */
//  public Account unlinkFromAccount() throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du droit à enchérir courant
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
//   * Getter du lien vers le compte utilisateur du droit à enchérir
//   * @return Le lien vers le compte utilisateur du droit à enchérir
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
//   * Setter du lien vers le compte utilisateur du droit à enchérir
//   * @param account Compte utilisateur avec lequel lier le droit à enchérir
//   */
//  private void setAccountLink(Account account)
//  {
//    this.accountLink = account;
//  }
//}
