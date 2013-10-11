package com.bid4win.service.auction;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
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
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.service.connection.SessionData;

/**
 * Manager interne de base de gestion des ventes aux ench�res incluant la gestion
 * des transactions ainsi que celle des habilitations. Ses m�thodes ne doivent pas
 * �tre expos�es mais �tre utilis�es au travers de processus internes<BR>
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
 * @param <HANDLER> TODO A COMMENTER<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AuctionAbstractInternalService_<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                                      BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                                      BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                                      SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                      TERMS extends TermsAbstract<TERMS>,
                                                      CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
                                                      INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ? extends CreditUsage<?, INVOLVEMENT>, AUCTION, ?>,
                                                      HANDLER extends AuctionAbstractInternalHandler_<HANDLER, AUCTION, SCHEDULE, ?>,
                                                      SERVICE extends AuctionAbstractInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, HANDLER, SERVICE>>
       extends Bid4WinService_<SessionData, Account, SERVICE>
{
  /** Map des handlers de ventes aux ench�res */
  private Bid4WinMap<String, HANDLER> handlerMap = new Bid4WinMap<String, HANDLER>();

  /**
   * Cette m�thode permet de rechercher la vente aux ench�res dont l'identifiant
   * est pr�cis� en argument tout en b�n�ficiant du cache applicatif
   * @param id Identifiant de la vente aux ench�res � rechercher
   * @return La vente aux ench�res trouv�e avec ses relations
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public AUCTION getAuction(String id) throws PersistenceException, NotFoundEntityException
  {
    try
    {
      return this.load(this.getAuctionClass(), id);
    }
    catch(UserException ex)
    {
      // Ne devrais pas arriver car on ne demande aucune validation d'habilitation
      throw new PersistenceException(ex);
    }
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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinMap<String, AUCTION> getAuctionList(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    try
    {
      return this.loadAll(this.getAuctionClass(), idSet);
    }
    catch(UserException ex)
    {
      // Ne devrais pas arrive car on ne demande aucune validation d'habilitation
      throw new PersistenceException(ex);
    }
  }

  /**
   * Cette m�thode permet de placer une ench�re sur une vente pour le compte utilisateur
   * connect�. Elle est dite interne car elle ne doit pas �tre pr�sent�e par le
   * service de gestion des ventes aux ench�res mais �tre utilis�e au travers de
   * la m�thode bid() du service qui informera le handler d'une modification sur
   * la vente aux ench�res dont il a la charge
   * @param auctionId Identifiant de la vente sur laquelle placer l'ench�re
   * @return La vente sur laquelle a �t� plac�e l'ench�re
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION bid(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkUserRole();
    return this.getManager().bid(auctionId, this.getConnectedAccountId()).loadRelation();
  }

  /**
   * Cette m�thode permet de valider la vente aux ench�res dont l'identifiant est
   * pass� en argument. Elle est dite interne car elle ne doit pas �tre pr�sent�e
   * par le service de gestion des ventes aux ench�res mais �tre utilis�e au travers
   * de la m�thode validateAuction() du service qui affectera alors un handler �
   * la vente aux ench�res
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION validate(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    //Valide la vente aux ench�res
    return this.getManager().validateAuction(auctionId).loadRelation();
  }
  /**
   * Cette m�thode permet de d�marrer la vente aux ench�res dont l'identifiant est
   * pass� en argument si la date de d�marrage est atteinte. Cette m�thode est dite
   * interne car elle ne doit pas �tre pr�sent�e par le service de gestion des ventes
   * aux ench�res mais �tre utilis�e au travers de leurs handlers
   * @param auctionId Identifiant de la vente aux ench�res � d�marrer
   * @return La vente aux ench�res d�marr�e ou son dernier �tat si la date de d�marrage
   * n'est pas atteinte
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   * @throws UserException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION start(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // D�marre la vente aux ench�res
    return this.getManager().start(auctionId).loadRelation();
  }
  /**
   * Cette m�thode permet de terminer la vente aux ench�res d�finie par l'identifiant
   * en argument si la date de cl�ture est atteinte. Suivant ses conditions d'
   * annulation, celle-ci sera close ou annul�e. Cette m�thode est dite interne
   * car elle ne doit pas �tre pr�sent�e par le service de gestion des ventes aux
   * ench�res mais �tre utilis�e au travers de leurs handlers (pas de v�rification
   * d'habilitations
   * @param auctionId Identifiant de la vente aux ench�res � cl�turer
   * @return La vente aux ench�res termin�e ou son dernier �tat si la date de cl�ture
   * n'est pas atteinte
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   * @throws UserException Si la vente aux ench�res n'est pas d�marr�e
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION end(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Termine la vente aux ench�res
    return this.getManager().end(auctionId).loadRelation();
  }

  /**
   * Cette m�thode permet de r�cup�rer le handler de la vente aux ench�res en argument
   * @param auction Vente aux ench�res dont il faut r�cup�rer le handler
   * @return Le handler de la vente aux ench�res en argument
   */
  protected HANDLER getHandler(AUCTION auction)
  {
    return this.handlerMap.get(auction.getId());
  }
  /**
   * Cette m�thode permet de d'affecter (si ce n'est d�j� fait ou dans le cas contraire
   * de notifier d'une modification) un handler sur la vente aux ench�res en argument
   * En s'assurant du positionnement de celle-ci dans le cache applicatif. Le handler
   * aura la charge de suivre son cycle de vie afin de l'ouvrir, la cl�turer, l'annuler
   * suivant sa planification. Il validera aussi la pr�sence des ressources li�es au
   * produit vendu. Attention donc � n'affecter un handler qu'� des ventes aux ench�res
   * prot�g�es contre les modifications
   */
  protected AUCTION affectHandler(AUCTION auction)
  {
    HANDLER handler = this.getHandler(auction);
    if(handler == null)
    {
      // L'ajout d'un handler unique dans le service prendra en charge le positionnement
      // dans le cache applicatif
      this.createHandler(auction);
    }
    else if(handler.updateAuction(auction))
    {
      return this.cache(this.getAuctionClass(), auction);
    }
    return auction;
  }
  /**
   * Cette m�thode doit �tre d�finie afin de cr�er le handler pour la vente aux
   * ench�res en argument
   * @param auction Vente aux ench�res pour laquelle il faut cr�er un handler
   * @return Le handler cr�er pour la vente aux ench�res en argument
   */
  protected abstract HANDLER createHandler(AUCTION auction);
  /**
   * Cette m�thode permet d'ajouter le handler de vente aux ench�res en argument
   * s'il n'existe pas d�j� et dans ce cas de positionner la vente aux ench�res
   * concern�e dans le cache applicatif. Elle ne doit �tre appel� que par le handler
   * au moment de son lancement ce qui �vite d'avoir des blocages de synchronisation
   * @param handler Handler de vente aux ench�res � ajouter
   * @return True si le handler n'existait pas d�j� et a �t� ajout�, false sinon
   */
  protected boolean addHandler(HANDLER handler)
  {
    if(this.getHandler(handler.getAuction()) == null)
    {
      synchronized(this.handlerMap)
      {
        if(this.getHandler(handler.getAuction()) == null)
        {
          this.handlerMap.put(handler.getAuctionId(), handler);
          this.cache(this.getAuctionClass(), handler.getAuction());
          return true;
        }
      }
    }
    return false;
  }
  /**
   * Cette m�thode permet de retirer le handler de vente aux ench�res en argument
   * s'il est bien arr�t� et r�f�renc� par le service et alors de retirer du cache
   * applicatif la vente aux ench�res concern�e. Elle ne doit �tre appel� que par
   * le handler au moment de son arr�t ce qui �vite d'avoir des blocages de synchronisation
   * @param handler Handler de vente aux ench�res � retirer
   */
  protected void removeHandler(HANDLER handler)
  {
    if(this.getHandler(handler.getAuction()) != null)
    {
      synchronized(this.handlerMap)
      {
        if(handler == this.getHandler(handler.getAuction()) &&
           handler.isEnded())
        {
          this.handlerMap.remove(handler.getAuction().getId());
          this.uncache(this.getAuctionClass(), handler.getAuction());
        }
      }
    }
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
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.BIZ;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Class<AUCTION> getAuctionClass()
  {
    return this.getManager().getAuctionClass();
  }

  /**
   * Permet de pr�ciser la r�f�rence du manager de gestion des ventes aux ench�res
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected abstract AuctionAbstractManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT> getManager();
}
