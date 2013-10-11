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
// * Cette classe d�fini un lot de cr�dits pour un compte utilisateur<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//@AttributeOverride(name = "nb", column = @Column(name = "CURRENT_NB"))
//public class CreditBundle<CLASS extends CreditBundle<CLASS>> extends Credit<CLASS>
//{
//  /** Lien vers le compte utilisateur du lot de cr�dits */
//  @Transient
//  private Account accountLink = null;
//  /** Nombre initial de cr�dits du lot */
//  @Transient
//  private int initialNb = 0;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected CreditBundle()
//  {
//    super();
//  }
//  
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du lot de cr�dits
//   * @param nb Nombre de cr�dits du lot
//   * @throws ProtectionException Si le comte utilisateur en argument est prot�g�
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   * ou si le nombre de cr�dits est inf�rieur � un
//   */
//  public CreditBundle(Account account, int nb) throws ProtectionException, ModelArgumentException
//  {
//    super(account, nb);
//    // D�fini le nombre initial de cr�dits du lot
//    this.defineInitialNb(nb);
//    // Lie le lot de cr�dits � son compte utilisateur
//    this.linkToAccount();
//  }
//
//  /**
//   * Ajoute � la liste des noeuds de relations du lot de cr�dits le lien vers son
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
//   * Permet de r�cup�rer le lien vers le compte utilisateur du lot de cr�dits s'
//   * il correspond � la relation en argument.
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
//   * Red�fini l'�quivalence interne de deux lots de cr�dits sans prise en compte
//   * de leurs relations afin d'y ajouter le test de leurs donn�es propres
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
//   * Permet d'effectuer le rendu simple du lot de cr�dits courant sans prise en
//   * compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'un cr�dit
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les �l�ments du lot de cr�dits
//    buffer.append(" INITIAL_NB=" + this.getInitialNb());
//    buffer.append(" CURRENT_NB=" + this.getCurrentNb());
//    // Retourne le rendu
//    return buffer;
//  }
//  
//  /**
//   * Cette m�thode permet d'utiliser un des cr�dits du lot
//   * @return Le cr�dit utilis�
//   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
//   * @throws ModelArgumentException Si un probl�me intervient lors de la construction
//   * des cr�dits utilis�s
//   * @throws UserException S'il n'y a pas de cr�dit dans le lot
//   */
//  public Credit<?> use() throws ProtectionException, ModelArgumentException, UserException
//  {
//    return this.use(1);
//  }
//  /**
//   * Cette m�thode permet d'utiliser un certain nombre de cr�dits du lot
//   * @param nb Nombre de cr�dits � utiliser
//   * @return Les cr�dits utilis�s
//   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
//   * @throws ModelArgumentException Si le nombre de cr�dits � utiliser est inf�rieur
//   * � z�ro ou si un probl�me intervient lors de la construction des cr�dits utilis�s
//   * @throws UserException S'il n'y a pas assez de cr�dits dans le lot
//   */
//  public Credit<?> use(int nb) throws ProtectionException, ModelArgumentException, UserException
//  {
//    // V�rification de la possibilit� d'utiliser le nombre de cr�dits choisi
//    UtilNumber.checkMinValue("nb", nb, 0, true);
//    UtilNumber.checkMinValue("currentNb", this.getCurrentNb(), nb, true,
//        MessageRef.ERROR_BID_RIGHT_NB_INSUFFICIENT);
//    // Cr�ation des cr�dits utilis�s
//    Credit<?> credit = new Credit(this.getAccount(), nb);
//    try
//    {
//      // Diminution du nombre de cr�dits du lot
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
//   * Permet de savoir si le lot de cr�dits courant est li� � un compte utilisateur
//   * @return True si le lot de cr�dits courant est li� � un compte utilisateur,
//   * false sinon
//   */
//  public boolean isLinkedToAccount()
//  {
//    return this.getAccountLink() != null;
//  }
//  /**
//   * Cette m�thode permet de lier le lot de cr�dits courant � son compte utilisateur
//   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
//   * @throws ModelArgumentException Si le compte utilisateur du lot de cr�dits est
//   * nul ou si ce dernier est d�j� li�
//   */
//  protected void linkToAccount() throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection du lot de cr�dits courant
//    this.checkProtection();
//    // V�rifie que le compte utilisateur n'est pas nul
//    UtilObject.checkNotNull("account", this.getAccount());
//    // V�rifie que le lot de cr�dits courant n'est pas d�j� li�
//    UtilBoolean.checkFalse("isLinkedToAccount()", this.isLinkedToAccount());
//    // Cr�e le lien du lot de cr�dits vers son compte utilisateur
//    this.setAccountLink(this.getAccount());
//  }
//  /**
//   * Cette m�thode permet de d�lier le lot de cr�dits courant de son compte utilisateur
//   * @return Le compte utilisateur anciennement li� au lot de cr�dits courant
//   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
//   * @throws ModelArgumentException Si le lot de cr�dits courant n'est li� � aucun
//   * compte utilisateur
//   */
//  public Account unlinkFromAccount() throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection du lot de cr�dits courant
//    this.checkProtection();
//    // V�rifie que le lot de cr�dits courant est bien li�
//    Account account = UtilObject.checkNotNull("accountLink", this.getAccountLink());
//    // Supprime le lien du lot de cr�dits vers son compte utilisateur
//    this.setAccountLink(null);
//    return account;
//  }
//  /**
//   * Cette m�thode permet de d�finir le nombre initial de cr�dits du lot
//   * @param initialNb D�finition du nombre initial de cr�dits du lot
//   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
//   * @throws ModelArgumentException Si on d�finie un nombre initial inf�rieur � un
//   */
//  private void defineInitialNb(int initialNb) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection du lot de cr�dits courant
//    this.checkProtection();
//    this.setInitialNb(UtilNumber.checkMinValue("initialNb", initialNb, 1, true));
//  }
//  /**
//   * Getter du nombre courant de cr�dits du lot
//   * @return Le nombre courant de cr�dits du lot
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
//   * Getter du lien vers le compte utilisateur du cr�dit
//   * @return Le lien vers le compte utilisateur du cr�dit
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
//   * Setter du lien vers le compte utilisateur du cr�dit
//   * @param account Compte utilisateur avec lequel lier le cr�dit
//   */
//  private void setAccountLink(Account account)
//  {
//    this.accountLink = account;
//  }
//
//  /**
//   * Getter du nombre initial de cr�dits du lot
//   * @return Le nombre initial de cr�dits du lot
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "INITIAL_NB", length = 3, nullable = false, unique = false)
//  public int getInitialNb()
//  {
//    return this.initialNb;
//  }
//  /**
//   * Setter du nombre initial de cr�dits du lot
//   * @param initialNb Nombre initial de cr�dits du lot � positionner
//   */
//  private void setInitialNb(int initialNb)
//  {
//    this.initialNb = initialNb;
//  }
//  
//}
