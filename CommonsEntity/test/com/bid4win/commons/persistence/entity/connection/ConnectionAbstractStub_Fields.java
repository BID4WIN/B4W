package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Définition des accès aux champs de la classe ConnectionAbstractStub<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionAbstractStub_Fields extends ConnectionAbstract_Fields
{
  /** Definition du champ correspondant à TODO A COMMENTER */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static final Bid4WinFieldSimple<ConnectionAbstractStub, ConnectionData> DATA =
      new Bid4WinFieldSimple<ConnectionAbstractStub, ConnectionData>(null, ConnectionAbstractStub_.data);
}
