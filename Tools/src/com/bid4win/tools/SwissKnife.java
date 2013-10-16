package com.bid4win.tools;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdProcess;
import com.bid4win.service.account.AccountService;
import com.bid4win.service.connection.SessionData;
import com.bid4win.tools.connection.ConnectItem;
import com.bid4win.tools.connection.DisconnectItem;
import com.bid4win.tools.console.Menu;
import com.bid4win.tools.dependency.DependencyManager;
import com.bid4win.tools.locale.I18nManager;
import com.bid4win.tools.property.PropertyManager;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class SwissKnife extends Menu<Menu<?>>
{
  /**
   *
   * TODO A COMMENTER
   * @param args TODO A COMMENTER
   */
  public static void main(String[] args)
  {
    SwissKnife swissKnife = new SwissKnife();
    swissKnife.start(args);
  }

  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.tools.console.Menu#addSpecificItems()
   */
  @Override
  protected void addSpecificItems()
  {
    this.addItem(new ConnectItem(this));
    this.addItem(new DisconnectItem(this));
    this.addItem(new DependencyManager(this));
    this.addItem(new PropertyManager(this));
    this.addItem(new I18nManager(this));
  }

  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.tools.console.Menu#displayMenu()
   */
  @Override
  protected void displayMenu()
  {
    // S'assure de la validité de la session
    System.out.println("********************************************************************");
    try
    {
      SessionData data = this.getSession();
      System.out.println("*** Session ID " + data.getSessionId());
      System.out.println("*** Process ID " + IdProcess.ID);
      System.out.println("*** " + data.getIpAddress().render());
      if(data.getConnection() == null)
      {
        System.out.println("*** Not connected");
      }
      else
      {
        System.out.println("*** " + data.getConnection().getAccount().getCredential().getLogin().getValue() + " connected");
      }
    }
    catch(Bid4WinException ex)
    {
      System.out.println(ex.getMessage());
    }
    super.displayMenu();
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected AccountService getAccountService() throws UserException
  {
    return this.getBean("AccountService", AccountService.class);
  }
}
