package com.bid4win.service.auction;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.ObjectProtector;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.manager.auction.AuctionAbstractManager_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.service.connection.SessionData;

/**
 * Manager de base de gestion des ventes aux ench�res incluant la gestion des
 * transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @param <AUCTION> D�finition du type de vente aux ench�res g�r� par le manager<BR>
 * @param <BID> D�finition du type d'ench�re des ventes g�r�es par le manager<BR>
 * @param <BID_HISTORY> D�finition du type d'historique associ�e aux ench�res<BR>
 * @param <SCHEDULE> D�finition des �l�ments de planification des ventes aux ench�res
 * g�r�es par le manager<BR>
 * @param <TERMS> D�finition des conditions des ventes aux ench�res g�r�es par le
 * manager<BR>
 * @param <CANCEL_POLICY> D�finition de la politique d'annulation des ventes aux
 * ench�res g�r�es par le manager<BR>
 * @param <INVOLVEMENT> D�finition des implications de cr�dits sur les ventes aux
 * ench�res g�r�es par le manager<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AuctionAbstractService_<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                              BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                              BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                              SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                              TERMS extends TermsAbstract<TERMS>,
                                              CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
                                              INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ? extends CreditUsage<?, INVOLVEMENT>, AUCTION, ?>,
                                              SERVICE extends AuctionAbstractService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, SERVICE>>
       extends Bid4WinService_<SessionData, Account, SERVICE>
{
  /**
   * Cette m�thode permet de r�cup�rer une vente aux ench�res en fonction de son
   * identifiant
   * @param auctionId Identifiant de la vente aux ench�res � r�cup�rer
   * @return La vente aux ench�res trouv�e correspondant � l'identifiant en argument
   * sans ces relations charg�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   */
/*  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public AUCTION getById(String auctionId) throws PersistenceException, NotFoundEntityException
  {// TODO voir pour le chargement des relations (seulement produit ?)
    return this.getInternal().getById(auctionId).loadRelation();
  }*/

  /**
   * Cette m�thode permet de r�cup�rer toutes les ventes aux ench�res dont le status
   * appartient � un de ceux en argument ou toutes les ventes si aucun status n'
   * est pr�cis�
   * @param loadRelation Permet d'indiquer si on souhaite ou non le chargement des
   * relations des ventes aux ench�res remont�es
   * @param statusArray Status des ventes aux ench�res � r�cup�rer
   * @return Les ventes aux ench�res sont le status appartient � un de ceux en
   * argument (c'est � dire que lui ou un de ses parents correspond � un des status
   * donn�es) avec leurs relations si demand� express�ment
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<AUCTION> getAuctionListByStatus(boolean loadRelation,
                                                     Status ... statusArray)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    if(statusArray.length == 0)
    {
      // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
      this.checkAdminRole();
    }
    else
    {
      for(Status status : statusArray)
      {
        if(!status.belongsTo(Status.VALID) && !status.belongsTo(Status.ENDED))
        {
          // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
          this.checkAdminRole();
          break;
        }
      }
    }
    Bid4WinList<AUCTION> result = this.getManager().getAuctionListByStatus(statusArray);
    if(loadRelation)
    {
      Bid4WinEntityLoader.getInstance().loadRelation(result);
    }
    return result;
  }

  /**
   * Cette m�thode permet de r�cup�rer toutes les ventes aux ench�res valides, c'
   * est � dire celles sur lesquelles l'utilisateur peut �tre amen� � effectuer
   * une action. La remont�e de ces ventes leur affectera un handler si ce n'est
   * d�j� fait qui aura la charge de suivre le cycle de vie de celles-ci afin de
   * les ouvrir, les cl�turer, les annuler suivant leur planification. L'affectation
   * d'un handler � une vente aux ench�res validera aussi la pr�sence des ressources
   * li�es au produit vendu et positionnera la vente aux ench�res dans le cache
   * applicatif
   * @return La liste de toutes les ventes aux ench�res valides avec leurs relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<AUCTION> getValidAuctionList() throws PersistenceException
  {
    Bid4WinList<AUCTION> result = null;
    // R�cup�re les ventes aux ench�res en les prot�geant contre toute modification
    // ult�rieure
    String protectionId = ObjectProtector.startProtection();
    try
    {
      result = this.self().getAuctionListByStatus(true, Status.VALID);
    }
    catch(UserException ex)
    {
      throw new PersistenceException();
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    // Affecte un handler � chaque vente aux ench�res si ce n'est d�j� fait
    for(AUCTION auction : result)
    {
      this.getInternal().affectHandler(auction);
    }
    return result;
  }
  /**
   * Cette m�thode permet de rechercher la vente aux ench�res dont l'identifiant
   * est pr�cis� en argument tout en b�n�ficiant du cache applicatif
   * @param id Identifiant de la vente aux ench�res � rechercher
   * @return La vente aux ench�res trouv�e avec ses relations
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   */
  public AUCTION getAuction(String id) throws PersistenceException, NotFoundEntityException
  {
    return this.getInternal().getAuction(id);
  }
  /**
   * Cette m�thode permet de rechercher les ventes aux ench�res dont les identifiants
   * sont pr�cis�s en argument tout en b�n�ficiant du cache applicatif
   * @param idSet Identifiants des ventes aux ench�res � rechercher
   * @return La map des ventes aux ench�res class�es selon leur identifiant avec
   * leurs relations
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si un des identifiants ne correspond pas �
   * une vente aux ench�res existantes
   */
  public Bid4WinMap<String, AUCTION> getAuctionList(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return this.getInternal().getAuctionList(idSet);
  }

  /**
   * Cette m�thode permet de cr�er une vente aux ench�res pour le produit en argument
   * @param productId Identifiant du produit vendu aux ench�res
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @return La vente aux ench�res du produit en argument
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiant en argument
   * @throws UserException Si un probl�me intervient lors de la r�cup�ration du
   * produit ou de la cr�ation de la vente aux ench�res
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION createAuction(String productId, SCHEDULE schedule, TERMS terms)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Cr�e la vente aux ench�res
    return this.getManager().createAuction(productId, schedule, terms).loadRelation();
  }
  /**
   * Cette m�thode permet de modifier la vente aux ench�res d�finie en argument
   * @param auctionId Identifiant de la vente aux ench�res � modifier
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @return La vente aux ench�res modifi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   * @throws UserException TODO A COMMENTER
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION updateAuction(String auctionId, SCHEDULE schedule, TERMS terms)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Cr�e la vente aux ench�res
    return this.getManager().updateAuction(auctionId, schedule, terms).loadRelation();
  }
  /**
   * Cette m�thode permet de valider la vente aux ench�res dont l'identifiant est
   * pass� en argument
   * @param auctionId Identifiant de la vente aux ench�res � valider
   * @return La vente aux ench�res valid�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   * @throws UserException TODO A COMMENTER
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  public AUCTION validateAuction(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    AUCTION auction = null;
    // Valide la vente aux ench�res en la prot�geant contre toute modification
    // ult�rieure
    String protectionId = ObjectProtector.startProtection();
    try
    {
      auction = this.getInternal().validate(auctionId);
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    // Affecte ou notifie le handler concern�
    return this.getInternal().affectHandler(auction);
  }

  /**
   * Cette m�thode permet de placer une ench�re sur une vente pour le compte utilisateur
   * connect�
   * @param auctionId Identifiant de la vente sur laquelle placer l'ench�re
   * @return La vente sur laquelle a �t� plac�e l'ench�re
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur ou la vente aux ench�res
   * n'a pu �tre trouv�
   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits ou
   * est d�j� le dernier ench�risseur ou si la vente aux ench�res n'a pas d�but�
   * ou est termin�e
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  public AUCTION bid(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    AUCTION auction = null;
    // Place l'ench�re sur la vente en la prot�geant contre toute modification
    // ult�rieure
    String protectionId = ObjectProtector.startProtection();
    try
    {
      auction = this.getInternal().bid(auctionId);
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    // Affecte ou notifie le handler concern�
    return this.getInternal().affectHandler(auction);
  }

  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#clearCache()
   */
  @Override
  protected void clearCache()
  {
    super.clearCache();
    this.getInternal().clearCache();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public final Role getAdminRole()
  {
    return this.getInternal().getAdminRole();
  }
  /**
   * Permet de pr�ciser la r�f�rence du manager de gestion des ventes aux ench�res
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected AuctionAbstractManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT> getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Permet de pr�ciser la r�f�rence du service interne de gestion des ventes aux
   * ench�res
   * @return La r�f�rence du service interne de gestions des ventes aux ench�res
   */
  protected abstract AuctionAbstractInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, ?, ?> getInternal();
}
