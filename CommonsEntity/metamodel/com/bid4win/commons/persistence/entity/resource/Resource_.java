package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe Resource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Resource.class)
public class Resource_ extends Bid4WinEntityAutoID_
{
  /** Définition du champ permettant le forçage de la modification de la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, Integer> updateForce;
  /** Définition de l'emplacement de stockage de la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, String> path;
  /** Définition du nom de la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, String> name;
  /** Définition du type de la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, ResourceType<?>> type;
  /** Définition de chemin d'accès complet à la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, String> fullPath;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
