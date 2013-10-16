package com.bid4win.communication.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.bid4win.commons.core.reference.PropertyRef;
import com.bid4win.service.connection.ConnectionService;
import com.bid4win.service.connection.SessionHandler;
import com.bid4win.service.property.PropertyInternalService;


/**
 * Classe d'écoute de modification de session
 *
 * @author Maxime Ollagnier
 */
public class SessionListener implements HttpSessionListener
{
  /** Unique instance utilisée comme singleton */
  private static SessionListener instance;

  /**
   * Getter de l'unique instance en mémoire
   * @return L'unique instance en mémoire
   */
  public static SessionListener getInstance()
  {
    return SessionListener.instance;
  }

  /**
   * Constructeur privé
   */
  public SessionListener()
  {
    super();
    SessionListener.instance = this;
  }

  /**
   * Méthode appelé à la création d'une nouvelle session afin de définir ses propriétés
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
   * Méthode appelé à la destruction d'une session existante afin de relâcher les
   * ressources nécessaires
   * @param sessionEvent {@inheritDoc}
   * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
   */
  @Override
  public void sessionDestroyed(HttpSessionEvent sessionEvent)
  {
    Bid4WinSession session = new Bid4WinSession(sessionEvent.getSession());
    // Si un compte utilisateur était connecté sur la session, il faut terminer cette connexion
    if(session.isConnected())
    {
      boolean sessionStarted = SessionHandler.getInstance().isSessionStarted();
      try
      {
        // Démarre une nouvelle session métier si nécessaire
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
        // Termine la session métier si elle venait d'être créée
        if(!sessionStarted)
        {
          SessionHandler.getInstance().stopSession();
        }
      }
    }
  }
}
