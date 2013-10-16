package com.bid4win.commons.persistence.request;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe d�fini la pagination potentiellement souhait�e pour une requ�te
 * de type liste<BR>
 * <BR>
 * @param <ENTITY> D�finition de l'entit� sur laquelle la requ�te doit �tre pagin�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinPagination<ENTITY extends Bid4WinEntity<?, ?>>
{
  /** Plage des donn�es � remonter */
  private Bid4WinRange range = null;
  /** Champs � utiliser pour ordonner la requ�te avant l'application de la plage de donn�es */
  private Bid4WinOrder<ENTITY, ?>[] orders = null;

  /**
   * Constructeur
   * @param orders Champs � utiliser pour ordonner la requ�te
   */
  public Bid4WinPagination(Bid4WinOrder<ENTITY, ?> ... orders)
  {
    this(null, orders);
  }
  /**
   * Constructeur
   * @param range Plage des donn�es � remonter
   * @param orders Champs � utiliser pour ordonner la requ�te avant l' application
   * de la plage de donn�es
   */
  public Bid4WinPagination(Bid4WinRange range, Bid4WinOrder<ENTITY, ?> ... orders)
  {
    this.setRange(range);
    this.setOrders(orders);
  }

  /**
   * Getter de la plage des donn�es � remonter
   * @return La plage des donn�es � remonter
   */
  public Bid4WinRange getRange()
  {
    return this.range;
  }
  /**
   * Setter de la plage des donn�es � remonter
   * @param range Plage des donn�es � remonter � positionner
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
   * Getter des champs � utiliser pour ordonner la requ�te
   * @return Les champs � utiliser pour ordonner la requ�te
   */
  protected Bid4WinOrder<ENTITY, ?>[] getOrders()
  {
    return this.orders;
  }
  /**
   * Setter des champs � utiliser pour ordonner la requ�te
   * @param orders Champs � utiliser pour ordonner la requ�te � positionner
   */
  private void setOrders(Bid4WinOrder<ENTITY, ?> ... orders)
  {
    this.orders = orders;
  }
}
