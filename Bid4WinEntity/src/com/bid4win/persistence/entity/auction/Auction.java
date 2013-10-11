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
 * Cette classe d�fini une vente aux ench�res ayant une map des derni�res ench�res<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <BID> D�finition des ench�res associ�es � la vente<BR>
 * @param <SCHEDULE> D�finition les �l�ments de planification de la vente aux ench�res<BR>
 * @param <TERMS> D�finition des conditions de la vente aux ench�res<BR>
 * @param <CANCEL_POLICY> D�finition de la politique d'annulation de la vente aux
 * ench�res<BR>
 * <BR>
 * @author Emeric Fill�tre
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
  /** Taille maximale de la map des derni�res ench�res plac�es sur la vente */
  @Transient
  private int lastBidNbMax = 5;
  /** Map des derni�res ench�res plac�es sur la vente */
  @Transient
  private Bid4WinMap<Integer, BID> lastBidMap = new Bid4WinMap<Integer, BID>();

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Auction()
  {
    super();
  }
  /**
   * Constructeur complet de la vente aux ench�res
   * @param product Produit vendu aux ench�res
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @throws UserException Si le produit, les �l�ments de planification ou les
   * conditions en argument sont nuls
   */
  protected Auction(Product product, SCHEDULE schedule, TERMS terms) throws UserException
  {
    super(product, schedule, terms);
  }

  /**
   * Ajoute � la liste des noeuds de relations de la vente le lien vers ses ench�res
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
   * Permet de r�cup�rer la map des derni�res ench�res de la vente si elle correspond
   * � la relation en argument.
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
   * Cette m�thode permet de r�cup�rer la cl� associ�e � l'entit� en param�tre pour
   * sont classement dans la map correspondant � la relation en argument
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
   * Getter du nombre de derni�res ench�res accessibles sur la vente aux ench�res
   * courante
   * @return Le nombre de derni�res ench�res accessibles sur la vente aux ench�res
   * courante
   */
  public int getLastBidNb()
  {
    return this.getLastBidMap().size();
  }
  /**
   * Getter de la Taille maximale de la map des derni�res ench�res plac�es sur la
   * vente
   * @return La taille maximale de la map des derni�res ench�res plac�es sur la
   * vente
   */
  public int getLastBidNbMax()
  {
    return this.lastBidNbMax;
  }
  /**
   * Getter de l'ench�re dont la position absolue est pr�cis�e en argument
   * @param absolutePosition Position absolue de l'ench�re � retourner
   * @return L'ench�re trouv�e � la position absolue indiqu�e en argument
   */
  public BID getBid(int absolutePosition)
  {
    return this.getLastBidMap().get(absolutePosition);
  }
  /**
   * Cette m�thode permet de remonter l'ench�re remportant la vente
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
   * Getter de l'ench�re dont la position relative par rapport � la derni�re ench�re
   * de la vente est pr�cis�e en argument
   * @param relativePosition Position relative de l'ench�re � retourner par rapport
   * � la derni�re ench�re de la vente
   * @return L'ench�re trouv�e � la position relative indiqu�e en argument
   */
  public BID getLastBid(int relativePosition)
  {
    return this.getBid(this.getBidNb() - relativePosition);
  }
  /**
   * Getter de la derni�re ench�re de la vente
   * @return La derni�re ench�re de la vente
   */
  public BID getLastBid()
  {
    return this.getLastBid(0);
  }
  /**
   * Getter de la liste des derni�res ench�res de la vente tri�es par position
   * d�croissante
   * @return La liste des derni�res ench�res de la vente tri�es par position
   * d�croissante
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
   * Cette m�thode permet de conna�tre le montant correspondant � la derni�re ench�re
   * plac�e sur la vente dans la monnaie en argument
   * @param currency Monnaie dans laquelle remonter le montant correspondant � la
   * derni�re ench�re plac�e sur la vente
   * @return Le montant correspondant � la derni�re ench�re plac�e sur la vente
   * dans la monnaie en argument
   * @throws UserException TODO A COMMENTER
   */
  public Amount getCurrentBidValue(Currency currency) throws UserException
  {
    return this.getBidValue(currency, this.getBidNb());
  }
  /**
   * Cette m�thode permet de conna�tre le montant correspondant � la prochaine ench�re
   * � placer sur la vente dans la monnaie en argument
   * @param currency Monnaie dans laquelle remonter le montant correspondant � la
   * prochaine ench�re � placer sur la vente
   * @return Le montant correspondant � la prochaine ench�re � placer sur la vente
   * dans la monnaie en argument
   * @throws UserException TODO A COMMENTER
   */
  public Amount getNextBidValue(Currency currency) throws UserException
  {
    return this.getBidValue(currency, this.getBidNb() + 1);
  }
  /**
   * Cette m�thode permet de conna�tre le montant correspondant � l'ench�re plac�e
   * sur la vente � la position choisie dans la monnaie en argument
   * @param currency Monnaie dans laquelle remonter le montant correspondant � l'
   * ench�re plac�e sur la vente � la position choisie
   * @param position Position dans la vente pour de l'ench�re pour laquelle calculer
   * le montant
   * @return Le montant correspondant � l'ench�re plac�e sur la vente � la position
   * choisie
   * @throws UserException Si la position en argument est n�gative
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
   * Red�fini la m�thode d'ajout d'ench�re afin de recalculer la date de fin pr�vue
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
    // D�fini la nouvelle date de fin
    this.getSchedule().defineNewEndDate(bidDate);
    return bid;
  }
  /**
   * Cette m�thode permet de v�rifier que le compte utilisateur en argument n'est
   * pas nul et ne correspond pas au dernier ench�risseur
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc} ou est d�j� le dernier ench�risseur
   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#checkBidder(com.bid4win.persistence.entity.account.Account)
   */
  @Override
  protected Account checkBidder(Account account) throws UserException
  {
    // V�rifie que le compte utilisateur de l'ench�re � ajouter n'est pas nul
    super.checkBidder(account);
    // V�rifie que le compte utilisateur n'est pas d�j� le dernier ench�risseur
    if(this.getBidNb() != 0)
    {
      UtilObject.checkDiffers("accountId", account.getId(), this.getLastBidder().getId(),
                              AuctionRef.AUCTION_BID_DEFINED_ERROR);
    }
    return account;
  }
  /**
   * Getter de la map des derni�res ench�res plac�es sur la vente
   * @return La map des derni�res ench�res plac�es sur la vente
   */
  private Bid4WinMap<Integer, BID> getLastBidMap()
  {
    return this.lastBidMap;
  }
  /**
   * Setter de la map des derni�res ench�res plac�es sur la vente
   * @param lastBidMap Map des derni�res ench�res plac�es sur la vente � positionner
   */
  private void setLastBidMap(Bid4WinMap<Integer, BID> lastBidMap)
  {
    this.lastBidMap = lastBidMap;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map interne des derni�res ench�res plac�es sur la vente
   * @return La map interne des derni�res ench�res plac�es sur la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "auctionLink", fetch = FetchType.LAZY, cascade = {})
  @MapKey(name = "position")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  private Map<Integer, BID> getLastBidMapInternal()
  {
    return this.getLastBidMap().getInternal();
  }
  /**
   * Setter de la map interne des derni�res ench�res plac�es sur la vente
   * @param internalLastBidMap Map interne des derni�res ench�res � positionner
   * sur la vente
   */
  @SuppressWarnings(value = "unused")
  private void setLastBidMapInternal(Map<Integer, BID> internalLastBidMap)
  {
    this.setLastBidMap(new Bid4WinMap<Integer, BID>(internalLastBidMap, true));
  }
}
