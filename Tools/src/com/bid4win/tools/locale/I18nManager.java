package com.bid4win.tools.locale;

import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.tools.console.Menu;
import com.bid4win.tools.property.PropertyAbstractManager;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class I18nManager extends PropertyAbstractManager<I18n, I18nRoot, Account>
{
  /**
   * Constructeur d'un menu principal
   */
  public I18nManager()
  {
    super();
  }
  /**
   * Constructeur d'un sous menu
   * @param menu Menu dans lequel le menu est initialisé
   */
  public I18nManager(Menu<?> menu)
  {
    super(menu);
  }

  /**
   * 
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.tools.console.Menu#addSpecificItems()
   */
  @Override
  protected void addSpecificItems()
  {
    this.addItem(new I18nExport(this));
    this.addItem(new I18nImport(this));
  }
  
  /**
   * 
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.property.PropertyAbstractManager#getPropertyType()
   */
  @Override
  public Type getPropertyType()
  {
    return Type.I18N;
  }
}
