package com.bid4win.persistence.entity.auction;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.Currency;
import com.bid4win.persistence.entity.product.Product;

/**
 * Cette classe défini une vente aux enchères ayant une map des dernières enchères<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <BID> Définition des enchères associées à la vente<BR>
 * @param <SCHEDULE> Définition les éléments de planification de la vente aux enchères<BR>
 * @param <TERMS> Définition des conditions de la vente aux enchères<BR>
 * @param <CANCEL_POLICY> Définition de la politique d'annulation de la vente aux
 * enchères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
@AttributeOverride(name = "version", column = @Column(length = 5))
public abstract class Auction<CLASS extends Auction<CLASS, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                              BID extends Bid<BID, CLASS, ?>,
                              SCHEDULE extends Schedule<SCHEDULE>,
                              TERMS extends Terms<TERMS>,
                              CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>>
       extends AuctionAbstract<CLASS, BID, SCHEDULE, TERMS, CANCEL_POLICY>
{
  /** Taille maximale de la map des dernières enchères placées sur la vente */
  @Transient
  private int lastBidNbMax = 5;
  /** Map des dernières enchères placées sur la vente */
  @Transient
  private Bid4WinMap<Integer, BID> lastBidMap = new Bid4WinMap<Integer, BID>();

  /**
   * Constructeur pour création par introspection
   */
  protected Auction()
  {
    super();
  }
  /**
   * Constructeur complet de la vente aux enchères
   * @param product Produit vendu aux enchères
   * @param schedule Éléments de planification de la vente aux enchères
   * @param terms Conditions de la vente aux enchères
   * @throws UserException Si le produit, les éléments de planification ou les
   * conditions en argument sont nuls
   */
  protected Auction(Product product, SCHEDULE schedule, TERMS terms) throws UserException
  {
    super(product, schedule, terms);
  }

  /**
   * Ajoute à la liste des noeuds de relations de la vente le lien vers ses enchères
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(Auction_Relations.NODE_LAST_BID_MAP);
    return nodeList;
  }
  /**
   * Permet de récupérer la map des dernières enchères de la vente si elle correspond
   * à la relation en argument.
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected java.util.Map<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
  {
    if(relation.equals(Auction_Relations.RELATION_LAST_BID_MAP))
    {
      return this.getLastBidMapInternal();
    }
    return super.getRelationMap(relation);
  }
  /**
   * Cette méthode permet de récupérer la clé associée à l'entité en paramètre pour
   * sont classement dans la map correspondant à la relation en argument
   * @param relation {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMapKey(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected Object getRelationMapKey(Bid4WinRelation relation, Bid4WinEntity<?, ?> value)
  {
    if(relation.equals(Auction_Relations.RELATION_LAST_BID_MAP))
    {
      return ((BID)value).getPosition();
    }
    return super.getRelationMapKey(relation, value);
  }

  /**
   * Getter du nombre de dernières enchères accessibles sur la vente aux enchères
   * courante
   * @return Le nombre de dernières enchères accessibles sur la vente aux enchères
   * courante
   */
  public int getLastBidNb()
  {
    return this.getLastBidMap().size();
  }
  /**
   * Getter de la Taille maximale de la map des dernières enchères placées sur la
   * vente
   * @return La taille maximale de la map des dernières enchères placées sur la
   * vente
   */
  public int getLastBidNbMax()
  {
    return this.lastBidNbMax;
  }
  /**
   * Getter de l'enchère dont la position absolue est précisée en argument
   * @param absolutePosition Position absolue de l'enchère à retourner
   * @return L'enchère trouvée à la position absolue indiquée en argument
   */
  public BID getBid(int absolutePosition)
  {
    return this.getLastBidMap().get(absolutePosition);
  }
  /**
   * Cette méthode permet de remonter l'enchère remportant la vente
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#getWinningBid()
   */
  @Override
  public BID getWinningBid() throws UserException
  {
    return UtilObject.checkNotNull("lastBid", this.getLastBid(),
                                   AuctionRef.AUCTION_BID_MISSING_ERROR);
  }
  /**
   * Getter de l'enchère dont la position relative par rapport à la dernière enchère
   * de la vente est précisée en argument
   * @param relativePosition Position relative de l'enchère à retourner par rapport
   * à la dernière enchère de la vente
   * @return L'enchère trouvée à la position relative indiquée en argument
   */
  public BID getLastBid(int relativePosition)
  {
    return this.getBid(this.getBidNb() - relativePosition);
  }
  /**
   * Getter de la dernière enchère de la vente
   * @return La dernière enchère de la vente
   */
  public BID getLastBid()
  {
    return this.getLastBid(0);
  }
  /**
   * Getter de la liste des dernières enchères de la vente triées par position
   * décroissante
   * @return La liste des dernières enchères de la vente triées par position
   * décroissante
   */
  public Bid4WinList<BID> getLastBidList()
  {
    return new Bid4WinList<BID>(this.getLastBidMap().values()).sort();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Account getLastBidder()
  {
    if(this.getLastBidNb() == 0)
    {
      return null;
    }
    return this.getLastBid().getAccount();
  }
  /**
   * Cette méthode permet de connaître le montant correspondant à la dernière enchère
   * placée sur la vente dans la monnaie en argument
   * @param currency Monnaie dans laquelle remonter le montant correspondant à la
   * dernière enchère placée sur la vente
   * @return Le montant correspondant à la dernière enchère placée sur la vente
   * dans la monnaie en argument
   * @throws UserException TODO A COMMENTER
   */
  public Amount getCurrentBidValue(Currency currency) throws UserException
  {
    return this.getBidValue(currency, this.getBidNb());
  }
  /**
   * Cette méthode permet de connaître le montant correspondant à la prochaine enchère
   * à placer sur la vente dans la monnaie en argument
   * @param currency Monnaie dans laquelle remonter le montant correspondant à la
   * prochaine enchère à placer sur la vente
   * @return Le montant correspondant à la prochaine enchère à placer sur la vente
   * dans la monnaie en argument
   * @throws UserException TODO A COMMENTER
   */
  public Amount getNextBidValue(Currency currency) throws UserException
  {
    return this.getBidValue(currency, this.getBidNb() + 1);
  }
  /**
   * Cette méthode permet de connaître le montant correspondant à l'enchère placée
   * sur la vente à la position choisie dans la monnaie en argument
   * @param currency Monnaie dans laquelle remonter le montant correspondant à l'
   * enchère placée sur la vente à la position choisie
   * @param position Position dans la vente pour de l'enchère pour laquelle calculer
   * le montant
   * @return Le montant correspondant à l'enchère placée sur la vente à la position
   * choisie
   * @throws UserException Si la position en argument est négative
   */
  public Amount getBidValue(Currency currency, int position) throws UserException
  {
    if(currency == null)
    {
      currency = Currency.DEFAULT;
    }
    Amount value = this.getTerms().getBidIncrement().multiply(position);
    return this.getExchangeRates().changeTo(value, currency);
  }
  /**
   *
   * TODO A COMMENTER
   * @param bidValue TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public int getBidPosition(Amount bidValue) throws UserException
  {
    UtilObject.checkNotNull("bidValue", bidValue, CurrencyRef.CURRENCY_AMOUNT_MISSING_ERROR);
    bidValue = this.getExchangeRates().changeTo(bidValue, Currency.DEFAULT);
    return (int)(bidValue.getValue() / this.getTerms().getBidIncrement().getValue());
  }


  /**
   * Redéfini la méthode d'ajout d'enchère afin de recalculer la date de fin prévue
   * @param account {@inheritDoc}
   * @param bidDate {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#addBid(com.bid4win.persistence.entity.account.Account, com.bid4win.commons.core.Bid4WinDate)
   */
  @Override
  public BID addBid(Account account, Bid4WinDate bidDate) throws ProtectionException, UserException
  {
    BID bid = super.addBid(account, bidDate);
    // Défini la nouvelle date de fin
    this.getSchedule().defineNewEndDate(bidDate);
    return bid;
  }
  /**
   * Cette méthode permet de vérifier que le compte utilisateur en argument n'est
   * pas nul et ne correspond pas au dernier enchérisseur
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc} ou est déjà le dernier enchérisseur
   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#checkBidder(com.bid4win.persistence.entity.account.Account)
   */
  @Override
  protected Account checkBidder(Account account) throws UserException
  {
    // Vérifie que le compte utilisateur de l'enchère à ajouter n'est pas nul
    super.checkBidder(account);
    // Vérifie que le compte utilisateur n'est pas déjà le dernier enchérisseur
    if(this.getBidNb() != 0)
    {
      UtilObject.checkDiffers("accountId", account.getId(), this.getLastBidder().getId(),
                              AuctionRef.AUCTION_BID_DEFINED_ERROR);
    }
    return account;
  }
  /**
   * Getter de la map des dernières enchères placées sur la vente
   * @return La map des dernières enchères placées sur la vente
   */
  private Bid4WinMap<Integer, BID> getLastBidMap()
  {
    return this.lastBidMap;
  }
  /**
   * Setter de la map des dernières enchères placées sur la vente
   * @param lastBidMap Map des dernières enchères placées sur la vente à positionner
   */
  private void setLastBidMap(Bid4WinMap<Integer, BID> lastBidMap)
  {
    this.lastBidMap = lastBidMap;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map interne des dernières enchères placées sur la vente
   * @return La map interne des dernières enchères placées sur la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "auctionLink", fetch = FetchType.LAZY, cascade = {})
  @MapKey(name = "position")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  private Map<Integer, BID> getLastBidMapInternal()
  {
    return this.getLastBidMap().getInternal();
  }
  /**
   * Setter de la map interne des dernières enchères placées sur la vente
   * @param internalLastBidMap Map interne des dernières enchères à positionner
   * sur la vente
   */
  @SuppressWarnings(value = "unused")
  private void setLastBidMapInternal(Map<Integer, BID> internalLastBidMap)
  {
    this.setLastBidMap(new Bid4WinMap<Integer, BID>(internalLastBidMap, true));
  }
}
