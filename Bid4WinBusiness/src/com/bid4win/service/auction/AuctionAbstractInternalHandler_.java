package com.bid4win.service.auction;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.service.product.ProductService;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <SERVICE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AuctionAbstractInternalHandler_<CLASS extends AuctionAbstractInternalHandler_<CLASS, AUCTION, SCHEDULE, SERVICE>,
                                                      AUCTION extends AuctionAbstract<AUCTION, ?, SCHEDULE, ?, ?>,
                                                      SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                      SERVICE extends AuctionAbstractInternalService_<AUCTION, ?, ?, SCHEDULE, ?, ?, ?, CLASS, ?>>
       extends Thread
{
  /** Flag indiquant que l'arr�t est demand� pour le handler */
  private boolean stopRequired = false;
  /** Flag indiquant que le handler est arr�t� */
  private boolean ended = false;
  /** Flag indiquant une nouvelle version de produit � charger */
  private boolean productChanged = true;
  /** Objet fournissant la temporisation entre les diff�rentes �tapes de la vente aux ench�res */
  private Object timer = new Object();
  /** Vente aux ench�res du handler */
  private AUCTION auction = null;
  /** Service de gestion de la vente aux ench�res du handler */
  private SERVICE service = null;

  /**
   * Constructeur du handler qui le d�marrera si la vente aux ench�res en argument
   * est ouverte
   * @param auction Vente aux ench�res du handler
   * @param service Service de gestion de la vente aux ench�res du handler
   */
  protected AuctionAbstractInternalHandler_(AUCTION auction, SERVICE service)
  {
    if(this.setAuction(auction))
    {
      this.setService(service);
      this.start();
    }
    else
    {
      this.end();
    }
  }

  /**
   * Cette fonction permet de prendre en charge la vente aux ench�res du handler.
   * Cette fonction s'occupera de faire �voluer la vente aux ench�res du handler
   * et son status en fonction de ses �l�ments de planification et des actions
   * concurrentes potentielles
   * @see java.lang.Thread#run()
   */
  @SuppressWarnings("unchecked")
  @Override
  public void run()
  {
    // Si le handler ne peut �tre ajout�, c'est que le manager en poss�de d�j� un
    if(this.getService().addHandler((CLASS)this))
    {
      try
      {
        // On continue tant que l'arr�t n'est pas demand�
        while(!this.isStopRequired())
        {
          // Attend la dur�e n�cessaire avant la prochaine �tape
          this.waitForNextStep();
          // Si l'arr�t n'est pas demand� on continue
          if(!this.isStopRequired())
          {
            // S'assure du chargement des ressources des produits vendus aux ench�res
            this.loadProduct();
            // Si le temps d'attente est bien �puis�, on passe � l'�tape suivante
            if(this.getNextStepCountdown() == 0)
            {
              try
              {
                // Valide la prochaine �tape de la vente aux ench�res
                this.setAuction(this.validateNextStep());
              }
              // L'action a �chou�, il faut rechercher la vente aux ench�res pour
              // s'assurer de sa validit�
              catch(UserException ex)
              {
                this.reloadAuction();
              }
            }
          }
        }
      }
      catch(Throwable th)
      {
        // TODO LOG
      }
      // Le handler est marqu� comme arr�t� et retir� du service
      this.end();
      this.getService().removeHandler((CLASS)this);
    }
    else
    {
      this.end();
    }
  }
  /**
   * Cette fonction permet d'attendre la prochaine �tape de la vente aux ench�res
   * @throws InterruptedException Si l'attente est interrompue
   */
  protected void waitForNextStep() throws InterruptedException
  {
    // On n'attend pas si l'arr�t est demand�
    if(!this.isStopRequired())
    {
      synchronized(this.getTimer())
      {
        // R�cup�re le temps d'attente
        long countdown = this.getNextStepCountdown();
        // Attend le temps demand�
        if(countdown > 0)
        {
          this.getTimer().wait(countdown);
        }
        // Attend un temps ind�fini
        else if(countdown < 0)
        {
          this.getTimer().wait();
        }
      }
    }
  }
  /**
   *
   * TODO A COMMENTER
   */
  protected void wakeUp()
  {
    synchronized(this.getTimer())
    {
      this.getTimer().notifyAll();
    }
  }
  /**
   * Cette fonction permet de conna�tre le temps � attendre avant la prochaine �tape
   * de la vente aux ench�res. Si le temps d'attente doit �tre ind�fini, la m�thode
   * doit retourner un nombre n�gatif
   * @return Le temps � attendre en millisecondes avant la prochaine �tape
   */
  protected long getNextStepCountdown()
  {
    // La vente aux ench�res est en attente de d�marrage
    if(this.getAuction().getStatus().equals(Status.WAITING))
    {
      return this.getAuction().getSchedule().getStartCountdown(new Bid4WinDate());
    }
    // La vente aux ench�re est d�marr�e
    if(this.getAuction().getStatus().equals(Status.STARTED))
    {
      return this.getAuction().getSchedule().getEndCountdown(new Bid4WinDate());
    }
    return -1;
  }
  /**
   * Cette fonction permet de valider la prochaine �tape de la vente aux ench�res.
   * Celle-ci peut �tre son ouverture ou sa cl�ture par exemple
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   * @throws UserException Si la prochaine �tape ne peut �tre valid�e, car le status
   * de la vente aux ench�res a chang� entre temps par exemple
   */
  protected AUCTION validateNextStep()
            throws PersistenceException, NotFoundEntityException, UserException
  {
    // La vente aux ench�res est en attente de d�marrage
    if(this.getAuction().getStatus().equals(Status.WAITING))
    {
      return this.getService().start(this.getAuctionId());
    }
    // La vente aux ench�re est d�marr�e
    if(this.getAuction().getStatus().equals(Status.STARTED))
    {
      return this.getService().end(this.getAuctionId());
    }
    return this.getAuction();
  }
  /**
   *
   * TODO A COMMENTER
   */
  protected void loadProduct()
  {
    if(this.hasProductChanged())
    {
      try
      {
        ProductService.getInstance().loadProduct(this.getAuction().getProduct().getId());
      }
      catch(Throwable th)
      {
        // TODO LOG
      }
    }
  }

  /**
   * Getter du flag indiquant que l'arr�t est demand� pour le handler
   * @return Le flag indiquant que l'arr�t est demand� pour le handler
   */
  public boolean isStopRequired()
  {
    return this.stopRequired;
  }
  /**
   * Indique que l'arr�t est demand� pour le handler
   */
  public void requireStop()
  {
    this.stopRequired = true;
    this.wakeUp();
  }
  /**
   * Getter du flag indiquant que le handler est arr�t�
   * @return Le flag indiquant que le handler est arr�t�
   */
  public boolean isEnded()
  {
    return this.ended;
  }
  /**
   * Indique que le handler est arr�t�
   */
  private void end()
  {
    this.ended = true;
  }
  /**
   * Getter du flag indiquant une nouvelle version de produit � charger
   * @return Le flag indiquant une nouvelle version de produit � charger
   */
  public boolean hasProductChanged()
  {
    return this.productChanged;
  }
  /**
   * Setter du flag indiquant une nouvelle version de produit � charger
   * @param productChanged Flag indiquant une nouvelle version de produit � charger
   */
  private void setProductChanged(boolean productChanged)
  {
    this.productChanged = productChanged;
  }
  /**
   * Getter de l'objet fournissant la temporisation entre les diff�rentes �tapes
   * de la vente aux ench�res
   * @return L'objet fournissant la temporisation entre les diff�rentes �tapes de
   * la vente aux ench�res
   */
  private Object getTimer()
  {
    return this.timer;
  }

  /**
   * Getter de l'identifiant de la vente aux ench�res du handler
   * @return L'identifiant de la vente aux ench�res du handler
   */
  public String getAuctionId()
  {
    return this.getAuction().getId();
  }
  /**
   * Getter de la vente aux ench�res du handler
   * @return La vente aux ench�res du handler
   */
  public AUCTION getAuction()
  {
    return this.auction;
  }
  /**
   * Cette m�thode permet de recharger la vente aux ench�res du handler
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   */
  private void reloadAuction() throws PersistenceException, NotFoundEntityException
  {
    this.setAuction(this.getService().getAuction(this.getAuctionId()));
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected boolean updateAuction(AUCTION auction)
  {
    boolean result = this.setAuction(auction);
    this.wakeUp();
    return result;
  }
  /**
   * Setter de la vente aux ench�res du handler. Une validation de la version de
   * la vente aux ench�res sera effectu�e et dans le cas o� la vente ne serait pas
   * ouverte, l'arr�t du handler sera demand�
   * @param auction La vente aux ench�res du handler
   * @return True si la vente aux ench�res a bien �t� positionn�e et est ouverte.
   */
  private synchronized boolean setAuction(AUCTION auction)
  {
    // Pas de vente aux ench�res � positionner
    if(auction == null)
    {
      return false;
    }
    // La vente aux ench�res remplace une d�j� d�finie. On valide donc son identifiant
    // et sa version
    if(this.getAuction() != null)
    {
      if(!this.getAuctionId().equals(auction.getId()) ||
         this.getAuction().getVersion() >= auction.getVersion())
      {
        return false;
      }
      if(this.getAuction().getProduct().getVersion() != auction.getProduct().getVersion())
      {
        this.setProductChanged(true);
      }
    }
    this.auction = auction;
    // Le handler n'a plus d'action a effectuer sur une vente aux ench�res en
    // construction
    if(!this.getAuction().getStatus().belongsTo(Status.VALID))
    {
      this.requireStop();
      return false;
    }
    return true;
  }
  /**
   * Getter du service de gestion de la vente aux ench�re du handler
   * @return Le service de gestion de la vente aux ench�re du handler
   */
  protected SERVICE getService()
  {
    return this.service;
  }
  /**
   *
   * Setter du service de gestion de la vente aux ench�re du handler
   * @param service Le service de gestion de la vente aux ench�re du handler
   */
  private void setService(SERVICE service)
  {
    this.service = service;
  }
}
