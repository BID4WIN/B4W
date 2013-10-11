package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel de la classe ConnectionAbstractStub<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(ConnectionAbstractStub.class)
public abstract class ConnectionAbstractStub_ extends ConnectionAbstract_
{
  /** D�finition de l'adresse IP de connexion */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<ConnectionAbstractStub, IpAddress> ipAddress;
}
