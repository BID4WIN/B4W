package com.bid4win.service.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.service.property.PropertyAbstractInternalService_;
import com.bid4win.manager.locale.I18nManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion interne des propriétés d'internationalisation incluant la
 * gestion des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("I18nInternalService")
@Scope("singleton")
public class I18nInternalService
       extends PropertyAbstractInternalService_<I18n, I18nRoot, SessionData,
                                                Account, I18nInternalService>
{
  /** Référence du manager de gestion des propriétés d'internationalisation */
  @Autowired
  @Qualifier("I18nManager")
  private I18nManager manager = null;

  /**
   * L'oscar du rôle d'administration des propriétés d'internationalisation est
   * attribué aux linguistes
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.I18N;
  }
  /**
   * Getter de la référence du manager de gestion des propriétés d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractInternalService_#getManager()
   */
  @Override
  protected I18nManager getManager()
  {
    return this.manager;
  }
}
