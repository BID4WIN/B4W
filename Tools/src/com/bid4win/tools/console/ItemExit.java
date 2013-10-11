package com.bid4win.tools.console;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.service.connection.ConnectionService;

/**
 *  Cette classe est l'item du menu qui permet de le quitter<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ItemExit extends Item<Menu<?>>
{
  /**
   * Constructeur
   * @param menu Menu dans lequel l'item est initialis�
   */
  public ItemExit(Menu<?> menu)
  {
    super("Exit", menu);
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
    if(this.getMenu().isMainMenu())
    {
      // D�connecte tout compte utilisateur potentiellement connect�
      try
      {
        this.getBean("ConnectionService", ConnectionService.class).disconnect();
      }
      catch(Bid4WinException ex)
      {
        System.out.println(ex.getMessage());
      }
      System.exit(0);
    }
    return false;
  }
}
