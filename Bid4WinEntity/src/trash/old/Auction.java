package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import java.util.Map;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.AttributeOverride;
//import javax.persistence.Column;
//import javax.persistence.FetchType;
//import javax.persistence.MapKey;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.OneToMany;
//import javax.persistence.Transient;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//import org.hibernate.annotations.OptimisticLock;
//
//import com.bid4win.commons.core.UtilBoolean;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.Auction_Relations;
//import com.bid4win.persistence.entity.product.Product;
//
///**
// * Cette classe d�fini une vente aux ench�res<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * @param <BID> D�finition des ench�res associ�es � la vente<BR>
// * @param <SCHEDULE> D�finition les �l�ments de planification de la vente aux ench�res<BR>
// * @param <TERMS> D�finition des conditions de la vente aux ench�res<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//@AttributeOverride(name = "version", column = @Column(length = 5))
//public abstract class Auction<CLASS extends Auction<CLASS, BID, SCHEDULE, TERMS>,
//                              BID extends Bid<BID, CLASS>,
//                              SCHEDULE extends Schedule<SCHEDULE>,
//                              TERMS extends Terms<TERMS>>
//       extends AuctionAbstract<CLASS, BID, SCHEDULE, TERMS>
//{
//  /** Taille maximale de la map des derni�res ench�res plac�es sur la vente */
//  @Transient
//  private int lastBidNbMax = 10;
//  /** Map des derni�res ench�res plac�es sur la vente */
//  @Transient
//  private Bid4WinMap<Integer, BID> lastBidMap = new Bid4WinMap<Integer, BID>();
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected Auction()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet de la vente aux ench�res
//   * @param product Produit vendu aux ench�res
//   * @param schedule �l�ments de planification de la vente aux ench�res
//   * @param terms Conditions de la vente aux ench�res
//   * @throws ModelArgumentException  Si le produit, les �l�ments de planification
//   * ou les conditions en argument sont nuls
//   */
//  protected Auction(Product product, SCHEDULE schedule, TERMS terms) throws ModelArgumentException
//  {
//    super(product, schedule, terms);
//  }
//  /**
//   * Constructeur complet de la vente aux ench�res avec pr�cision de l'identifiant
//   * @param id Identifiant de la vente aux ench�res
//   * @param product Produit vendu aux ench�res
//   * @param schedule �l�ments de planification de la vente aux ench�res
//   * @param terms Conditions de la vente aux ench�res
//   * @throws ModelArgumentException Si l'identifiant, le produit, les �l�ments de
//   * planification ou les conditions en argument sont nuls nul
//   */
//  protected Auction(String id, Product product, SCHEDULE schedule, TERMS terms)
//            throws ModelArgumentException
//  {
//    super(id, product, schedule, terms);
//  }
//
//  /**
//   * Ajoute � la liste des noeuds de relations de la vente le lien vers ses ench�res
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(Auction_Relations.NODE_LAST_BID_MAP);
//    return nodeList;
//  }
//  /**
//   * Permet de r�cup�rer la map des derni�res ench�res de la vente si elle correspond
//   * � la relation en argument.
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected java.util.Map<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
//  {
//    if(relation.equals(Auction_Relations.RELATION_LAST_BID_MAP))
//    {
//      return this.getLastBidMapInternal();
//    }
//    return super.getRelationMap(relation);
//  }
//
//  /**
//   * Getter du nombre de derni�res ench�res accessibles sur la vente aux ench�res
//   * courante
//   * @return Le nombre de derni�res ench�res accessibles sur la vente aux ench�res
//   * courante
//   */
//  public int getLastBidNb()
//  {
//    return this.getLastBidMap().size();
//  }
//  /**
//   * Getter de la Taille maximale de la map des derni�res ench�res plac�es sur la
//   * vente
//   * @return La taille maximale de la map des derni�res ench�res plac�es sur la
//   * vente
//   */
//  public int getLastBidNbMax()
//  {
//    return this.lastBidNbMax;
//  }
//  /**
//   * Getter de l'ench�re dont la position absolue est pr�cis�e en argument
//   * @param absolutePosition Position absolue de l'ench�re � retourner
//   * @return L'ench�re trouv�e � la position absolue indiqu�e en argument
//   */
//  public BID getBid(int absolutePosition)
//  {
//    return this.getLastBidMap().get(absolutePosition);
//  }
//  /**
//   * Getter de l'ench�re dont la position relative par rapport � la derni�re ench�re
//   * de la vente est pr�cis�e en argument
//   * @param relativePosition Position relative de l'ench�re � retourner par rapport
//   * � la derni�re ench�re de la vente
//   * @return L'ench�re trouv�e � la position relative indiqu�e en argument
//   */
//  public BID getLastBid(int relativePosition)
//  {
//    return this.getBid(this.getBidNb() - relativePosition);
//  }
//  /**
//   * Getter de la derni�re ench�re de la vente
//   * @return La derni�re ench�re de la vente
//   */
//  public BID getLastBid()
//  {
//    return this.getLastBid(0);
//  }
//  /**
//   * Getter de la liste des derni�res ench�res de la vente tri�es par position
//   * d�croissante
//   * @return La liste des derni�res ench�res de la vente tri�es par position
//   * d�croissante
//   */
//  public Bid4WinList<BID> getLastBidList()
//  {
//    return new Bid4WinList<BID>(this.getLastBidMap().values()).sort();
//  }
//
//  /**
//   * Cette fonction permet d'ajouter une ench�re � la vente courante
//   * @param account Compte utilisateur de l'ench�re � ajouter � la vente courante
//   * @return L'ench�re ajout�e � la vente courante
//   * @throws ProtectionException Si la vente courante est prot�g�e
//   * @throws ModelArgumentException Si le comte utilisateur en argument est nul
//   * @throws UserException Si le comte utilisateur est d�j� le dernier ench�risseur
//   */
//  public BID addBid(Account account)
//         throws ProtectionException, ModelArgumentException, UserException
//  {
//    // V�rifie la vente aux ench�res courante
//    this.checkProtection();
//    // Calcule la position absolue de la nouvelle ench�re dans la vente
//    int position = this.getBidNb() + 1;
//    // Cr�e l'ench�re et l'ajoute � la vente
//    BID bid = this.createBid(this.checkBidder(account), position);
//    // Augmente le nombre d'ench�res de la vente
//    this.defineBidNb(position);
//    return bid;
//  }
//  /**
//   * Cette m�thode permet de v�rifier que le compte utilisateur en argument n'est
//   * pas nul et ne correspond pas au dernier ench�risseur
//   * @param account Compte utilisateur � v�rifier
//   * @return Le compte utilisateur v�rifi�
//   * @throws ModelArgumentException Si le comte utilisateur en argument est nul
//   * @throws UserException Si le comte utilisateur est d�j� le dernier ench�risseur
//   */
//  protected Account checkBidder(Account account) throws ModelArgumentException, UserException
//  {
//    // V�rifie que le compte utilisateur de l'ench�re � ajouter n'est pas nul
//    UtilObject.checkNotNull("account", account);
//    // V�rifie que le compte utilisateur n'est pas d�j� le dernier ench�risseur
//    if(this.getBidNb() != 0)
//    {
//      UtilObject.checkDiffers("accountId", account.getId(),
//                              this.getLastBid().getAccount().getId(),
//                              MessageRef.UNKNOWN_ERROR);
//    }
//    return account;
//  }
//  /**
//   * Cette fonction permet d'ajouter l'ench�re en argument
//   * @param bid Ench�re � ajouter de la vente courante
//   * @throws ProtectionException Si l'ench�re courante est prot�g�e
//   * @throws ModelArgumentException Si l'ench�re � ajouter est nulle ou sa position
//   * d�j� utilis�e
//   */
//  protected void putBid(BID bid) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la vente aux ench�res courante
//    this.checkProtection();
//    // V�rifie que l'ench�re en argument n'est pas nulle
//    UtilObject.checkNotNull("bid", bid);
//    // V�rifie que la vente courante ne contient pas d�j� d'ench�re � la m�me position
//    UtilBoolean.checkFalse("containsKey", this.getLastBidMap().containsKey(bid.getPosition()));
//    this.getLastBidMap().put(bid.getPosition(), bid);
//  }
//  /**
//   * Cette fonction permet de retirer l'ench�re en argument
//   * @param bid Ench�re � retirer de la vente courante
//   * @throws ProtectionException Si l'ench�re courante est prot�g�e
//   * @throws ModelArgumentException Si l'ench�re � retirer ne peut �tre trouv�e
//   */
//  protected void removeBid(BID bid) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la vente aux ench�res courante
//    this.checkProtection();
//    UtilObject.checkNotNull("remove", this.getLastBidMap().remove(bid.getPosition()));
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param account TODO A COMMENTER
//   * @param position TODO A COMMENTER
//   * @return TODO A COMMENTER
//   * @throws ProtectionException TODO A COMMENTER
//   * @throws ModelArgumentException TODO A COMMENTER
//   * @throws UserException TODO A COMMENTER
//   */
//  protected abstract BID createBid(Account account, int position)
//            throws ProtectionException, ModelArgumentException, UserException;
//  /**
//   * Getter de la map des derni�res ench�res plac�es sur la vente
//   * @return La map des derni�res ench�res plac�es sur la vente
//   */
//  private Bid4WinMap<Integer, BID> getLastBidMap()
//  {
//    return this.lastBidMap;
//  }
//  /**
//   * Setter de la map des derni�res ench�res plac�es sur la vente
//   * @param lastBidMap Map des derni�res ench�res plac�es sur la vente � positionner
//   */
//  private void setLastBidMap(Bid4WinMap<Integer, BID> lastBidMap)
//  {
//    this.lastBidMap = lastBidMap;
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de la map interne des derni�res ench�res plac�es sur la vente
//   * @return La map interne des derni�res ench�res plac�es sur la vente
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @OneToMany(mappedBy = "auctionLink", fetch = FetchType.LAZY, cascade = {})
//  @MapKey(name = "position")
//  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
//  @OptimisticLock(excluded = false)
//  private Map<Integer, BID> getLastBidMapInternal()
//  {
//    return this.getLastBidMap().getInternal();
//  }
//  /**
//   * Setter de la map interne des derni�res ench�res plac�es sur la vente
//   * @param internalLastBidMap Map interne des derni�res ench�res � positionner
//   * sur la vente
//   */
//  @SuppressWarnings(value = "unused")
//  private void setLastBidMapInternal(Map<Integer, BID> internalLastBidMap)
//  {
//    this.setLastBidMap(new Bid4WinMap<Integer, BID>(internalLastBidMap, true));
//  }
//}
