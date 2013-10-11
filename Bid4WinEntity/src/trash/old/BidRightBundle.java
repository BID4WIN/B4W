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
// * Cette classe d�fini un lot de droits � ench�rir pour un compte utilisateur<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class BidRightBundle extends BidRight<BidRightBundle>
//{
//  /** Nombre initial de droits � ench�rir du lot */
//  @Transient
//  private int initialNb = 0;
//  /** Nombre courant de droits � ench�rir du lot */
//  @Transient
//  private int currentNb = 0;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private BidRightBundle()
//  {
//    super();
//  }
//
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du lot de droits � ench�rir
//   * @param nb Nombre de droits � ench�rir du lot
//   * @throws ProtectionException Si le comte utilisateur en argument est prot�g�
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   * ou si le nombre de droits � ench�rir est inf�rieur � un
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
//   * Red�fini l'�quivalence interne de deux lots de droits � ench�rir sans prise
//   * en compte de leurs relations afin d'y ajouter le test de leurs donn�es propres
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
//   * Permet d'effectuer le rendu simple du lot de droits � ench�rir courant sans
//   * prise en compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'un droit � ench�rir
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les �l�ments du lot de droits � ench�rir
//    buffer.append(" INITIAL_NB=" + this.getInitialNb());
//    buffer.append(" CURRENT_NB=" + this.getCurrentNb());
//    // Retourne le rendu
//    return buffer;
//  }
//
//  /**
//   * Red�fini la m�thode de suppression d'un lien entre un compte utilisateur et
//   * un droit � ench�rir afin d'ajouter la suppression du lien inverse
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
//   * Cette m�thode permet d'utiliser un des droits � ench�rir du lot
//   * @return Le droit � ench�rir utilis�
//   * @throws ProtectionException Si le lot de droits � ench�rir courant est prot�g�
//   * @throws UserException S'il n'y a pas de droits � ench�rir dans le lot
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
//   * Cette m�thode permet d'utiliser un certain nombre des droits � ench�rir du lot
//   * @param nb Nombre de droit � ench�rir � utiliser
//   * @return La liste de droits � ench�rir utilis�s
//   * @throws ProtectionException Si le lot de droits � ench�rir courant est prot�g�
//   * @throws UserException S'il n'y a pas assez de droits � ench�rir dans le lot
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
//   * Cette m�thode permet de d�finir le nombre initial de droits � ench�rir du lot
//   * @param initialNb D�finition du nombre initial de droits � ench�rir du lot
//   * @throws ProtectionException Si le lot de droits � ench�rir courant est prot�g�
//   * @throws ModelArgumentException Si on d�finie un nombre initial inf�rieur � un
//   */
//  private void defineInitialNb(int initialNb) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection du lot de droits � ench�rir courant
//    this.checkProtection();
//    this.setInitialNb(UtilNumber.checkMinValue("initialNb", initialNb, 1, true));
//  }
//  /**
//   * Cette m�thode permet de d�finir le nombre courant de droits � ench�rir du lot
//   * @param currentNb D�finition du nombre courant de droits � ench�rir du lot
//   * @throws ProtectionException Si le lot de droits � ench�rir courant est prot�g�
//   * @throws ModelArgumentException Si on d�finie un nombre courant inf�rieur � z�ro
//   */
//  private void defineCurrentNb(int currentNb) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection du lot de droits � ench�rir courant
//    this.checkProtection();
//    this.setCurrentNb(UtilNumber.checkMinValue("currentNb", currentNb, 0, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du nombre initial de droits � ench�rir du lot
//   * @return Le nombre initial de droits � ench�rir du lot
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "INITIAL_NB", length = 3, nullable = false, unique = false)
//  public int getInitialNb()
//  {
//    return this.initialNb;
//  }
//  /**
//   * Setter du nombre initial de droits � ench�rir du lot
//   * @param initialNb Nombre initial de droits � ench�rir du lot � positionner
//   */
//  private void setInitialNb(int initialNb)
//  {
//    this.initialNb = initialNb;
//  }
//
//  /**
//   * Getter du nombre courant de droits � ench�rir du lot
//   * @return Le nombre courant de droits � ench�rir du lot
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "CURRENT_NB", length = 3, nullable = false, unique = false)
//  public int getCurrentNb()
//  {
//    return this.currentNb;
//  }
//  /**
//   * Setter du nombre courant de droits � ench�rir du lot
//   * @param currentNb Nombre courant de droits � ench�rir du lot � positionner
//   */
//  private void setCurrentNb(int currentNb)
//  {
//    this.currentNb = currentNb;
//  }
//}
