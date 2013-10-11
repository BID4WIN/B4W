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
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractInternalHandler_<CLASS extends AuctionAbstractInternalHandler_<CLASS, AUCTION, SCHEDULE, SERVICE>,
                                                      AUCTION extends AuctionAbstract<AUCTION, ?, SCHEDULE, ?, ?>,
                                                      SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                      SERVICE extends AuctionAbstractInternalService_<AUCTION, ?, ?, SCHEDULE, ?, ?, ?, CLASS, ?>>
       extends Thread
{
  /** Flag indiquant que l'arrêt est demandé pour le handler */
  private boolean stopRequired = false;
  /** Flag indiquant que le handler est arrêté */
  private boolean ended = false;
  /** Flag indiquant une nouvelle version de produit à charger */
  private boolean productChanged = true;
  /** Objet fournissant la temporisation entre les différentes étapes de la vente aux enchères */
  private Object timer = new Object();
  /** Vente aux enchères du handler */
  private AUCTION auction = null;
  /** Service de gestion de la vente aux enchères du handler */
  private SERVICE service = null;

  /**
   * Constructeur du handler qui le démarrera si la vente aux enchères en argument
   * est ouverte
   * @param auction Vente aux enchères du handler
   * @param service Service de gestion de la vente aux enchères du handler
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
   * Cette fonction permet de prendre en charge la vente aux enchères du handler.
   * Cette fonction s'occupera de faire évoluer la vente aux enchères du handler
   * et son status en fonction de ses éléments de planification et des actions
   * concurrentes potentielles
   * @see java.lang.Thread#run()
   */
  @SuppressWarnings("unchecked")
  @Override
  public void run()
  {
    // Si le handler ne peut être ajouté, c'est que le manager en possède déjà un
    if(this.getService().addHandler((CLASS)this))
    {
      try
      {
        // On continue tant que l'arrêt n'est pas demandé
        while(!this.isStopRequired())
        {
          // Attend la durée nécessaire avant la prochaine étape
          this.waitForNextStep();
          // Si l'arrêt n'est pas demandé on continue
          if(!this.isStopRequired())
          {
            // S'assure du chargement des ressources des produits vendus aux enchères
            this.loadProduct();
            // Si le temps d'attente est bien épuisé, on passe à l'étape suivante
            if(this.getNextStepCountdown() == 0)
            {
              try
              {
                // Valide la prochaine étape de la vente aux enchères
                this.setAuction(this.validateNextStep());
              }
              // L'action a échoué, il faut rechercher la vente aux enchères pour
              // s'assurer de sa validité
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
      // Le handler est marqué comme arrêté et retiré du service
      this.end();
      this.getService().removeHandler((CLASS)this);
    }
    else
    {
      this.end();
    }
  }
  /**
   * Cette fonction permet d'attendre la prochaine étape de la vente aux enchères
   * @throws InterruptedException Si l'attente est interrompue
   */
  protected void waitForNextStep() throws InterruptedException
  {
    // On n'attend pas si l'arrêt est demandé
    if(!this.isStopRequired())
    {
      synchronized(this.getTimer())
      {
        // Récupère le temps d'attente
        long countdown = this.getNextStepCountdown();
        // Attend le temps demandé
        if(countdown > 0)
        {
          this.getTimer().wait(countdown);
        }
        // Attend un temps indéfini
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
   * Cette fonction permet de connaître le temps à attendre avant la prochaine étape
   * de la vente aux enchères. Si le temps d'attente doit être indéfini, la méthode
   * doit retourner un nombre négatif
   * @return Le temps à attendre en millisecondes avant la prochaine étape
   */
  protected long getNextStepCountdown()
  {
    // La vente aux enchères est en attente de démarrage
    if(this.getAuction().getStatus().equals(Status.WAITING))
    {
      return this.getAuction().getSchedule().getStartCountdown(new Bid4WinDate());
    }
    // La vente aux enchère est démarrée
    if(this.getAuction().getStatus().equals(Status.STARTED))
    {
      return this.getAuction().getSchedule().getEndCountdown(new Bid4WinDate());
    }
    return -1;
  }
  /**
   * Cette fonction permet de valider la prochaine étape de la vente aux enchères.
   * Celle-ci peut être son ouverture ou sa clôture par exemple
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   * @throws UserException Si la prochaine étape ne peut être validée, car le status
   * de la vente aux enchères a changé entre temps par exemple
   */
  protected AUCTION validateNextStep()
            throws PersistenceException, NotFoundEntityException, UserException
  {
    // La vente aux enchères est en attente de démarrage
    if(this.getAuction().getStatus().equals(Status.WAITING))
    {
      return this.getService().start(this.getAuctionId());
    }
    // La vente aux enchère est démarrée
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
   * Getter du flag indiquant que l'arrêt est demandé pour le handler
   * @return Le flag indiquant que l'arrêt est demandé pour le handler
   */
  public boolean isStopRequired()
  {
    return this.stopRequired;
  }
  /**
   * Indique que l'arrêt est demandé pour le handler
   */
  public void requireStop()
  {
    this.stopRequired = true;
    this.wakeUp();
  }
  /**
   * Getter du flag indiquant que le handler est arrêté
   * @return Le flag indiquant que le handler est arrêté
   */
  public boolean isEnded()
  {
    return this.ended;
  }
  /**
   * Indique que le handler est arrêté
   */
  private void end()
  {
    this.ended = true;
  }
  /**
   * Getter du flag indiquant une nouvelle version de produit à charger
   * @return Le flag indiquant une nouvelle version de produit à charger
   */
  public boolean hasProductChanged()
  {
    return this.productChanged;
  }
  /**
   * Setter du flag indiquant une nouvelle version de produit à charger
   * @param productChanged Flag indiquant une nouvelle version de produit à charger
   */
  private void setProductChanged(boolean productChanged)
  {
    this.productChanged = productChanged;
  }
  /**
   * Getter de l'objet fournissant la temporisation entre les différentes étapes
   * de la vente aux enchères
   * @return L'objet fournissant la temporisation entre les différentes étapes de
   * la vente aux enchères
   */
  private Object getTimer()
  {
    return this.timer;
  }

  /**
   * Getter de l'identifiant de la vente aux enchères du handler
   * @return L'identifiant de la vente aux enchères du handler
   */
  public String getAuctionId()
  {
    return this.getAuction().getId();
  }
  /**
   * Getter de la vente aux enchères du handler
   * @return La vente aux enchères du handler
   */
  public AUCTION getAuction()
  {
    return this.auction;
  }
  /**
   * Cette méthode permet de recharger la vente aux enchères du handler
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
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
   * Setter de la vente aux enchères du handler. Une validation de la version de
   * la vente aux enchères sera effectuée et dans le cas où la vente ne serait pas
   * ouverte, l'arrêt du handler sera demandé
   * @param auction La vente aux enchères du handler
   * @return True si la vente aux enchères a bien été positionnée et est ouverte.
   */
  private synchronized boolean setAuction(AUCTION auction)
  {
    // Pas de vente aux enchères à positionner
    if(auction == null)
    {
      return false;
    }
    // La vente aux enchères remplace une déjà définie. On valide donc son identifiant
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
    // Le handler n'a plus d'action a effectuer sur une vente aux enchères en
    // construction
    if(!this.getAuction().getStatus().belongsTo(Status.VALID))
    {
      this.requireStop();
      return false;
    }
    return true;
  }
  /**
   * Getter du service de gestion de la vente aux enchère du handler
   * @return Le service de gestion de la vente aux enchère du handler
   */
  protected SERVICE getService()
  {
    return this.service;
  }
  /**
   *
   * Setter du service de gestion de la vente aux enchère du handler
   * @param service Le service de gestion de la vente aux enchère du handler
   */
  private void setService(SERVICE service)
  {
    this.service = service;
  }
}
