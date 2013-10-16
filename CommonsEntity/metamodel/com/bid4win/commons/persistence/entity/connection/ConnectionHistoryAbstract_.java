package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;

/**
 * Metamodel de la classe ConnectionHistoryAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ConnectionHistoryAbstract.class)
public class ConnectionHistoryAbstract_ extends AccountBasedEntityMultipleAutoID_
{
  /** TODO A COMMENTER */
  public static volatile SingularAttribute<ConnectionHistoryAbstract<?, ?>, ConnectionData> data;
  /** Définition de la date de fin de connexion */
  public static volatile SingularAttribute<ConnectionData, Bid4WinDate> endDate;
  /** Définition du flag indiquant si une réutilisation de la rémanence a déjà été tentée */
//  public static volatile SingularAttribute<ConnectionHistoryAbstract<?, ?>, Boolean> reuseAttempted;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
