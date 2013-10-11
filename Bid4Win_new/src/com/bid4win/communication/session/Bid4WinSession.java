package com.bid4win.communication.session;

import javax.servlet.http.HttpSession;

import org.apache.catalina.session.StandardSessionFacade;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Class enrichissant la StandardSessionFacade de fonctionnalités spécifiques.
 * 
 * @author Maxime Ollagnier
 */
public class Bid4WinSession extends StandardSessionFacade
{
  /** TODO */
  private final static String ACCOUNT_ID = "accountId";

  /** Nom de l'attribut language de la session */
  private final static String LANGUAGE = "language";

  /**
   * Constructeur
   * @param session La session HTTP à encapsuler
   */
  public Bid4WinSession(HttpSession session)
  {
    super(session);
  }

  /**
   * Setter de l'ID du compte utilisateur de la session
   * @param accountId ID du compte utilisateur à setter
   * @throws ModelArgumentException Si l'argument en paramètre est nul
   */
  public void setAccountId(String accountId) throws ModelArgumentException
  {
    UtilObject.checkNotNull(Bid4WinSession.ACCOUNT_ID, accountId);
    this.setAttribute(Bid4WinSession.ACCOUNT_ID, accountId);
  }

  /**
   * Setter du language de la session
   * @param language Language à setter
   * @throws ModelArgumentException Si le language en paramètre est nul
   */
  public void setLanguage(Language language) throws ModelArgumentException
  {
    UtilObject.checkNotNull(Bid4WinSession.LANGUAGE, language);
    this.setAttribute(Bid4WinSession.LANGUAGE, language);
  }

  /**
   * TODO Guetter du compte utilisateur de la session
   * @return Le compte utilisateur de la session
   */
  public String getAccountId()
  {
    return (String)(this.getAttribute(Bid4WinSession.ACCOUNT_ID));
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
   * TODO Supprime le compte utilisateur potentiellement en session
   */
  public void removeAccountId()
  {
    this.removeAttribute(Bid4WinSession.ACCOUNT_ID);
  }

  /**
   * Supprime le language potentiellement en session
   */
  public void removeLanguage()
  {
    this.removeAttribute(Bid4WinSession.LANGUAGE);
  }

  /**
   * TODO Renvoi true si un compte utilisateur est enregistré dans la session
   * @return true si un compte utilisateur est enregistré dans la session
   */
  public boolean hasAccountId()
  {
    return this.getAttribute(Bid4WinSession.ACCOUNT_ID) != null;
  }

  /**
   * Renvoi true si un language est enregistré dans la session
   * @return true si un language est enregistré dans la session
   */
  public boolean hasLanguage()
  {
    return this.getAttribute(Bid4WinSession.LANGUAGE) != null;
  }

  /**
   * Renvoi true si le langage en paramètre est le même que celui de la session
   * @param language Le langage à tester
   * @return true si le langage en paramètre est le même que celui de la session
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
}
