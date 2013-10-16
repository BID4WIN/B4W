package com.bid4win.commons.persistence.request;

import java.util.List;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe représente les résultats d'une requête sur une liste d'entités
 * potentiellement paginée<BR>
 * <BR>
 * @param <ENTITY> Définition des entités retournées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinResult<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinList<ENTITY>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -3578611355887204861L;

  /** Nombre total d'entités entrant dans le périmètre de la requête */
  private long totalCount = 0;

  /**
   * Constructeur d'un résultat de requête dont toutes les entités sont fournies
   * en paramètre
   * @param entityList Liste complètes des entités résultat
   */
  public Bid4WinResult(List<ENTITY> entityList)
  {
    this(entityList, entityList.size());
  }
  /**
   * Constructeur d'un résultat de requête paginées dont une partie seulement des
   * entités est fournie en paramètre
   * @param entityList Nombre total d'entités entrant dans le périmètre de la requête
   * @param totalCount Liste des entités liées à la pagination demandée
   */
  public Bid4WinResult(List<ENTITY> entityList, long totalCount)
  {
    super(entityList, true);
    this.setTotalCount(totalCount);
  }

  /**
   * Getter du nombre total d'entités entrant dans le périmètre de la requête
   * @return Le nombre total d'entités entrant dans le périmètre de la requête
   */
  public long getTotalCount()
  {
    return this.totalCount;
  }
  /**
   * Setter du nombre total d'entités entrant dans le périmètre de la requête
   * @param totalCount Nombre total d'entités entrant dans le périmètre de la requête
   * à positionner
   */
  private void setTotalCount(long totalCount)
  {
    this.totalCount = totalCount;
  }
}
