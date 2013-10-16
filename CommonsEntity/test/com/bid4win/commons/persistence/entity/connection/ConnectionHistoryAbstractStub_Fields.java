package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;

/**
 * D�finition des acc�s aux champs de la classe ConnectionHistoryAbstractStub<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ConnectionHistoryAbstractStub_Fields extends ConnectionHistoryAbstract_Fields
{
  /** Definition du champ correspondant � TODO A COMMENTER */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static final Bid4WinFieldSimple<ConnectionHistoryAbstractStub, ConnectionData> DATA =
      new Bid4WinFieldSimple<ConnectionHistoryAbstractStub, ConnectionData>(null, ConnectionHistoryAbstractStub_.data);
  public static final Bid4WinFieldSimpleToSimple<ConnectionHistoryAbstractStub, ConnectionData, String> SESSION_ID =
      new Bid4WinFieldSimpleToSimple<ConnectionHistoryAbstractStub, ConnectionData, String>(DATA, ConnectionData_Fields.SESSION_ID);
}
