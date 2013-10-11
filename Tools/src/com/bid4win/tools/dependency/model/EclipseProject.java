package com.bid4win.tools.dependency.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Classe définissant un projet eclipse avec ses dépendances<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class EclipseProject extends Project<EclipseProject>
{
  /**
   * Constructeur par copie
   * @param project Projet à partir duquel créer le nouveau projet
   * @throws UserException Si la racine du projet en argument est invalide
   */
  public EclipseProject(EclipseProject project) throws UserException
  {
    super(project);
  }
  /**
   * Constructeur
   * @param rootPath Racine du projet
   * @param name Nom du projet
   */
  public EclipseProject(String rootPath, String name)
  {
    super(rootPath, name);
  }
  /**
   * Constructeur
   * @param rootPath Racine du projet
   * @param name Nom du projet
   */
  public EclipseProject(File rootPath, String name)
  {
    super(rootPath, name);
  }
  /**
   * Constructeur
   * @param path Emplacement du projet
   */
  public EclipseProject(File path)
  {
    super(path);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public StringBuffer toXmlProject()
  {
    StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    UtilString.addParagraph(buffer, "<projectDescription>");
    UtilString.addParagraph(buffer, "<name>" + this.getName() + "</name>", 1);
    UtilString.addParagraph(buffer, "<comment></comment>", 1);

    StringBuffer projects = new StringBuffer("<projects>");
    UtilString.addParagraph(projects, this.getXmlProjectProjects(), 1);
    UtilString.trimEmptyLine(projects);
    UtilString.addParagraph(projects, "</projects>");
    UtilString.addParagraph(buffer, projects, 1);

    StringBuffer buildSpec = new StringBuffer("<buildSpec>");
    UtilString.addParagraph(buildSpec, this.getXmlProjectBuildSpec(), 1);
    UtilString.addParagraph(buildSpec, "</buildSpec>");
    UtilString.addParagraph(buffer, buildSpec, 1);

    StringBuffer natures = new StringBuffer("<natures>");
    UtilString.addParagraph(natures, this.getXmlProjectNatures(), 1);
    UtilString.addParagraph(natures, "</natures>");
    UtilString.addParagraph(buffer, natures, 1);

    return UtilString.addParagraph(buffer, "</projectDescription>");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected StringBuffer getXmlProjectProjects()
  {
    return new StringBuffer();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected StringBuffer getXmlProjectBuildSpec()
  {
    StringBuffer buffer = new StringBuffer("<buildCommand>");
    UtilString.addParagraph(buffer, "<name>org.eclipse.jdt.core.javabuilder</name>", 1);
    UtilString.addParagraph(buffer, "<arguments>", 1);
    UtilString.addParagraph(buffer, "</arguments>", 1);
    UtilString.addParagraph(buffer, "</buildCommand>");

    UtilString.addParagraph(buffer, "<buildCommand>");
    UtilString.addParagraph(buffer, "<name>org.eclipse.wst.common.project.facet.core.builder</name>", 1);
    UtilString.addParagraph(buffer, "<arguments>", 1);
    UtilString.addParagraph(buffer, "</arguments>", 1);
    UtilString.addParagraph(buffer, "</buildCommand>");

    UtilString.addParagraph(buffer, "<buildCommand>");
    UtilString.addParagraph(buffer, "<name>org.eclipse.wst.validation.validationbuilder</name>", 1);
    UtilString.addParagraph(buffer, "<arguments>", 1);
    UtilString.addParagraph(buffer, "</arguments>", 1);
    UtilString.addParagraph(buffer, "</buildCommand>");
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected StringBuffer getXmlProjectNatures()
  {
    StringBuffer buffer = new StringBuffer("<nature>org.eclipse.jdt.core.javanature</nature>");
    UtilString.addParagraph(buffer, "<nature>org.eclipse.jem.workbench.JavaEMFNature</nature>");
    UtilString.addParagraph(buffer, "<nature>org.eclipse.wst.common.modulecore.ModuleCoreNature</nature>");
    UtilString.addParagraph(buffer, "<nature>org.eclipse.wst.common.project.facet.core.nature</nature>");
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public StringBuffer toXmlComponent() throws ModelArgumentException
  {
    StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    UtilString.addParagraph(buffer, "<project-modules id=\"moduleCoreId\" project-version=\"1.5.0\">");
    StringBuffer module = new StringBuffer("<wb-module deploy-name=\"" + this.getName() + "\">");
    UtilString.addParagraph(module, this.getXmlComponentModule(), 1);
    UtilString.addParagraph(module, "</wb-module>");
    UtilString.addParagraph(buffer, module, 1);
    return UtilString.addParagraph(buffer, "</project-modules>");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  @SuppressWarnings("unused")
  protected StringBuffer getXmlComponentModule() throws ModelArgumentException
  {
    StringBuffer module = new StringBuffer();
    for(Source source : this.getSourceList())
    {
      UtilString.addParagraph(module, source.toXmlComponent(this.getSourceDeployPath()));
    }
    return UtilString.trimEmptyLine(module);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public StringBuffer toXmlFacet()
  {
    StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    UtilString.addParagraph(buffer, "<faceted-project>");
    UtilString.addParagraph(buffer, "<fixed facet=\"jst.java\"/>", 1);
    UtilString.addParagraph(buffer, "<fixed facet=\"jst.utility\"/>", 1);
    UtilString.addParagraph(buffer, "<installed facet=\"jst.java\" version=\"6.0\"/>", 1);
    UtilString.addParagraph(buffer, "<installed facet=\"jst.utility\" version=\"1.0\"/>", 1);
    UtilString.addParagraph(buffer, "</faceted-project>");
    return buffer;

  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public StringBuffer toPropertiesPrefs()
  {
    StringBuffer buffer = new StringBuffer("eclipse.preferences.version=1");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode=enabled");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.6");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.codegen.unusedLocal=preserve");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.compliance=1.6");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.debug.lineNumber=generate");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.debug.localVariable=generate");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.debug.sourceFile=generate");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.problem.assertIdentifier=error");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.problem.enumIdentifier=error");
    UtilString.addParagraph(buffer, "org.eclipse.jdt.core.compiler.source=1.6");
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected String getSourceDeployPath()
  {
    return "/";
  }

  /**
   * Cette méthode permet de charger le projet courant
   * @throws CommunicationException Si un problème intervient lors de l'accés aux
   * différents fichiers de configuration
   * @throws ModelArgumentException Si le chargement du projet échoue
   * @throws UserException Si le classpath du projet courant est invalide
   */
  public void loadProject() throws CommunicationException, ModelArgumentException, UserException
  {
    this.loadProject(new Bid4WinMap<String, EclipseProject>());
  }
  /**
   * Cette méthode permet de charger le projet courant
   * @param loadedMap Map des projets déjà chargés
   * @throws CommunicationException Si un problème intervient lors de l'accés aux
   * différents fichiers de configuration
   * @throws ModelArgumentException Si le chargement du projet échoue
   * @throws UserException Si le classpath du projet courant est invalide
   */
  public void loadProject(Bid4WinMap<String, EclipseProject> loadedMap)
         throws CommunicationException, ModelArgumentException, UserException
  {
    NodeList nodeList = this.getNodeList("//classpath/classpathentry", this.getClasspath());
    for(int i = 0 ; i < nodeList.getLength() ; i++)
    {
      this.loadNode(loadedMap, nodeList.item(i));
    }
  }
  /**
   * Cette méthode permet de charger l'entrée de configuration donnée
   * @param loadedMap Map des projets déjà chargés
   * @param node Entrée de configuration à charger
   * @throws CommunicationException Si un problème intervient lors de l'accés aux
   * différents fichiers de configuration
   * @throws ModelArgumentException Si le chargement du projet échoue
   * @throws UserException Si l'un des classpath des sous-projet courant est invalide
   */
  protected void loadNode(Bid4WinMap<String, EclipseProject> loadedMap, Node node)
            throws CommunicationException, ModelArgumentException, UserException
  {
    // On se trouve sur une entrée de source
    if(node.getAttributes().getNamedItem("combineaccessrules") == null &&
       node.getAttributes().getNamedItem("kind") != null &&
       node.getAttributes().getNamedItem("kind").getNodeValue().equals("src"))
    {
      String source = node.getAttributes().getNamedItem("path").getNodeValue();
      // on ne prend pas en compte les sources dites de test
      if(!source.startsWith("test"))
      {
        this.addSource(new Source(this, source));
      }
    }
    // On se trouve sur une entrée à exporter
    else if(node.getAttributes().getNamedItem("exported") != null &&
            node.getAttributes().getNamedItem("exported").getNodeValue().equals("true"))
    {
      // On se trouve sur une entrée de sous-projet
      if(node.getAttributes().getNamedItem("kind") != null)
      {
        if(node.getAttributes().getNamedItem("kind").getNodeValue().equals("src"))
        {
          String path = node.getAttributes().getNamedItem("path").getNodeValue();
          String name = path.substring(1);
          EclipseProject project = loadedMap.get(name);
          // Le projet n'a pas déjà été chargé
          if(project == null)
          {
            project = new EclipseProject(this.getRootPath(), name);
            loadedMap.put(name, project);
            project.loadProject(loadedMap);
          }
          this.addSubProject(project);
        }
        else if(node.getAttributes().getNamedItem("kind").getNodeValue().equals("lib"))
        {
          String path = node.getAttributes().getNamedItem("path").getNodeValue();
          String name = path.substring(path.lastIndexOf("/") + 1);
          path = path.substring(1, path.lastIndexOf("/"));
          this.addLibrairy(new Librairy(this.getRootPath(), path, name));
        }
      }
    }
  }

  /**
   * Retourne une liste de noeuds a partir d'une requête au sens <b>XPath</b>.
   * @param xPathRequest la requête XPath en entrée
   * @param filename Nom complet du fichier dans lequel chercher la liste de noeuds
   * @return La liste de noeuds correspondant au résultat de cette requête dans
   * le document
   * @throws CommunicationException Si un problème intervient lors de l'accés au
   * fichier spécifié
   */
  private NodeList getNodeList(String xPathRequest, String filename) throws CommunicationException
  {
    // Ouvre le flux
    InputStream inputStream;
    try
    {
      inputStream = new FileInputStream(filename);
    }
    catch(FileNotFoundException ex)
    {
      throw new CommunicationException(ex);
    }
    try
    {
      return this.getNodeList(xPathRequest, inputStream);
    }
    finally
    {
      try
      {
        inputStream.close();
      }
      catch(IOException ex)
      {
        throw new CommunicationException(ex);
      }
    }
  }
  /**
   * Retourne une liste de noeuds a partir d'une requête au sens <b>XPath</b>.
   * @param xPathRequest la requête XPath en entrée
   * @param inputStream Flux dans lequel chercher la liste de noeuds
   * @return La liste de noeuds correspondant au résultat de cette requête dans
   * le document
   * @throws CommunicationException Si un problème intervient lors de l'accés au
   * flux en argument
   */
  private NodeList getNodeList(String xPathRequest, InputStream inputStream)
          throws CommunicationException
  {
    try
    {
      // Construit la requête XPath
      XPath xpath = XPathFactory.newInstance().newXPath();
      return this.getNodeList(xpath.compile(xPathRequest), inputStream);
    }
    catch(XPathExpressionException ex)
    {
      throw new CommunicationException(ex);
    }
  }
  /**
   * Retourne une liste de noeuds a partir d'une requête au sens <b>XPath</b>.
   * @param xPathRequest la requête XPath en entrée
   * @param inputStream Flux dans lequel chercher la liste de noeuds
   * @return La liste de noeuds correspondant au résultat de cette requête dans
   * le document
   * @throws CommunicationException Si un problème intervient lors de l'accés au
   * flux en argument
   */
  private NodeList getNodeList(XPathExpression xPathRequest, InputStream inputStream)
          throws CommunicationException
  {
    try
    {
      // Crée l'objet qui va permettre de construit le document XML à partir du flux
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setIgnoringComments(true);
      factory.setNamespaceAware(true);
      factory.setValidating(false);
      DocumentBuilder builder = factory.newDocumentBuilder();
      // Récupère le document XML
      Document doc = builder.parse(inputStream);
      // Effectue la requête et retourne le résultat
      return (NodeList)xPathRequest.evaluate(doc, XPathConstants.NODESET);
    }
    catch(ParserConfigurationException ex)
    {
      throw new CommunicationException(ex);
    }
    catch(IOException ex)
    {
      throw new CommunicationException(ex);
    }
    catch(SAXException ex)
    {
      throw new CommunicationException(ex);
    }
    catch(XPathExpressionException ex)
    {
      throw new CommunicationException(ex);
    }
  }


  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public String getComponentPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getPath(), ".settings", "org.eclipse.wst.common.component");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public String getFacetPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getPath(), ".settings", "org.eclipse.wst.common.project.facet.core.xml");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public String getPrefsPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getPath(), ".settings", "org.eclipse.jdt.core.prefs");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public String getProjectPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getPath(), ".project");
  }
}
