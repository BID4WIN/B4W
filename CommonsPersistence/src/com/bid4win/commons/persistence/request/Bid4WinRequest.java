package com.bid4win.commons.persistence.request;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe permet de définir une requête sur un type d'entité précis en lui
 * fournissant les critères et la potentielle pagination attendues<BR>
 * <BR>
 * @param <ENTITY> Définition de l'entité sur laquelle porte la requête<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinRequest<ENTITY extends Bid4WinEntity<?, ?>>
{
  /**  Critères à utiliser pour la requête */
  private Bid4WinCriteria<ENTITY> criteria = null;
  /** Pagination potentielle à utiliser pour la requête */
  private Bid4WinPagination<ENTITY> pagination = null;

  /**
   * Constructeur sans pagination ni critères
   */
  public Bid4WinRequest()
  {
    this(null, null);
  }
  /**
   * Constructeur sans pagination
   * @param criteria Critères à utiliser pour la requête
   */
  public Bid4WinRequest(Bid4WinCriteria<ENTITY> criteria)
  {
    this(criteria, null);
  }
  /**
   * Constructeur sans critères
   * @param pagination Pagination potentielle à utiliser pour la requête
   */
  public Bid4WinRequest(Bid4WinPagination<ENTITY> pagination)
  {
    this(null, pagination);
  }
  /**
   * Constructeur dont les critères sont exhaustifs
   * @param criteria Critères à utiliser pour la requête
   * @param pagination Pagination potentielle à utiliser pour la requête
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
   * Getter de la pagination potentielle à utiliser pour la requête
   * @return La pagination potentielle à utiliser pour la requête
   */
  public Bid4WinPagination<ENTITY> getPagination()
  {
    return this.pagination;
  }
  /**
   * Setter de la pagination potentielle à utiliser pour la requête
   * @param pagination Pagination potentielle à utiliser pour la requête à positionner
   */
  private void setPagination(Bid4WinPagination<ENTITY> pagination)
  {
    this.pagination = pagination;
  }
}
