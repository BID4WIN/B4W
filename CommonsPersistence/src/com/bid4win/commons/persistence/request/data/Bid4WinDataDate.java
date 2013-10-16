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
public class Bid4WinDataDate<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinData<ENTITY, Bid4WinDateForRequest>
{
  /**
   * Constructeur dont la validit� du jeu de donn�es n'est pas exhaustive et le
   * test est l'�galit�
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinDataDate(Bid4WinField<? super ENTITY, ?, Bid4WinDateForRequest, ?> field,
                         Bid4WinDate ... data)
  {
    super(field, Bid4WinDateForRequest.getDateForRequests(data));
  }
  /**
   * Constructeur dont le test est l'�galit�
   * @param exhaustive Boolean indiquant si la validit� du jeu de donn�es est exhaustive
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinDataDate(boolean exhaustive,
                         Bid4WinField<? super ENTITY, ?, Bid4WinDateForRequest, ?> field,
                         Bid4WinDate ... data)
  {
    super(exhaustive, field, Bid4WinDateForRequest.getDateForRequests(data));
  }
  /**
   * Constructeur dont la validit� du jeu de donn�es n'est pas exhaustive
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param equalTo Boolean indiquant si le test est l'�galit�
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinDataDate(Bid4WinField<? super ENTITY, ?, Bid4WinDateForRequest, ?> field,
                         boolean equalTo,
                         Bid4WinDate ... data)
  {
    super(field, equalTo, Bid4WinDateForRequest.getDateForRequests(data));
  }
  /**
   * Constructeur
   * @param exhaustive Boolean indiquant si la validit� du jeu de donn�es est exhaustive
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param equalTo Boolean indiquant si le test est l'�galit�
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinDataDate(boolean exhaustive,
                         Bid4WinField<? super ENTITY, ?, Bid4WinDateForRequest, ?> field,
                         boolean equalTo,
                         Bid4WinDateForRequest ... data)
  {

    super(exhaustive, field, equalTo, data);
  }
}
