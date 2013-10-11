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
 * Cette classe d�fini l'abstraction de toute ench�re plac�e sur une vente<BR>
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
public abstract class BidAbstract<CLASS extends BidAbstract<CLASS, AUCTION, HISTORY>,
                                  AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                  HISTORY extends BidHistory<HISTORY, AUCTION, ?>>
       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
       implements Comparable<CLASS>
{
  /** Position de l'ench�re sur la vente */
  @Transient
  private int position = 0;
  /** Vente sur laquelle est plac�e l'ench�re */
  @Transient
  private AUCTION auction = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected BidAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur de l'ench�re
   * @param auction Vente de l'ench�re
   * @param position Position de l'ench�re dans la vente
   * @throws UserException Si le compte utilisateur ou la vente est nul ou si la
   * position est inf�rieure � un
   */
  protected BidAbstract(Account account, AUCTION auction, int position) throws UserException
  {
    super(account);
    this.linkToAuction(auction);
    this.definePosition(position);
  }

  /**
   * Red�fini l'�quivalence interne de deux ench�res sans prise en compte de leurs
   * relations afin d'y ajouter le test de leurs donn�es propres
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
   * Permet d'effectuer le rendu simple de l'ench�re courante sans prise en compte
   * de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments de l'ench�re
    buffer.append(" POSITION=" + this.getPosition());
    // Retourne le rendu
    return buffer;
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
    nodeList.add(BidAbstract_Relations.NODE_AUCTION);
    return nodeList;
  }
  /**
   * Permet de r�cup�rer la vente de l'ench�re si elle correspond � la relation
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
   * Permet de positionner la vente de l'ench�re si elle correspond � la relation
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
   * Cette m�thode permet de comparer l'ench�re courante � celle en argument afin
   * de retourner un nombre n�gatif, positif ou z�ro si la position de l'ench�re
   * courante est sup�rieure, inf�rieure ou �gale � celle en argument. Une liste
   * d'ench�res tri�e simplement aura donc l'ench�re la plus r�cente plac�e en
   * premi�re position et la plus ancienne � sa fin
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
   * Cette m�thode doit �tre d�finie afin de construire l'historique de l'ench�re
   * courante
   * @return L'historique de l'ench�re courante
   * @throws UserException Si la construction de l'historique est impossible
   */
  public abstract HISTORY toHistory() throws UserException;

  /**
   * Cette m�thode permet de d�finir la vente sur laquelle est plac�e l'ench�re
   * @param auction D�finition de la vente sur laquelle est plac�e l'ench�re
   * @throws ProtectionException Si l'ench�re courante est prot�g�e
   * @throws UserException Si on d�fini une vente nulle
   */
  private void linkToAuction(AUCTION auction) throws ProtectionException, UserException
  {
    this.linkTo(BidAbstract_Relations.RELATION_AUCTION, auction);
  }
  /**
   * Cette m�thode permet de d�finir la position de l'ench�re dans la vente
   * @param position D�finition de la position de l'ench�re dans la vente
   * @throws UserException Si on d�fini une position inf�rieure � un
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
   * Getter de la position de l'ench�re dans la vente
   * @return La position de l'ench�re dans la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "POSITION", length = 5, nullable = false, unique = false)
  public int getPosition()
  {
    return this.position;
  }
  /**
   * Setter de la position de l'ench�re dans la vente
   * @param position Position de l'ench�re dans la vente � positionner
   */
  private void setPosition(int position)
  {
    this.position = position;
  }

  /**
   * Getter de la vente sur laquelle est plac�e l'ench�re
   * @return La vente sur laquelle est plac�e l'ench�re
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
   * Setter de la vente sur laquelle est plac�e l'ench�re
   * @param auction Vente sur laquelle est plac�e l'ench�re � positionner
   */
  private void setAuction(AUCTION auction)
  {
    this.auction = auction;
  }
}
