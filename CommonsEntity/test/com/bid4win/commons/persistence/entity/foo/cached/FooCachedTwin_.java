package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooCachedTwin<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooCachedTwin.class)
public abstract class FooCachedTwin_ extends FooCached_
{
  /** Définition du jumeau de l'objet */
  public static volatile SingularAttribute<FooCachedTwin, FooCachedTwin> twin;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    FooCachedTwin_Relations.NODE_TWIN.addNode(FooCachedTwin_Relations.NODE_TWIN);
    Bid4WinEntity_.stopProtection();
  }
}