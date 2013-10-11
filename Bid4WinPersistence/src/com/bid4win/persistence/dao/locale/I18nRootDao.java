package com.bid4win.persistence.dao.locale;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;

/**
 * DAO pour les entit�s de la classe I18nRoot<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("I18nRootDao")
@Scope("singleton")
public class I18nRootDao extends PropertyRootAbstractDao_<I18nRoot, I18n>
{
  /**
   * Constructeur
   */
  protected I18nRootDao()
  {
    super(I18nRoot.class);
  }

  /**
   * D�fini la cl� unique de la racine des propri�t�s d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_#getUniqueId()
   */
  @Override
  public int getUniqueId()
  {
    return I18nRoot.UNIQUE_ID;
  }

  /**
   * Cette m�thode permet de cr�er la racine des propri�t�s d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_#createPropertyRoot()
   */
  @Override
  protected I18nRoot createPropertyRoot()
  {
    return new I18nRoot();
  }
}
