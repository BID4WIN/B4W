package com.bid4win.tools.dependency;

import java.io.File;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.io.KeyboardSelection;
import com.bid4win.tools.console.Item;
import com.bid4win.tools.dependency.model.EclipseProject;
import com.bid4win.tools.dependency.model.EclipseProjectWeb;

/**
 * Cet item correspond à l'item de base de gestion des dépendances entre projets
 * Eclipse<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class EclipseProjectWebItem extends Item<DependencyManager>
{
  /**
   * Constructeur
   * @param wording Libellé correspondant à l'item
   * @param manager Menu dans lequel l'item est initialisé
   */
  public EclipseProjectWebItem(String wording, DependencyManager manager)
  {
    super(wording, manager);
  }

  /**
   * Cette méthode permet d'execute l'item courant. La liste des projets disponibles
   * sera chargée à ce moment est l'action s'appliquera sur la selection de l'utilisateur
   * @throws Bid4WinException {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.tools.console.Item#execute()
   */
  @Override
  public boolean execute() throws Bid4WinException
  {
    System.out.println(this.getWording());
    System.out.println("Choose project :");
    System.out.println("");
    // Récupère la liste des projets WEB pouvant être gérés
    Bid4WinList<File> projectRootDirList = this.getWebProjectRootDirList();
    int choice = 1;
    for(File file : projectRootDirList)
    {
      System.out.println("[" + (choice++) + "] " + file.getName());
    }
    System.out.println("[0] ALL PROJECTS");
    System.out.println("");
    System.out.println("Entre your choice: ");
    choice = KeyboardSelection.getInstance().parseIntBounds(0, projectRootDirList.size());
    System.out.println("");
    // L'action s'appliquera sur la selection de l'utilisateur
    Bid4WinList<EclipseProjectWeb> projectList = new Bid4WinList<EclipseProjectWeb>();
    if(choice == 0)
    {
      for(File projectRootDir : projectRootDirList)
      {
        projectList.add(new EclipseProjectWeb(projectRootDir));
      }
    }
    else
    {
      projectList.add(new EclipseProjectWeb(projectRootDirList.get(choice - 1)));
    }
    Bid4WinMap<String, EclipseProject> loadedMap = new Bid4WinMap<String, EclipseProject>();
    for(EclipseProjectWeb project : projectList)
    {
      project.loadProject(loadedMap);
      this.execute(project);
    }
    return true;
  }
  
  /**
   * 
   * TODO A COMMENTER
   * @param project TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public abstract void execute(EclipseProjectWeb project) throws Bid4WinException;
}
