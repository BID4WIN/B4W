package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.account.Account;

/**
 * Cette classe défini une base d'enchère placée sur une vente ayant une map des
 * dernières enchères<BR>
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
public abstract class Bid<CLASS extends Bid<CLASS, AUCTION, HISTORY>,
                          AUCTION extends Auction<AUCTION, CLASS, ?, ?, ?>,
                          HISTORY extends BidHistory<HISTORY, AUCTION, CLASS>>
       extends BidAbstract<CLASS, AUCTION, HISTORY>
{
  /** Lien vers la vente sur laquelle est placée l'enchère */
  @Transient
  private AUCTION auctionLink = null;

  /**
   * Constructeur pour création par introspection
   */
  protected Bid()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur de l'enchère
   * @auction Vente de l'enchère
   * @position Position de l'enchère dans la vente
   * @throws ProtectionException Si la vente aux enchères est protégée
   * @throws UserException Si le compte utilisateur ou la vente est nul ou si la
   * position est inférieure à un
   */
  protected Bid(Account account, AUCTION auction, int position)
            throws ProtectionException, UserException
  {
    super(account, auction, position);
    this.linkToAuction();
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
    nodeList.add(Bid_Relations.NODE_AUCTION_LINK);
    return nodeList;
  }
  /**
   * Permet de récupérer le lien vers la vente de l'enchère s'il correspond à la
   * relation en argument.
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(Bid_Relations.RELATION_AUCTION_LINK))
    {
      return this.getAuctionLink();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner le lien vers la vente de l'enchère si elle correspond
   * à la relation en argument.
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(Bid_Relations.RELATION_AUCTION_LINK))
    {
      this.setAuctionLink((AUCTION)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   * Permet de savoir si l'enchère courante est liée à une vente
   * @return True si l'enchère courante est liée à une vente, false sinon
   */
  public boolean isLinkedToAuction()
  {
    return this.getAuctionLink() != null;
  }
  /**
   * Cette méthode permet de lier l'enchère courante à sa vente
   * @throws ProtectionException Si l'enchère courante ou sa vente est protégée
   * @throws UserException Si la vente de l'enchère courante est nulle ou la référence
   * déjà ou si cette dernière est déjà liée
   */
  private void linkToAuction() throws ProtectionException, UserException
  {
    this.linkTo(Bid_Relations.RELATION_AUCTION_LINK,
                Auction_Relations.RELATION_LAST_BID_MAP,
                this.getAuction());
  }
  /**
   * Cette méthode permet de délier l'enchère courante de sa vente et inversement
   * @return La vente anciennement liée à l'enchère courante
   * @throws ProtectionException Si l'enchère courante ou sa vente est protégée
   * @throws UserException Si l'enchère courante n'est pas liée ou pas référencée
   * par la vente liée
   */
  @SuppressWarnings("unchecked")
  public AUCTION unlinkFromAuction() throws ProtectionException, UserException
  {
    return (AUCTION)this.unlinkFrom(Bid_Relations.RELATION_AUCTION_LINK,
                                    Auction_Relations.RELATION_LAST_BID_MAP);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du lien vers la vente sur laquelle est placée l'enchère
   * @return Le lien vers la vente sur laquelle est placée l'enchère
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "AUCTION_ID_LINK", nullable = true, unique = false)
  public AUCTION getAuctionLink()
  {
    return this.auctionLink;
  }
  /**
   * Setter du lien vers la vente sur laquelle est placée l'enchère
   * @param auction Vente avec laquelle lier l'enchère
   */
  private void setAuctionLink(AUCTION auction)
  {
    this.auctionLink = auction;
  }
}
