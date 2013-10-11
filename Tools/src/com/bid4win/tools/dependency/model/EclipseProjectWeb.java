package com.bid4win.tools.dependency.model;

import java.io.File;

import org.w3c.dom.Node;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Classe définissant un projet WEB avec ses dépendances<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class EclipseProjectWeb extends EclipseProject
{
  /** Set des entrées de déploiement du projet WEB courant */
  private Bid4WinSet<Output> outputSet = new Bid4WinSet<Output>();

  /**
   * Constructeur par copie
   * @param project Projet WEB à partir duquel créer le nouveau projet
   * @throws UserException Si la racine du projet WEB en argument est invalide
   */
  public EclipseProjectWeb(EclipseProjectWeb project) throws UserException
  {
    super(project);
  }
  /**
   * Constructeur
   * @param rootPath Racine du projet
   * @param name Nom du projet
   */
  public EclipseProjectWeb(String rootPath, String name)
  {
    super(rootPath, name);
  }
  /**
   * Constructeur
   * @param rootPath Racine du projet
   * @param name Nom du projet
   */
  public EclipseProjectWeb(File rootPath, String name)
  {
    super(rootPath, name);
  }
  /**
   * Constructeur
   * @param path Emplacement du projet
   */
  public EclipseProjectWeb(File path)
  {
    super(path);
  }


  /**
   * Cette méthode permet de charger l'entrée de configuration donnée
   * @param loadedMap {@inheritDoc}
   * @param node {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.EclipseProject#loadNode(com.bid4win.commons.core.collection.Bid4WinMap, org.w3c.dom.Node)
   */
  @Override
  protected void loadNode(Bid4WinMap<String, EclipseProject> loadedMap, Node node)
            throws CommunicationException, ModelArgumentException, UserException
  {
    // On se trouve sur une entrée de déploiement
    if(node.getAttributes().getNamedItem("kind") != null &&
       node.getAttributes().getNamedItem("kind").getNodeValue().equals("output"))
    {
      this.addOutput(new Output(this,
                                node.getAttributes().getNamedItem("path").getNodeValue()));
    }
    else
    {
      super.loadNode(loadedMap, node);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.EclipseProject#getXmlProjectProjects()
   */
  @Override
  protected StringBuffer getXmlProjectProjects()
  {
    StringBuffer buffer = super.getXmlProjectProjects();
    for(Project<?> subProject : this.getRecursiveSubProjectList())
    {
      UtilString.addParagraph(buffer, "<project>" + subProject.getName() + "</project>", 1);
    }
    return UtilString.trimEmptyLine(buffer);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.EclipseProject#getXmlProjectBuildSpec()
   */
  @Override
  protected StringBuffer getXmlProjectBuildSpec()
  {
    StringBuffer buffer = super.getXmlProjectBuildSpec();

    UtilString.addParagraph(buffer, "<buildCommand>");
    UtilString.addParagraph(buffer, "<name>org.eclipse.wst.jsdt.core.javascriptValidator</name>", 1);
    UtilString.addParagraph(buffer, "<arguments>", 1);
    UtilString.addParagraph(buffer, "</arguments>", 1);
    UtilString.addParagraph(buffer, "</buildCommand>");

    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.EclipseProject#getXmlProjectNatures()
   */
  @Override
  protected StringBuffer getXmlProjectNatures()
  {
    StringBuffer buffer = super.getXmlProjectNatures();

    UtilString.addParagraph(buffer, "<nature>org.eclipse.wst.jsdt.core.jsNature</nature>");

    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.EclipseProject#getXmlComponentModule()
   */
  @Override
  protected StringBuffer getXmlComponentModule() throws ModelArgumentException
  {
    StringBuffer ressources = new StringBuffer("<property name=\"context-root\" value=\"" + this.getName() + "\"/>");
    for(Output output : this.getOutputList())
    {
      UtilString.addParagraph(ressources, output.toXmlComponent(""));
    }
    UtilString.addParagraph(ressources, new Source(this, "WebContent").toXmlComponent("/"));

    UtilString.addParagraph(ressources, super.getXmlComponentModule());

    for(Project<?> project : this.getRecursiveSubProjectList())
    {
      UtilString.addParagraph(ressources, project.toXmlComponent(this.getLibDeployPath()));
    }
    for(Librairy librairy : this.getRecursiveLibrairyList())
    {
      UtilString.addParagraph(ressources, librairy.toXmlComponent(this.getLibDeployPath()));
    }
    return ressources;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.EclipseProject#getSourceDeployPath()
   */
  @Override
  protected String getSourceDeployPath()
  {
    return "/WEB-INF/classes";
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected String getLibDeployPath()
  {
    return "/WEB-INF/lib";
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public String getLibPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getPath(), "WebContent", this.getLibDeployPath());
  }

  /**
   * Getter du set des entrées de déploiement du projet WEB
   * @return Le set des entrées de déploiement du projet WEB
   */
  public Bid4WinSet<Output> getOutputSet()
  {
    return this.outputSet.clone();
  }
  /**
   * Getter de la liste ordonnée des entrées de déploiement du projet WEB
   * @return La liste ordonnée des entrées de déploiement du projet WEB
   */
  public Bid4WinList<Output> getOutputList()
  {
    return new Bid4WinList<Output>(this.getOutputSet()).sort();
  }
  /**
   * Permet d'ajouter une entrée de déploiement du projet WEB
   * @param toBeAdded Entrée de déploiement du projet WEB à ajouter
   */
  protected void addOutput(Output toBeAdded)
  {
    this.outputSet.add(toBeAdded);
  }
}
