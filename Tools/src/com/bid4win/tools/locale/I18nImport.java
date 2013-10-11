package com.bid4win.tools.locale;

import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.tools.property.PropertyAbstractImport;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class I18nImport extends PropertyAbstractImport<I18n, I18nRoot, Account>
{
  /**
   * 
   * TODO A COMMENTER
   * @param menu TODO A COMMENTER
   */
  public I18nImport(I18nManager menu)
  {
    super(menu);
  }
}
