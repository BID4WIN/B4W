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
 * Classe d�finissant un projet WEB avec ses d�pendances<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class EclipseProjectWeb extends EclipseProject
{
  /** Set des entr�es de d�ploiement du projet WEB courant */
  private Bid4WinSet<Output> outputSet = new Bid4WinSet<Output>();

  /**
   * Constructeur par copie
   * @param project Projet WEB � partir duquel cr�er le nouveau projet
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
   * Cette m�thode permet de charger l'entr�e de configuration donn�e
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
    // On se trouve sur une entr�e de d�ploiement
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
   * Getter du set des entr�es de d�ploiement du projet WEB
   * @return Le set des entr�es de d�ploiement du projet WEB
   */
  public Bid4WinSet<Output> getOutputSet()
  {
    return this.outputSet.clone();
  }
  /**
   * Getter de la liste ordonn�e des entr�es de d�ploiement du projet WEB
   * @return La liste ordonn�e des entr�es de d�ploiement du projet WEB
   */
  public Bid4WinList<Output> getOutputList()
  {
    return new Bid4WinList<Output>(this.getOutputSet()).sort();
  }
  /**
   * Permet d'ajouter une entr�e de d�ploiement du projet WEB
   * @param toBeAdded Entr�e de d�ploiement du projet WEB � ajouter
   */
  protected void addOutput(Output toBeAdded)
  {
    this.outputSet.add(toBeAdded);
  }
}
