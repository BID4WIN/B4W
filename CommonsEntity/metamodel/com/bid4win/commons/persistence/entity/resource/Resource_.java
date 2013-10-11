package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe Resource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Resource.class)
public class Resource_ extends Bid4WinEntityAutoID_
{
  /** D�finition du champ permettant le for�age de la modification de la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, Integer> updateForce;
  /** D�finition de l'emplacement de stockage de la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, String> path;
  /** D�finition du nom de la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, String> name;
  /** D�finition du type de la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, ResourceType<?>> type;
  /** D�finition de chemin d'acc�s complet � la ressource */
  public static volatile SingularAttribute<Resource<?, ?>, String> fullPath;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
