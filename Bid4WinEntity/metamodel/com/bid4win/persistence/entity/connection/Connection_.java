package com.bid4win.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract_;
import com.bid4win.commons.persistence.entity.connection.IpAddress;

/**
 * Metamodel de la classe Connection<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Connection.class)
public abstract class Connection_ extends ConnectionAbstract_
{
  /** Définition de l'adresse IP de connexion */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<Connection, IpAddress> ipAddress;
}
