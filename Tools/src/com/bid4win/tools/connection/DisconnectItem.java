package com.bid4win.tools.connection;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.service.connection.ConnectionService;
import com.bid4win.tools.SwissKnife;
import com.bid4win.tools.console.Item;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class DisconnectItem extends Item<SwissKnife>
{
  /**
   * Constructeur
   * @param menu Menu dans lequel l'item est initialis�
   */
  public DisconnectItem(SwissKnife menu)
  {
    super("Disconnect", menu);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.tools.console.Item#execute()
   */
  @Override
  public boolean execute() throws Bid4WinException
  {
    // D�connecte tout compte utilisateur potentiellement connect�
    this.getBean("ConnectionService", ConnectionService.class).disconnect();
    return true;
  }
}
