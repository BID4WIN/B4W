package com.bid4win.communication.session;



import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.struts2.ServletActionContext;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.entity.connection.IpAddress;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Class encapsulant et la enrichissant la session HTTP de fonctionnalit�s sp�cifiques.
 *
 * @author Maxime Ollagnier
 */
public class Bid4WinSession implements HttpSession
{
  /**
   * Cette m�thode permet de r�cup�rer la session correspondant au context actuel
   * @return La session correspondant au context actuel
   */
  public static Bid4WinSession getSession()
  {
    return new Bid4WinSession();
  }

  /** Cl� de stockage dans la session de la cl� de protection de cette session */
  private final static String PROTECTION_KEY = "protectionKey";
  /** Cl� de stockage dans la session de l'�tat de connexion d'un compte utilisateur sur cette session */
  private final static String CONNECTED = "connected";
  /** Cl� de stockage dans la session de l'adresse IP de connexion d'un compte utilisateur sur cette session */
  private final static String IP_ADDRESS = "ipAddress";
  /** Cl� de stockage dans la session de la langue choisie */
  private final static String LANGUAGE = "language";

  /** Session HTTP encapsul�e par la session courante */
  private HttpSession internalSession;

  /**
   * Constructeur encapsulant la session HTTP correspondant au context actuel
   */
  private Bid4WinSession()
  {
    this.defineInternalSession();
  }
  /**
   * Constructeur
   * @param session Session HTTP � encapsuler
   */
  protected Bid4WinSession(HttpSession session)
  {
    this.setInternalSession(session);
  }

  /**
   * Getter de la cl� de protection de la session
   * @return La cl� de protection de la session
   */
  public String getProtectionKey()
  {
    return UtilString.trimNotNull((String)this.getAttribute(Bid4WinSession.PROTECTION_KEY));
  }
  /**
   * Setter de la cl� de protection de la session
   * @param key Cl� de protection de la session
   */
  public void setProtectionKey(String key)
  {
    this.setAttribute(Bid4WinSession.PROTECTION_KEY, key);
  }

  /**
   * Getter du status de connexion d'un compte utilisateur sur la session
   * @return True si un compte utilisateur est connecte sur la session, false sinon
   */
  public boolean isConnected()
  {
    Boolean connected = (Boolean)this.getAttribute(Bid4WinSession.CONNECTED);
    if(connected == null)
    {
      return false;
    }
    return connected;
  }
  /**
   * Cette m�thode permet de d�finir qu'un compte utilisateur est connect� sur la
   * session courante
   */
  public void connect()
  {
    this.setAttribute(Bid4WinSession.CONNECTED, true);
  }
  /**
   * Cette m�thode permet de d�finir qu'aucun compte utilisateur n'est connect�
   * sur la session courante
   */
  public void disconnect()
  {
    this.setAttribute(Bid4WinSession.CONNECTED, false);
  }

  /**
   * Getter de l'adresse IP de connexion � la session
   * @return L'adresse IP de connexion � la session
   */
  public IpAddress getIpAddress()
  {
    return (IpAddress)this.getAttribute(Bid4WinSession.IP_ADDRESS);
  }
  /**
   * Setter de l'adresse IP de connexion � la session
   * @param ipAddress Adresse IP de connexion � la session
   */
  public void setIpAddress(IpAddress ipAddress)
  {
    this.setAttribute(Bid4WinSession.IP_ADDRESS, ipAddress);
  }

  /**
   * Guetter du language de la session
   * @return Le language de la session
   */
  public Language getLanguage()
  {
    return (Language)(this.getAttribute(Bid4WinSession.LANGUAGE));
  }
  /**
   * Setter du language de la session
   * @param language Language � setter
   * @throws ModelArgumentException Si le language en param�tre est nul
   */
  public void setLanguage(Language language) throws ModelArgumentException
  {
    UtilObject.checkNotNull(Bid4WinSession.LANGUAGE, language);
    this.setAttribute(Bid4WinSession.LANGUAGE, language);
  }
  /**
   * Renvoi true si un language est enregistr� dans la session
   * @return true si un language est enregistr� dans la session
   */
  public boolean hasLanguage()
  {
    return this.getAttribute(Bid4WinSession.LANGUAGE) != null;
  }
  /**
   * Renvoi true si le langage en param�tre est le m�me que celui de la session
   * @param language Le langage � tester
   * @return true si le langage en param�tre est le m�me que celui de la session
   */
  public boolean hasLanguage(Language language)
  {
    if(language == null)
    {
      if(!this.hasLanguage())
      {
        return true;
      }
      return false;
    }
    return language.equals(this.getAttribute(Bid4WinSession.LANGUAGE));
  }


  /**
   * Cette m�thode permet d'invalider la session HTTP encapsul�e et d'en d�marrer
   * une nouvelle
   * @see javax.servlet.http.HttpSession#invalidate()
   */
  @Override
  public void invalidate()
  {
    HttpSession oldSession = this.getInternalSession();
    oldSession.invalidate();
    this.defineInternalSession();
  }

  /**
   * Getter de l'identifiant de la session
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getId()
   */
  @Override
  public String getId()
  {
    return this.getInternalSession().getId();
  }

  /**
   *
   * TODO A COMMENTER
   * @param name {@inheritDoc}
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
   */
  @Override
  public Object getAttribute(String name)
  {
    return this.getInternalSession().getAttribute(name);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getAttributeNames()
   */
  @SuppressWarnings("unchecked")
  @Override
  public Enumeration<String> getAttributeNames()
  {
    return this.getInternalSession().getAttributeNames();
  }
  /**
   *
   * TODO A COMMENTER
   * @param name {@inheritDoc}
   * @param attribute {@inheritDoc}
   * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
   */
  @Override
  public void setAttribute(String name, Object attribute)
  {
    this.getInternalSession().setAttribute(name, attribute);
  }
  /**
   *
   * TODO A COMMENTER
   * @param name {@inheritDoc}
   * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
   */
  @Override
  public void removeAttribute(String name)
  {
    this.getInternalSession().removeAttribute(name);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#isNew()
   */
  @Override
  public boolean isNew()
  {
    return this.getInternalSession().isNew();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getCreationTime()
   */
  @Override
  public long getCreationTime()
  {
    return this.getInternalSession().getCreationTime();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getLastAccessedTime()
   */
  @Override
  public long getLastAccessedTime()
  {
    return this.getInternalSession().getLastAccessedTime();
  }
  /**
   * Getter du temps, en secondes, entre deux requ�tes d'un client avant que la
   * session soit annul�e
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
   */
  @Override
  public int getMaxInactiveInterval()
  {
    return this.getInternalSession().getMaxInactiveInterval();
  }
  /**
   * Setter du temps, en secondes, entre deux requ�tes d'un client avant que la
   * session soit annul�e. Un temps n�gatif indique que la session ne devrait jamais
   * s'achever
   * @param interval {@inheritDoc}
   * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
   */
  @Override
  public void setMaxInactiveInterval(int interval)
  {
    this.getInternalSession().setMaxInactiveInterval(interval);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getServletContext()
   */
  @Override
  public ServletContext getServletContext()
  {
    return this.getInternalSession().getServletContext();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getSessionContext()
   */
  @Deprecated
  @Override
  public HttpSessionContext getSessionContext()
  {
    return null;
  }
  /**
   *
   * TODO A COMMENTER
   * @param name {@inheritDoc}
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
   */
  @Deprecated
  @Override
  public Object getValue(String name)
  {
    return null;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getValueNames()
   */
  @Deprecated
  @Override
  public String[] getValueNames()
  {
    return null;
  }
  /**
   *
   * TODO A COMMENTER
   * @param name {@inheritDoc}
   * @param value {@inheritDoc}
   * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
   */
  @Deprecated
  @Override
  public void putValue(String name, Object value)
  {
    //
  }
  /**
   *
   * TODO A COMMENTER
   * @param name {@inheritDoc}
   * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
   */
  @Deprecated
  @Override
  public void removeValue(String name)
  {
    //
  }

  /**
   * Getter de la session HTTP encapsul�e par la session courante
   * @return La session HTTP encapsul�e par la session courante
   */
  private HttpSession getInternalSession()
  {
    return this.internalSession;
  }
  /**
   * Setter de la session HTTP encapsul�e par la session courante
   * @param internalSession Session HTTP � encapsuler par la session courante
   */
  private void setInternalSession(HttpSession internalSession)
  {
    this.internalSession = internalSession;
  }
  /**
   * Cette m�thode permet de d�finir comme session HTTP � encapsuler par la session
   * courante celle correspondant au context actuel
   */
  private void defineInternalSession()
  {
    this.internalSession = ServletActionContext.getRequest().getSession();
  }
}
