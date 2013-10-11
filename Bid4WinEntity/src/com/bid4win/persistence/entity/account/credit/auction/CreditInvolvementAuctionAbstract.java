package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement;
import com.bid4win.persistence.entity.account.credit.CreditUsageAbstract;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <BUNDLE> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CreditInvolvementAuctionAbstract<CLASS extends CreditInvolvementAuctionAbstract<CLASS, USAGE, BUNDLE, AUCTION, HISTORY>,
                                                       USAGE extends CreditUsageAbstract<USAGE, BUNDLE, CLASS>,
                                                       BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                                       AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                       HISTORY extends CreditInvolvementAuctionAbstract<HISTORY, ?, CreditBundleHistory, AUCTION, ?>>
       extends CreditInvolvement<CLASS, USAGE, BUNDLE, HISTORY>
{
  /** Identifiant de la vente aux enchères sur laquelle sont impliqués les crédits */
  @Transient
  private String auctionId = null;
  /** Vente aux enchères sur laquelle sont impliqués les crédits */
  @Transient
  private AUCTION auction = null;

  /**
   * Constructeur pour création par introspection
   */
  protected CreditInvolvementAuctionAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur des crédits utilisés
   * @param auction Vente aux enchères sur laquelle sont impliqués les crédits
   * @throws UserException Si le compte utilisateur ou la vente aux enchères est
   * nul
   */
  protected CreditInvolvementAuctionAbstract(Account account, AUCTION auction) throws UserException
  {
    super();
    // Défini la vente sur laquelle sont impliqués les crédits en premier pour
    // pouvoir classer l'impliquation de crédits en fonction de son identifiant
    this.linkToAuction(auction);
    // Crée le lien avec le compte utilisateur
    this.linkToAccount(account);
  }

  /**
   * Ajoute à la liste des noeuds de relations de l'implication de crédits le lien
   * vers sa vente aux enchères si tel est le cas
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvement#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(CreditInvolvementAuctionAbstract_Relations.NODE_AUCTION);
    return nodeList;
  }
  /**
   * Permet de récupérer la vente aux enchères de l'implication de crédits si elle
   * correspond à la relation en argument. Elle doit être redéfinie pour toute nouvelle
   * relation de type simple à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(CreditInvolvementAuctionAbstract_Relations.RELATION_AUCTION))
    {
      return this.getAuction();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner la vente aux enchères de l'implication de crédits si
   * elle correspondant à la relation en argument. Elle doit être redéfinie pour
   * toute nouvelle relation de type simple à positionner
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(CreditInvolvementAuctionAbstract_Relations.RELATION_AUCTION))
    {
      this.setAuction((AUCTION)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getMessageRefBase(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    if(relation.equals(CreditInvolvementAuctionAbstract_Relations.RELATION_AUCTION))
    {
      return AuctionRef.AUCTION;
    }
    return super.getMessageRefBase(relation);
  }

  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  private void linkToAuction(AUCTION auction) throws ProtectionException, UserException
  {
    this.linkTo(CreditInvolvementAuctionAbstract_Relations.RELATION_AUCTION, auction);
    this.setAuctionId(auction.getId());
  }


  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de la vente aux enchères impliquée qui fait office de
   * clé à utiliser pour classer les implications dans leur compte utilisateur
   * @return L'identifiant de la vente aux enchères impliquée
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "AUCTION_ID", length = 12, nullable = false, unique = false,
          // Utilise la même colonne que le lien réel vers la table des ventes aux enchères
          insertable = false, updatable = false)
  public String getAuctionId()
  {
    return this.auctionId;
  }
  /**
   * Setter de l'identifiant de la vente aux enchères impliquée
   * @param auctionId Identifiant de la vente aux enchères impliquée à positionner
   */
  private void setAuctionId(String auctionId)
  {
    this.auctionId = auctionId;
  }

  /**
   * Getter de la vente aux enchères sur laquelle sont impliqués les crédits
   * @return La vente aux enchères sur laquelle sont impliqués les crédits
   */
  // Annotation pour la persistence
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "AUCTION_ID", nullable = false, unique = false)
  public AUCTION getAuction()
  {
    return this.auction;
  }
  /**
   * Setter de la vente aux enchères sur laquelle sont impliqués les crédits
   * @param auction Vente aux enchères sur laquelle sont impliqués les crédits à
   * positionner
   */
  private void setAuction(AUCTION auction)
  {
    this.auction = auction;
  }
}
