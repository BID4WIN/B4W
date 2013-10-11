package com.bid4win.commons.persistence.usertype.collection;

import java.io.Serializable;

import com.bid4win.commons.core.collection.Bid4WinList;

/**
 * Cette classe pr�cise les principaux fonctionnements des 'types utilisateur'
 * g�rant en base de donn�es des listes sous la forme d'une seule colonne de type
 * cha�ne de caract�res<BR>
 * <BR>
 * @param <ELEMENT> D�finition des �l�ments de la liste � g�rer en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinListUserType<ELEMENT extends Serializable>
       extends Bid4WinCollectionUserType<ELEMENT, Bid4WinList<ELEMENT>>
{
  /**
   * Constructeur
   */
  @SuppressWarnings("unchecked")
  protected Bid4WinListUserType()
  {
    super((Class<Bid4WinList<ELEMENT>>)new Bid4WinList<ELEMENT>().getClass());
  }
}
