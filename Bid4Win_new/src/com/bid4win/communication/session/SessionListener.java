package com.bid4win.communication.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bid4win.service.connection.ConnectionService;


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
   * Méthode appelé à la création d'une nouvelle session
   * @param sessionEvent {@inheritDoc}
   * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
   */
  @Override
  public void sessionCreated(HttpSessionEvent sessionEvent)
  {
    // A new session is created
    //System.out.println("NEW [ " + sessionEvent.getSession().getId() + " ]");
  }

  /**
   * Méthode appelé à la destruction d'une session existante
   * @param sessionEvent {@inheritDoc}
   * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
   */
  @Override
  public void sessionDestroyed(HttpSessionEvent sessionEvent)
  {
    try
    {
      Bid4WinSession session = new Bid4WinSession(sessionEvent.getSession());
      if(session.hasAccountId())
      {
        this.getConnectionService(sessionEvent).endConnection(sessionEvent.getSession().getId());
      }
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Renvoi le ConnectionService du contexte Spring
   * @param sessionEvent l'évennement de session dont on extrait le contexte
   * @return Le ConnectionService du contexte Spring
   */
  private ConnectionService getConnectionService(HttpSessionEvent sessionEvent)
  {
    // Récupération du ServletContext de la session
    ServletContext servletContext = sessionEvent.getSession().getServletContext();
    // Récupération du contexte Spring
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    // Récupération et renvoi du bean nommé "ConnectionService"
    ConnectionService connectionService = (ConnectionService)ctx.getBean("ConnectionService");
    return connectionService;
  }
}
