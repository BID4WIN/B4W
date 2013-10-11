package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.Currency;

/**
 * Cette classe défini le fonctionnement de base d'un robot d'enchères<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée
 * @param <AUCTION> Définition de la vente associée au robot d'enchères<BR>
 * @param <BID> Définition des enchères de la vente associée au robot<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bot<CLASS extends Bot<CLASS, AUCTION, BID>,
                          AUCTION extends Auction<AUCTION, BID, ?, ?, ?>,
                          BID extends Bid<BID, AUCTION, ?>>
       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
{
  /** Définition d'une absence de minimum */
  public final static int NO_MIN = 0;
  /** Définition d'une absence de maximum */
  public final static int NO_MAX = 99999;

  /** Vente du robot d'enchères */
  @Transient
  private AUCTION auction = null;

  /** Position minimum autorisée des enchères du robot dans la vente */
  @Transient
  private int minBidPosition = Bot.NO_MIN;
  /** Position maximum autorisée des enchères du robot dans la vente */
  @Transient
  private int maxBidPosition = Bot.NO_MAX;
  /** Dernière position d'enchère du robot dans la vente */
  @Transient
  private int lastBidPosition = 0;
  /** Nombre d'enchères maximum autorisées pour le robot sur la vente */
  @Transient
  private int maxBidNb = Bot.NO_MAX;
  /** Nombre d'enchères positionnées par le robot sur la vente */
  @Transient
  private int usedBidNb = 0;

  /**
   * Constructeur pour création par introspection
   */
  protected Bot()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du robot d'enchère
   * @param auction Vente du robot d'enchères
   * @param minBidPosition Position minimum autorisée des enchères du robot dans
   * la vente
   * @param maxBidPosition Position maximum autorisée des enchères du robot dans
   * la vente
   * @param maxBidNb Nombre d'enchères maximum autorisées pour le robot sur la vente
   * @throws UserException Si le compte utilisateur ou la vente est nul, si la plage
   * d'enchères est invalide ou si le nombre maximum d'enchères est inférieur à un
   */
  protected Bot(Account account, AUCTION auction,
                int minBidPosition, int maxBidPosition, int maxBidNb)
            throws UserException
  {
    super(account);
    this.linkToAuction(auction);
    this.defineBidRange(minBidPosition, maxBidPosition);
    this.defineMaxBidNb(maxBidNb);
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du robot d'enchère
   * @param auction Vente du robot d'enchères
   * @param minBid Minimum autorisé des enchères du robot dans la vente ou null
   * pour aucun minimum
   * @param maxBid Maximum autorisé des enchères du robot dans la vente ou null
   * pour aucun maximum
   * @param maxBidNb Nombre d'enchères maximum autorisées pour le robot sur la vente
   * @throws UserException Si le compte utilisateur ou la vente est nul, si la plage
   * d'enchères est invalide ou si le nombre maximum d'enchères est inférieur à un
   */
  protected Bot(Account account, AUCTION auction, Amount minBid, Amount maxBid, int maxBidNb)
            throws UserException
  {
    super(account);
    this.linkToAuction(auction);
    this.defineBidRange(minBid, maxBid);
    this.defineMaxBidNb(maxBidNb);
  }

  /**
   * Redéfini l'équivalence interne de deux robots d'enchères sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs données propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           this.getMinBidPosition() == toBeCompared.getMinBidPosition() &&
           this.getMaxBidPosition() == toBeCompared.getMaxBidPosition();
  }
  /**
   * Permet d'effectuer le rendu simple du robot d'enchères courant sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments du robot d'enchères
    buffer.append(" MIN_BID_POSITION=" + this.getMinBidPosition());
    buffer.append(" MAX_BID_POSITION=" + this.getMaxBidPosition());
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute à la liste des noeuds de relations du robot d'enchères le lien vers
   * sa vente
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(Bot_Relations.NODE_AUCTION);
    return nodeList;
  }
  /**
   * Permet de récupérer la vente du robot d'enchères si elle correspond à la relation
   * en argument.
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(Bot_Relations.RELATION_AUCTION))
    {
      return this.getAuction();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner la vente du robot d'enchères si elle correspond à la
   * relation en argument.
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(Bot_Relations.RELATION_AUCTION))
    {
      this.setAuction((AUCTION)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }
  /**
   * Cette méthode permet de définir la vente du robot d'enchères
   * @param auction Définition de la vente du robot d'enchères
   * @throws ProtectionException Si le robot d'enchères courant est protégé
   * @throws UserException Si on défini une vente nulle
   */
  private void linkToAuction(AUCTION auction) throws ProtectionException, UserException
  {
    this.linkTo(Bot_Relations.RELATION_AUCTION, auction);
  }

  /**
   * TODO A COMMENTER
   * @param bid TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  @SuppressWarnings("unchecked")
  public CLASS autoBid(BID bid) throws ProtectionException, UserException
  {
    this.checkProtection();
    UtilObject.checkNotNull("bid", bid, AuctionRef.AUCTION_BID_MISSING_ERROR);
    UtilObject.checkEquals("auctionId", bid.getAuction().getId(),
                           this.getAuction().getId(), AuctionRef.AUCTION_INVALID_ERROR);
    UtilObject.checkEquals("accountId", bid.getAccount().getId(),
                           this.getAccount().getId(), AccountRef.ACCOUNT_INVALID_ERROR);
    UtilNumber.checkMaxValue("usedBid", this.getUsedBidNb(), this.getMaxBidNb(), false,
                             MessageRef.UNKNOWN_INVALID_ERROR);
    UtilNumber.checkMinValue("position", bid.getPosition(), this.getMinBidPosition(), true,
                             MessageRef.UNKNOWN_INVALID_ERROR);
    UtilNumber.checkMaxValue("position", bid.getPosition(), this.getMaxBidPosition(), true,
                             MessageRef.UNKNOWN_INVALID_ERROR);
    this.setLastBidPosition(bid.getPosition());
    this.setUsedBidNb(this.getUsedBidNb() + 1);
    return (CLASS)this;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getBidIncrementLimit()
  {
    return Math.min(this.getBidNbLimit(), this.getCreditNbLimit());
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getBidNbLimit()
  {
    int bidNbLimit = this.getMaxBidNb();
    if(bidNbLimit != Bot.NO_MAX)
    {
      bidNbLimit -= this.getUsedBidNb();
    }
    return bidNbLimit;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getCreditNbLimit()
  {
    int creditNbLimit = Bot.NO_MAX;
    int creditNbToUse = this.getAuction().getTerms().getCreditNbPerBid();
    if(creditNbToUse != 0)
    {
      creditNbLimit = this.getAccount().getCreditNb() / creditNbToUse;
    }
    return creditNbLimit;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getPositionIncrementLimit()
  {
    int maxPosition = this.getMaxBidPosition();
    if(maxPosition != Bot.NO_MAX)
    {
      maxPosition = Math.min(0, this.getAuction().getBidNb() + 1 - maxPosition);
    }
    return maxPosition;
  }
  /**
   *
   * TODO A COMMENTER
   * @param currency TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public Amount getMinBid(Currency currency) throws UserException
  {
    if(this.getMinBidPosition() == Bot.NO_MIN)
    {
      return null;
    }
    return this.getAuction().getBidValue(currency, this.getMinBidPosition());
  }
  /**
   *
   * TODO A COMMENTER
   * @param currency TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public Amount getMaxBid(Currency currency) throws UserException
  {
    if(this.getMaxBidPosition() == Bot.NO_MIN)
    {
      return null;
    }
    return this.getAuction().getBidValue(currency, this.getMaxBidPosition());
  }
  /**
   * Cette méthode permet de définir la plage de positions autorisées des enchères
   * du robot dans la vente
   * @param minBid Définition du minimum autorisé des enchères du robot dans la
   * vente ou null pour aucun minimum
   * @param maxBid Définition du maximum autorisé des enchères du robot dans la
   * vente ou null pour aucun maximum
   * @throws ProtectionException Si le robot d'enchères courant est protégé
   * @throws UserException Si on défini une plage de positions invalide
   */
  public void defineBidRange(Amount minBid, Amount maxBid)
         throws ProtectionException, UserException
  {
    int minBidPosition = (minBid == null ? Bot.NO_MIN : this.getAuction().getBidPosition(minBid));
    int maxBidPosition = (maxBid == null ? Bot.NO_MAX : this.getAuction().getBidPosition(maxBid));
    this.defineBidRange(minBidPosition, maxBidPosition);
  }
  /**
   * Cette méthode permet de définir la plage de positions autorisées des enchères
   * du robot dans la vente
   * @param minBidPosition Définition de la position minimum autorisée des enchères
   * du robot dans la vente
   * @param maxBidPosition Définition de la position maximum autorisée des enchères
   * du robot dans la vente
   * @throws ProtectionException Si le robot d'enchères courant est protégé
   * @throws UserException Si on défini une plage de positions invalide
   */
  public void defineBidRange(int minBidPosition, int maxBidPosition)
         throws ProtectionException, UserException
  {
    this.checkProtection();
    UtilNumber.checkMinValue("minBidPosition", minBidPosition, NO_MIN, true,
                             MessageRef.UNKNOWN_INVALID_ERROR);
    UtilNumber.checkMaxValue("maxBidPosition", maxBidPosition, NO_MAX, true,
                             MessageRef.UNKNOWN_INVALID_ERROR);
    UtilNumber.checkMinValue("maxBidPosition", maxBidPosition, minBidPosition, true,
                             MessageRef.UNKNOWN_INVALID_ERROR);
    this.setMinBidPosition(minBidPosition);
    this.setMaxBidPosition(maxBidPosition);
  }
  /**
   *
   * TODO A COMMENTER
   * @param maxBidNb Définition du nombre d'enchères maximum autorisées pour le
   * robot sur la vente
   * @throws ProtectionException Si le robot d'enchères courant est protégé
   * @throws UserException TODO A COMMENTER
   */
  public void defineMaxBidNb(int maxBidNb) throws ProtectionException, UserException
  {
    this.checkProtection();
    UtilNumber.checkMinValue("maxBidNb", maxBidNb, NO_MIN, false,
                             MessageRef.UNKNOWN_INVALID_ERROR);
    UtilNumber.checkMaxValue("maxBidNb", maxBidNb, NO_MAX, true,
                             MessageRef.UNKNOWN_INVALID_ERROR);
    this.setMaxBidNb(maxBidNb);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la vente du robot d'enchères
   * @return La vente du robot d'enchères
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "AUCTION_ID", nullable = false, unique = false)
  public AUCTION getAuction()
  {
    return this.auction;
  }
  /**
   * Setter de la vente du robot d'enchères
   * @param auction Vente du robot d'enchères à positionner
   */
  private void setAuction(AUCTION auction)
  {
    this.auction = auction;
  }

  /**
   * Getter du compte utilisateur du robot d'enchères (afin d'avoir accès à ses
   * champs spécifiques pour les requêtes)
   * @return Le compte utilisateur du robot d'enchères (afin d'avoir accès à ses
   * champs spécifiques pour les requêtes)
   */
  @SuppressWarnings("unused")
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "ACCOUNT_ID", nullable = false, unique = false,
              // Utilise la même colonne que le lien réel vers la table des comptes utilisateurs
              insertable = false, updatable = false)
  private Account getAccountLink()
  {
    return this.getAccount();
  }
  /**
   * Setter inutilisé du compte utilisateur du robot d'enchères
   * @param account Compte utilisateur du robot d'enchères
   */
  @SuppressWarnings("unused")
  private void setAccountLink(Account account)
  {
    // TODO
  }

  /**
   * Getter de la position minimum autorisée des enchères du robot dans la vente
   * @return La position minimum autorisée des enchères du robot dans la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "MIN_BID_POSITION", length = 5, nullable = false, unique = false)
  public int getMinBidPosition()
  {
    return this.minBidPosition;
  }
  /**
   * Setter de la position minimum autorisée des enchères du robot dans la vente
   * @param minBidPosition Position minimum autorisée des enchères du robot dans
   * la vente
   */
  private void setMinBidPosition(int minBidPosition)
  {
    this.minBidPosition = minBidPosition;
  }

  /**
   * Getter de la position maximum autorisée des enchères du robot dans la vente
   * @return La position maximum autorisée des enchères du robot dans la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "MAX_BID_POSITION", length = 5, nullable = false, unique = false)
  public int getMaxBidPosition()
  {
    return this.maxBidPosition;
  }
  /**
   * Setter de la position maximum autorisée des enchères du robot dans la vente
   * @param maxBidPosition Position maximum autorisée des enchères du robot dans
   * la vente
   */
  private void setMaxBidPosition(int maxBidPosition)
  {
    this.maxBidPosition = maxBidPosition;
  }

  /**
   * Getter de la dernière position d'enchère du robot dans la vente
   * @return La dernière position d'enchère du robot dans la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "LAST_BID_POSITION", length = 5, nullable = false, unique = false)
  public int getLastBidPosition()
  {
    return this.lastBidPosition;
  }
  /**
   * Setter de la dernière position d'enchère du robot dans la vente
   * @param lastBidPosition Dernière position d'enchère du robot dans la vente
   */
  private void setLastBidPosition(int lastBidPosition)
  {
    this.lastBidPosition = lastBidPosition;
  }

  /**
   * Getter du nombre d'enchères maximum autorisées pour le robot sur la vente
   * @return Le nombre d'enchères maximum autorisées pour le robot sur la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "MAX_BID_NB", length = 5, nullable = false, unique = false)
  public int getMaxBidNb()
  {
    return this.maxBidNb;
  }
  /**
   * Setter du nombre d'enchères maximum autorisées pour le robot sur la vente
   * @param maxBidNb Nombre d'enchères maximum autorisées pour le robot sur la vente
   */
  private void setMaxBidNb(int maxBidNb)
  {
    this.maxBidNb = maxBidNb;
  }

  /**
   * Getter du nombre d'enchères positionnées par le robot sur la vente
   * @return Le nombre d'enchères positionnées par le robot sur la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "USED_BID_NB", length = 5, nullable = false, unique = false)
  public int getUsedBidNb()
  {
    return this.usedBidNb;
  }
  /**
   * Setter du nombre d'enchères positionnées par le robot sur la vente
   * @param usedBidNb Nombre d'enchères positionnées par le robot sur la vente
   */
  private void setUsedBidNb(int usedBidNb)
  {
    this.usedBidNb = usedBidNb;
  }
}
