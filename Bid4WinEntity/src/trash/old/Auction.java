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
// * Cette classe défini une vente aux enchères<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <BID> Définition des enchères associées à la vente<BR>
// * @param <SCHEDULE> Définition les éléments de planification de la vente aux enchères<BR>
// * @param <TERMS> Définition des conditions de la vente aux enchères<BR>
// * <BR>
// * @author Emeric Fillâtre
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
//  /** Taille maximale de la map des dernières enchères placées sur la vente */
//  @Transient
//  private int lastBidNbMax = 10;
//  /** Map des dernières enchères placées sur la vente */
//  @Transient
//  private Bid4WinMap<Integer, BID> lastBidMap = new Bid4WinMap<Integer, BID>();
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected Auction()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet de la vente aux enchères
//   * @param product Produit vendu aux enchères
//   * @param schedule Éléments de planification de la vente aux enchères
//   * @param terms Conditions de la vente aux enchères
//   * @throws ModelArgumentException  Si le produit, les éléments de planification
//   * ou les conditions en argument sont nuls
//   */
//  protected Auction(Product product, SCHEDULE schedule, TERMS terms) throws ModelArgumentException
//  {
//    super(product, schedule, terms);
//  }
//  /**
//   * Constructeur complet de la vente aux enchères avec précision de l'identifiant
//   * @param id Identifiant de la vente aux enchères
//   * @param product Produit vendu aux enchères
//   * @param schedule Éléments de planification de la vente aux enchères
//   * @param terms Conditions de la vente aux enchères
//   * @throws ModelArgumentException Si l'identifiant, le produit, les éléments de
//   * planification ou les conditions en argument sont nuls nul
//   */
//  protected Auction(String id, Product product, SCHEDULE schedule, TERMS terms)
//            throws ModelArgumentException
//  {
//    super(id, product, schedule, terms);
//  }
//
//  /**
//   * Ajoute à la liste des noeuds de relations de la vente le lien vers ses enchères
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
//   * Permet de récupérer la map des dernières enchères de la vente si elle correspond
//   * à la relation en argument.
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
//   * Getter du nombre de dernières enchères accessibles sur la vente aux enchères
//   * courante
//   * @return Le nombre de dernières enchères accessibles sur la vente aux enchères
//   * courante
//   */
//  public int getLastBidNb()
//  {
//    return this.getLastBidMap().size();
//  }
//  /**
//   * Getter de la Taille maximale de la map des dernières enchères placées sur la
//   * vente
//   * @return La taille maximale de la map des dernières enchères placées sur la
//   * vente
//   */
//  public int getLastBidNbMax()
//  {
//    return this.lastBidNbMax;
//  }
//  /**
//   * Getter de l'enchère dont la position absolue est précisée en argument
//   * @param absolutePosition Position absolue de l'enchère à retourner
//   * @return L'enchère trouvée à la position absolue indiquée en argument
//   */
//  public BID getBid(int absolutePosition)
//  {
//    return this.getLastBidMap().get(absolutePosition);
//  }
//  /**
//   * Getter de l'enchère dont la position relative par rapport à la dernière enchère
//   * de la vente est précisée en argument
//   * @param relativePosition Position relative de l'enchère à retourner par rapport
//   * à la dernière enchère de la vente
//   * @return L'enchère trouvée à la position relative indiquée en argument
//   */
//  public BID getLastBid(int relativePosition)
//  {
//    return this.getBid(this.getBidNb() - relativePosition);
//  }
//  /**
//   * Getter de la dernière enchère de la vente
//   * @return La dernière enchère de la vente
//   */
//  public BID getLastBid()
//  {
//    return this.getLastBid(0);
//  }
//  /**
//   * Getter de la liste des dernières enchères de la vente triées par position
//   * décroissante
//   * @return La liste des dernières enchères de la vente triées par position
//   * décroissante
//   */
//  public Bid4WinList<BID> getLastBidList()
//  {
//    return new Bid4WinList<BID>(this.getLastBidMap().values()).sort();
//  }
//
//  /**
//   * Cette fonction permet d'ajouter une enchère à la vente courante
//   * @param account Compte utilisateur de l'enchère à ajouter à la vente courante
//   * @return L'enchère ajoutée à la vente courante
//   * @throws ProtectionException Si la vente courante est protégée
//   * @throws ModelArgumentException Si le comte utilisateur en argument est nul
//   * @throws UserException Si le comte utilisateur est déjà le dernier enchérisseur
//   */
//  public BID addBid(Account account)
//         throws ProtectionException, ModelArgumentException, UserException
//  {
//    // Vérifie la vente aux enchères courante
//    this.checkProtection();
//    // Calcule la position absolue de la nouvelle enchère dans la vente
//    int position = this.getBidNb() + 1;
//    // Crée l'enchère et l'ajoute à la vente
//    BID bid = this.createBid(this.checkBidder(account), position);
//    // Augmente le nombre d'enchères de la vente
//    this.defineBidNb(position);
//    return bid;
//  }
//  /**
//   * Cette méthode permet de vérifier que le compte utilisateur en argument n'est
//   * pas nul et ne correspond pas au dernier enchérisseur
//   * @param account Compte utilisateur à vérifier
//   * @return Le compte utilisateur vérifié
//   * @throws ModelArgumentException Si le comte utilisateur en argument est nul
//   * @throws UserException Si le comte utilisateur est déjà le dernier enchérisseur
//   */
//  protected Account checkBidder(Account account) throws ModelArgumentException, UserException
//  {
//    // Vérifie que le compte utilisateur de l'enchère à ajouter n'est pas nul
//    UtilObject.checkNotNull("account", account);
//    // Vérifie que le compte utilisateur n'est pas déjà le dernier enchérisseur
//    if(this.getBidNb() != 0)
//    {
//      UtilObject.checkDiffers("accountId", account.getId(),
//                              this.getLastBid().getAccount().getId(),
//                              MessageRef.UNKNOWN_ERROR);
//    }
//    return account;
//  }
//  /**
//   * Cette fonction permet d'ajouter l'enchère en argument
//   * @param bid Enchère à ajouter de la vente courante
//   * @throws ProtectionException Si l'enchère courante est protégée
//   * @throws ModelArgumentException Si l'enchère à ajouter est nulle ou sa position
//   * déjà utilisée
//   */
//  protected void putBid(BID bid) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la vente aux enchères courante
//    this.checkProtection();
//    // Vérifie que l'enchère en argument n'est pas nulle
//    UtilObject.checkNotNull("bid", bid);
//    // Vérifie que la vente courante ne contient pas déjà d'enchère à la même position
//    UtilBoolean.checkFalse("containsKey", this.getLastBidMap().containsKey(bid.getPosition()));
//    this.getLastBidMap().put(bid.getPosition(), bid);
//  }
//  /**
//   * Cette fonction permet de retirer l'enchère en argument
//   * @param bid Enchère à retirer de la vente courante
//   * @throws ProtectionException Si l'enchère courante est protégée
//   * @throws ModelArgumentException Si l'enchère à retirer ne peut être trouvée
//   */
//  protected void removeBid(BID bid) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la vente aux enchères courante
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
//   * Getter de la map des dernières enchères placées sur la vente
//   * @return La map des dernières enchères placées sur la vente
//   */
//  private Bid4WinMap<Integer, BID> getLastBidMap()
//  {
//    return this.lastBidMap;
//  }
//  /**
//   * Setter de la map des dernières enchères placées sur la vente
//   * @param lastBidMap Map des dernières enchères placées sur la vente à positionner
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
//   * Getter de la map interne des dernières enchères placées sur la vente
//   * @return La map interne des dernières enchères placées sur la vente
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @OneToMany(mappedBy = "auctionLink", fetch = FetchType.LAZY, cascade = {})
//  @MapKey(name = "position")
//  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
//  @OptimisticLock(excluded = false)
//  private Map<Integer, BID> getLastBidMapInternal()
//  {
//    return this.getLastBidMap().getInternal();
//  }
//  /**
//   * Setter de la map interne des dernières enchères placées sur la vente
//   * @param internalLastBidMap Map interne des dernières enchères à positionner
//   * sur la vente
//   */
//  @SuppressWarnings(value = "unused")
//  private void setLastBidMapInternal(Map<Integer, BID> internalLastBidMap)
//  {
//    this.setLastBidMap(new Bid4WinMap<Integer, BID>(internalLastBidMap, true));
//  }
//}
