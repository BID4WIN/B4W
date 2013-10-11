package trash.old;
//package com.bid4win.persistence.entity.account;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Cacheable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Transient;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;
//
///**
// * Cette classe défini un lot de crédits pour un compte utilisateur<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class CreditBundle extends AccountBasedEntityMultipleAutoID<CreditBundle, Account>
//{
//  /** Lien vers le compte utilisateur du lot de crédits */
//  @Transient
//  private Account accountLink = null;
//  /** Nombre initial de crédits du lot */
//  @Transient
//  private int initialNb = 0;
//  /** Nombre courant de crédits du lot */
//  @Transient
//  private int currentNb = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected CreditBundle()
//  {
//    super();
//  }
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
//    super(account);
//    // Défini le nombre initial de crédits du lot
//    this.defineInitialNb(nb);
//    // Défini le nombre courant de crédits du lot
//    this.defineCurrentNb(nb);
//    // Lie le lot de crédits à son compte utilisateur
//    this.linkToAccount();
//  }
//
//  /**
//   * Redéfini l'équivalence interne de deux lots de crédits sans prise en compte
//   * de leurs relations afin d'y ajouter le test de leurs données propres
//   * @param toBeCompared {@inheritDoc}
//   * @param identical {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
//   */
//  @Override
//  protected boolean sameRelationNoneInternal(CreditBundle toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getInitialNb() == toBeCompared.getInitialNb() &&
//           this.getCurrentNb() == toBeCompared.getCurrentNb();
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
//    // Effectue le rendu de base sans lien d'une entité
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les éléments du lot de crédits
//    buffer.append(" INITIAL_NB=" + this.getInitialNb());
//    buffer.append(" CURRENT_NB=" + this.getCurrentNb());
//    // Retourne le rendu
//    return buffer;
//  }
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
//    nodeList.add(CreditBundle_Relations.NODE_ACCOUNT_LINK);
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
//    if(relation.equals(CreditBundle_Relations.RELATION_ACCOUNT_LINK))
//    {
//      return this.getAccountLink();
//    }
//    return super.getRelationSimple(relation);
//  }
//
//  /**
//   * Cette méthode permet d'utiliser un des crédits du lot. Si le lot est vide
//   * une fois le crédit utilisé, il sera retiré du compte utilisateur
//   * @return Le lot courant des crédits utilisés
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si le lot n'est lié à aucun compte utilisateur
//   * @throws UserException S'il n'y a pas de crédit dans le lot
//   */
//  protected CreditBundle use() throws ProtectionException, ModelArgumentException, UserException
//  {
//    return this.use(1);
//  }
//  /**
//   * Cette méthode permet d'utiliser un certain nombre de crédits du lot. Si le
//   * lot est vide une fois les crédits utilisés, il sera retiré du compte utilisateur
//   * @param nb Nombre de crédits à utiliser
//   * @return Le lot courant des crédits utilisés
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si le nombre de crédits à utiliser est inférieur
//   * à un ou si le lot n'est lié à aucun compte utilisateur
//   * @throws UserException S'il n'y a pas assez de crédits dans le lot
//   */
//  protected CreditBundle use(int nb) throws ProtectionException, ModelArgumentException, UserException
//  {
//    // Vérification du nombre de crédits à utiliser
//    UtilNumber.checkMinValue("nb", nb, 1, true);
//    try
//    {
//      // Diminution du nombre de crédits du lot
//      this.defineCurrentNb(this.getCurrentNb() - nb);
//    }
//    catch(ModelArgumentException ex)
//    {
//      throw new UserException(MessageRef.ERROR_CREDIT_NB_INSUFFICIENT, ex);
//    }
//    // On retire le lot du compte utilisateur si tous les crédits sont utilisés
//    if(this.getCurrentNb() == 0)
//    {
//      this.unlinkFromAccount();
//    }
//    return this;
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
//   * @throws ProtectionException Si le lot de crédits courant ou son compte utilisateur
//   * est protégé
//   * @throws ModelArgumentException Si le compte utilisateur du lot de crédits est
//   * nul ou si ce dernier est déjà lié
//   */
//  private void linkToAccount() throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de crédits courant
//    this.checkProtection();
//    // Vérifie que le compte utilisateur du lot de crédits courant n'est pas nul
//    Account account = UtilObject.checkNotNull("account", this.getAccount());
//    // Vérifie que le lot de crédits courant n'est pas déjà lié
//    UtilObject.checkNull("accountLink", this.getAccountLink());
//    // Ajoute le lot de crédits au compte utilisateur
//    account.addCreditBundle(this);
//    // Crée le lien du lot de crédits vers son compte utilisateur
//    this.setAccountLink(account);
//  }
//  /**
//   * Cette méthode permet de délier le lot de crédits courant de son compte utilisateur
//   * @return Le compte utilisateur anciennement lié au lot de crédits courant
//   * @throws ProtectionException Si le lot de crédits courant ou son compte utilisateur
//   * est protégé
//   * @throws ModelArgumentException Si le lot de crédits courant n'est lié à aucun
//   * compte utilisateur ou contient encore des crédits à utiliser
//   */
//  public Account unlinkFromAccount() throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de crédits courant
//    this.checkProtection();
//    // Vérifie que le lot de crédits courant est bien lié
//    Account account = UtilObject.checkNotNull("accountLink", this.getAccountLink());
//    // Retire le lot de crédits du compte utilisateur
//    account.removeCreditBundle(this);
//    // Supprime le lien du lot de crédits vers son compte utilisateur
//    this.setAccountLink(null);
//    return account;
//  }
//  /**
//   * Cette méthode permet de définir le nombre initial de crédits du lot
//   * @param initialNb Définition du nombre initial de crédits du lot
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si on défini un nombre inférieur à un
//   */
//  private void defineInitialNb(int initialNb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de crédits courant
//    this.checkProtection();
//    this.setInitialNb(UtilNumber.checkMinValue("initialNb", initialNb, 1, true));
//  }
//  /**
//   * Cette méthode permet de définir le nombre courant de crédits du lot
//   * @param currentNb Définition du nombre courant de crédits du lot
//   * @throws ProtectionException Si le lot de crédits courant est protégé
//   * @throws ModelArgumentException Si on défini un nombre inférieur à zéro
//   */
//  private void defineCurrentNb(int currentNb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de crédits courant
//    this.checkProtection();
//    this.setCurrentNb(UtilNumber.checkMinValue("currentNb", currentNb, 0, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du lien vers le compte utilisateur du lot de crédits
//   * @return Le lien vers le compte utilisateur du lot de crédits
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
//   * Setter du lien vers le compte utilisateur du lot de crédits
//   * @param account Compte utilisateur avec lequel lier le lot de crédits
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
//  /**
//   * Getter du nombre courant de crédits du lot
//   * @return Le nombre courant de crédits du lot
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "CURRENT_NB", length = 3, nullable = false, unique = false)
//  public int getCurrentNb()
//  {
//    return this.currentNb;
//  }
//  /**
//   * Setter du nombre courant de crédits du lot
//   * @param currentNb Nombre courant de crédits du lot à positionner
//   */
//  private void setCurrentNb(int currentNb)
//  {
//    this.currentNb = currentNb;
//  }
//}
