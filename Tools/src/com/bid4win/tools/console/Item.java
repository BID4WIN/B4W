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
 * Cette classe représente un item de menu générique. Un item est constitué d'un
 * libellé et d'une action associée utilisable via la méthode execute()<BR>
 * <BR>
 * @param <MENU> Définition du menu auquel appartient l'item<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Item<MENU extends Menu<?>>
{
  /** Map de contextes déjà initialisés classées en fonction de leur nom de fichier de configuration */
  private final static Bid4WinMap<String, ClassPathXmlApplicationContext> contextMap =
      new Bid4WinMap<String, ClassPathXmlApplicationContext>();

  /** TODO A COMMENTER */
  private final String sessionId = "01234567890123456789012345678932";//IdGenerator.generateId(32);
  /** Libellé correspondant à l'item */
  private String wording = "";
  /** Menu dans lequel l'item est initialisé */
  private MENU menu;
  /** Indique si le menu est lancé en ligne */
  private boolean standalone = false;

  /**
   * Constructeur d'un item
   * @param wording Libellé correspondant à l'item
   * @param menu Menu dans lequel l'item est initialisé
   */
  public Item(String wording, MENU menu)
  {
    this(wording, menu, false);
  }

  /**
   * Constructeur d'un item
   * @param wording Libellé correspondant à l'item
   * @param menu Menu dans lequel l'item est initialisé
   * @param standalone Indique si le menu est lancé en ligne
   */
  public Item(String wording, MENU menu, boolean standalone)
  {
    this.setWording(wording);
    this.setMenu(menu);
    this.setStandalone(standalone);
  }

  /**
   * Cette méthode est à implémenter par chaque item afin de définir l'action qui
   * sera effectuée quand l'item est sélectionné
   * @return True si le menu doit attendre avant d'être ré-affiché, false sinon
   * @throws Bid4WinException Si un problème intervient lors de l'execution de l'
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
  * Getter du contexte utilisé pour rechercher les différents beans
  * @return Le contexte utilisé pour rechercher les différents beans
  * @throws UserException Si l'emplacement du fichier de configuration du contexte
  * est invalide
  */
 private ClassPathXmlApplicationContext getContext() throws UserException
 {
   return this.getContext(this.getContextConfigurationFileName());
 }
 /**
  * Getter de l'emplacement relatif du fichier de configuration du contexte utilisé
  * pour rechercher les différents beans. Cette méthode peut être redéfinie afin
  * de préciser un autre fichier de configuration
  * @return L'emplacement relatif du fichier de configuration du contexte utilisé
  * pour rechercher les différents beans
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
  * contexte sera initialisé si ce n'a pas déjà été le cas
  * @param configurationFileName Nom du fichier de configuration correspondant
  * au contexte à récupérer
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
  * Getter de la map de contextes déjà initialisés
  * @return La map de contextes déjà initialisés
  */
 private static Bid4WinMap<String, ClassPathXmlApplicationContext> getContextMap()
 {
   return contextMap;
 }

  /**
   * Permet de récupérer le libellé de l'item
   * @return Le libellé correspondant à l'item
   */
  public String getWording()
  {
    return this.wording;
  }
  /**
   * Permet de définir le libellé de l'item
   * @param wording Le libellé de l'item
   */
  private void setWording(String wording)
  {
    this.wording = wording;
  }

  /**
   * Permet de récupérer le menu dans lequel l'item est initialisé
   * @return Le menu dans lequel l'item est initialisé
   */
  public MENU getMenu()
  {
    return this.menu;
  }
  /**
   * Permet de définir le menu dans lequel l'item est initialisé
   * @param menu Le menu dans lequel l'item est initialisé
   */
  private void setMenu(MENU menu)
  {
    this.menu = menu;
  }

  /**
   * Permet de savoir si le client d'administration a été lancé en ligne
   * @return True si le client d'administration a été lancé en ligne, false sinon
   */
  public boolean isStandalone()
  {
    return this.standalone;
  }
  /**
   * Permet de définir si le client d'administration a été lancé en ligne
   * @param standalone Flag indiquant si le client d'administration a été lancé
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
