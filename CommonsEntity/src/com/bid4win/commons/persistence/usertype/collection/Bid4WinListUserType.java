package com.bid4win.commons.persistence.usertype.collection;

import java.io.Serializable;

import com.bid4win.commons.core.collection.Bid4WinList;

/**
 * Cette classe précise les principaux fonctionnements des 'types utilisateur'
 * gérant en base de données des listes sous la forme d'une seule colonne de type
 * chaîne de caractères<BR>
 * <BR>
 * @param <ELEMENT> Définition des éléments de la liste à gérer en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
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
