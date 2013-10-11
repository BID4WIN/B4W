package com.bid4win.commons.persistence.usertype.collection;

import java.io.Serializable;

import com.bid4win.commons.core.collection.Bid4WinSet;

/**
 * Cette classe pr�cise les principaux fonctionnements des 'types utilisateur'
 * g�rant en base de donn�es des sets sous la forme d'une seule colonne de type
 * cha�ne de caract�res<BR>
 * <BR>
 * @param <ELEMENT> D�finition des �l�ments du set � g�rer en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinSetUserType<ELEMENT extends Serializable>
       extends Bid4WinCollectionUserType<ELEMENT, Bid4WinSet<ELEMENT>>
{
  /**
   * Constructeur
   */
  @SuppressWarnings("unchecked")
  protected Bid4WinSetUserType()
  {
    super((Class<Bid4WinSet<ELEMENT>>)new Bid4WinSet<ELEMENT>().getClass());
  }
}
