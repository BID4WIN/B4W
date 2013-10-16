package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe ConnectionData<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(ConnectionData.class)
public class ConnectionData_ extends Bid4WinEmbeddable_
{
  /** D�finition de l'identifiant de la session li�e � la connexion */
  public static volatile SingularAttribute<ConnectionData, String> sessionId;
  /** D�finition du flag indiquant la r�manence de la connexion */
  public static volatile SingularAttribute<ConnectionData, Boolean> remanent;
  /** D�finition de l'adresse IP de connexion */
  public static volatile SingularAttribute<ConnectionData, IpAddress> ipAddress;
  /** D�finition de la date de d�but de connexion */
  public static volatile SingularAttribute<ConnectionData, Bid4WinDate> startDate;
  /** D�finition de la raison de fin de connexion */
  public static volatile SingularAttribute<ConnectionData, DisconnectionReason> disconnectionReason;
}
