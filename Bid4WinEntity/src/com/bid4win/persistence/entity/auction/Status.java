package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinCollection;

/**
 * Cette classe d�fini un status de vente aux ench�res<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Status extends Bid4WinObjectType<Status>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -3213258365243991173L;

  /** Status d'une vente aux ench�res en construction */
  public final static Status WORKING = new Status("WORKING");
  /** Status d'une vente aux ench�res valide */
  public final static Status VALID = new Status("VALID");
  /** Status d'une vente aux ench�res en attente */
  public final static Status WAITING = new Status("WAITING", VALID);
  /** Status d'une vente aux ench�res ouverte */
  public final static Status OPENED = new Status("OPENED", VALID);
  /** Status d'une vente aux ench�res donc la pr�-inscription a d�marr� */
  public final static Status PREBOOKING = new Status("PREBOOKING", OPENED);
  /** Status d'une vente aux ench�res d�marr�e */
  public final static Status STARTED = new Status("STARTED", OPENED);
  /** Status d'une vente aux ench�res en pause */
  public final static Status PAUSED = new Status("PAUSED", OPENED);
  /** Status d'une vente aux ench�res en termin�e */
  public final static Status ENDED = new Status("ENDED");
  /** Status d'une vente aux ench�res en termin�e car cl�tur�e */
  public final static Status CLOSED = new Status("CLOSED", ENDED);
  /** Status d'une vente aux ench�res en termin�e car annul�e */
  public final static Status CANCELED = new Status("CANCELED", ENDED);

  /** D�finition du status de vente aux ench�res par d�faut */
  public final static Status DEFAULT = Bid4WinObjectType.getDefaultType(Status.class);

  /**
   * Cette m�thode permet de r�cup�rer tous les status existants
   * @return Tous les status d�finis
   */
  public static Bid4WinCollection<Status> getStatus()
  {
    return Bid4WinObjectType.getTypes(Status.class);
  }

  /**
   * Constructeur
   * @param code Code du status de vente aux ench�res
   */
  private Status(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code du status de vente aux ench�res
   * @param parent Parent du status de vente aux ench�res
   */
  private Status(String code, Status parent)
  {
    super(code, parent);
  }
}
