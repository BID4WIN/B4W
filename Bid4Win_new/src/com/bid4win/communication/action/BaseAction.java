package com.bid4win.communication.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.communication.cookie.CookieManager;
import com.bid4win.communication.session.Bid4WinSession;
import com.bid4win.persistence.entity.locale.Language;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Main Action class.
 *
 * @author Maxime Ollagnier
 */
@Namespace("/")
@ParentPackage("bid4win-package")
@InterceptorRefs({@InterceptorRef("accessInterceptor")})
public class BaseAction extends ActionSupport
{
  /** Serial */
  private static final long serialVersionUID = -2026138582001695392L;

  public static final String TYPE_TILE = "tile";
  public static final String TYPE_JSON = "json";
  public static final String REFRESH = "refresh";
  /** JSON result type */
  public static final String JSON_RESULT = "success";

  /** Uploaded file */
  private File upload;

  /** Uploaded file content type */
  private String uploadContentType;

  /** Uploaded file name */
  private String uploadFileName;

  /** InputStream for response output streams **/
  private InputStream inputStream;

  /** Manager des cookies */
  private CookieManager cookieManager = null;

  /**
   * Renvoi le paramètre trouvé dans la requête s'il s'y trouve, null sinon
   * @param parameterName le nom du paramètre à chercher dans la requête
   * @return le paramètre trouvé dans la requête s'il s'y trouve, null sinon
   */
  public String findParameter(String parameterName)
  {
    return this.getRequest().getParameter(parameterName);
  }

  /**
   * Renvoi le paramètre de la requête dont le nom est donné en argument
   * @param parameterName le nom du paramètre à retourner
   * @return le paramètre de la requête dont le nom est donné en argument
   * @throws ModelArgumentException si le paramètre n'a pas été trouvé dans la
   * requête
   */
  public String getParameter(String parameterName) throws ModelArgumentException
  {
    return UtilObject.checkNotNull("Parameter " + parameterName, this.getRequest().getParameter(parameterName));
  }

  /**
   * Renvoi le paramètre de la requête dont le nom est donné en argument
   * @param parameterName Le nom du paramètre à retourner
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return Le paramètre de la requête dont le nom est donné en argument
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public String getParameter(String parameterName, MessageRef errorMessageRef) throws UserException
  {
    return UtilObject.checkNotNull("Parameter " + parameterName, this.getRequest().getParameter(parameterName), errorMessageRef);
  }

  /**
   * Renvoi le paramètre de la requête dont le nom est donné en argument
   * @param parameterName le nom du paramètre à retourner
   * @return le paramètre de la requête dont le nom est donné en argument
   * @throws ModelArgumentException si le paramètre n'a pas été trouvé dans la
   * requête
   */
  public boolean getParameterBool(String parameterName) throws ModelArgumentException
  {
    String string = this.getParameter(parameterName);
    return Boolean.parseBoolean(string);
  }

  /**
   * Renvoi le paramètre de la requête dont le nom est donné en argument
   * @param parameterName Le nom du paramètre à retourner
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return Le paramètre de la requête dont le nom est donné en argument
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public boolean getParameterBool(String parameterName, MessageRef errorMessageRef) throws UserException
  {
    String string = this.getParameter(parameterName, errorMessageRef);
    return Boolean.parseBoolean(string);
  }

  /**
   * Renvoi le paramètre de la requête dont le nom est donné en argument
   * @param parameterName le nom du paramètre à retourner
   * @return le paramètre de la requête dont le nom est donné en argument
   * @throws ModelArgumentException si le paramètre n'a pas été trouvé dans la
   * requête
   */
  public long getParameterLong(String parameterName) throws ModelArgumentException
  {
    try
    {
      String string = this.getParameter(parameterName);
      return Long.parseLong(string);
    }
    catch(NumberFormatException e)
    {
      throw new ModelArgumentException(e);
    }
  }

  /**
   * Renvoi le paramètre de la requête dont le nom est donné en argument
   * @param parameterName Le nom du paramètre à retourner
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return Le paramètre de la requête dont le nom est donné en argument
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public long getParameterLong(String parameterName, MessageRef errorMessageRef) throws UserException
  {
    try
    {
      String string = this.getParameter(parameterName, errorMessageRef);
      return Long.parseLong(string);
    }
    catch(NumberFormatException e)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, e);
    }
  }

  /**
   * Renvoi la requête du ServletActionContext
   * @return la requête du ServletActionContext
   */
  public HttpServletRequest getRequest()
  {
    return ServletActionContext.getRequest();
  }

  /**
   * Renvoi la réponse du ServletActionContext
   * @return la réponse du ServletActionContext
   */
  public HttpServletResponse getResponse()
  {
    return ServletActionContext.getResponse();
  }

  /**
   * Renvoi le contexte du ServletActionContext
   * @return le contexte du ServletActionContext
   */
  public ServletContext getServletContext()
  {
    return ServletActionContext.getServletContext();
  }

  /**
   * Renvoi la session de la requête
   * @return la session de la requête
   */
  public Bid4WinSession getSession()
  {
    return Bid4WinSession.getSession();
  }

  /**
   * Renvoi le chemin du répertoire WEB-SUP enrichit du chemin fourni
   * @param virtualPath le chemin à ajouter
   * @return le chemin du répertoire WEB-SUP enrichit du chemin fourni
   */
  public String getWebSupPath(String virtualPath)
  {
    return this.getServletContext().getRealPath("\\WEB-SUP\\") + "\\" + virtualPath;
  }

  /**
   * Renvoi le chemin du répertoire WEB-SUP
   * @return le chemin du répertoire WEB-SUP enrichit du chemin fourni
   */
  public String getWebSupPath()
  {
    return this.getWebSupPath("");
  }

  /**
   * Renvoi le chemin du répertoire WEB-INF enrichit du chemin fourni
   * @param virtualPath le chemin à ajouter
   * @return le chemin du répertoire WEB-INF enrichit du chemin fourni
   */
  public String getWebInfPath(String virtualPath)
  {
    return this.getServletContext().getRealPath("\\WEB-INF\\") + "\\" + virtualPath;
  }

  /**
   * Renvoi le chemin du répertoire WEB-INF
   * @return le chemin du répertoire WEB-INF enrichit du chemin fourni
   */
  public String getWebInfPath()
  {
    return this.getWebInfPath("");
  }

  /**
   * Renvoi le chemin du répertoire META-INF enrichit du chemin fourni
   * @param virtualPath le chemin à ajouter
   * @return le chemin du répertoire META-INF enrichit du chemin fourni
   */
  public String getMetaInfPath(String virtualPath)
  {
    return this.getServletContext().getRealPath("\\META-INF\\") + "\\" + virtualPath;
  }

  /**
   * Renvoi le chemin du répertoire META-INF
   * @return le chemin du répertoire META-INF enrichit du chemin fourni
   */
  public String getMetaInfPath()
  {
    return this.getMetaInfPath("");
  }

  /**
   * File injection setter for fileUpload interceptor
   * @param upload the file to be set
   */
  public void setUpload(File upload)
  {
    this.upload = upload;
  }

  /**
   * File content type injection setter for fileUpload interceptor
   * @param uploadContentType the file content type to be set
   */
  public void setUploadContentType(String uploadContentType)
  {
    this.uploadContentType = uploadContentType;
  }

  /**
   * File name injection setter for fileUpload interceptor
   * @param uploadFileName the file name to be set
   */
  public void setUploadFileName(String uploadFileName)
  {
    this.uploadFileName = uploadFileName;
  }

  /**
   * Returns the uploaded file
   * @return the uploaded file
   */
  public File getUpload()
  {
    return this.upload;
  }

  /**
   * Returns the buffered image from the uploaded file
   * @return the buffered image from the uploaded file
   * @throws IOException if the uploaded file reading fails
   */
  public BufferedImage getUploadedImage() throws IOException
  {
    if(this.getUpload() != null)
    {
      return ImageIO.read(this.getUpload());
    }
    return null;
  }

  /**
   * Returns the uploaded file content type
   * @return the uploaded file content type
   */
  public String getUploadContentType()
  {
    return this.uploadContentType;
  }

  /**
   * Returns the uploaded file name
   * @return the uploaded file name
   */
  public String getUploadFileName()
  {
    return this.uploadFileName;
  }

  /**
   * Setter for response stream
   * @param inputStream the stream handled through the response
   */
  public void setInputStream(InputStream inputStream)
  {
    this.inputStream = inputStream;
  }

  /**
   * Returns the response stream
   * @return the response stream
   */
  public InputStream getInputStream()
  {
    return this.inputStream;
  }

  /**
   * Renvoi le nom de l'action de la requête
   * @return le nom de l'action de la requête
   */
  public String getActionName()
  {
    String uri = this.getRequest().getRequestURI();
    return uri.substring(uri.lastIndexOf('/') + 1);
  }

  /**
   * Renvoi le manager de cookies. Si le manager de cookies n'existe pas il est
   * créé.
   * @return Le manager de cookies
   */
  public CookieManager getCookieManager()
  {
    if(this.cookieManager == null)
    {
      this.cookieManager = new CookieManager(this.getRequest(), this.getResponse());
    }
    return this.cookieManager;
  }

  /**
   * Détermine et renvoi le langage en session. Le langage est déterminé de la
   * manière suivante :<BR>
   *<BR>
   * - Le langage de la session s'il existe<BR>
   * - Le langage des cookies s'il existe<BR>
   * - Le premier langage du navigateur étant disponible en BDD s'il existe<BR>
   * - Le langage par défaut sinon<BR>
   *
   * @return Le langage en session
   * @throws ModelArgumentException Si une erreur survient lors de la mise à
   * jour du langage en session
   */
  public Language getSessionLanguage() throws ModelArgumentException
  {
    // Le langage en session
    Language sessionLanguage = this.getSession().getLanguage();

    // Si le langage en session n'existe pas
    if(sessionLanguage == null)
    {
      // Récupération du langage des cookies
      String cookieLanguageCode = this.getCookieManager().findLanguage();
      if(!UtilString.trimNotNull(cookieLanguageCode).equals(""))
      {
        try
        {
          sessionLanguage = Language.getLanguage(cookieLanguageCode);
        }
        catch(UserException e)
        {
          // Le cookie est corrompu il sera écrasé
        }
      }
    }
    // Si le langage des cookies n'existe pas
    if(sessionLanguage == null)
    {
      // Récupération du langage du navigateur
      Enumeration<Locale> localeEnum = this.getRequest().getLocales();
      while(localeEnum.hasMoreElements() && sessionLanguage == null)
      {
        try
        {
          sessionLanguage = Language.getLanguage(localeEnum.nextElement().getCountry());
        }
        catch(UserException e)
        {
          // La locale courante ne correspond à aucun language existant
        }
      }
    }
    // Si le langage du navigateur n'existe pas
    if(sessionLanguage == null)
    {
      // Le langage utilisé sera le langage par défaut
      sessionLanguage = Language.DEFAULT;
    }
    // Le langage en session est mis à jour
    this.getSession().setLanguage(sessionLanguage);
    // Le langage en session est renvoyé
    return sessionLanguage;
  }

  /**
   * Détermine si le language en paramètre est différent du langage de la
   * session. Si oui le langage désynchronisé est sauvegardé en session et la
   * méthode renvoie true, false sinon.
   * @param language le nouveau langage
   * @return true Si le langage était désynchronisé et a été sauvé. False si le
   * langage n'était pas désynchronisé. Dans ce cas le langage de la session n'a
   * pas été modifié.
   * @throws ModelArgumentException Si le langage en paramètre est nul
   */
  public boolean saveLanguageInSession(Language language) throws ModelArgumentException
  {
    if(!this.getSessionLanguage().equals(language))
    {
      this.getSession().setLanguage(language);
      return true;
    }
    return false;
  }
}
