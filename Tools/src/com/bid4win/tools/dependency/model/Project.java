package com.bid4win.tools.dependency.model;

import java.io.File;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.renderer.Bid4WinEntityCollectionRenderer;

/**
 * Classe définissant un projet avec ses dépendances<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Project<CLASS extends Project<CLASS>>
       extends Reference<CLASS>
{
  /** Défini la relation existant avec les sous-projets */
  public final static Bid4WinRelation RELATION = new Bid4WinRelation("SUB_PROJECT",
                                                                     Type.SET);
  /** Défini le noeud de relation existant avec les sous-projets */
  public final static Bid4WinRelationNode NODE = new Bid4WinRelationNode(Project.RELATION);
  static
  {
    Project.NODE.addNode(Project.NODE);
  }

  /** Set des sources dont dépend le projet courant */
  private Bid4WinSet<Source> sourceSet = new Bid4WinSet<Source>();
  /** Set des projets dont dépend le projet courant */
  private Bid4WinSet<CLASS> subProjectSet = new Bid4WinSet<CLASS>();
  /** Set des librairies dont dépend le projet courant */
  private Bid4WinSet<Librairy> librairySet = new Bid4WinSet<Librairy>();

  /**
   * Constructeur par copie
   * @param project Projet à partir duquel créer le nouveau projet
   * @throws UserException Si la racine du projet en argument est invalide
   */
  public Project(Project<?> project) throws UserException
  {
    super(project);
  }
  /**
   * Constructeur
   * @param rootPath Racine du projet
   * @param name Nom du projet
   */
  public Project(String rootPath, String name)
  {
    super(rootPath, name);
  }
  /**
   * Constructeur
   * @param rootPath Racine du projet
   * @param name Nom du projet
   */
  public Project(File rootPath, String name)
  {
    super(rootPath, name);
  }
  /**
   * Constructeur
   * @param path Emplacement du projet
   */
  public Project(File path)
  {
    super(path);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.Reference#getXmlComponentHandleModule()
   */
  @Override
  public StringBuffer getXmlComponentHandleModule()
  {
    return new StringBuffer("/resource/").append(this.getName()).append("/").append(this.getName());
  }
  /**
   * Cette méthode permet de récupérer le nom de l'archive (ie le jar) correspondant
   * au projet courant
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.Reference#getArchiveName()
   */
  @Override
  public StringBuffer getArchiveName()
  {
    return super.getArchiveName().append(".jar");
  }

  /**
   * Cette méthode permet de récupérer le fichier de classpath du projet
   * @return Le fichier de classpath du projet
   * @throws UserException Si le classpath du projet est invalide
   */
  public File getClasspathFile() throws UserException
  {
    return new File(this.getClasspath());
  }
  /**
   * Cette méthode permet de récupérer le classpath du projet
   * @return Le classpath du projet
   * @throws UserException Si le classpath du projet est invalide
   */
  public String getClasspath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE, this.getPath(), ".classpath");
  }

  /**
   * Getter du set des sources dont dépend directement le projet courant
   * @return Le set des sources dont dépend directement le projet courant
   */
  public Bid4WinSet<Source> getSourceSet()
  {
    return this.sourceSet.clone();
  }
  /**
   * Getter de la liste ordonnée des sources dont dépend directement le projet
   * courant
   * @return La liste ordonnée des sources dont dépend directement le projet courant
   */
  public Bid4WinList<Source> getSourceList()
  {
    return new Bid4WinList<Source>(this.getSourceSet()).sort();
  }
  /**
   * Permet d'ajouter une source dont dépend directement le projet courant
   * @param toBeAdded Source à ajouter dont dépend directement le projet courant
   */
  protected void addSource(Source toBeAdded)
  {
    this.sourceSet.add(toBeAdded);
  }
  /**
   * Getter du set des sous-projets dont dépend directement le projet courant
   * @return Le set des sous-projets dont dépend directement le projet courant
   */
  public Bid4WinSet<CLASS> getSubProjectSet()
  {
    return this.subProjectSet.clone();
  }
  /**
   * Getter du set de tous les sous-projets dont dépend le projet courant
   * @return Le set de tous les sous-projets dont dépend le projet courant
   */
  public Bid4WinSet<CLASS> getRecursiveSubProjectSet()
  {
    Bid4WinSet<CLASS> result = this.getSubProjectSet();
    for(CLASS project : this.subProjectSet)
    {
      result.addAll(project.getRecursiveSubProjectSet());
    }
    return result;
  }
  /**
   * Getter de la liste ordonnée de tous les sous-projets dont dépend le projet
   * courant
   * @return La liste ordonnée de tous les sous-projets dont dépend le projet
   * courant
   */
  public Bid4WinList<CLASS> getRecursiveSubProjectList()
  {
    return new Bid4WinList<CLASS>(this.getRecursiveSubProjectSet()).sort();
  }
  /**
   * Permet d'ajouter un projet dont dépend directement le projet courant
   * @param toBeAdded Projet à ajouter dont dépend directement le projet courant
   */
  protected void addSubProject(CLASS toBeAdded)
  {
    this.subProjectSet.add(toBeAdded);
  }
  /**
   * Getter du set des librairies dont dépend directement le projet courant
   * @return Le set des librairies dont dépend directement le projet courant
   */
  public Bid4WinSet<Librairy> getLibrairySet()
  {
    return this.librairySet.clone();
  }
  /**
   * Getter du set de toutes les librairies dont dépend le projet courant
   * @return Le set de toutes les librairies dont dépend le projet courant
   */
  public Bid4WinSet<Librairy> getRecursiveLibrairySet()
  {
    Bid4WinSet<Librairy> result = this.getLibrairySet();
    for(CLASS project : this.subProjectSet)
    {
      result.addAll(project.getRecursiveLibrairySet());
    }
    return result;
  }
  /**
   * Getter de la liste ordonnée de toutes les librairies dont dépend le projet
   * courant
   * @return La liste ordonnée de toutes les librairies dont dépend le projet
   * courant
   */
  public Bid4WinList<Librairy> getRecursiveLibrairyList()
  {
    return new Bid4WinList<Librairy>(this.getRecursiveLibrairySet()).sort();
  }
  /**
   * Permet d'ajouter une librairie dont dépend directement le projet courant
   * @param toBeAdded Librairie à ajouter dont dépend directement le projet courant
   */
  protected void addLibrairy(Librairy toBeAdded)
  {
    this.librairySet.add(toBeAdded);
  }

  /**
   * Permet de récupérer le set de sous-projets du projet s'il correspondant à la
   * relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type set à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSet(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinSet<? extends Bid4WinEntity<?, ?>> getRelationSet(Bid4WinRelation relation)
  {
    if(relation.equals(Project.RELATION))
    {
      return this.getSubProjectSet();
    }
    return super.getRelationSet(relation);
  }
  /**
   * Ajoute à la liste des noeuds de relations du projet le lien vers son set de
   * sous-projets
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> list = super.getFullRelationNodeList();
    list.add(Project.NODE);
    return list;
  }

  /**
   * Permet d'effectuer le rendu simple du projet courant sans prise en compte de
   * ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.Reference#renderRelationNone()
   */
  @Override
  public StringBuffer renderRelationNone()
  {
    StringBuffer buffer = UtilString.addParagraph(super.renderRelationNone(),
                                                  "LIBRAIRY_SET=");
    return UtilString.addLine(buffer,
                              Bid4WinEntityCollectionRenderer.getInstanceEntityCollection().render(
                                  this.getLibrairySet(), new Bid4WinList<Bid4WinRelationNode>()));
  }
}
