package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel de la classe ConnectionAbstractStub<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ConnectionAbstractStub.class)
public abstract class ConnectionAbstractStub_ extends ConnectionAbstract_
{
  /** Définition de l'adresse IP de connexion */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<ConnectionAbstractStub, IpAddress> ipAddress;
}
