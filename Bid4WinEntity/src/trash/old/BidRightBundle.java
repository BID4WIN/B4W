//package trash.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Cacheable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
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
//import com.bid4win.commons.core.exception.UserException.MessageRef;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.persistence.entity.account.Account;
//
///**
// * Cette classe défini un lot de droits à enchérir pour un compte utilisateur<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class BidRightBundle extends BidRight<BidRightBundle>
//{
//  /** Nombre initial de droits à enchérir du lot */
//  @Transient
//  private int initialNb = 0;
//  /** Nombre courant de droits à enchérir du lot */
//  @Transient
//  private int currentNb = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private BidRightBundle()
//  {
//    super();
//  }
//
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du lot de droits à enchérir
//   * @param nb Nombre de droits à enchérir du lot
//   * @throws ProtectionException Si le comte utilisateur en argument est protégé
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   * ou si le nombre de droits à enchérir est inférieur à un
//   */
//  public BidRightBundle(Account account, int nb) throws ProtectionException, ModelArgumentException
//  {
//    super(account);
//    this.defineInitialNb(nb);
//    this.defineCurrentNb(nb);
//    UtilObject.checkNotNull("account", account).addBidRightBundle(this);
//  }
//
//  /**
//   * Redéfini l'équivalence interne de deux lots de droits à enchérir sans prise
//   * en compte de leurs relations afin d'y ajouter le test de leurs données propres
//   * @param toBeCompared {@inheritDoc}
//   * @param identical {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
//   */
//  @Override
//  protected boolean sameRelationNoneInternal(BidRightBundle toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getInitialNb() == toBeCompared.getInitialNb() &&
//           this.getCurrentNb() == toBeCompared.getCurrentNb();
//
//  }
//  /**
//   * Permet d'effectuer le rendu simple du lot de droits à enchérir courant sans
//   * prise en compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'un droit à enchérir
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les éléments du lot de droits à enchérir
//    buffer.append(" INITIAL_NB=" + this.getInitialNb());
//    buffer.append(" CURRENT_NB=" + this.getCurrentNb());
//    // Retourne le rendu
//    return buffer;
//  }
//
//  /**
//   * Redéfini la méthode de suppression d'un lien entre un compte utilisateur et
//   * un droit à enchérir afin d'ajouter la suppression du lien inverse
//   * @return {@inheritDoc}
//   * @throws ProtectionException {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see com.bid4win.persistence.entity.account.BidRight#unlinkFromAccount()
//   */
//  @Override
//  public Account unlinkFromAccount() throws ProtectionException, ModelArgumentException
//  {
//    Account account = super.unlinkFromAccount();
//    account.removeBidRightBundle(this);
//    return account;
//  }
//
//  /**
//   * Cette méthode permet d'utiliser un des droits à enchérir du lot
//   * @return Le droit à enchérir utilisé
//   * @throws ProtectionException Si le lot de droits à enchérir courant est protégé
//   * @throws UserException S'il n'y a pas de droits à enchérir dans le lot
//   */
//  public BidRight<?> use() throws ProtectionException, UserException
//  {
//    this.getAccount().useBidRight();
//    UtilNumber.checkMinValue("currentNb", this.getCurrentNb(), 1, true,
//                             MessageRef.ERROR_BID_RIGHT_NB_INSUFFICIENT);
//    try
//    {
//      this.defineCurrentNb(this.getCurrentNb() - 1);
//    }
//    catch(ModelArgumentException ex)
//    {
//      throw new UserException(MessageRef.ERROR_BID_RIGHT_NB_INSUFFICIENT, ex);
//    }
//    return this;
//  }
//  /**
//   * Cette méthode permet d'utiliser un certain nombre des droits à enchérir du lot
//   * @param nb Nombre de droit à enchérir à utiliser
//   * @return La liste de droits à enchérir utilisés
//   * @throws ProtectionException Si le lot de droits à enchérir courant est protégé
//   * @throws UserException S'il n'y a pas assez de droits à enchérir dans le lot
//   */
//  public Bid4WinList<BidRight<?>> use(int nb) throws ProtectionException, UserException
//  {
//    UtilNumber.checkMinValue("currentNb", this.getCurrentNb(), nb, true,
//                             MessageRef.ERROR_BID_RIGHT_NB_INSUFFICIENT);
//    Bid4WinList<BidRight<?>> bidRightList = new Bid4WinList<BidRight<?>>();
//    for(int i = 0 ; i < nb ; i++)
//    {
//      bidRightList.add(this.use());
//    }
//    return bidRightList;
//  }
//
//  /**
//   * Cette méthode permet de définir le nombre initial de droits à enchérir du lot
//   * @param initialNb Définition du nombre initial de droits à enchérir du lot
//   * @throws ProtectionException Si le lot de droits à enchérir courant est protégé
//   * @throws ModelArgumentException Si on définie un nombre initial inférieur à un
//   */
//  private void defineInitialNb(int initialNb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de droits à enchérir courant
//    this.checkProtection();
//    this.setInitialNb(UtilNumber.checkMinValue("initialNb", initialNb, 1, true));
//  }
//  /**
//   * Cette méthode permet de définir le nombre courant de droits à enchérir du lot
//   * @param currentNb Définition du nombre courant de droits à enchérir du lot
//   * @throws ProtectionException Si le lot de droits à enchérir courant est protégé
//   * @throws ModelArgumentException Si on définie un nombre courant inférieur à zéro
//   */
//  private void defineCurrentNb(int currentNb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du lot de droits à enchérir courant
//    this.checkProtection();
//    this.setCurrentNb(UtilNumber.checkMinValue("currentNb", currentNb, 0, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du nombre initial de droits à enchérir du lot
//   * @return Le nombre initial de droits à enchérir du lot
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "INITIAL_NB", length = 3, nullable = false, unique = false)
//  public int getInitialNb()
//  {
//    return this.initialNb;
//  }
//  /**
//   * Setter du nombre initial de droits à enchérir du lot
//   * @param initialNb Nombre initial de droits à enchérir du lot à positionner
//   */
//  private void setInitialNb(int initialNb)
//  {
//    this.initialNb = initialNb;
//  }
//
//  /**
//   * Getter du nombre courant de droits à enchérir du lot
//   * @return Le nombre courant de droits à enchérir du lot
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "CURRENT_NB", length = 3, nullable = false, unique = false)
//  public int getCurrentNb()
//  {
//    return this.currentNb;
//  }
//  /**
//   * Setter du nombre courant de droits à enchérir du lot
//   * @param currentNb Nombre courant de droits à enchérir du lot à positionner
//   */
//  private void setCurrentNb(int currentNb)
//  {
//    this.currentNb = currentNb;
//  }
//}
