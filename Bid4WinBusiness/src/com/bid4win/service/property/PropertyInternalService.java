package com.bid4win.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.service.property.PropertyAbstractInternalService_;
import com.bid4win.manager.property.PropertyManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion interne des propriétés incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyInternalService")
@Scope("singleton")
public class PropertyInternalService
       extends PropertyAbstractInternalService_<Property, PropertyRoot, SessionData,
                                                Account, PropertyInternalService>
{
  private static PropertyInternalService instance = null;
  public static PropertyInternalService getInstance()
  {
    return PropertyInternalService.instance;
  }

  /** Référence du manager de gestion des propriétés */
  @Autowired
  @Qualifier("PropertyManager")
  private PropertyManager manager = null;

  /**
   * L'oscar du rôle d'administration des produits et ressources associées est
   * attribué aux équipes techniques
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.TECH;
  }
  /**
   * Getter de la référence du manager interne de gestion des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractInternalService_#getManager()
   */
  @Override
  protected PropertyManager getManager()
  {
    return this.manager;
  }
  /**
   *
   * TODO A COMMENTER
   * @param self {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean#setSelf(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  protected void setSelf(PropertyInternalService self)
  {
    super.setSelf(self);
    PropertyInternalService.instance = this.self();
  }
}
