package com.bid4win.commons.persistence.usertype;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.usertype.account.security.RoleUserType;
import com.bid4win.commons.persistence.usertype.connection.DisconnectionReasonUserType;
import com.bid4win.commons.persistence.usertype.core.Bid4WinDateUserType;

/**
 * Cette classe sert uniquement pour la d�finition des UserType utilis�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@TypeDefs(value = {@TypeDef(defaultForType = Bid4WinDate.class,
                            name = "DATE_TIME", typeClass = Bid4WinDateUserType.class,
                            parameters = {@Parameter(name = Bid4WinDateUserType.TIME_PARAMETER,
                                                     value = "true")}),
                   @TypeDef(name = "DATE", typeClass = Bid4WinDateUserType.class,
                            parameters = {@Parameter(name = Bid4WinDateUserType.TIME_PARAMETER,
                                                     value = "false")}),
                   @TypeDef(defaultForType = Role.class,
                            name = "ROLE", typeClass = RoleUserType.class),
                   @TypeDef(defaultForType = DisconnectionReason.class,
                            name = "DISCONNECTION_REASON", typeClass = DisconnectionReasonUserType.class)})
public class Bid4WinUserTypeDefinition
{
  // Cette classe sert uniquement pour la d�finition des UserType utilis�s
}
