package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID_;

/**
 * Metamodel de la classe ConnectionAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(ConnectionAbstract.class)
public class ConnectionAbstract_ extends AccountBasedEntityMultipleGeneratedID_
{
  /** D�finition de l'adresse IP de connexion */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, IpAddress> ipAddress;
  /** D�finition de l'identifiant du process � l'origine de la connexion */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, String> processId;
  /** D�finition du flag indiquant la r�manence de la connexion */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, Boolean> remanent;
  /** D�finition du flag indiquant si la connexion est active */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, Boolean> active;
  /** D�finition de la raison de fin de connexion */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, DisconnectionReason> disconnectionReason;
  /** D�finition de la date de d�but de connexion */
  public static volatile SingularAttribute<ConnectionAbstract<?, ?, ?>, Bid4WinDate> startDate;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
