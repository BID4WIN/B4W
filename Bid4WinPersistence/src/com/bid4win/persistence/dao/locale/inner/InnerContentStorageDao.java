package com.bid4win.persistence.dao.locale.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.resource.ResourceStorageDao_;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;

/**
 * DAO pour les entit�s de la classe InnerContentStorage<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("InnerContentStorageDao")
@Scope("singleton")
public class InnerContentStorageDao extends ResourceStorageDao_<InnerContentStorage, InnerContentType, InnerContentUsage>
{
  /** R�f�rence du DAO des utilisations de contenu */
  @Autowired
  @Qualifier("InnerContentUsageDao")
  private InnerContentUsageDao usageDao;

  /**
   * Constructeur
   */
  protected InnerContentStorageDao()
  {
    super(InnerContentStorage.class);
  }

  /**
   * Getter du DAO des utilisations de contenu
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceStorageDao_#getUsageDao()
   */
  @Override
  public InnerContentUsageDao getUsageDao()
  {
    return this.usageDao;
  }
}
