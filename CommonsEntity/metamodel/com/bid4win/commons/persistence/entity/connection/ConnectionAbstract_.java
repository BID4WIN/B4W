package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID_;

/**
 * Metamodel de la classe ConnectionAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ConnectionAbstract.class)
public class ConnectionAbstract_ extends AccountBasedEntityMultipleGeneratedID_
{
  /** Définition de l'identifiant du process à l'origine de la connexion */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, String> processId;
  /** Définition du flag indiquant si la connexion est active */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, Boolean> active;
  /** TODO A COMMENTER */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, ConnectionData> data;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
