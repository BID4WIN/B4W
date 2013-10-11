package com.bid4win.tools.property;

import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;
import com.bid4win.tools.console.Menu;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PropertyManager extends PropertyAbstractManager<Property, PropertyRoot, Account>
{
  /**
   * Constructeur d'un menu principal
   */
  public PropertyManager()
  {
    super();
  }
  /**
   * Constructeur d'un sous menu
   * @param menu Menu dans lequel le menu est initialisé
   */
  public PropertyManager(Menu<?> menu)
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
    this.addItem(new PropertyExport(this));
    this.addItem(new PropertyImport(this));
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
    return Type.PROPERTY;
  }
}
