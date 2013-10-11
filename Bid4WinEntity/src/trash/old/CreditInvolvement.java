package trash.old;
//package com.bid4win.persistence.entity.account.credit.old;
//
//import java.util.Map.Entry;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef.AccountRef;
//import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
//import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.commons.persistence.entity.account.AccountBasedEntity_Relations;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.account.Account_Relations;
//import com.bid4win.persistence.entity.auction.Auction;
//import com.bid4win.persistence.entity.auction.normal.NormalAuction;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class CreditInvolvement extends CreditInvolvementAbstract<CreditInvolvement, CreditUsage>
//{
//  /** Vente aux ench�res normale sur laquelle sont potentiellement impliqu�s les cr�dits */
//  @Transient
//  private NormalAuction normalAuction = null;
//  /** Vente aux ench�res avec pr�-inscription sur laquelle sont potentiellement impliqu�s les cr�dits */
//  @Transient
//  private PrebookedAuction prebookedAuction = null;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected CreditInvolvement()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des cr�dits utilis�s
//   * @param involvement Type d'implication des cr�dits
//   * @throws ProtectionException Si le compte utilisateur en argument est prot�g�
//   * @throws UserException Si le compte utilisateur ou le type d'implication est nul
//   */
//  private CreditInvolvement(Account account, Involvement involvement)
//          throws ProtectionException, UserException
//  {
//    super(account, involvement);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param account Compte utilisateur des cr�dits utilis�s
//   * @param auction Vente aux ench�res d'implication des cr�dits
//   * @throws ProtectionException Si le compte utilisateur en argument est prot�g�
//   * @throws UserException  Si le compte utilisateur ou la vente aux ench�res est nul
//   */
//  public CreditInvolvement(Account account, Auction<?, ?, ?, ?> auction)
//         throws ProtectionException, UserException
//  {
//    this(account, (auction == null ? null :
//                   Involvement.getInvolvement(auction.getType())));
//    try
//    {
//      this.linkToInvolvement(auction);
//    }
//    catch(UserException ex)
//    {
//      this.unlinkFromAccount();
//      throw ex;
//    }
//  }
//
//  /**
//   * Ajoute � la liste des noeuds de relations de l'implication de cr�dits le lien
//   * vers sa potentielle vente aux ench�res si tel est le cas
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvementAbstract#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    if(this.getInvolvement().equals(Involvement.AUCTION_NORMAL))
//    {
//      nodeList.add(CreditInvolvement_Relations.NODE_NORMAL_AUCTION);
//    }
//    else if(this.getInvolvement().equals(Involvement.AUCTION_PREBOOKED))
//    {
//      nodeList.add(CreditInvolvement_Relations.NODE_PREBOOKED_AUCTION);
//    }
//    return nodeList;
//  }
//  /**
//   * Permet de r�cup�rer la potentielle vente aux ench�res de l'implication de
//   * cr�dits si elle correspond � la relation en argument. Elle doit �tre red�finie
//   * pour toute nouvelle relation de type simple � remonter
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
//  {
//    if(relation.equals(CreditInvolvement_Relations.NODE_NORMAL_AUCTION))
//    {
//      return this.getNormalAuction();
//    }
//    else if(relation.equals(CreditInvolvement_Relations.NODE_PREBOOKED_AUCTION))
//    {
//      return this.getPrebookedAuction();
//    }
//    return super.getRelationSimple(relation);
//  }
//  /**
//   * Permet de positionner la potentielle vente aux ench�res de l'implication de
//   * cr�dits si elle correspondant � la relation en argument. Elle doit �tre red�finie
//   * pour toute nouvelle relation de type simple � positionner
//   * @param relation {@inheritDoc}
//   * @param entity {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
//   */
//  @Override
//  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
//  {
//    if(relation.equals(CreditInvolvement_Relations.NODE_NORMAL_AUCTION))
//    {
//      this.setNormalAuction((NormalAuction)entity);
//    }
//    else if(relation.equals(CreditInvolvement_Relations.NODE_PREBOOKED_AUCTION))
//    {
//      this.setPrebookedAuction((PrebookedAuction)entity);
//    }
//    else
//    {
//      super.setRelationSimple(relation, entity);
//    }
//  }
//
//  /**
//   * Cette m�thode permet d'ajouter � l'implication de cr�dits courante des cr�dits
//   * issus des lots d�finis en argument
//   * @param usageMap Map des lots de provenance et nombres des cr�dits utilis�s
//   * @return Le set d'utilisations de tous le cr�dits des lots en argument par l'
//   * implication  courante
//   * @throws ProtectionException Si l'implication de cr�dits courante ou une des
//   * utilisations de cr�dits est prot�g�e
//   * @throws UserException Si l'un des lots de cr�dits est nul ou si son compte
//   * utilisateur ne correspond pas au compte utilisateur de l'implication courante
//   * ou si un des nombres de cr�dits utilis�s associ� est inf�rieur � un
//   */
//  public Bid4WinSet<CreditUsage> addUsage(Bid4WinMap<CreditBundle, Integer> usageMap)
//        throws ProtectionException, UserException
//  {
//    Bid4WinMap<String, CreditUsage> resultMap = new Bid4WinMap<String, CreditUsage>();
//    for(Entry<CreditBundle, Integer> entry : usageMap.entrySet())
//    {
//      CreditUsage bundleUsage = this.addUsage(entry.getKey(), entry.getValue());
//      resultMap.put(bundleUsage.getReference(), bundleUsage);
//    }
//    return new Bid4WinSet<CreditUsage>(resultMap.values());
//  }
//  /**
//   * Cette m�thode permet d'ajouter � l'implication de cr�dits courante des cr�dits
//   * issus du lot d�fini en argument
//   * @param bundle Lot de provenance des cr�dits utilis�s
//   * @param usedNb Nombre de cr�dit utilis�s
//   * @return L'utilisation de tous le cr�dits du lot en argument par l'implication
//   * courante
//   * @throws ProtectionException Si l'implication de cr�dits courante ou une des
//   * utilisations de cr�dits est prot�g�e
//   * @throws UserException Si le lot de cr�dits est nul ou si son compte utilisateur
//   * ne correspond pas au compte utilisateur de l'implication courante ou si le
//   * nombre de cr�dits utilis�s est inf�rieur � un
//   */
//  public CreditUsage addUsage(CreditBundle bundle, int usedNb)
//         throws ProtectionException, UserException
//  {
//    // V�rifie la provenance des cr�dits utilis�s
//    UtilObject.checkNotNull("bundle", bundle, AccountRef.ACCOUNT_CREDIT_REFERENCE_MISSING_ERROR);
//    UtilObject.checkEquals("accountId", bundle.getAccount().getId(),
//                           this.getAccount().getId(),
//                           ConnectionRef.CONNECTION_PERMISSION_NOT_GRANTED_ERROR);
//    // Ajoute et retourne l'utilisation des cr�dits du lot en argument
//    return this.addUsage(bundle.getOrigin(), usedNb);
//  }
//  /**
//   * Cette m�thode permet d'ajouter � l'utilisation de cr�dits courante des cr�dits
//   * du lot dont la provenance est d�finie en argument
//   * @param origin Provenance des cr�dits � ajouter aux cr�dits utilis�s
//   * @param usedNb Nombre de cr�dits utilis�s � ajouter
//   * @return L'utilisation de tous le cr�dits du lot d�fini en argument
//   * @throws ProtectionException Si l'utilisation de cr�dits courante ou une de
//   * ses utilisations de lot de cr�dits est prot�g�e
//   * @throws UserException Si la provenance en argument est nulle ou le nombre de
//   * cr�dits utilis�s est inf�rieur � un
//   */
//  private CreditUsage addUsage(CreditOrigin origin, int usedNb)
//          throws ProtectionException, UserException
//  {
//    // V�rifie la protection de l'utilisation de cr�dits courante
//    this.checkProtection();
//    // Recherche une utilisation du m�me lot d�j� r�f�renc�e
//    CreditUsage bundleUsage = this.getUsage(origin);
//    // Augmente l'utilisation du lot d�j� r�f�renc�
//    if(bundleUsage != null)
//    {
//      bundleUsage.addUsedNb(usedNb);
//    }
//    // Ajoute une utilisation du lot en argument
//    else
//    {
//      bundleUsage = new CreditUsage(this, origin, usedNb);
//    }
//    // Retourne l'utilisation des cr�dits du lot en argument
//    return bundleUsage;
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param account {@inheritDoc}
//   * @throws ProtectionException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#linkToAccount(com.bid4win.commons.persistence.entity.account.AccountAbstract)
//   */
//  @Override
//  protected void linkToAccount(Account account) throws ProtectionException, UserException
//  {
//    this.linkTo(AccountBasedEntity_Relations.RELATION_ACCOUNT,
//                Account_Relations.RELATION_INVOLVEMENT_SET,
//                account);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @throws ProtectionException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#unlinkFromAccount()
//   */
//  @Override
//  public Account unlinkFromAccount() throws ProtectionException, UserException
//  {
//    return (Account)this.unlinkFrom(AccountBasedEntity_Relations.RELATION_ACCOUNT,
//                                    Account_Relations.RELATION_INVOLVEMENT_SET);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param entity TODO A COMMENTER
//   * @throws ProtectionException TODO A COMMENTER
//   * @throws UserException TODO A COMMENTER
//   */
//  private void linkToInvolvement(Bid4WinEntity<?, ?> entity) throws ProtectionException, UserException
//  {
//    if(this.getInvolvement().equals(Involvement.AUCTION_NORMAL))
//    {
//      this.linkTo(CreditInvolvement_Relations.RELATION_NORMAL_AUCTION, entity);
//    }
//    else if(this.getInvolvement().equals(Involvement.AUCTION_PREBOOKED))
//    {
//      this.linkTo(CreditInvolvement_Relations.RELATION_PREBOOKED_AUCTION, entity);
//    }
//    else
//    {
//      throw new UserException(AuctionRef.AUCTION_INVALID_ERROR);
//    }
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de la vente aux ench�res normale sur laquelle sont potentiellement
//   * impliqu�s les cr�dits
//   * @return La vente aux ench�res normale sur laquelle sont potentiellement
//   * impliqu�s les cr�dits
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
//  @JoinColumn(name = "NORMAL_ID", nullable = true, unique = false)
//  public NormalAuction getNormalAuction()
//  {
//    return this.normalAuction;
//  }
//  /**
//   * Setter de la vente aux ench�res normale sur laquelle sont potentiellement
//   * impliqu�s les cr�dits
//   * @param normalAuction Vente aux ench�res normale sur laquelle sont potentiellement
//   * impliqu�s les cr�dits � positionner
//   */
//  private void setNormalAuction(NormalAuction normalAuction)
//  {
//    this.normalAuction = normalAuction;
//  }
//
//  /**
//   * Getter de la vente aux ench�res avec pr�-inscription sur laquelle sont potentiellement
//   * impliqu�s les cr�dits
//   * @return La vente aux ench�res avec pr�-inscription sur laquelle sont potentiellement
//   * impliqu�s les cr�dits
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
//  @JoinColumn(name = "PREBOOKED_ID", nullable = true, unique = false)
//  public PrebookedAuction getPrebookedAuction()
//  {
//    return this.prebookedAuction;
//  }
//  /**
//   * Setter de la vente aux ench�res avec pr�-inscription sur laquelle sont potentiellement
//   * impliqu�s les cr�dits
//   * @param prebookedAuction Vente aux ench�res avec pr�-inscription sur laquelle
//   * sont potentiellement impliqu�s les cr�dits � positionner
//   */
//  private void setPrebookedAuction(PrebookedAuction prebookedAuction)
//  {
//    this.prebookedAuction = prebookedAuction;
//  }
//}
