package com.bid4win.commons.persistence.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;

/**
 * Metamodel de la classe Bid4WinEntity<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Bid4WinEntity.class)
public class Bid4WinEntity_
{
  /** Definition de la version de l'entit� */
  public static volatile SingularAttribute<Bid4WinEntity<?, ?>, Integer> version;
  /** Definition de la date de cr�ation de l'entit� */
  public static volatile SingularAttribute<Bid4WinEntity<?, ?>, Bid4WinDate> createDate;
  /** Definition de la date de modification de l'entit� */
  public static volatile SingularAttribute<Bid4WinEntity<?, ?>, Bid4WinDate> updateDate;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }

  /**
   * Permet de d�marrer la protection des relations
   */
  protected static void startProtection()
  {
    Bid4WinEntity_Relations.startProtection();
  }
  /**
   * Permet d'arr�ter la protection des relations
   */
  protected static void stopProtection()
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
