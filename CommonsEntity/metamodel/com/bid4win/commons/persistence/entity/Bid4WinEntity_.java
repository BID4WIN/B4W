package com.bid4win.commons.persistence.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;

/**
 * Metamodel de la classe Bid4WinEntity<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Bid4WinEntity.class)
public class Bid4WinEntity_
{
  /** Definition de la version de l'entité */
  public static volatile SingularAttribute<Bid4WinEntity<?, ?>, Integer> version;
  /** Definition de la date de création de l'entité */
  public static volatile SingularAttribute<Bid4WinEntity<?, ?>, Bid4WinDate> createDate;
  /** Definition de la date de modification de l'entité */
  public static volatile SingularAttribute<Bid4WinEntity<?, ?>, Bid4WinDate> updateDate;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }

  /**
   * Permet de démarrer la protection des relations
   */
  protected static void startProtection()
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /**
   * Permet d'arréter la protection des relations
   */
  protected static void stopProtection()
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
