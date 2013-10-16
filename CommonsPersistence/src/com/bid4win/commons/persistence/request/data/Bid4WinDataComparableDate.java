package com.bid4win.commons.persistence.request.data;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinField;
import com.bid4win.commons.persistence.entity.core.Bid4WinDateForRequest;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDataComparableDate<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinDataComparable<ENTITY, Bid4WinDateForRequest>
{
  /**
   * Constructeur dont la validité du jeu de données n'est pas exhaustive et dont
   * la comparaison sera du type "supérieur ou égal"
   * @param field Définition du champ concerné par le jeu de données
   * @param data Jeu de données appliquées au champ défini
   */
  public Bid4WinDataComparableDate(Bid4WinField<? super ENTITY, ?, Bid4WinDateForRequest, ?> field,
                                   Bid4WinDate ... data)
  {
    super(field, Bid4WinDateForRequest.getDateForRequests(data));
  }
  /**
   * Constructeur
   * @param field Définition du champ concerné par le jeu de données
   * @param exhaustive Boolean indiquant si la validité du jeu de données est exhaustive
   * @param greaterThan Boolean indiquant si le champ doit être supérieur aux
   * données du jeu testé
   * @param equalTo Boolean indiquant si le champ peut être égal aux données du
   * jeu testé
   * @param data Jeu de données appliquées au champ défini
   */
  public Bid4WinDataComparableDate(boolean exhaustive,
                                   Bid4WinField<? super ENTITY, ?, Bid4WinDateForRequest, ?> field,
                                   boolean greaterThan, boolean equalTo,
                                   Bid4WinDate ... data)
  {
    super(exhaustive, field, greaterThan, equalTo, Bid4WinDateForRequest.getDateForRequests(data));
  }
}
