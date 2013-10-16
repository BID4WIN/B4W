package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinCollection;

/**
 * Cette classe défini un status de vente aux enchères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Status extends Bid4WinObjectType<Status>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -3213258365243991173L;

  /** Status d'une vente aux enchères en construction */
  public final static Status WORKING = new Status("WORKING");
  /** Status d'une vente aux enchères valide */
  public final static Status VALID = new Status("VALID");
  /** Status d'une vente aux enchères en attente */
  public final static Status WAITING = new Status("WAITING", VALID);
  /** Status d'une vente aux enchères ouverte */
  public final static Status OPENED = new Status("OPENED", VALID);
  /** Status d'une vente aux enchères donc la pré-inscription a démarré */
  public final static Status PREBOOKING = new Status("PREBOOKING", OPENED);
  /** Status d'une vente aux enchères démarrée */
  public final static Status STARTED = new Status("STARTED", OPENED);
  /** Status d'une vente aux enchères en pause */
  public final static Status PAUSED = new Status("PAUSED", OPENED);
  /** Status d'une vente aux enchères en terminée */
  public final static Status ENDED = new Status("ENDED");
  /** Status d'une vente aux enchères en terminée car clôturée */
  public final static Status CLOSED = new Status("CLOSED", ENDED);
  /** Status d'une vente aux enchères en terminée car annulée */
  public final static Status CANCELED = new Status("CANCELED", ENDED);

  /** Définition du status de vente aux enchères par défaut */
  public final static Status DEFAULT = Bid4WinObjectType.getDefaultType(Status.class);

  /**
   * Cette méthode permet de récupérer tous les status existants
   * @return Tous les status définis
   */
  public static Bid4WinCollection<Status> getStatus()
  {
    return Bid4WinObjectType.getTypes(Status.class);
  }

  /**
   * Constructeur
   * @param code Code du status de vente aux enchères
   */
  private Status(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code du status de vente aux enchères
   * @param parent Parent du status de vente aux enchères
   */
  private Status(String code, Status parent)
  {
    super(code, parent);
  }
}
