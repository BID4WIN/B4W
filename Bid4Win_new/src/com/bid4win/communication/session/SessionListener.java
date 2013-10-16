package com.bid4win.communication.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.bid4win.commons.core.reference.PropertyRef;
import com.bid4win.service.connection.ConnectionService;
import com.bid4win.service.connection.SessionHandler;
import com.bid4win.service.property.PropertyInternalService;


/**
 * Classe d'�coute de modification de session
 *
 * @author Maxime Ollagnier
 */
public class SessionListener implements HttpSessionListener
{
  /** Unique instance utilis�e comme singleton */
  private static SessionListener instance;

  /**
   * Getter de l'unique instance en m�moire
   * @return L'unique instance en m�moire
   */
  public static SessionListener getInstance()
  {
    return SessionListener.instance;
  }

  /**
   * Constructeur priv�
   */
  public SessionListener()
  {
    super();
    SessionListener.instance = this;
  }

  /**
   * M�thode appel� � la cr�ation d'une nouvelle session afin de d�finir ses propri�t�s
   * @param sessionEvent {@inheritDoc}
   * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
   */
  @Override
  public void sessionCreated(HttpSessionEvent sessionEvent)
  {
    // A new session is created
    System.out.println("NEW [ " + sessionEvent.getSession().getId() + " ]");

    try
    {
      Bid4WinSession session = new Bid4WinSession(sessionEvent.getSession());
      // Positionne le timeout de session
      session.setMaxInactiveInterval(PropertyInternalService.getInstance().getProperty(
          PropertyRef.SERVER_SESSION_TIMEOUT.getCode()).getIntValue());
    }
    catch(Throwable th)
    {
      th.printStackTrace();
    }
  }

  /**
   * M�thode appel� � la destruction d'une session existante afin de rel�cher les
   * ressources n�cessaires
   * @param sessionEvent {@inheritDoc}
   * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
   */
  @Override
  public void sessionDestroyed(HttpSessionEvent sessionEvent)
  {
    Bid4WinSession session = new Bid4WinSession(sessionEvent.getSession());
    // Si un compte utilisateur �tait connect� sur la session, il faut terminer cette connexion
    if(session.isConnected())
    {
      boolean sessionStarted = SessionHandler.getInstance().isSessionStarted();
      try
      {
        // D�marre une nouvelle session m�tier si n�cessaire
        if(!sessionStarted)
        {
          SessionHandler.getInstance().startSession(session.getId(), session.getIpAddress());
        }
        ConnectionService.getInstance().endConnection();
      }
      catch(Throwable th)
      {
        th.printStackTrace();
      }
      finally
      {
        // Termine la session m�tier si elle venait d'�tre cr��e
        if(!sessionStarted)
        {
          SessionHandler.getInstance().stopSession();
        }
      }
    }
  }
}
