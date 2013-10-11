package com.bid4win.service.locale.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.service.resource.ResourceRepositoryMultiPartService_;
import com.bid4win.manager.locale.inner.InnerContentManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.locale.resource.InnerContent;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion de contenus internationalisés internes à l'application et
 * de leurs utilisations incluant la gestion des transactions ainsi que celle des
 * habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentService")
@Scope("singleton")
public class InnerContentService
       extends ResourceRepositoryMultiPartService_<InnerContentStorage, InnerContentUsage,
                                                   InnerContentType, Language, InnerContent,
                                                   SessionData, Account, InnerContentService>
{
  /** Référence du manager de gestion des contenus et de leurs utilisations */
  @Autowired
  @Qualifier("InnerContentManager")
  private InnerContentManager manager = null;

  /**
   * L'oscar du rôle d'administration des contenus et de leurs utilisations est
   * attribué aux gestionnaires métier
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.I18N;
  }
  /**
   * Getter de la référence du manager interne de gestion des contenus
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceRepositoryMultiPartService_#getManager()
   */
  @Override
  protected InnerContentManager getManager()
  {
    return this.manager;
  }
}
