//package trash;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.AttributeOverride;
//import javax.persistence.Column;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilBoolean;
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.exception.UserException.MessageRef;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.account.Account;
//
///**
// * Cette classe défini un lot de crédits pour un compte utilisateur<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//@AttributeOverride(name = "nb", column = @Column(name = "CURRENT_NB"))
//public class CreditBundle<CLASS extends CreditBundle<CLASS>> extends Credit<CLASS>
//{
//  /** Lien vers le compte utilisateur du lot de crédits */
//  @Transient
//  private Account accountLink = null;
//  /** Nombre initial de crédits du lot */
//  @Transient
//  private int initialNb = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected CreditBundle()
//  {
//    super();
//  }
//  
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du lot de crédits
//   * @param nb Nombre de crédits du lot
//   * @throws ProtectionException Si le comte utilisateur en argument est protégé
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   * ou si le nombre de crédits est inférieur à un
//   */
//  public CreditBundle(Account account, int nb) throws ProtectionException, ModelArgumentException
//  {
//    super(account, nb);
//    // Défini le nombre initial de crédits du lot
//    this.defineInitialNb(nb);
//    // Lie le lot de crédits à son compte utilisateur
//    this.linkToAccount();
//  }
//
//  /**
//   * Ajoute à la liste des noeuds de relations du lot de crédits le lien vers son
//   * compte utilisateur
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(CreditBundle_.NODE_ACCOUNT_LINK);
//    return nodeList;
//  }
//  /**
//   * Permet de récupérer le lien vers le compte utilisateur du lot de crédits s'
//   * il correspond à la relation en argument.
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
//  {
//    if(relation.equals(CreditBundle_.RELATION_ACCOUNT_LINK))
//    {
//      return this.getAccountLink();
//    }
//    return super.getRelationSimple(relation);
//  }
//  /**
//   * Redéfini l'équivalence interne de deux lots de crédits sans prise en compte
//   * de leurs relations afin d'y ajouter le test de leurs données propres
//   * @param toBeCompared {@inheritDoc}
//   * @param identical {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see trash.Credit#sameRelationNoneInternal(trash.Credit, boolean)
//   */
//  @Override
//  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getInitialNb() == toBeCompared.getInitialNb();
//    
//  }
//  /**
//   * Permet d'effectuer le rendu simple du lot de crédits courant sans prise en
//   * compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'un crédit
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les éléments du lot de crédits
//    buffer.append(" INITIAL_NB=" + this.getInitialNb());
//    buffer.append(" CURRENT_NB=" + this.getCurrentNb());
//    // Retourne le rendu
//    return buffer;
//  }
//  
//  /**
//   * Cette méthode permet d'utiliser un des crédits du lot
//   * @return Le crédit utilisé
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si un problème intervient lors de la construction
//   * des crédits utilisés
//   * @throws UserException S'il n'y a pas de crédit dans le lot
//   */
//  public Credit<?> use() throws ProtectionException, ModelArgumentException, UserException
//  {
//    return this.use(1);
//  }
//  /**
//   * Cette méthode permet d'utiliser un certain nombre de crédits du lot
//   * @param nb Nombre de crédits à utiliser
//   * @return Les crédits utilisés
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si le nombre de crédits à utiliser est inférieur
//   * à zéro ou si un problème intervient lors de la construction des crédits utilisés
//   * @throws UserException S'il n'y a pas assez de crédits dans le lot
//   */
//  public Credit<?> use(int nb) throws ProtectionException, ModelArgumentException, UserException
//  {
//    // Vérification de la possibilité d'utiliser le nombre de crédits choisi
//    UtilNumber.checkMinValue("nb", nb, 0, true);
//    UtilNumber.checkMinValue("currentNb", this.getCurrentNb(), nb, true,
//        MessageRef.ERROR_BID_RIGHT_NB_INSUFFICIENT);
//    // Création des crédits utilisés
//    Credit<?> credit = new Credit(this.getAccount(), nb);
//    try
//    {
//      // Diminution du nombre de crédits du lot
//      this.defineNb(this.getCurrentNb() - nb);
//    }
//    catch(ModelArgumentException ex)
//    {
//      throw new UserException(MessageRef.ERROR_BID_RIGHT_NB_INSUFFICIENT, ex);
//    }
//    return credit;
//  }
//  
//  /**
//   * Permet de savoir si le lot de crédits courant est lié à un compte utilisateur
//   * @return True si le lot de crédits courant est lié à un compte utilisateur,
//   * false sinon
//   */
//  public boolean isLinkedToAccount()
//  {
//    return this.getAccountLink() != null;
//  }
//  /**
//   * Cette méthode permet de lier le lot de crédits courant à son compte utilisateur
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si le compte utilisateur du lot de crédits est
//   * nul ou si ce dernier est déjà lié
//   */
//  protected void linkToAccount() throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de crédits courant
//    this.checkProtection();
//    // Vérifie que le compte utilisateur n'est pas nul
//    UtilObject.checkNotNull("account", this.getAccount());
//    // Vérifie que le lot de crédits courant n'est pas déjà lié
//    UtilBoolean.checkFalse("isLinkedToAccount()", this.isLinkedToAccount());
//    // Crée le lien du lot de crédits vers son compte utilisateur
//    this.setAccountLink(this.getAccount());
//  }
//  /**
//   * Cette méthode permet de délier le lot de crédits courant de son compte utilisateur
//   * @return Le compte utilisateur anciennement lié au lot de crédits courant
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si le lot de crédits courant n'est lié à aucun
//   * compte utilisateur
//   */
//  public Account unlinkFromAccount() throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de crédits courant
//    this.checkProtection();
//    // Vérifie que le lot de crédits courant est bien lié
//    Account account = UtilObject.checkNotNull("accountLink", this.getAccountLink());
//    // Supprime le lien du lot de crédits vers son compte utilisateur
//    this.setAccountLink(null);
//    return account;
//  }
//  /**
//   * Cette méthode permet de définir le nombre initial de crédits du lot
//   * @param initialNb Définition du nombre initial de crédits du lot
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si on définie un nombre initial inférieur à un
//   */
//  private void defineInitialNb(int initialNb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de crédits courant
//    this.checkProtection();
//    this.setInitialNb(UtilNumber.checkMinValue("initialNb", initialNb, 1, true));
//  }
//  /**
//   * Getter du nombre courant de crédits du lot
//   * @return Le nombre courant de crédits du lot
//   */
//  public int getCurrentNb()
//  {
//    return this.getNb();
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du lien vers le compte utilisateur du crédit
//   * @return Le lien vers le compte utilisateur du crédit
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
//   * Setter du lien vers le compte utilisateur du crédit
//   * @param account Compte utilisateur avec lequel lier le crédit
//   */
//  private void setAccountLink(Account account)
//  {
//    this.accountLink = account;
//  }
//
//  /**
//   * Getter du nombre initial de crédits du lot
//   * @return Le nombre initial de crédits du lot
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "INITIAL_NB", length = 3, nullable = false, unique = false)
//  public int getInitialNb()
//  {
//    return this.initialNb;
//  }
//  /**
//   * Setter du nombre initial de crédits du lot
//   * @param initialNb Nombre initial de crédits du lot à positionner
//   */
//  private void setInitialNb(int initialNb)
//  {
//    this.initialNb = initialNb;
//  }
//  
//}
