package com.bid4win.persistence.dao.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;

/**
 * DAO pour les entit�s de la classe I18n<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("I18nDao")
@Scope("singleton")
public class I18nDao extends PropertyAbstractDao_<I18n, I18nRoot>
{
  /** R�f�rence du DAO des racines d'internationalisation */
  @Autowired
  @Qualifier("I18nRootDao")
  private I18nRootDao rootDao;

  /**
   * Constructeur
   */
  protected I18nDao()
  {
    super(I18n.class);
  }
  
  /**
   * Getter du DAO des racines d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#getRootDao()
   */
  @Override
  public I18nRootDao getRootDao()
  {
    return this.rootDao;
  }
}
