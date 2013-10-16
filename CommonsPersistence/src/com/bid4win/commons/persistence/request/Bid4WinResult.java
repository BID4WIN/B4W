package com.bid4win.commons.persistence.request;

import java.util.List;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe repr�sente les r�sultats d'une requ�te sur une liste d'entit�s
 * potentiellement pagin�e<BR>
 * <BR>
 * @param <ENTITY> D�finition des entit�s retourn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinResult<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinList<ENTITY>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -3578611355887204861L;

  /** Nombre total d'entit�s entrant dans le p�rim�tre de la requ�te */
  private long totalCount = 0;

  /**
   * Constructeur d'un r�sultat de requ�te dont toutes les entit�s sont fournies
   * en param�tre
   * @param entityList Liste compl�tes des entit�s r�sultat
   */
  public Bid4WinResult(List<ENTITY> entityList)
  {
    this(entityList, entityList.size());
  }
  /**
   * Constructeur d'un r�sultat de requ�te pagin�es dont une partie seulement des
   * entit�s est fournie en param�tre
   * @param entityList Nombre total d'entit�s entrant dans le p�rim�tre de la requ�te
   * @param totalCount Liste des entit�s li�es � la pagination demand�e
   */
  public Bid4WinResult(List<ENTITY> entityList, long totalCount)
  {
    super(entityList, true);
    this.setTotalCount(totalCount);
  }

  /**
   * Getter du nombre total d'entit�s entrant dans le p�rim�tre de la requ�te
   * @return Le nombre total d'entit�s entrant dans le p�rim�tre de la requ�te
   */
  public long getTotalCount()
  {
    return this.totalCount;
  }
  /**
   * Setter du nombre total d'entit�s entrant dans le p�rim�tre de la requ�te
   * @param totalCount Nombre total d'entit�s entrant dans le p�rim�tre de la requ�te
   * � positionner
   */
  private void setTotalCount(long totalCount)
  {
    this.totalCount = totalCount;
  }
}
