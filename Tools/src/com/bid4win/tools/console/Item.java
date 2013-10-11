package com.bid4win.tools.console;

import java.io.File;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.connection.IpAddress;
import com.bid4win.service.connection.SessionData;
import com.bid4win.service.connection.SessionHandler;

/**
 * Cette classe repr�sente un item de menu g�n�rique. Un item est constitu� d'un
 * libell� et d'une action associ�e utilisable via la m�thode execute()<BR>
 * <BR>
 * @param <MENU> D�finition du menu auquel appartient l'item<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Item<MENU extends Menu<?>>
{
  /** Map de contextes d�j� initialis�s class�es en fonction de leur nom de fichier de configuration */
  private final static Bid4WinMap<String, ClassPathXmlApplicationContext> contextMap =
      new Bid4WinMap<String, ClassPathXmlApplicationContext>();

  /** TODO A COMMENTER */
  private final String sessionId = "01234567890123456789012345678932";//IdGenerator.generateId(32);
  /** Libell� correspondant � l'item */
  private String wording = "";
  /** Menu dans lequel l'item est initialis� */
  private MENU menu;
  /** Indique si le menu est lanc� en ligne */
  private boolean standalone = false;

  /**
   * Constructeur d'un item
   * @param wording Libell� correspondant � l'item
   * @param menu Menu dans lequel l'item est initialis�
   */
  public Item(String wording, MENU menu)
  {
    this(wording, menu, false);
  }

  /**
   * Constructeur d'un item
   * @param wording Libell� correspondant � l'item
   * @param menu Menu dans lequel l'item est initialis�
   * @param standalone Indique si le menu est lanc� en ligne
   */
  public Item(String wording, MENU menu, boolean standalone)
  {
    this.setWording(wording);
    this.setMenu(menu);
    this.setStandalone(standalone);
  }

  /**
   * Cette m�thode est � impl�menter par chaque item afin de d�finir l'action qui
   * sera effectu�e quand l'item est s�lectionn�
   * @return True si le menu doit attendre avant d'�tre r�-affich�, false sinon
   * @throws Bid4WinException Si un probl�me intervient lors de l'execution de l'
   * action
   */
  public abstract boolean execute() throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public SessionData getSession() throws UserException
  {
    SessionHandler handler = SessionHandler.getInstance();
    if(handler.isSessionStarted())
    {
      return handler.getSessionData();
    }
    return handler.startSession(this.sessionId, new IpAddress("1.2.3.4"));
  }
  /**
   *
   * TODO A COMMENTER
   * @param beanName TODO A COMMENTER
   * @param beanClass TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException Si l'emplacement du fichier de configuration du contexte
   * est invalide
   */
  @SuppressWarnings({"unchecked", "unused"})
  public <BEAN> BEAN getBean(String beanName, Class<BEAN> beanClass) throws UserException
  {
   SessionHandler.getInstance().disconnect();
   return (BEAN)this.getContext().getBean(beanName);
  }
 /**
  * Getter du contexte utilis� pour rechercher les diff�rents beans
  * @return Le contexte utilis� pour rechercher les diff�rents beans
  * @throws UserException Si l'emplacement du fichier de configuration du contexte
  * est invalide
  */
 private ClassPathXmlApplicationContext getContext() throws UserException
 {
   return this.getContext(this.getContextConfigurationFileName());
 }
 /**
  * Getter de l'emplacement relatif du fichier de configuration du contexte utilis�
  * pour rechercher les diff�rents beans. Cette m�thode peut �tre red�finie afin
  * de pr�ciser un autre fichier de configuration
  * @return L'emplacement relatif du fichier de configuration du contexte utilis�
  * pour rechercher les diff�rents beans
  * @throws UserException Si l'emplacement relatif construit ne respecte pas le
  * format attendu
  */
 protected String getContextConfigurationFileName() throws UserException
 {
   return UtilFile.concatRelativePath(
       ResourceRef.RESOURCE, "META-INF", "config", "spring-tools.xml");
 }
 /**
  * Getter d'un contexte en fonction du nom de son fichier de configuration. Le
  * contexte sera initialis� si ce n'a pas d�j� �t� le cas
  * @param configurationFileName Nom du fichier de configuration correspondant
  * au contexte � r�cup�rer
  * @return Le contexte correspondant au nom du fichier de configuration en argument
  */
 private ClassPathXmlApplicationContext getContext(String configurationFileName)
 {
   ClassPathXmlApplicationContext context = Item.getContextMap().get(configurationFileName);
   if(context == null)
   {
     context = new ClassPathXmlApplicationContext(configurationFileName);
     Item.getContextMap().put(configurationFileName, context);
   }
   return context;
 }
 /**
  * Getter de la map de contextes d�j� initialis�s
  * @return La map de contextes d�j� initialis�s
  */
 private static Bid4WinMap<String, ClassPathXmlApplicationContext> getContextMap()
 {
   return contextMap;
 }

  /**
   * Permet de r�cup�rer le libell� de l'item
   * @return Le libell� correspondant � l'item
   */
  public String getWording()
  {
    return this.wording;
  }
  /**
   * Permet de d�finir le libell� de l'item
   * @param wording Le libell� de l'item
   */
  private void setWording(String wording)
  {
    this.wording = wording;
  }

  /**
   * Permet de r�cup�rer le menu dans lequel l'item est initialis�
   * @return Le menu dans lequel l'item est initialis�
   */
  public MENU getMenu()
  {
    return this.menu;
  }
  /**
   * Permet de d�finir le menu dans lequel l'item est initialis�
   * @param menu Le menu dans lequel l'item est initialis�
   */
  private void setMenu(MENU menu)
  {
    this.menu = menu;
  }

  /**
   * Permet de savoir si le client d'administration a �t� lanc� en ligne
   * @return True si le client d'administration a �t� lanc� en ligne, false sinon
   */
  public boolean isStandalone()
  {
    return this.standalone;
  }
  /**
   * Permet de d�finir si le client d'administration a �t� lanc� en ligne
   * @param standalone Flag indiquant si le client d'administration a �t� lanc�
   * en ligne
   */
  private void setStandalone(boolean standalone)
  {
    this.standalone = standalone;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public File getProjectRootDir()
  {
    return new java.io.File("").getAbsoluteFile();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public File getWorkspaceRootDir()
  {
    return this.getProjectRootDir().getParentFile();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getWorkspaceRootPath()
  {
    return this.getProjectRootDir().getAbsolutePath();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinList<File> getProjectRootDirList()
  {
    Bid4WinList<File> result = new Bid4WinList<File>();
    for(File directory : this.getWorkspaceRootDir().listFiles())
    {
      if(directory.isDirectory())
      {
        for(File file : directory.listFiles())
        {
          if(file.isFile() && file.getName().equals(".project"))
          {
            result.add(directory);
            break;
          }
        }
      }
    }
    return result;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinList<File> getWebProjectRootDirList()
  {
    Bid4WinList<File> result = new Bid4WinList<File>();
    for(File directory : this.getProjectRootDirList())
    {
      for(File file : directory.listFiles())
      {
        if(file.isDirectory() && file.getName().equals("WebContent"))
        {
          result.add(directory);
          break;
        }
      }
    }
    return result;
  }
}
