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
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;
import com.bid4win.persistence.entity.account.Account;

/**
 * Cette classe défini l'abstraction de toute enchère placée sur une vente<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <AUCTION> Définition de la vente associée à l'enchère<BR>
 * @param <HISTORY> Définition de la classe d'historique associée à l'enchère<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BidAbstract<CLASS extends BidAbstract<CLASS, AUCTION, HISTORY>,
                                  AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                  HISTORY extends BidHistory<HISTORY, AUCTION, ?>>
       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
       implements Comparable<CLASS>
{
  /** Position de l'enchère sur la vente */
  @Transient
  private int position = 0;
  /** Vente sur laquelle est placée l'enchère */
  @Transient
  private AUCTION auction = null;

  /**
   * Constructeur pour création par introspection
   */
  protected BidAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur de l'enchère
   * @param auction Vente de l'enchère
   * @param position Position de l'enchère dans la vente
   * @throws UserException Si le compte utilisateur ou la vente est nul ou si la
   * position est inférieure à un
   */
  protected BidAbstract(Account account, AUCTION auction, int position) throws UserException
  {
    super(account);
    this.linkToAuction(auction);
    this.definePosition(position);
  }

  /**
   * Redéfini l'équivalence interne de deux enchères sans prise en compte de leurs
   * relations afin d'y ajouter le test de leurs données propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           this.getPosition() == toBeCompared.getPosition();
  }
  /**
   * Permet d'effectuer le rendu simple de l'enchère courante sans prise en compte
   * de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments de l'enchère
    buffer.append(" POSITION=" + this.getPosition());
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute à la liste des noeuds de relations de l'enchère le lien vers sa vente
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(BidAbstract_Relations.NODE_AUCTION);
    return nodeList;
  }
  /**
   * Permet de récupérer la vente de l'enchère si elle correspond à la relation
   * en argument.
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(BidAbstract_Relations.RELATION_AUCTION))
    {
      return this.getAuction();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner la vente de l'enchère si elle correspond à la relation
   * en argument.
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(BidAbstract_Relations.RELATION_AUCTION))
    {
      this.setAuction((AUCTION)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   * Cette méthode permet de comparer l'enchère courante à celle en argument afin
   * de retourner un nombre négatif, positif ou zéro si la position de l'enchère
   * courante est supérieure, inférieure ou égale à celle en argument. Une liste
   * d'enchères triée simplement aura donc l'enchère la plus récente placée en
   * première position et la plus ancienne à sa fin
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#compareTo(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public int compareTo(CLASS toBeCompared)
  {
    if(toBeCompared == null)
    {
      return 1;
    }
    if(this.getAuction().getId() == toBeCompared.getAuction().getId())
    {
      return toBeCompared.getPosition() - this.getPosition();
    }
    return -super.compareTo(toBeCompared);
  }
  /**
   * Cette méthode doit être définie afin de construire l'historique de l'enchère
   * courante
   * @return L'historique de l'enchère courante
   * @throws UserException Si la construction de l'historique est impossible
   */
  public abstract HISTORY toHistory() throws UserException;

  /**
   * Cette méthode permet de définir la vente sur laquelle est placée l'enchère
   * @param auction Définition de la vente sur laquelle est placée l'enchère
   * @throws ProtectionException Si l'enchère courante est protégée
   * @throws UserException Si on défini une vente nulle
   */
  private void linkToAuction(AUCTION auction) throws ProtectionException, UserException
  {
    this.linkTo(BidAbstract_Relations.RELATION_AUCTION, auction);
  }
  /**
   * Cette méthode permet de définir la position de l'enchère dans la vente
   * @param position Définition de la position de l'enchère dans la vente
   * @throws UserException Si on défini une position inférieure à un
   */
  private void definePosition(int position) throws UserException
  {
    this.checkProtection();
    this.setPosition(UtilNumber.checkMinValue("position", position, 1, true,
                                              AuctionRef.AUCTION_BID_INVALID_ERROR));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la position de l'enchère dans la vente
   * @return La position de l'enchère dans la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "POSITION", length = 5, nullable = false, unique = false)
  public int getPosition()
  {
    return this.position;
  }
  /**
   * Setter de la position de l'enchère dans la vente
   * @param position Position de l'enchère dans la vente à positionner
   */
  private void setPosition(int position)
  {
    this.position = position;
  }

  /**
   * Getter de la vente sur laquelle est placée l'enchère
   * @return La vente sur laquelle est placée l'enchère
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
   * Setter de la vente sur laquelle est placée l'enchère
   * @param auction Vente sur laquelle est placée l'enchère à positionner
   */
  private void setAuction(AUCTION auction)
  {
    this.auction = auction;
  }
}
