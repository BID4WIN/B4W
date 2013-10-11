package com.bid4win.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract_;
import com.bid4win.commons.persistence.entity.connection.IpAddress;

/**
 * Metamodel de la classe ConnectionHistory<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(ConnectionHistory.class)
public abstract class ConnectionHistory_ extends ConnectionHistoryAbstract_
{
  /** D�finition de l'adresse IP de connexion */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<ConnectionHistory, IpAddress> ipAddress;
}
