package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe ConnectionData<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ConnectionData.class)
public class ConnectionData_ extends Bid4WinEmbeddable_
{
  /** Définition de l'identifiant de la session liée à la connexion */
  public static volatile SingularAttribute<ConnectionData, String> sessionId;
  /** Définition du flag indiquant la rémanence de la connexion */
  public static volatile SingularAttribute<ConnectionData, Boolean> remanent;
  /** Définition de l'adresse IP de connexion */
  public static volatile SingularAttribute<ConnectionData, IpAddress> ipAddress;
  /** Définition de la date de début de connexion */
  public static volatile SingularAttribute<ConnectionData, Bid4WinDate> startDate;
  /** Définition de la raison de fin de connexion */
  public static volatile SingularAttribute<ConnectionData, DisconnectionReason> disconnectionReason;
}
