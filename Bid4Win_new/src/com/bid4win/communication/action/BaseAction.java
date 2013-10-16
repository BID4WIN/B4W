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
   * Renvoi le param�tre trouv� dans la requ�te s'il s'y trouve, null sinon
   * @param parameterName le nom du param�tre � chercher dans la requ�te
   * @return le param�tre trouv� dans la requ�te s'il s'y trouve, null sinon
   */
  public String findParameter(String parameterName)
  {
    return this.getRequest().getParameter(parameterName);
  }

  /**
   * Renvoi le param�tre de la requ�te dont le nom est donn� en argument
   * @param parameterName le nom du param�tre � retourner
   * @return le param�tre de la requ�te dont le nom est donn� en argument
   * @throws ModelArgumentException si le param�tre n'a pas �t� trouv� dans la
   * requ�te
   */
  public String getParameter(String parameterName) throws ModelArgumentException
  {
    return UtilObject.checkNotNull("Parameter " + parameterName, this.getRequest().getParameter(parameterName));
  }

  /**
   * Renvoi le param�tre de la requ�te dont le nom est donn� en argument
   * @param parameterName Le nom du param�tre � retourner
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'
   * �chec
   * @return Le param�tre de la requ�te dont le nom est donn� en argument
   * @throws UserException Si le param�tre n'a pas �t� trouv� dans la requ�te
   */
  public String getParameter(String parameterName, MessageRef errorMessageRef) throws UserException
  {
    return UtilObject.checkNotNull("Parameter " + parameterName, this.getRequest().getParameter(parameterName), errorMessageRef);
  }

  /**
   * Renvoi le param�tre de la requ�te dont le nom est donn� en argument
   * @param parameterName le nom du param�tre � retourner
   * @return le param�tre de la requ�te dont le nom est donn� en argument
   * @throws ModelArgumentException si le param�tre n'a pas �t� trouv� dans la
   * requ�te
   */
  public boolean getParameterBool(String parameterName) throws ModelArgumentException
  {
    String string = this.getParameter(parameterName);
    return Boolean.parseBoolean(string);
  }

  /**
   * Renvoi le param�tre de la requ�te dont le nom est donn� en argument
   * @param parameterName Le nom du param�tre � retourner
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'
   * �chec
   * @return Le param�tre de la requ�te dont le nom est donn� en argument
   * @throws UserException Si le param�tre n'a pas �t� trouv� dans la requ�te
   */
  public boolean getParameterBool(String parameterName, MessageRef errorMessageRef) throws UserException
  {
    String string = this.getParameter(parameterName, errorMessageRef);
    return Boolean.parseBoolean(string);
  }

  /**
   * Renvoi le param�tre de la requ�te dont le nom est donn� en argument
   * @param parameterName le nom du param�tre � retourner
   * @return le param�tre de la requ�te dont le nom est donn� en argument
   * @throws ModelArgumentException si le param�tre n'a pas �t� trouv� dans la
   * requ�te
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
   * Renvoi le param�tre de la requ�te dont le nom est donn� en argument
   * @param parameterName Le nom du param�tre � retourner
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'
   * �chec
   * @return Le param�tre de la requ�te dont le nom est donn� en argument
   * @throws UserException Si le param�tre n'a pas �t� trouv� dans la requ�te
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
   * Renvoi la requ�te du ServletActionContext
   * @return la requ�te du ServletActionContext
   */
  public HttpServletRequest getRequest()
  {
    return ServletActionContext.getRequest();
  }

  /**
   * Renvoi la r�ponse du ServletActionContext
   * @return la r�ponse du ServletActionContext
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
   * Renvoi la session de la requ�te
   * @return la session de la requ�te
   */
  public Bid4WinSession getSession()
  {
    return Bid4WinSession.getSession();
  }

  /**
   * Renvoi le chemin du r�pertoire WEB-SUP enrichit du chemin fourni
   * @param virtualPath le chemin � ajouter
   * @return le chemin du r�pertoire WEB-SUP enrichit du chemin fourni
   */
  public String getWebSupPath(String virtualPath)
  {
    return this.getServletContext().getRealPath("\\WEB-SUP\\") + "\\" + virtualPath;
  }

  /**
   * Renvoi le chemin du r�pertoire WEB-SUP
   * @return le chemin du r�pertoire WEB-SUP enrichit du chemin fourni
   */
  public String getWebSupPath()
  {
    return this.getWebSupPath("");
  }

  /**
   * Renvoi le chemin du r�pertoire WEB-INF enrichit du chemin fourni
   * @param virtualPath le chemin � ajouter
   * @return le chemin du r�pertoire WEB-INF enrichit du chemin fourni
   */
  public String getWebInfPath(String virtualPath)
  {
    return this.getServletContext().getRealPath("\\WEB-INF\\") + "\\" + virtualPath;
  }

  /**
   * Renvoi le chemin du r�pertoire WEB-INF
   * @return le chemin du r�pertoire WEB-INF enrichit du chemin fourni
   */
  public String getWebInfPath()
  {
    return this.getWebInfPath("");
  }

  /**
   * Renvoi le chemin du r�pertoire META-INF enrichit du chemin fourni
   * @param virtualPath le chemin � ajouter
   * @return le chemin du r�pertoire META-INF enrichit du chemin fourni
   */
  public String getMetaInfPath(String virtualPath)
  {
    return this.getServletContext().getRealPath("\\META-INF\\") + "\\" + virtualPath;
  }

  /**
   * Renvoi le chemin du r�pertoire META-INF
   * @return le chemin du r�pertoire META-INF enrichit du chemin fourni
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
   * Renvoi le nom de l'action de la requ�te
   * @return le nom de l'action de la requ�te
   */
  public String getActionName()
  {
    String uri = this.getRequest().getRequestURI();
    return uri.substring(uri.lastIndexOf('/') + 1);
  }

  /**
   * Renvoi le manager de cookies. Si le manager de cookies n'existe pas il est
   * cr��.
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
   * D�termine et renvoi le langage en session. Le langage est d�termin� de la
   * mani�re suivante :<BR>
   *<BR>
   * - Le langage de la session s'il existe<BR>
   * - Le langage des cookies s'il existe<BR>
   * - Le premier langage du navigateur �tant disponible en BDD s'il existe<BR>
   * - Le langage par d�faut sinon<BR>
   *
   * @return Le langage en session
   * @throws ModelArgumentException Si une erreur survient lors de la mise �
   * jour du langage en session
   */
  public Language getSessionLanguage() throws ModelArgumentException
  {
    // Le langage en session
    Language sessionLanguage = this.getSession().getLanguage();

    // Si le langage en session n'existe pas
    if(sessionLanguage == null)
    {
      // R�cup�ration du langage des cookies
      String cookieLanguageCode = this.getCookieManager().findLanguage();
      if(!UtilString.trimNotNull(cookieLanguageCode).equals(""))
      {
        try
        {
          sessionLanguage = Language.getLanguage(cookieLanguageCode);
        }
        catch(UserException e)
        {
          // Le cookie est corrompu il sera �cras�
        }
      }
    }
    // Si le langage des cookies n'existe pas
    if(sessionLanguage == null)
    {
      // R�cup�ration du langage du navigateur
      Enumeration<Locale> localeEnum = this.getRequest().getLocales();
      while(localeEnum.hasMoreElements() && sessionLanguage == null)
      {
        try
        {
          sessionLanguage = Language.getLanguage(localeEnum.nextElement().getCountry());
        }
        catch(UserException e)
        {
          // La locale courante ne correspond � aucun language existant
        }
      }
    }
    // Si le langage du navigateur n'existe pas
    if(sessionLanguage == null)
    {
      // Le langage utilis� sera le langage par d�faut
      sessionLanguage = Language.DEFAULT;
    }
    // Le langage en session est mis � jour
    this.getSession().setLanguage(sessionLanguage);
    // Le langage en session est renvoy�
    return sessionLanguage;
  }

  /**
   * D�termine si le language en param�tre est diff�rent du langage de la
   * session. Si oui le langage d�synchronis� est sauvegard� en session et la
   * m�thode renvoie true, false sinon.
   * @param language le nouveau langage
   * @return true Si le langage �tait d�synchronis� et a �t� sauv�. False si le
   * langage n'�tait pas d�synchronis�. Dans ce cas le langage de la session n'a
   * pas �t� modifi�.
   * @throws ModelArgumentException Si le langage en param�tre est nul
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
