package com.bid4win.tools.connection;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.io.KeyboardSelection;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.service.connection.ConnectionService;
import com.bid4win.tools.SwissKnife;
import com.bid4win.tools.console.Item;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectItem extends Item<SwissKnife>
{
  /**
   * Constructeur
   * @param menu Menu dans lequel l'item est initialisé
   */
  public ConnectItem(SwissKnife menu)
  {
    super("Connect", menu);
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
    // Déconnecte tout compte utilisateur potentiellement connecté
    this.getBean("ConnectionService", ConnectionService.class).disconnect();
    // Affiche le libellé de l'action
    System.out.println(this.getWording());
    System.out.println("Choose account:");
    String loginOrEmail = KeyboardSelection.getInstance().parseString();
    System.out.println("Enter password:");
    String password = String.valueOf(KeyboardSelection.getInstance().parseString().hashCode());
    /*byte[] passwordByte = KeyboardSelection.getInstance().parseString().getBytes();
    String password = null;
    try
    {
      password = new String(MessageDigest.getInstance("MD5").digest(passwordByte));
    }
    catch(NoSuchAlgorithmException ex)
    {
      throw new TechnicalException(ex);
    }*/
    System.out.println("");
    try
    {

      this.getBean("ConnectionService", ConnectionService.class).connect(
          loginOrEmail, new Password(password), false);
      System.out.println("Connected");
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      System.out.println("Connection failed");
    }
    return true;
  }
}
