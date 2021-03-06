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
 * Cette classe d�fini une base d'ench�re plac�e sur une vente ayant une map des
 * derni�res ench�res<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <AUCTION> D�finition de la vente associ�e � l'ench�re<BR>
 * @param <HISTORY> D�finition de la classe d'historique associ�e � l'ench�re<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid<CLASS extends Bid<CLASS, AUCTION, HISTORY>,
                          AUCTION extends Auction<AUCTION, CLASS, ?, ?, ?>,
                          HISTORY extends BidHistory<HISTORY, AUCTION, CLASS>>
       extends BidAbstract<CLASS, AUCTION, HISTORY>
{
  /** Lien vers la vente sur laquelle est plac�e l'ench�re */
  @Transient
  private AUCTION auctionLink = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Bid()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur de l'ench�re
   * @auction Vente de l'ench�re
   * @position Position de l'ench�re dans la vente
   * @throws ProtectionException Si la vente aux ench�res est prot�g�e
   * @throws UserException Si le compte utilisateur ou la vente est nul ou si la
   * position est inf�rieure � un
   */
  protected Bid(Account account, AUCTION auction, int position)
            throws ProtectionException, UserException
  {
    super(account, auction, position);
    this.linkToAuction();
  }

  /**
   * Ajoute � la liste des noeuds de relations de l'ench�re le lien vers sa vente
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
   * Permet de r�cup�rer le lien vers la vente de l'ench�re s'il correspond � la
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
   * Permet de positionner le lien vers la vente de l'ench�re si elle correspond
   * � la relation en argument.
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
   * Permet de savoir si l'ench�re courante est li�e � une vente
   * @return True si l'ench�re courante est li�e � une vente, false sinon
   */
  public boolean isLinkedToAuction()
  {
    return this.getAuctionLink() != null;
  }
  /**
   * Cette m�thode permet de lier l'ench�re courante � sa vente
   * @throws ProtectionException Si l'ench�re courante ou sa vente est prot�g�e
   * @throws UserException Si la vente de l'ench�re courante est nulle ou la r�f�rence
   * d�j� ou si cette derni�re est d�j� li�e
   */
  private void linkToAuction() throws ProtectionException, UserException
  {
    this.linkTo(Bid_Relations.RELATION_AUCTION_LINK,
                Auction_Relations.RELATION_LAST_BID_MAP,
                this.getAuction());
  }
  /**
   * Cette m�thode permet de d�lier l'ench�re courante de sa vente et inversement
   * @return La vente anciennement li�e � l'ench�re courante
   * @throws ProtectionException Si l'ench�re courante ou sa vente est prot�g�e
   * @throws UserException Si l'ench�re courante n'est pas li�e ou pas r�f�renc�e
   * par la vente li�e
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
   * Getter du lien vers la vente sur laquelle est plac�e l'ench�re
   * @return Le lien vers la vente sur laquelle est plac�e l'ench�re
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
   * Setter du lien vers la vente sur laquelle est plac�e l'ench�re
   * @param auction Vente avec laquelle lier l'ench�re
   */
  private void setAuctionLink(AUCTION auction)
  {
    this.auctionLink = auction;
  }
}
