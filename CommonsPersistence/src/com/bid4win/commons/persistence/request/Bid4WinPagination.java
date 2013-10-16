package com.bid4win.commons.persistence.request;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe défini la pagination potentiellement souhaitée pour une requête
 * de type liste<BR>
 * <BR>
 * @param <ENTITY> Définition de l'entité sur laquelle la requête doit être paginée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinPagination<ENTITY extends Bid4WinEntity<?, ?>>
{
  /** Plage des données à remonter */
  private Bid4WinRange range = null;
  /** Champs à utiliser pour ordonner la requête avant l'application de la plage de données */
  private Bid4WinOrder<ENTITY, ?>[] orders = null;

  /**
   * Constructeur
   * @param orders Champs à utiliser pour ordonner la requête
   */
  public Bid4WinPagination(Bid4WinOrder<ENTITY, ?> ... orders)
  {
    this(null, orders);
  }
  /**
   * Constructeur
   * @param range Plage des données à remonter
   * @param orders Champs à utiliser pour ordonner la requête avant l' application
   * de la plage de données
   */
  public Bid4WinPagination(Bid4WinRange range, Bid4WinOrder<ENTITY, ?> ... orders)
  {
    this.setRange(range);
    this.setOrders(orders);
  }

  /**
   * Getter de la plage des données à remonter
   * @return La plage des données à remonter
   */
  public Bid4WinRange getRange()
  {
    return this.range;
  }
  /**
   * Setter de la plage des données à remonter
   * @param range Plage des données à remonter à positionner
   */
  private void setRange(Bid4WinRange range)
  {
    if(range == null)
    {
      range = new Bid4WinRange();
    }
    this.range = range;
  }

  /**
   * Getter des champs à utiliser pour ordonner la requête
   * @return Les champs à utiliser pour ordonner la requête
   */
  protected Bid4WinOrder<ENTITY, ?>[] getOrders()
  {
    return this.orders;
  }
  /**
   * Setter des champs à utiliser pour ordonner la requête
   * @param orders Champs à utiliser pour ordonner la requête à positionner
   */
  private void setOrders(Bid4WinOrder<ENTITY, ?> ... orders)
  {
    this.orders = orders;
  }
}
