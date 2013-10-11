package com.bid4win.commons.persistence.usertype.collection;

import java.io.Serializable;

import com.bid4win.commons.core.collection.Bid4WinSet;

/**
 * Cette classe précise les principaux fonctionnements des 'types utilisateur'
 * gérant en base de données des sets sous la forme d'une seule colonne de type
 * chaîne de caractères<BR>
 * <BR>
 * @param <ELEMENT> Définition des éléments du set à gérer en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
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
