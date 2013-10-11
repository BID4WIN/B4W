package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CreditInvolvementAuction<CLASS extends CreditInvolvementAuction<CLASS, USAGE, AUCTION, HISTORY>,
                                               USAGE extends CreditUsage<USAGE, CLASS>,
                                               AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                               HISTORY extends CreditInvolvementAuctionHistory<HISTORY, ?, AUCTION, CLASS>>
       extends CreditInvolvementAuctionAbstract<CLASS, USAGE, CreditBundle, AUCTION, HISTORY>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditInvolvementAuction()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur des cr�dits utilis�s
   * @param auction Vente aux ench�res sur laquelle sont impliqu�s les cr�dits
   * @throws UserException Si le compte utilisateur ou la vente aux ench�res est
   * nul
   */
  protected CreditInvolvementAuction(Account account, AUCTION auction) throws UserException
  {
    super(account, auction);
  }

  /**
   * Ajoute � la liste des noeuds de relations de l'implication de cr�dits le lien
   * vers son historisation si tel est le cas
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(CreditInvolvementAuction_Relations.NODE_HISTORY);
    return nodeList;
  }
  /**

   * Permet de r�cup�rer l'historisation de l'implication de cr�dits si elle correspond
   * � la relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type simple � remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionAbstract#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(CreditInvolvementAuction_Relations.RELATION_HISTORY))
    {
      return this.getHistory();
    }
    return super.getRelationSimple(relation);
  }

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#linkToAccount(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected void linkToAccount(Account account) throws ProtectionException, UserException
  {
    this.linkTo(CreditInvolvementAuction_Relations.RELATION_ACCOUNT,
                this.getAccountRelationToInvolvement(),
                account);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#unlinkFromAccount()
   */
  @Override
  public Account unlinkFromAccount() throws ProtectionException, UserException
  {
    return (Account)this.unlinkFrom(CreditInvolvementAuction_Relations.RELATION_ACCOUNT,
                                    this.getAccountRelationToInvolvement());
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract Bid4WinRelation getAccountRelationToInvolvement();

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant d'historisation de l'implication de cr�dits
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvement#getHistoryId()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "HISTORY_ID", length = 10, nullable = true, unique = true,
          // Utilise la m�me colonne que le lien r�el vers la table des historisations
          insertable = false, updatable = false)
  public Long getHistoryId()
  {
    return super.getHistoryId();
  }
  /**
   * Getter de l'historisation de l'implication de cr�dits
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvement#getHistory()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "HISTORY_ID", nullable = true, unique = true)
  public HISTORY getHistory()
  {
    return super.getHistory();
  }
}
