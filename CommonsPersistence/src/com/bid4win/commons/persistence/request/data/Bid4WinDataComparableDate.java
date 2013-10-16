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
 * @author Emeric Fill�tre
 */
public class Bid4WinDataComparableDate<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinDataComparable<ENTITY, Bid4WinDateForRequest>
{
  /**
   * Constructeur dont la validit� du jeu de donn�es n'est pas exhaustive et dont
   * la comparaison sera du type "sup�rieur ou �gal"
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinDataComparableDate(Bid4WinField<? super ENTITY, ?, Bid4WinDateForRequest, ?> field,
                                   Bid4WinDate ... data)
  {
    super(field, Bid4WinDateForRequest.getDateForRequests(data));
  }
  /**
   * Constructeur
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param exhaustive Boolean indiquant si la validit� du jeu de donn�es est exhaustive
   * @param greaterThan Boolean indiquant si le champ doit �tre sup�rieur aux
   * donn�es du jeu test�
   * @param equalTo Boolean indiquant si le champ peut �tre �gal aux donn�es du
   * jeu test�
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinDataComparableDate(boolean exhaustive,
                                   Bid4WinField<? super ENTITY, ?, Bid4WinDateForRequest, ?> field,
                                   boolean greaterThan, boolean equalTo,
                                   Bid4WinDate ... data)
  {
    super(exhaustive, field, greaterThan, equalTo, Bid4WinDateForRequest.getDateForRequests(data));
  }
}
