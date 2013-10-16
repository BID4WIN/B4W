package com.bid4win.commons.persistence.request;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe permet de d�finir une requ�te sur un type d'entit� pr�cis en lui
 * fournissant les crit�res et la potentielle pagination attendues<BR>
 * <BR>
 * @param <ENTITY> D�finition de l'entit� sur laquelle porte la requ�te<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinRequest<ENTITY extends Bid4WinEntity<?, ?>>
{
  /**  Crit�res � utiliser pour la requ�te */
  private Bid4WinCriteria<ENTITY> criteria = null;
  /** Pagination potentielle � utiliser pour la requ�te */
  private Bid4WinPagination<ENTITY> pagination = null;

  /**
   * Constructeur sans pagination ni crit�res
   */
  public Bid4WinRequest()
  {
    this(null, null);
  }
  /**
   * Constructeur sans pagination
   * @param criteria Crit�res � utiliser pour la requ�te
   */
  public Bid4WinRequest(Bid4WinCriteria<ENTITY> criteria)
  {
    this(criteria, null);
  }
  /**
   * Constructeur sans crit�res
   * @param pagination Pagination potentielle � utiliser pour la requ�te
   */
  public Bid4WinRequest(Bid4WinPagination<ENTITY> pagination)
  {
    this(null, pagination);
  }
  /**
   * Constructeur dont les crit�res sont exhaustifs
   * @param criteria Crit�res � utiliser pour la requ�te
   * @param pagination Pagination potentielle � utiliser pour la requ�te
   */
  public Bid4WinRequest(Bid4WinCriteria<ENTITY> criteria,
                        Bid4WinPagination<ENTITY> pagination)
  {
    this.setCriteria(criteria);
    this.setPagination(pagination);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinCriteria<ENTITY> getCriteria()
  {
    return this.criteria;
  }
  /**
   *
   * TODO A COMMENTER
   * @param criteria TODO A COMMENTER
   */
  private void setCriteria(Bid4WinCriteria<ENTITY> criteria)
  {
    this.criteria = criteria;
  }
  /**
   * Getter de la pagination potentielle � utiliser pour la requ�te
   * @return La pagination potentielle � utiliser pour la requ�te
   */
  public Bid4WinPagination<ENTITY> getPagination()
  {
    return this.pagination;
  }
  /**
   * Setter de la pagination potentielle � utiliser pour la requ�te
   * @param pagination Pagination potentielle � utiliser pour la requ�te � positionner
   */
  private void setPagination(Bid4WinPagination<ENTITY> pagination)
  {
    this.pagination = pagination;
  }
}
