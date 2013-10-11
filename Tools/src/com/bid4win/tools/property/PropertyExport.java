package com.bid4win.tools.property;

import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PropertyExport extends PropertyAbstractExport<Property, PropertyRoot, Account>
{
  /**
   * 
   * TODO A COMMENTER
   * @param menu TODO A COMMENTER
   */
  public PropertyExport(PropertyManager menu)
  {
    super(menu);
  }
}
