package com.bid4win.tools.console;

/**
 *  Cette classe est l'item du menu qui permet de revenir au menu pr�c�dent<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ItemPrevious extends Item<Menu<?>>
{
  /**
   * Constructeur
   * @param menu Menu dans lequel l'item est initialis�
   */
  public ItemPrevious(Menu<?> menu)
  {
    super("Go back", menu);
  }

  /**
   * L'execution de cet item quitte le menu auquel il est rattach�
   * @return {@inheritDoc}
   * @see com.bid4win.tools.console.Item#execute()
   */
  @Override
  public boolean execute()
  {
    this.getMenu().setStopped(true);
    return false;
  }
}
